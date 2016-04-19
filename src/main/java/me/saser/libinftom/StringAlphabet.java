package me.saser.libinftom;

import com.google.common.collect.ImmutableSet;

import java.util.Set;

public class StringAlphabet implements Alphabet {

    private final Set<String> symbolSet;

    /**
     * Create a alphabet from the given <b>comma separated string</b> of symbols. Be sure to not include commas in the
     * symbols themselves.
     *
     * @param symbols a comma separated string of symbols
     */
    public StringAlphabet(String symbols) {
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
