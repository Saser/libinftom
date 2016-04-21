package me.saser.libinftom.dfa;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.gson.Gson;
import me.saser.libinftom.Alphabet;
import me.saser.libinftom.StringAlphabet;

import java.util.Map;
import java.util.Set;

public class HashDFA implements DFA {

    private Set<String> states;
    private Alphabet alphabet;
    private Map<String, Map<String, String>> delta;
    private String initialState;
    private Set<String> finalStates;

    private HashDFA(Set<String> states, Set<String> alphabet, Map<String, Map<String, String>> delta, String initialState, Set<String> finalStates) {
        // Disallow empty states in set of states.
        for (String state : states) {
            if (state.equals("")) {
                throw new IllegalArgumentException("Having empty state in set of states is disallowed");
            }
        }
        this.states = ImmutableSet.copyOf(states);

        this.alphabet = new StringAlphabet(alphabet);

        // Check validity of delta.
        // 1. It should be defined for exactly the states in this.states.
        // 2. For each state, it should be defined for exactly the symbols in the alphabet.
        // 3. For each symbol, the resulting state should be in this.states or null.
        if (delta.keySet().equals(this.states) == false) {
            throw new IllegalArgumentException("delta not defined for exactly the given states");
        }

        for (Map.Entry<String, Map<String, String>> inputState : delta.entrySet()) {
            // The key of inputState is the state which we are defining the transitions for.
            // The value of inputState is a map from input symbol to next state.

            // Verify that exactly all symbols are used in the definition of the transitions.
            if (inputState.getValue().keySet().equals(alphabet) == false) {
                throw new IllegalArgumentException("Transition definition not defined for exactly all symbols in alphabet");
            }

            for (String resultingState : inputState.getValue().values()) {
                if (resultingState != null && this.states.contains(resultingState) == false) {
                    throw new IllegalArgumentException("Resulting state of transition is invalid");
                }
            }
        }
        // If we reach this point, we have verified that the delta input is valid.
        this.delta = ImmutableMap.copyOf(delta);

        // Verify that the initial state is a valid state.
        if (this.states.contains(initialState) == false) {
            throw new IllegalArgumentException("Initial state is not a valid state");
        }
        this.initialState = initialState;

        // Verify that all elements in the set of final states are valid states.
        if (this.states.containsAll(finalStates) == false) {
            throw new IllegalArgumentException("The set of final states contains invalid state(s)");
        }
        this.finalStates = ImmutableSet.copyOf(finalStates);
    }

    public static DFA fromJSON(String json) {
        Gson gson = new Gson();
        DFAData data = gson.fromJson(json, DFAData.class);
        return new HashDFA(data.states, data.alphabet, data.delta, data.initialState, data.finalStates);
    }

    @Override
    public Set<String> getStates() {
        return this.states;
    }

    @Override
    public Alphabet getAlphabet() {
        return this.alphabet;
    }

    @Override
    public String getInitialState() {
        return this.initialState;
    }

    @Override
    public Set<String> getFinalStates() {
        return this.finalStates;
    }

    @Override
    public DFARunner runner() {
        return null;
    }

    @Override
    public String nextState(String state, String symbol) {
        return null;
    }

    private static class DFAData {

        private Set<String> states;
        private Set<String> alphabet;
        private Map<String, Map<String, String>> delta;
        private String initialState;
        private Set<String> finalStates;

    }
}
