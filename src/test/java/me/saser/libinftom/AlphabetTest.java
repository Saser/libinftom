package me.saser.libinftom;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class AlphabetTest {

    private static Alphabet alphabet;

    @BeforeClass
    public static void setUp() throws Exception {
        alphabet = new StringAlphabet("a,b,cde,hello world");
    }

    @Test
    public void getSymbols() throws Exception {
        Set<String> expectedSymbols = new HashSet<>();
        expectedSymbols.add("a");
        expectedSymbols.add("b");
        expectedSymbols.add("cde");
        expectedSymbols.add("hello world");

        assertEquals("The returned set should contain 'a', 'b', 'cde', 'hello world'", expectedSymbols, alphabet.getSymbols());
    }

    @Test
    public void isValidSymbol() throws Exception {
        assertTrue("'a' is a valid symbol", alphabet.isValidSymbol("a"));
        assertTrue("'hello world' is a valid symbol", alphabet.isValidSymbol("hello world"));

        assertFalse("'hello, world' is not a valid symbol (contains a comma)", alphabet.isValidSymbol("hello, world"));
    }

    @Test
    public void isValidWord() throws Exception {
        assertTrue("'' (the empty word) is a valid word", alphabet.isValidWord(""));
        assertTrue("'a', a word with a single symbol, is a valid word", alphabet.isValidWord("a"));
        assertTrue("'hello world', a word with a single symbol, is a valid word", alphabet.isValidWord("hello world"));
        assertTrue("'a,b,cde', a word with three symbols, is a valid word", alphabet.isValidWord("a,b,cde"));
        assertTrue("'a,a,hello world,a,b', a word with a few symbols, is a valid word", alphabet.isValidWord("a,a,hello world,a,b"));

        assertFalse("'q', a word with a single invalid symbol, is an invalid word", alphabet.isValidWord("q"));
        assertFalse("'a,q', a word with one valid and one invalid symbol, is an invalid word", alphabet.isValidWord("a,q"));
        assertFalse("'a,cde,hello world,q', a word with a few valid symbols and one invalid symbol, is an invalid word", alphabet.isValidWord("a,cde,hello world,q"));
        assertFalse("'q,q,q,derp,sc2', a word with a few invalid symbols, is an invalid word", alphabet.isValidWord("q,q,q,derp,sc2"));
    }

    @Test
    public void disallowEmptySymbols() throws Exception {
        boolean thrown = false;
        String message = "";

        try {
            new StringAlphabet("a,b,c,,e");
        } catch (IllegalArgumentException e) {
            thrown = true;
            message = e.getMessage();
        }

        assertTrue("An IllegalArgumentException should have been thrown", thrown);
        assertTrue("The exception should contain the string \"empty symbol\"", message.contains("empty symbol"));

        try {
            new StringAlphabet("a,b,c,e,");
        } catch (IllegalArgumentException e) {
            thrown = true;
            message = e.getMessage();
        }

        assertTrue("An IllegalArgumentException should have been thrown", thrown);
        assertTrue("The exception should contain the string \"empty symbol\"", message.contains("empty symbol"));

        try {
            new StringAlphabet(",a,b,c,e");
        } catch (IllegalArgumentException e) {
            thrown = true;
            message = e.getMessage();
        }

        assertTrue("An IllegalArgumentException should have been thrown", thrown);
        assertTrue("The exception should contain the string \"empty symbol\"", message.contains("empty symbol"));
    }
}
