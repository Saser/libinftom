package me.saser.libinftom;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class StringLanguage implements Language {

    private final Set<String> symbolSet;

    public StringLanguage(String symbols) {
        this.symbolSet = new HashSet<>();
        Collections.addAll(this.symbolSet, symbols.split(","));
    }

    @Override
    public Set<String> getSymbols() {
        return new HashSet<>(this.symbolSet);
    }
}
