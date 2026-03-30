package de.wetter.ui;

import de.wetter.model.HourlyWeather;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Chart-Komponente für die stündliche Wettervorhersage.
 */
public class HourlyForecastChart extends VBox {

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd.MM HH:mm");

    public HourlyForecastChart(List<HourlyWeather> hourlyData) {
        super(15);
        setPadding(new Insets(20));
        setAlignment(Pos.CENTER);
        getStyleClass().add("hourly-forecast-panel");
        setMaxWidth(900);

        Label titleLabel = new Label("24-Stunden-Vorhersage");
        titleLabel.getStyleClass().add("section-title");

        // Temperatur-Chart
        LineChart<String, Number> tempChart = createTemperatureChart(hourlyData);

        // Niederschlags-Chart
        BarChart<String, Number> precipChart = createPrecipitationChart(hourlyData);

        getChildren().addAll(titleLabel, tempChart, precipChart);
    }

    private LineChart<String, Number> createTemperatureChart(List<HourlyWeather> hourlyData) {
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Uhrzeit");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Temperatur (\u00B0C)");

        LineChart<String, Number> chart = new LineChart<>(xAxis, yAxis);
        chart.setTitle("Temperaturverlauf");
        chart.setLegendVisible(false);
        chart.setPrefHeight(250);
        chart.getStyleClass().add("temperature-chart");

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Temperatur");

        // Nur die nächsten 24 Stunden anzeigen
        int count = Math.min(24, hourlyData.size());
        for (int i = 0; i < count; i++) {
            HourlyWeather hw = hourlyData.get(i);
            String timeLabel = hw.getTime().format(TIME_FORMATTER);
            series.getData().add(new XYChart.Data<>(timeLabel, hw.getTemperature()));
        }

        chart.getData().add(series);
        return chart;
    }

    private BarChart<String, Number> createPrecipitationChart(List<HourlyWeather> hourlyData) {
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Uhrzeit");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Niederschlag (mm)");

        BarChart<String, Number> chart = new BarChart<>(xAxis, yAxis);
        chart.setTitle("Niederschlagswahrscheinlichkeit");
        chart.setLegendVisible(false);
        chart.setPrefHeight(200);
        chart.getStyleClass().add("precipitation-chart");
        chart.setCategoryGap(2);
        chart.setBarGap(0);

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Niederschlag");

        // Nur die nächsten 24 Stunden anzeigen
        int count = Math.min(24, hourlyData.size());
        for (int i = 0; i < count; i++) {
            HourlyWeather hw = hourlyData.get(i);
            String timeLabel = hw.getTime().format(TIME_FORMATTER);
            series.getData().add(new XYChart.Data<>(timeLabel, hw.getPrecipitation()));
        }

        chart.getData().add(series);
        return chart;
    }
}
