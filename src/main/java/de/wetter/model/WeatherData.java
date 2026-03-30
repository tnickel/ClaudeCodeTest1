package de.wetter.model;

import java.util.List;

/**
 * Modell für Wetterdaten einer Stadt.
 */
public class WeatherData {
    private String cityName;
    private double latitude;
    private double longitude;
    private CurrentWeather current;
    private List<HourlyWeather> hourlyForecast;
    private List<DailyWeather> dailyForecast;

    public WeatherData() {
    }

    public WeatherData(String cityName, double latitude, double longitude) {
        this.cityName = cityName;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public CurrentWeather getCurrent() {
        return current;
    }

    public void setCurrent(CurrentWeather current) {
        this.current = current;
    }

    public List<HourlyWeather> getHourlyForecast() {
        return hourlyForecast;
    }

    public void setHourlyForecast(List<HourlyWeather> hourlyForecast) {
        this.hourlyForecast = hourlyForecast;
    }

    public List<DailyWeather> getDailyForecast() {
        return dailyForecast;
    }

    public void setDailyForecast(List<DailyWeather> dailyForecast) {
        this.dailyForecast = dailyForecast;
    }
}
