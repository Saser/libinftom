package me.saser.libinftom;

import java.util.Set;

public interface Alphabet {

    /**
     * Gets the set of symbols that this alphabet accepts.
     *
     * @return a Set of symbols, as Strings, that this alphabet accepts
     */
    Set<String> getSymbols();

    /**
     * Determines whether the given String is a symbol in this alphabet.
     *
     * @param symbol a String for a single symbol
     * @return true if the symbols is valid
     */
    boolean isValidSymbol(String symbol);

    /**
     * Determines whether the given word is a word in this alphabet. The word is expected to be a <b>comma separated
     * String</b> of symbols. A word is valid if all symbols in it are valid symbols in this alphabet.
     *
     * @param word a comma separated list of symbols in this alphabet
     * @return true if all symbols are valid
     */
    boolean isValidWord(String word);
}
