package de.wetter.ui;

import de.wetter.model.CurrentWeather;
import de.wetter.model.WeatherData;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

/**
 * Panel zur Anzeige des aktuellen Wetters.
 */
public class CurrentWeatherPanel extends VBox {

    public CurrentWeatherPanel(WeatherData weatherData) {
        super(15);
        setPadding(new Insets(25));
        setAlignment(Pos.CENTER);
        getStyleClass().add("current-weather-panel");
        setMaxWidth(800);

        CurrentWeather current = weatherData.getCurrent();

        // Stadt und Wetter-Beschreibung
        Label cityLabel = new Label(weatherData.getCityName());
        cityLabel.getStyleClass().add("city-name");

        Label weatherDescLabel = new Label(current.getWeatherEmoji() + " " + current.getWeatherDescription());
        weatherDescLabel.getStyleClass().add("weather-description");

        // Große Temperaturanzeige
        HBox tempBox = new HBox(10);
        tempBox.setAlignment(Pos.CENTER);

        Label tempLabel = new Label(String.format("%.1f", current.getTemperature()));
        tempLabel.getStyleClass().add("temperature-main");

        Label unitLabel = new Label("\u00B0C");
        unitLabel.getStyleClass().add("temperature-unit");

        tempBox.getChildren().addAll(tempLabel, unitLabel);

        // Gefühlte Temperatur
        Label feelsLikeLabel = new Label(String.format("Gefühlt: %.1f\u00B0C", current.getApparentTemperature()));
        feelsLikeLabel.getStyleClass().add("feels-like");

        // Zusätzliche Wetterdaten in einem Grid
        GridPane detailsGrid = createDetailsGrid(current);

        getChildren().addAll(cityLabel, weatherDescLabel, tempBox, feelsLikeLabel, detailsGrid);
    }

    private GridPane createDetailsGrid(CurrentWeather current) {
        GridPane grid = new GridPane();
        grid.setHgap(30);
        grid.setVgap(15);
        grid.setAlignment(Pos.CENTER);
        grid.getStyleClass().add("details-grid");
        grid.setPadding(new Insets(20, 0, 0, 0));

        // Reihe 1
        addDetailItem(grid, 0, 0, "Luftfeuchtigkeit", current.getHumidity() + "%");
        addDetailItem(grid, 1, 0, "Wind", String.format("%.1f km/h", current.getWindSpeed()));
        addDetailItem(grid, 2, 0, "Windrichtung", getWindDirection(current.getWindDirection()));

        // Reihe 2
        addDetailItem(grid, 0, 1, "Niederschlag", String.format("%.1f mm", current.getPrecipitation()));
        addDetailItem(grid, 1, 1, "Bewölkung", current.getCloudCover() + "%");
        addDetailItem(grid, 2, 1, "Luftdruck", String.format("%.0f hPa", current.getPressure()));

        return grid;
    }

    private void addDetailItem(GridPane grid, int col, int row, String label, String value) {
        VBox item = new VBox(3);
        item.setAlignment(Pos.CENTER);
        item.getStyleClass().add("detail-item");

        Label labelNode = new Label(label);
        labelNode.getStyleClass().add("detail-label");

        Label valueNode = new Label(value);
        valueNode.getStyleClass().add("detail-value");

        item.getChildren().addAll(labelNode, valueNode);
        grid.add(item, col, row);
    }

    private String getWindDirection(int degrees) {
        String[] directions = {"N", "NO", "O", "SO", "S", "SW", "W", "NW"};
        int index = (int) Math.round(degrees / 45.0) % 8;
        return directions[index] + " (" + degrees + "\u00B0)";
    }
}
