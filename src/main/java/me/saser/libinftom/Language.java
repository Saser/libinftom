package me.saser.libinftom;

import java.util.Set;

public interface Language {

    /**
     * Get the set of symbols that this language accepts.
     * @return a Set of symbols, as Strings, that this language accepts
     */
    Set<String> getSymbols();
}
