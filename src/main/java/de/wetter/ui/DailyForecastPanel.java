package de.wetter.ui;

import de.wetter.model.DailyWeather;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

/**
 * Panel zur Anzeige der 7-Tage-Vorhersage.
 */
public class DailyForecastPanel extends VBox {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.");
    private static final Locale GERMAN = Locale.GERMAN;

    public DailyForecastPanel(List<DailyWeather> dailyData) {
        super(15);
        setPadding(new Insets(20));
        setAlignment(Pos.CENTER);
        getStyleClass().add("daily-forecast-panel");
        setMaxWidth(900);

        Label titleLabel = new Label("7-Tage-Vorhersage");
        titleLabel.getStyleClass().add("section-title");

        HBox daysContainer = new HBox(10);
        daysContainer.setAlignment(Pos.CENTER);

        for (DailyWeather day : dailyData) {
            VBox dayCard = createDayCard(day);
            HBox.setHgrow(dayCard, Priority.ALWAYS);
            daysContainer.getChildren().add(dayCard);
        }

        getChildren().addAll(titleLabel, daysContainer);
    }

    private VBox createDayCard(DailyWeather day) {
        VBox card = new VBox(8);
        card.setAlignment(Pos.CENTER);
        card.setPadding(new Insets(15, 10, 15, 10));
        card.getStyleClass().add("day-card");
        card.setMinWidth(100);
        card.setMaxWidth(130);

        // Wochentag
        String dayName = day.getDate().getDayOfWeek().getDisplayName(TextStyle.SHORT, GERMAN);
        Label dayLabel = new Label(dayName);
        dayLabel.getStyleClass().add("day-name");

        // Datum
        Label dateLabel = new Label(day.getDate().format(DATE_FORMATTER));
        dateLabel.getStyleClass().add("day-date");

        // Wetter-Symbol
        Label weatherEmoji = new Label(day.getWeatherEmoji());
        weatherEmoji.getStyleClass().add("day-emoji");

        // Wetter-Beschreibung
        Label weatherDesc = new Label(day.getWeatherDescription());
        weatherDesc.getStyleClass().add("day-weather-desc");
        weatherDesc.setWrapText(true);

        // Temperaturen
        HBox tempBox = new HBox(5);
        tempBox.setAlignment(Pos.CENTER);

        Label maxTemp = new Label(String.format("%.0f\u00B0", day.getTemperatureMax()));
        maxTemp.getStyleClass().add("temp-max");

        Label separator = new Label("/");
        separator.getStyleClass().add("temp-separator");

        Label minTemp = new Label(String.format("%.0f\u00B0", day.getTemperatureMin()));
        minTemp.getStyleClass().add("temp-min");

        tempBox.getChildren().addAll(maxTemp, separator, minTemp);

        // Niederschlag
        Label precipLabel = new Label(String.format("%.1f mm", day.getPrecipitationSum()));
        precipLabel.getStyleClass().add("day-precip");

        // Wind
        Label windLabel = new Label(String.format("%.0f km/h", day.getWindSpeedMax()));
        windLabel.getStyleClass().add("day-wind");

        card.getChildren().addAll(dayLabel, dateLabel, weatherEmoji, weatherDesc, tempBox, precipLabel, windLabel);
        return card;
    }
}
