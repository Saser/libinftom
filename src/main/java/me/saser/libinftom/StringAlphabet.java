package me.saser.libinftom;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import java.util.Set;

public class StringAlphabet implements Alphabet {

    private final Set<String> symbolSet;

    /**
     * Create a alphabet from the given <b>comma separated string</b> of symbols. Be sure to not include commas in the
     * symbols themselves.
     *
     * @param symbols a comma separated string of symbols
     * @throws IllegalArgumentException if any of the symbols is the empty string
     */
    public StringAlphabet(String symbols) {
        if (symbols.equals("")) {
            throw new IllegalArgumentException("Having an empty alphabet is disallowed");
        }

        // Throw exception on leading or trailing commas => empty symbols.
        if (symbols.charAt(0) == ',' ||
            symbols.charAt(symbols.length() - 1) == ',' ||
            noEmptySymbols(ImmutableList.copyOf(symbols.split(","))) == false) {
            throw new IllegalArgumentException("Having an empty symbol is disallowed");
        }

        this.symbolSet = ImmutableSet.copyOf(symbols.split(","));
    }

    /**
     * Create an alphabet from the given iterable of symbols. Any duplicates will be removed. Empty symbols are not
     * allowed, and will result in an IllegalArgumentException.
     *
     * @param symbols an Iterable of Strings to create the alphabet from
     * @throws IllegalArgumentException if any of the symbols is the empty string
     */
    public StringAlphabet(Iterable<String> symbols) {
        if (noEmptySymbols(symbols) == false) {
            throw new IllegalArgumentException("Having an empty symbol is disallowed");
        }

        this.symbolSet = ImmutableSet.copyOf(symbols);
    }

    private boolean noEmptySymbols(Iterable<String> symbols) {
        for (String sym : symbols) {
            if (sym.equals("")) {
                return false;
            }
        }

        return true;
    }

    @Override
    public Set<String> getSymbols() {
        return this.symbolSet;
    }

    @Override
    public boolean isValidSymbol(String symbol) {
        return this.symbolSet.contains(symbol);
    }

    @Override
    public boolean isValidWord(String word) {
        // We define that the empty word is valid, since that is how the set of words of an alphabet is usually
        // defined.
        if (word.equals("")) {
            return true;
        }

        // A word should not begin or end with an empty symbol, which means it should not begin or end with a comma.
        if (word.charAt(0) == ',' || word.charAt(word.length() - 1) == ',') {
            return false;
        }

        return this.symbolSet.containsAll(ImmutableSet.copyOf(word.split(",")));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StringAlphabet that = (StringAlphabet) o;

        return symbolSet.equals(that.symbolSet);

    }
}
