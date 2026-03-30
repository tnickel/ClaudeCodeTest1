package de.wetter.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import de.wetter.model.*;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;

/**
 * Service zum Abrufen von Wetterdaten von der Open-Meteo API.
 * Open-Meteo ist eine kostenlose Wetter-API ohne API-Schlüssel.
 */
public class WeatherService {

    private static final String BASE_URL = "https://api.open-meteo.com/v1/forecast";
    private final HttpClient httpClient;

    public WeatherService() {
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();
    }

    /**
     * Ruft Wetterdaten für eine Stadt asynchron ab.
     */
    public CompletableFuture<WeatherData> getWeatherAsync(City city) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return getWeather(city);
            } catch (Exception e) {
                throw new RuntimeException("Fehler beim Abrufen der Wetterdaten: " + e.getMessage(), e);
            }
        });
    }

    /**
     * Ruft Wetterdaten für eine Stadt synchron ab.
     */
    public WeatherData getWeather(City city) throws Exception {
        String url = buildUrl(city.getLatitude(), city.getLongitude());

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(Duration.ofSeconds(30))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new RuntimeException("API-Fehler: HTTP " + response.statusCode());
        }

        return parseResponse(response.body(), city);
    }

    /**
     * Ruft Wetterdaten für Koordinaten ab.
     */
    public WeatherData getWeather(double latitude, double longitude, String cityName) throws Exception {
        City city = new City(cityName, latitude, longitude, "");
        return getWeather(city);
    }

    private String buildUrl(double latitude, double longitude) {
        return String.format(Locale.US,
                "%s?latitude=%.4f&longitude=%.4f" +
                        "&current_weather=true" +
                        "&hourly=temperature_2m,relativehumidity_2m,precipitation,weathercode,windspeed_10m,cloudcover" +
                        "&daily=weathercode,temperature_2m_max,temperature_2m_min,precipitation_sum,windspeed_10m_max,sunrise,sunset" +
                        "&timezone=Europe%%2FBerlin" +
                        "&forecast_days=7",
                BASE_URL, latitude, longitude
        );
    }

    private WeatherData parseResponse(String json, City city) {
        JsonObject root = JsonParser.parseString(json).getAsJsonObject();

        WeatherData weatherData = new WeatherData(
                city.getName(),
                root.get("latitude").getAsDouble(),
                root.get("longitude").getAsDouble()
        );

        // Parse current weather
        if (root.has("current_weather")) {
            weatherData.setCurrent(parseCurrentWeather(root.getAsJsonObject("current_weather")));
        }

        // Parse hourly forecast
        if (root.has("hourly")) {
            weatherData.setHourlyForecast(parseHourlyForecast(root.getAsJsonObject("hourly")));
        }

        // Parse daily forecast
        if (root.has("daily")) {
            weatherData.setDailyForecast(parseDailyForecast(root.getAsJsonObject("daily")));
        }

        return weatherData;
    }

    private CurrentWeather parseCurrentWeather(JsonObject current) {
        CurrentWeather weather = new CurrentWeather();

        if (current.has("time")) {
            String timeStr = current.get("time").getAsString();
            // Format kann "2024-01-15T14:00" sein
            weather.setTime(LocalDateTime.parse(timeStr));
        }
        if (current.has("temperature")) {
            weather.setTemperature(current.get("temperature").getAsDouble());
        }
        // current_weather hat keine apparent_temperature, setze gleich wie temperature
        if (current.has("temperature")) {
            weather.setApparentTemperature(current.get("temperature").getAsDouble());
        }
        // current_weather hat keine humidity, setze Default
        weather.setHumidity(0);
        if (current.has("windspeed")) {
            weather.setWindSpeed(current.get("windspeed").getAsDouble());
        }
        if (current.has("winddirection")) {
            weather.setWindDirection(current.get("winddirection").getAsInt());
        }
        if (current.has("weathercode")) {
            weather.setWeatherCode(current.get("weathercode").getAsInt());
        }
        // current_weather hat keine precipitation, setze 0
        weather.setPrecipitation(0.0);
        // current_weather hat keine cloud_cover, setze 0
        weather.setCloudCover(0);
        // current_weather hat keine pressure, setze 0
        weather.setPressure(0.0);

        return weather;
    }

    private List<HourlyWeather> parseHourlyForecast(JsonObject hourly) {
        List<HourlyWeather> forecast = new ArrayList<>();

        JsonArray times = hourly.getAsJsonArray("time");
        JsonArray temperatures = hourly.getAsJsonArray("temperature_2m");
        JsonArray humidities = hourly.getAsJsonArray("relativehumidity_2m");
        JsonArray precipitations = hourly.getAsJsonArray("precipitation");
        JsonArray weatherCodes = hourly.getAsJsonArray("weathercode");
        JsonArray windSpeeds = hourly.getAsJsonArray("windspeed_10m");
        JsonArray cloudCovers = hourly.getAsJsonArray("cloudcover");

        // Nur die nächsten 48 Stunden
        int count = Math.min(48, times.size());
        for (int i = 0; i < count; i++) {
            HourlyWeather weather = new HourlyWeather();
            weather.setTime(LocalDateTime.parse(times.get(i).getAsString()));
            weather.setTemperature(temperatures.get(i).getAsDouble());
            weather.setHumidity(humidities.get(i).getAsInt());
            weather.setPrecipitation(precipitations.get(i).getAsDouble());
            weather.setWeatherCode(weatherCodes.get(i).getAsInt());
            weather.setWindSpeed(windSpeeds.get(i).getAsDouble());
            weather.setCloudCover(cloudCovers.get(i).getAsInt());
            forecast.add(weather);
        }

        return forecast;
    }

    private List<DailyWeather> parseDailyForecast(JsonObject daily) {
        List<DailyWeather> forecast = new ArrayList<>();

        JsonArray dates = daily.getAsJsonArray("time");
        JsonArray maxTemps = daily.getAsJsonArray("temperature_2m_max");
        JsonArray minTemps = daily.getAsJsonArray("temperature_2m_min");
        JsonArray precipitations = daily.getAsJsonArray("precipitation_sum");
        JsonArray weatherCodes = daily.getAsJsonArray("weathercode");
        JsonArray windSpeeds = daily.getAsJsonArray("windspeed_10m_max");
        JsonArray sunrises = daily.getAsJsonArray("sunrise");
        JsonArray sunsets = daily.getAsJsonArray("sunset");

        for (int i = 0; i < dates.size(); i++) {
            DailyWeather weather = new DailyWeather();
            weather.setDate(LocalDate.parse(dates.get(i).getAsString()));
            weather.setTemperatureMax(maxTemps.get(i).getAsDouble());
            weather.setTemperatureMin(minTemps.get(i).getAsDouble());
            weather.setPrecipitationSum(precipitations.get(i).getAsDouble());
            weather.setWeatherCode(weatherCodes.get(i).getAsInt());
            weather.setWindSpeedMax(windSpeeds.get(i).getAsDouble());
            weather.setSunrise(sunrises.get(i).getAsString());
            weather.setSunset(sunsets.get(i).getAsString());
            forecast.add(weather);
        }

        return forecast;
    }
}
