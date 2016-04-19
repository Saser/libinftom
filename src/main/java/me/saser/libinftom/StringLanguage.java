package me.saser.libinftom;

import com.google.common.collect.ImmutableSet;

import java.util.Set;

public class StringLanguage implements Language {

    private final Set<String> symbolSet;

    /**
     * Create a language from the given <b>comma separated string</b> of symbols. Be sure to not include commas in the
     * symbols themselves.
     * @param symbols a comma separated string of symbols
     */
    public StringLanguage(String symbols) {
        this.symbolSet = ImmutableSet.copyOf(symbols.split(","));
    }

    @Override
    public Set<String> getSymbols() {
        return this.symbolSet;
    }

    @Override
    public Boolean isValidSymbol(String symbol) {
        return this.symbolSet.contains(symbol);
    }

    @Override
    public Boolean isValidWord(String word) {
        // We define languages to allow the empty word.
        if (word.equals("")) {
            return true;
        }

        return this.symbolSet.containsAll(ImmutableSet.copyOf(word.split(",")));
    }
}
