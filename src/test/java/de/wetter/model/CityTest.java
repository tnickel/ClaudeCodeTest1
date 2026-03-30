package de.wetter.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests für die City-Klasse.
 */
class CityTest {

    @Test
    void testCityCreation() {
        City city = new City("Berlin", 52.52, 13.405, "DE");

        assertEquals("Berlin", city.getName());
        assertEquals(52.52, city.getLatitude(), 0.001);
        assertEquals(13.405, city.getLongitude(), 0.001);
        assertEquals("DE", city.getCountry());
    }

    @Test
    void testCityToString() {
        City city = new City("München", 48.1351, 11.582, "DE");
        assertEquals("München (DE)", city.toString());
    }

    @Test
    void testGetGermanCitiesNotEmpty() {
        City[] cities = City.getGermanCities();

        assertNotNull(cities);
        assertTrue(cities.length > 0);
    }

    @Test
    void testGetGermanCitiesContainsBerlin() {
        City[] cities = City.getGermanCities();

        boolean containsBerlin = false;
        for (City city : cities) {
            if ("Berlin".equals(city.getName())) {
                containsBerlin = true;
                break;
            }
        }

        assertTrue(containsBerlin, "German cities should contain Berlin");
    }

    @Test
    void testAllGermanCitiesHaveValidCoordinates() {
        City[] cities = City.getGermanCities();

        for (City city : cities) {
            // Deutsche Koordinaten: Breitengrad ca. 47-55, Längengrad ca. 6-15
            assertTrue(city.getLatitude() >= 47 && city.getLatitude() <= 55,
                    city.getName() + " should have valid latitude");
            assertTrue(city.getLongitude() >= 5 && city.getLongitude() <= 16,
                    city.getName() + " should have valid longitude");
        }
    }
}
