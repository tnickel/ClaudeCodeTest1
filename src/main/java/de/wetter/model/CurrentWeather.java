package de.wetter.model;

import java.time.LocalDateTime;

/**
 * Modell für aktuelle Wetterdaten.
 */
public class CurrentWeather {
    private LocalDateTime time;
    private double temperature;
    private double apparentTemperature;
    private int humidity;
    private double windSpeed;
    private int windDirection;
    private int weatherCode;
    private double precipitation;
    private int cloudCover;
    private double pressure;

    public CurrentWeather() {
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

    public double getApparentTemperature() {
        return apparentTemperature;
    }

    public void setApparentTemperature(double apparentTemperature) {
        this.apparentTemperature = apparentTemperature;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public int getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(int windDirection) {
        this.windDirection = windDirection;
    }

    public int getWeatherCode() {
        return weatherCode;
    }

    public void setWeatherCode(int weatherCode) {
        this.weatherCode = weatherCode;
    }

    public double getPrecipitation() {
        return precipitation;
    }

    public void setPrecipitation(double precipitation) {
        this.precipitation = precipitation;
    }

    public int getCloudCover() {
        return cloudCover;
    }

    public void setCloudCover(int cloudCover) {
        this.cloudCover = cloudCover;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    /**
     * Gibt eine Beschreibung des Wetters basierend auf dem WMO-Wettercode zurück.
     */
    public String getWeatherDescription() {
        return WeatherCodeUtil.getDescription(weatherCode);
    }

    /**
     * Gibt ein passendes Emoji für das aktuelle Wetter zurück.
     */
    public String getWeatherEmoji() {
        return WeatherCodeUtil.getEmoji(weatherCode);
    }
}
