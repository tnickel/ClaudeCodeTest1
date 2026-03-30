package de.wetter.app;

/**
 * Launcher-Klasse für die ausführbare JAR-Datei.
 * Diese Klasse wird benötigt, da JavaFX-Anwendungen nicht direkt
 * aus einer JAR-Datei gestartet werden können, wenn die Hauptklasse
 * von Application erbt.
 */
public class Launcher {
    public static void main(String[] args) {
        WetterApp.main(args);
    }
}
