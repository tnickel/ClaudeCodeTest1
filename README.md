# Wetter App

Eine JavaFX-Anwendung zur Anzeige von Wetterdaten mit grafischer Darstellung.

## Features

- Aktuelle Wetterdaten (Temperatur, Luftfeuchtigkeit, Wind, etc.)
- 24-Stunden-Vorhersage mit Temperatur- und Niederschlags-Charts
- 7-Tage-Vorhersage
- Auswahl aus 15 deutschen Städten
- Modernes, responsives UI-Design

## Technologien

- **Java 21**
- **JavaFX 21** - UI Framework
- **Maven** - Build-Management
- **Gson** - JSON-Parsing
- **Open-Meteo API** - Wetterdaten (kostenlos, kein API-Key erforderlich)

## Voraussetzungen

- Java 21 oder höher
- Maven 3.8+
- Eclipse IDE (empfohlen) oder IntelliJ IDEA

## Installation & Import in Eclipse

1. Repository klonen:
   ```bash
   git clone <repository-url>
   ```

2. In Eclipse importieren:
   - `File` > `Import` > `Maven` > `Existing Maven Projects`
   - Root-Verzeichnis des Projekts auswählen
   - `Finish` klicken

3. Maven-Abhängigkeiten werden automatisch heruntergeladen

## Anwendung starten

### Über Maven (Kommandozeile)
```bash
mvn javafx:run
```

### In Eclipse
- Rechtsklick auf `WetterApp.java` > `Run As` > `Java Application`

### Als JAR-Datei
```bash
mvn clean package
java -jar target/wetter-app-1.0.0-SNAPSHOT.jar
```

## Projektstruktur

```
src/
├── main/
│   ├── java/
│   │   ├── de/wetter/
│   │   │   ├── app/          # Hauptanwendung
│   │   │   ├── model/        # Datenmodelle
│   │   │   ├── service/      # API-Service
│   │   │   └── ui/           # UI-Komponenten
│   │   └── module-info.java  # Java Module Definition
│   └── resources/
│       └── styles/           # CSS-Stylesheets
└── test/
    └── java/                 # Unit Tests
```

## Tests ausführen

```bash
mvn test
```

## Datenquelle

Die Wetterdaten werden von der [Open-Meteo API](https://open-meteo.com/) bezogen. Diese API ist kostenlos und erfordert keinen API-Schlüssel.

## Lizenz

Dieses Projekt ist für Lern- und Testzwecke erstellt.
