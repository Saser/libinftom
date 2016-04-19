package me.saser.libinftom;

import java.util.Set;

public interface Language {

    /**
     * Get the set of symbols that this language accepts.
     * @return a Set of symbols, as Strings, that this language accepts
     */
    Set<String> getSymbols();

    /**
     * Determine whether the given symbol is a symbol in this language.
     * @param symbol
     * @return true if the symbols is valid
     */
    Boolean isValidSymbol(String symbol);

    /**
     * Determine whether the given word is a word in this language. The word is expected to be a <b>comma separated
     * string</b> of symbols. A word is valid if all symbols in it are valid symbols in this language.
     * @param word a comma separated list of symbols in this language
     * @return true if all symbols are valid
     */
    Boolean isValidWord(String word);
}
