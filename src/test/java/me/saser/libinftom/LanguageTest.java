package me.saser.libinftom;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class LanguageTest {

    private static Language language;

    @BeforeClass
    public static void setUp() throws Exception {
        language = new StringLanguage("a,b,cde,hello world");
    }

    @Test
    public void getSymbols() throws Exception {
        Set<String> expectedSymbols = new HashSet<>();
        expectedSymbols.add("a");
        expectedSymbols.add("b");
        expectedSymbols.add("cde");
        expectedSymbols.add("hello world");

        assertEquals("The returned set should contain 'a', 'b', 'cde', 'hello world'", expectedSymbols, language.getSymbols());
    }

    @Test
    public void isValidSymbol() throws Exception {
        assertTrue("'a' is a valid symbol", language.isValidSymbol("a"));
        assertTrue("'hello world' is a valid symbol", language.isValidSymbol("hello world"));
        assertFalse("'hello, world' is not a valid symbol (contains a comma)", language.isValidSymbol("hello, world"));
    }

}
