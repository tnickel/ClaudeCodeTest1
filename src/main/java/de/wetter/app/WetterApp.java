package de.wetter.app;

import de.wetter.model.City;
import de.wetter.model.WeatherData;
import de.wetter.service.WeatherService;
import de.wetter.ui.CurrentWeatherPanel;
import de.wetter.ui.DailyForecastPanel;
import de.wetter.ui.HourlyForecastChart;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

/**
 * Hauptklasse der Wetter-Anwendung.
 */
public class WetterApp extends Application {

    private final WeatherService weatherService = new WeatherService();
    private ComboBox<City> cityComboBox;
    private Button refreshButton;
    private Label statusLabel;
    private VBox contentArea;
    private ProgressIndicator loadingIndicator;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Wetter App - Wettervorhersage");

        BorderPane root = new BorderPane();
        root.getStyleClass().add("root-pane");

        // Header mit Stadtauswahl
        root.setTop(createHeader());

        // Hauptinhalt (scrollbar)
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.getStyleClass().add("content-scroll");

        contentArea = new VBox(20);
        contentArea.setPadding(new Insets(20));
        contentArea.setAlignment(Pos.TOP_CENTER);
        scrollPane.setContent(contentArea);

        root.setCenter(scrollPane);

        // Footer mit Status
        root.setBottom(createFooter());

        Scene scene = new Scene(root, 1000, 800);
        scene.getStylesheets().add(getClass().getResource("/styles/weather.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(600);
        primaryStage.show();

        // Lade initiale Wetterdaten für Berlin
        loadWeatherData(City.getGermanCities()[0]);
    }

    private HBox createHeader() {
        HBox header = new HBox(15);
        header.setPadding(new Insets(15, 20, 15, 20));
        header.setAlignment(Pos.CENTER_LEFT);
        header.getStyleClass().add("header");

        Label titleLabel = new Label("Wetter App");
        titleLabel.getStyleClass().add("title");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label cityLabel = new Label("Stadt:");
        cityLabel.getStyleClass().add("label-text");

        cityComboBox = new ComboBox<>();
        cityComboBox.getItems().addAll(City.getGermanCities());
        cityComboBox.setValue(City.getGermanCities()[0]);
        cityComboBox.getStyleClass().add("city-combo");
        cityComboBox.setOnAction(e -> loadWeatherData(cityComboBox.getValue()));

        refreshButton = new Button("Aktualisieren");
        refreshButton.getStyleClass().add("refresh-button");
        refreshButton.setOnAction(e -> loadWeatherData(cityComboBox.getValue()));

        loadingIndicator = new ProgressIndicator();
        loadingIndicator.setMaxSize(25, 25);
        loadingIndicator.setVisible(false);

        header.getChildren().addAll(titleLabel, spacer, cityLabel, cityComboBox, refreshButton, loadingIndicator);
        return header;
    }

    private HBox createFooter() {
        HBox footer = new HBox();
        footer.setPadding(new Insets(10, 20, 10, 20));
        footer.setAlignment(Pos.CENTER_LEFT);
        footer.getStyleClass().add("footer");

        statusLabel = new Label("Bereit");
        statusLabel.getStyleClass().add("status-label");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label dataSourceLabel = new Label("Datenquelle: Open-Meteo.com");
        dataSourceLabel.getStyleClass().add("data-source");

        footer.getChildren().addAll(statusLabel, spacer, dataSourceLabel);
        return footer;
    }

    private void loadWeatherData(City city) {
        if (city == null) return;

        setLoading(true);
        statusLabel.setText("Lade Wetterdaten für " + city.getName() + "...");

        weatherService.getWeatherAsync(city)
                .thenAccept(weatherData -> Platform.runLater(() -> displayWeatherData(weatherData)))
                .exceptionally(ex -> {
                    Platform.runLater(() -> {
                        setLoading(false);
                        statusLabel.setText("Fehler: " + ex.getMessage());
                        showError("Fehler beim Laden der Wetterdaten", ex.getMessage());
                    });
                    return null;
                });
    }

    private void displayWeatherData(WeatherData data) {
        contentArea.getChildren().clear();

        // Aktuelles Wetter
        if (data.getCurrent() != null) {
            CurrentWeatherPanel currentPanel = new CurrentWeatherPanel(data);
            contentArea.getChildren().add(currentPanel);
        }

        // Stündliche Vorhersage als Chart
        if (data.getHourlyForecast() != null && !data.getHourlyForecast().isEmpty()) {
            HourlyForecastChart hourlyChart = new HourlyForecastChart(data.getHourlyForecast());
            contentArea.getChildren().add(hourlyChart);
        }

        // 7-Tage-Vorhersage
        if (data.getDailyForecast() != null && !data.getDailyForecast().isEmpty()) {
            DailyForecastPanel dailyPanel = new DailyForecastPanel(data.getDailyForecast());
            contentArea.getChildren().add(dailyPanel);
        }

        setLoading(false);
        statusLabel.setText("Zuletzt aktualisiert: " + java.time.LocalTime.now().format(
                java.time.format.DateTimeFormatter.ofPattern("HH:mm:ss")
        ) + " - " + data.getCityName());
    }

    private void setLoading(boolean loading) {
        loadingIndicator.setVisible(loading);
        refreshButton.setDisable(loading);
        cityComboBox.setDisable(loading);
    }

    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
