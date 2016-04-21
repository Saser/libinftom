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
        this(ImmutableList.copyOf(symbols.split(",")));
    }

    /**
     * Create an alphabet from the given iterable of symbols. Any duplicates will be removed. Empty symbols are not
     * allowed, and will result in an IllegalArgumentException.
     * @param symbols an Iterable of Strings to create the alphabet from
     * @throws IllegalArgumentException if any of the symbols is the empty string
     */
    public StringAlphabet(Iterable<String> symbols) {
        for (String sym : symbols) {
            if (sym.equals("")) {
                throw new IllegalArgumentException("Having an empty symbol is disallowed");
            }
        }

        this.symbolSet = ImmutableSet.copyOf(symbols);
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
        // We define that the empty word is valid, since that is how the set of words of an alphabet is usually
        // defined.
        if (word.equals("")) {
            return true;
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
