package de.wetter.model;

/**
 * Hilfsmethoden für WMO Wettercodes.
 * Codes basieren auf dem WMO Weather Interpretation Codes (WW).
 */
public final class WeatherCodeUtil {

    private WeatherCodeUtil() {
    }

    /**
     * Gibt eine deutsche Beschreibung für den WMO-Wettercode zurück.
     */
    public static String getDescription(int code) {
        return switch (code) {
            case 0 -> "Klar";
            case 1 -> "Überwiegend klar";
            case 2 -> "Teilweise bewölkt";
            case 3 -> "Bedeckt";
            case 45 -> "Nebel";
            case 48 -> "Reifnebel";
            case 51 -> "Leichter Nieselregen";
            case 53 -> "Mäßiger Nieselregen";
            case 55 -> "Starker Nieselregen";
            case 56 -> "Leichter gefrierender Nieselregen";
            case 57 -> "Starker gefrierender Nieselregen";
            case 61 -> "Leichter Regen";
            case 63 -> "Mäßiger Regen";
            case 65 -> "Starker Regen";
            case 66 -> "Leichter gefrierender Regen";
            case 67 -> "Starker gefrierender Regen";
            case 71 -> "Leichter Schneefall";
            case 73 -> "Mäßiger Schneefall";
            case 75 -> "Starker Schneefall";
            case 77 -> "Schneekörner";
            case 80 -> "Leichte Regenschauer";
            case 81 -> "Mäßige Regenschauer";
            case 82 -> "Starke Regenschauer";
            case 85 -> "Leichte Schneeschauer";
            case 86 -> "Starke Schneeschauer";
            case 95 -> "Gewitter";
            case 96 -> "Gewitter mit leichtem Hagel";
            case 99 -> "Gewitter mit starkem Hagel";
            default -> "Unbekannt";
        };
    }

    /**
     * Gibt ein passendes Symbol/Emoji für den Wettercode zurück.
     */
    public static String getEmoji(int code) {
        return switch (code) {
            case 0 -> "\u2600"; // Sonne
            case 1 -> "\u26C5"; // Sonne mit Wolke
            case 2 -> "\u26C5"; // Sonne mit Wolke
            case 3 -> "\u2601"; // Wolke
            case 45, 48 -> "\uD83C\uDF2B"; // Nebel
            case 51, 53, 55, 56, 57 -> "\uD83C\uDF27"; // Regen
            case 61, 63, 65, 66, 67 -> "\uD83C\uDF27"; // Regen
            case 71, 73, 75, 77 -> "\u2744"; // Schneeflocke
            case 80, 81, 82 -> "\uD83C\uDF26"; // Regenschauer
            case 85, 86 -> "\uD83C\uDF28"; // Schneeschauer
            case 95, 96, 99 -> "\u26A1"; // Gewitter
            default -> "\u2753"; // Fragezeichen
        };
    }

    /**
     * Gibt eine CSS-Klasse für den Wettercode zurück (für Styling).
     */
    public static String getCssClass(int code) {
        return switch (code) {
            case 0 -> "weather-clear";
            case 1, 2 -> "weather-partly-cloudy";
            case 3 -> "weather-cloudy";
            case 45, 48 -> "weather-fog";
            case 51, 53, 55, 56, 57, 61, 63, 65, 66, 67, 80, 81, 82 -> "weather-rain";
            case 71, 73, 75, 77, 85, 86 -> "weather-snow";
            case 95, 96, 99 -> "weather-thunderstorm";
            default -> "weather-unknown";
        };
    }
}
