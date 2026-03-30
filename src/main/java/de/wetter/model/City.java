package de.wetter.model;

/**
 * Modell für eine Stadt mit Koordinaten.
 */
public class City {
    private final String name;
    private final double latitude;
    private final double longitude;
    private final String country;

    public City(String name, double latitude, double longitude, String country) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getCountry() {
        return country;
    }

    @Override
    public String toString() {
        return name + " (" + country + ")";
    }

    /**
     * Vordefinierte deutsche Städte.
     */
    public static City[] getGermanCities() {
        return new City[]{
                new City("Berlin", 52.52, 13.405, "DE"),
                new City("Hamburg", 53.5511, 9.9937, "DE"),
                new City("München", 48.1351, 11.582, "DE"),
                new City("Köln", 50.9375, 6.9603, "DE"),
                new City("Frankfurt am Main", 50.1109, 8.6821, "DE"),
                new City("Stuttgart", 48.7758, 9.1829, "DE"),
                new City("Düsseldorf", 51.2277, 6.7735, "DE"),
                new City("Leipzig", 51.3397, 12.3731, "DE"),
                new City("Dortmund", 51.5136, 7.4653, "DE"),
                new City("Essen", 51.4556, 7.0116, "DE"),
                new City("Bremen", 53.0793, 8.8017, "DE"),
                new City("Dresden", 51.0504, 13.7373, "DE"),
                new City("Hannover", 52.3759, 9.732, "DE"),
                new City("Nürnberg", 49.4521, 11.0767, "DE"),
                new City("Duisburg", 51.4344, 6.7623, "DE")
        };
    }
}
