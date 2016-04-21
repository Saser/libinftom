package me.saser.libinftom.dfa;

import com.google.gson.Gson;
import me.saser.libinftom.Alphabet;

import java.util.Map;
import java.util.Set;

public class HashDFA implements DFA {

    @Override
    public Set<String> getStates() {
        return null;
    }

    @Override
    public Alphabet getAlphabet() {
        return null;
    }

    @Override
    public String getInitialState() {
        return null;
    }

    @Override
    public Set<String> getFinalStates() {
        return null;
    }

    @Override
    public DFARunner runner() {
        return null;
    }

    private static class DFAData {

        public Set<String> states;
        public Set<String> alphabet;
        public Map<String, Map<String, String>> delta;
        public String initialState;
        public Set<String> finalStates;

    }
}
