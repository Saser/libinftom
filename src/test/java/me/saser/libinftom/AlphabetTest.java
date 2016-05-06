package me.saser.libinftom;

import com.google.common.collect.ImmutableSet;
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

        assertEquals("The returned set should contain \"a\", \"b\", \"cde\", \"hello world\"", expectedSymbols, alphabet.getSymbols());
    }

    @Test
    public void isValidSymbol() throws Exception {
        assertTrue("\"a\" is a valid symbol", alphabet.isValidSymbol("a"));
        assertTrue("\"hello world\" is a valid symbol", alphabet.isValidSymbol("hello world"));

        assertFalse("\"hello, world\" is not a valid symbol (contains a comma)", alphabet.isValidSymbol("hello, world"));
    }

    @Test
    public void isValidWord() throws Exception {
        assertTrue("\"\" (the empty word) is a valid word", alphabet.isValidWord(""));
        assertTrue("\"a\", a word with a single symbol, is a valid word", alphabet.isValidWord("a"));
        assertTrue("\"hello world\", a word with a single symbol, is a valid word", alphabet.isValidWord("hello world"));
        assertTrue("\"a,b,cde\", a word with three symbols, is a valid word", alphabet.isValidWord("a,b,cde"));
        assertTrue("\"a,a,hello world,a,b\", a word with a few symbols, is a valid word", alphabet.isValidWord("a,a,hello world,a,b"));

        assertFalse("\"q\", a word with a single invalid symbol, is an invalid word", alphabet.isValidWord("q"));
        assertFalse("\"a,q\", a word with one valid and one invalid symbol, is an invalid word", alphabet.isValidWord("a,q"));
        assertFalse("\"a,cde,hello world,q\", a word with a few valid symbols and one invalid symbol, is an invalid word", alphabet.isValidWord("a,cde,hello world,q"));
        assertFalse("\"q,q,q,derp,sc2\", a word with a few invalid symbols, is an invalid word", alphabet.isValidWord("q,q,q,derp,sc2"));

        assertFalse("\"a,b,,a\" is an invalid word since it contains an empty symbol", alphabet.isValidWord("a,b,,a"));
        assertFalse("\",a,b,a\" is an invalid word since it starts with an empty symbol", alphabet.isValidWord(",a,b,a"));
        assertFalse("\"a,b,a,\" is an invalid word since it ends with an empty symbol", alphabet.isValidWord("a,b,a,"));
    }

    @Test
    public void disallowEmptyAlphabet() throws Exception {
        try {
            new StringAlphabet("");

            fail("An IllegalArgumentException should have been thrown");
        } catch (IllegalArgumentException e) {
            assertTrue("Exception message should contain \"empty alphabet\"", e.getMessage().contains("empty alphabet"));
        }
    }

    @Test
    public void disallowEmptySymbolsCommaString() throws Exception {
        try {
            new StringAlphabet("a,b,c,,e");

            fail("An IllegalArgumentException should have been thrown");
        } catch (IllegalArgumentException e) {
            assertTrue("The exception should contain the string \"empty symbol\"", e.getMessage().contains("empty symbol"));
        }

        try {
            new StringAlphabet("a,b,c,e,");

            fail("An IllegalArgumentException should have been thrown");
        } catch (IllegalArgumentException e) {
            assertTrue("The exception should contain the string \"empty symbol\"", e.getMessage().contains("empty symbol"));
        }

        try {
            new StringAlphabet(",a,b,c,e");

            fail("An IllegalArgumentException should have been thrown");
        } catch (IllegalArgumentException e) {
            assertTrue("The exception should contain the string \"empty symbol\"", e.getMessage().contains("empty symbol"));
        }
    }

    @Test
    public void disallowEmptySymbolsIterable() throws Exception {
        try {
            new StringAlphabet(ImmutableSet.of("a", "b", "c", "", "e"));

            fail("An IllegalArgumentException should have been thrown");
        } catch (IllegalArgumentException e) {
            assertTrue("The exception should contain the string \"empty symbol\"", e.getMessage().contains("empty symbol"));
        }

        try {
            new StringAlphabet(ImmutableSet.of("a", "b", "c", "e", ""));

            fail("An IllegalArgumentException should have been thrown");
        } catch (IllegalArgumentException e) {
            assertTrue("The exception should contain the string \"empty symbol\"", e.getMessage().contains("empty symbol"));
        }

        try {
            new StringAlphabet(ImmutableSet.of("", "a", "b", "c", "e"));

            fail("An IllegalArgumentException should have been thrown");
        } catch (IllegalArgumentException e) {
            assertTrue("The exception should contain the string \"empty symbol\"", e.getMessage().contains("empty symbol"));
        }
    }

    @Test
    public void testEquals() throws Exception {
        // Create an alphabet from comma-separated list of symbols.
        Alphabet a1 = new StringAlphabet("a,b,c,d,e");
        // Create an alphabet from the same set of symbols, using an ImmutableSet.
        Alphabet a2 = new StringAlphabet(ImmutableSet.of("b", "e", "a", "d", "c"));

        assertEquals("An alphabet should be equal to itself", a1, a1);
        assertEquals("An alphabet should be equal to itself", a2, a2);

        assertFalse("No alphabet is equal to null", a1.equals(null));
        assertFalse("No alphabet is equal to null", a2.equals(null));

        assertTrue("The two alphabets should be equal", a1.equals(a2));
        assertTrue("The two alphabets should be equal", a2.equals(a1));

        assertFalse("Alphabets are not equal to any object of any other type", a1.equals("hello"));
        assertFalse("Alphabets are not equal to any object of any other type", a2.equals("hello"));
    }
}
