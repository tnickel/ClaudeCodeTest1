# CLAUDE.md - AI Assistant Guide

Dieses Dokument beschreibt die Codebase-Struktur, Entwicklungs-Workflows und wichtige Konventionen für AI-Assistenten, die an diesem Projekt arbeiten.

## Projekt-Übersicht

**Wetter App** - Eine JavaFX-Desktop-Anwendung zur Anzeige von Wetterdaten mit grafischer Darstellung.

### Technologie-Stack

| Technologie | Version | Verwendungszweck |
|------------|---------|------------------|
| Java | 21 | Programmiersprache |
| JavaFX | 21.0.2 | UI Framework |
| Maven | 3.8+ | Build & Dependency Management |
| Gson | 2.10.1 | JSON Parsing |
| JUnit 5 | 5.10.2 | Unit Testing |

### Datenquelle

- **Open-Meteo API** (https://api.open-meteo.com)
- Kostenlos, kein API-Key erforderlich
- Liefert aktuelle Wetterdaten und Vorhersagen

## Projektstruktur

```
ClaudeCodeTest1/
├── pom.xml                           # Maven Konfiguration
├── README.md                         # Projekt-Dokumentation
├── CLAUDE.md                         # Diese Datei
└── src/
    ├── main/
    │   ├── java/
    │   │   ├── module-info.java      # Java Module Definition
    │   │   └── de/wetter/
    │   │       ├── app/              # Hauptanwendung
    │   │       │   ├── WetterApp.java       # JavaFX Application Entry Point
    │   │       │   └── Launcher.java        # JAR Launcher
    │   │       ├── model/            # Datenmodelle (POJOs)
    │   │       │   ├── WeatherData.java     # Haupt-Wetterdaten Container
    │   │       │   ├── CurrentWeather.java  # Aktuelles Wetter
    │   │       │   ├── HourlyWeather.java   # Stündliche Vorhersage
    │   │       │   ├── DailyWeather.java    # Tägliche Vorhersage
    │   │       │   ├── City.java            # Stadtdaten mit Koordinaten
    │   │       │   └── WeatherCodeUtil.java # WMO Wettercode-Mapping
    │   │       ├── service/          # Business Logic & API
    │   │       │   └── WeatherService.java  # Open-Meteo API Client
    │   │       └── ui/               # JavaFX UI Komponenten
    │   │           ├── CurrentWeatherPanel.java   # Aktuelles Wetter Panel
    │   │           ├── HourlyForecastChart.java   # Stündliche Charts
    │   │           └── DailyForecastPanel.java    # 7-Tage Vorhersage
    │   └── resources/
    │       └── styles/
    │           └── weather.css       # JavaFX Stylesheet
    └── test/
        └── java/
            └── de/wetter/
                └── model/
                    ├── WeatherCodeUtilTest.java  # Tests für Wettercode-Mapping
                    └── CityTest.java             # Tests für City-Klasse
```

## Architektur

### Schichten-Architektur

```
┌─────────────────────────────────────┐
│           UI Layer (ui/)            │
│  JavaFX Panels, Charts, Styling     │
├─────────────────────────────────────┤
│         Application Layer           │
│       (app/WetterApp.java)          │
│  Koordiniert UI und Services        │
├─────────────────────────────────────┤
│        Service Layer (service/)     │
│   WeatherService - API Calls        │
├─────────────────────────────────────┤
│         Model Layer (model/)        │
│  POJOs, DTOs, Utility Classes       │
└─────────────────────────────────────┘
```

### Datenfluss

1. User wählt Stadt in `ComboBox`
2. `WetterApp` ruft `WeatherService.getWeatherAsync()` auf
3. `WeatherService` sendet HTTP Request an Open-Meteo API
4. JSON Response wird zu `WeatherData` geparst
5. UI-Komponenten werden mit neuen Daten aktualisiert

## Entwicklungs-Workflows

### Build & Run

```bash
# Kompilieren
mvn compile

# Anwendung starten
mvn javafx:run

# Tests ausführen
mvn test

# JAR erstellen
mvn clean package

# JAR ausführen
java -jar target/wetter-app-1.0.0-SNAPSHOT.jar
```

### In Eclipse importieren

1. `File` > `Import` > `Maven` > `Existing Maven Projects`
2. Projektverzeichnis auswählen
3. `Finish` klicken
4. Rechtsklick auf Projekt > `Maven` > `Update Project`

### Neue Stadt hinzufügen

In `City.java` die Methode `getGermanCities()` erweitern:

```java
new City("Stadtname", LATITUDE, LONGITUDE, "DE"),
```

### Neue Wettercode-Beschreibung hinzufügen

In `WeatherCodeUtil.java` die entsprechenden switch-cases erweitern:
- `getDescription()` - Deutsche Beschreibung
- `getEmoji()` - Unicode Emoji
- `getCssClass()` - CSS Klasse für Styling

## Code-Konventionen

### Namenskonventionen

| Element | Konvention | Beispiel |
|---------|-----------|----------|
| Klassen | PascalCase | `WeatherData`, `CurrentWeatherPanel` |
| Methoden | camelCase | `getWeather()`, `parseResponse()` |
| Variablen | camelCase | `hourlyForecast`, `cityName` |
| Konstanten | SCREAMING_SNAKE_CASE | `BASE_URL`, `TIME_FORMATTER` |
| Packages | lowercase | `de.wetter.model` |

### Code-Stil

- **Sprache**: Code in Englisch, UI-Texte und Kommentare in Deutsch
- **Einrückung**: 4 Spaces
- **Encoding**: UTF-8
- **Zeilenende**: Unix (LF)

### JavaFX Best Practices

1. **UI Updates auf JavaFX Thread**:
   ```java
   Platform.runLater(() -> updateUI(data));
   ```

2. **Asynchrone API Calls**:
   ```java
   CompletableFuture<WeatherData> getWeatherAsync(City city)
   ```

3. **CSS für Styling verwenden** statt inline-Styles

4. **StyleClass statt ID** für wiederverwendbare Komponenten

### Fehlerbehandlung

- API-Fehler werden in `Alert`-Dialogen angezeigt
- Null-Checks für optionale Daten
- Timeout für HTTP Requests (30 Sekunden)

## Wichtige Dateien

### `pom.xml`

Maven-Konfiguration mit:
- Java 21 Compiler Settings
- JavaFX Dependencies
- Gson für JSON
- JUnit 5 für Tests
- JavaFX Maven Plugin zum Ausführen
- Shade Plugin für ausführbare JAR

### `module-info.java`

Java Module System Konfiguration:
- Exportiert alle Packages
- Öffnet Packages für JavaFX und Gson Reflection

### `weather.css`

CSS-Variablen und Klassen für einheitliches Design:
- Hauptfarbe: `#667eea` (Lila/Blau)
- Akzentfarbe: `#764ba2` (Dunkleres Lila)
- Background: Gradient von Lila zu Dunkelblau

## API-Referenz

### Open-Meteo API Endpoint

```
GET https://api.open-meteo.com/v1/forecast
```

**Query Parameter:**
- `latitude`, `longitude` - Koordinaten
- `current` - Aktuelle Wetterdaten
- `hourly` - Stündliche Vorhersage
- `daily` - Tägliche Vorhersage
- `timezone` - Europe/Berlin
- `forecast_days` - 7

### WMO Wettercodes

Die API verwendet standardisierte WMO Weather Interpretation Codes:

| Code | Beschreibung |
|------|-------------|
| 0 | Klar |
| 1-3 | Bewölkung |
| 45, 48 | Nebel |
| 51-57 | Nieselregen |
| 61-67 | Regen |
| 71-77 | Schnee |
| 80-82 | Regenschauer |
| 85-86 | Schneeschauer |
| 95-99 | Gewitter |

## Test-Strategie

### Unit Tests

- Model-Klassen: Getter/Setter, Berechnungen
- Utility-Klassen: Wettercode-Mapping
- Fokus auf `model/` Package

### Manuelle Tests

- UI-Interaktionen
- API-Integration
- Netzwerk-Fehlerbehandlung

### Tests ausführen

```bash
mvn test                    # Alle Tests
mvn test -Dtest=CityTest    # Einzelne Testklasse
```

## Bekannte Einschränkungen

1. **Nur deutsche Städte** - Vordefinierte Liste in `City.java`
2. **Keine Offline-Funktionalität** - Erfordert Internetverbindung
3. **Keine Caching** - Jede Anfrage geht an die API
4. **Single-Threaded UI** - Lange API-Calls können UI blockieren (mitigiert durch async)

## Erweiterungsmöglichkeiten

1. **Mehr Städte** - Städtesuche mit Geocoding API
2. **Caching** - Lokales Speichern von Wetterdaten
3. **Einstellungen** - Temperatureinheit (C/F), Sprache
4. **Widgets** - System Tray Integration
5. **Wetterwarnungen** - Push-Benachrichtigungen

## Häufige Probleme & Lösungen

### JavaFX Runtime nicht gefunden

```bash
# Prüfen ob JavaFX Module vorhanden
mvn dependency:tree | grep javafx
```

Lösung: Maven Update durchführen oder JavaFX SDK separat installieren.

### Module-Fehler beim Start

```
Error: module not found: javafx.controls
```

Lösung: Über `mvn javafx:run` starten oder VM-Argumente in IDE konfigurieren.

### CSS wird nicht geladen

Prüfen ob Ressourcen-Pfad korrekt:
```java
getClass().getResource("/styles/weather.css")
```

## Kontakt & Support

Für Fragen oder Probleme bitte ein GitHub Issue erstellen.
