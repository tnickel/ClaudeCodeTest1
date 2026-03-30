package de.wetter.model;

import java.time.LocalDateTime;

/**
 * Modell für stündliche Wetterdaten.
 */
public class HourlyWeather {
    private LocalDateTime time;
    private double temperature;
    private int humidity;
    private double precipitation;
    private int weatherCode;
    private double windSpeed;
    private int cloudCover;

    public HourlyWeather() {
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public double getPrecipitation() {
        return precipitation;
    }

    public void setPrecipitation(double precipitation) {
        this.precipitation = precipitation;
    }

    public int getWeatherCode() {
        return weatherCode;
    }

    public void setWeatherCode(int weatherCode) {
        this.weatherCode = weatherCode;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public int getCloudCover() {
        return cloudCover;
    }

    public void setCloudCover(int cloudCover) {
        this.cloudCover = cloudCover;
    }

    public String getWeatherDescription() {
        return WeatherCodeUtil.getDescription(weatherCode);
    }

    public String getWeatherEmoji() {
        return WeatherCodeUtil.getEmoji(weatherCode);
    }
}
