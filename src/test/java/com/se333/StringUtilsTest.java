package com.se333;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class StringUtilsTest {
    private StringUtils stringUtils;

    @BeforeEach
    public void setUp() {
        stringUtils = new StringUtils();
    }

    // Tests for reverse()
    @Test
    public void testReverseNormalString() {
        assertEquals("olleh", stringUtils.reverse("hello"));
    }

    @Test
    public void testReverseEmptyString() {
        assertEquals("", stringUtils.reverse(""));
    }

    @Test
    public void testReverseNull() {
        assertNull(stringUtils.reverse(null));
    }

    @Test
    public void testReverseSingleCharacter() {
        assertEquals("a", stringUtils.reverse("a"));
    }

    @Test
    public void testReversePalindrome() {
        assertEquals("racecar", stringUtils.reverse("racecar"));
    }

    @Test
    public void testReverseWithSpaces() {
        assertEquals("dlrow olleH", stringUtils.reverse("Hello world"));
    }

    @Test
    public void testReverseSpecialCharacters() {
        assertEquals("!dlrow", stringUtils.reverse("world!"));
    }

    // Tests for isPalindrome()
    @Test
    public void testIsPalindromeTrue() {
        assertTrue(stringUtils.isPalindrome("racecar"));
        assertTrue(stringUtils.isPalindrome("a"));
    }

    @Test
    public void testIsPalindromeFalse() {
        assertFalse(stringUtils.isPalindrome("hello"));
        assertFalse(stringUtils.isPalindrome("abc"));
    }

    @Test
    public void testIsPalindromeNull() {
        assertFalse(stringUtils.isPalindrome(null));
    }

    @Test
    public void testIsPalindromeEmptyString() {
        assertTrue(stringUtils.isPalindrome(""));
    }

    @Test
    public void testIsPalindromeWithSpaces() {
        assertTrue(stringUtils.isPalindrome("A man a plan a canal Panama"));
    }

    @Test
    public void testIsPalindromeWithSpecialCharacters() {
        assertTrue(stringUtils.isPalindrome("race-car"));
        assertTrue(stringUtils.isPalindrome("12321"));
    }

    @Test
    public void testIsPalindromeIgnoresCase() {
        assertTrue(stringUtils.isPalindrome("RaceCar"));
        assertTrue(stringUtils.isPalindrome("RACECAR"));
    }

    // Tests for countVowels()
    @Test
    public void testCountVowelsNormal() {
        assertEquals(2, stringUtils.countVowels("hello"));
        assertEquals(1, stringUtils.countVowels("world"));
    }

    @Test
    public void testCountVowelsNoVowels() {
        assertEquals(0, stringUtils.countVowels("xyz"));
        assertEquals(0, stringUtils.countVowels("bcdfg"));
    }

    @Test
    public void testCountVowelsNull() {
        assertEquals(0, stringUtils.countVowels(null));
    }

    @Test
    public void testCountVowelsEmptyString() {
        assertEquals(0, stringUtils.countVowels(""));
    }

    @Test
    public void testCountVowelsUppercase() {
        assertEquals(2, stringUtils.countVowels("HELLO"));
        assertEquals(2, stringUtils.countVowels("HeLLo"));
    }

    @Test
    public void testCountVowelsWithNumbers() {
        assertEquals(1, stringUtils.countVowels("test123"));
    }

    @Test
    public void testCountVowelsAllVowels() {
        assertEquals(5, stringUtils.countVowels("aeiou"));
    }

    @Test
    public void testCountVowelsDuplicateVowels() {
        assertEquals(2, stringUtils.countVowels("aabbcc"));
    }

    // Tests for capitalize()
    @Test
    public void testCapitalizeNormal() {
        assertEquals("Hello", stringUtils.capitalize("hello"));
    }

    @Test
    public void testCapitalizeSingleCharacter() {
        assertEquals("H", stringUtils.capitalize("h"));
        assertEquals("A", stringUtils.capitalize("a"));
    }

    @Test
    public void testCapitalizeNull() {
        assertNull(stringUtils.capitalize(null));
    }

    @Test
    public void testCapitalizeEmptyString() {
        assertEquals("", stringUtils.capitalize(""));
    }

    @Test
    public void testCapitalizeAlreadyCapitalized() {
        assertEquals("Hello", stringUtils.capitalize("Hello"));
    }

    @Test
    public void testCapitalizeAllUppercase() {
        assertEquals("Helloworld", stringUtils.capitalize("HELLOWORLD"));
    }

    @Test
    public void testCapitalizeAllLowercase() {
        assertEquals("Helloworld", stringUtils.capitalize("helloworld"));
    }

    @Test
    public void testCapitalizeWithNumbers() {
        assertEquals("123abc", stringUtils.capitalize("123abc"));
    }

    @Test
    public void testCapitalizeSpecialCharacter() {
        assertEquals("!hello", stringUtils.capitalize("!hello"));
    }
}
