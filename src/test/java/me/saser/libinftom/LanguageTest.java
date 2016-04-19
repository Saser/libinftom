package me.saser.libinftom;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class LanguageTest {

    private static Language language;

    @BeforeClass
    public void setUp() throws Exception {
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

}
