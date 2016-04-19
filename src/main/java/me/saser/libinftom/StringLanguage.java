package me.saser.libinftom;

import com.google.common.collect.ImmutableSet;

import java.util.Set;

public class StringLanguage implements Language {

    private final Set<String> symbolSet;

    public StringLanguage(String symbols) {
        this.symbolSet = ImmutableSet.copyOf(symbols.split(","));
    }

    @Override
    public Set<String> getSymbols() {
        return this.symbolSet;
    }
}
