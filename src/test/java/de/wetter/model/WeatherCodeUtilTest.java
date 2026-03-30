package de.wetter.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests für die WeatherCodeUtil-Klasse.
 */
class WeatherCodeUtilTest {

    @Test
    void testGetDescriptionForClearSky() {
        assertEquals("Klar", WeatherCodeUtil.getDescription(0));
    }

    @Test
    void testGetDescriptionForPartlyCloudy() {
        assertEquals("Teilweise bewölkt", WeatherCodeUtil.getDescription(2));
    }

    @Test
    void testGetDescriptionForRain() {
        assertEquals("Mäßiger Regen", WeatherCodeUtil.getDescription(63));
    }

    @Test
    void testGetDescriptionForSnow() {
        assertEquals("Starker Schneefall", WeatherCodeUtil.getDescription(75));
    }

    @Test
    void testGetDescriptionForThunderstorm() {
        assertEquals("Gewitter", WeatherCodeUtil.getDescription(95));
    }

    @Test
    void testGetDescriptionForUnknownCode() {
        assertEquals("Unbekannt", WeatherCodeUtil.getDescription(999));
    }

    @Test
    void testGetEmojiForClearSky() {
        assertEquals("\u2600", WeatherCodeUtil.getEmoji(0));
    }

    @Test
    void testGetEmojiForRain() {
        assertEquals("\uD83C\uDF27", WeatherCodeUtil.getEmoji(63));
    }

    @Test
    void testGetEmojiForSnow() {
        assertEquals("\u2744", WeatherCodeUtil.getEmoji(75));
    }

    @Test
    void testGetCssClassForClear() {
        assertEquals("weather-clear", WeatherCodeUtil.getCssClass(0));
    }

    @Test
    void testGetCssClassForRain() {
        assertEquals("weather-rain", WeatherCodeUtil.getCssClass(63));
    }

    @Test
    void testGetCssClassForSnow() {
        assertEquals("weather-snow", WeatherCodeUtil.getCssClass(75));
    }
}
