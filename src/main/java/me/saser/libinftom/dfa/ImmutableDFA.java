package me.saser.libinftom.dfa;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.gson.Gson;
import me.saser.libinftom.Alphabet;
import me.saser.libinftom.StringAlphabet;

import java.util.Map;
import java.util.Set;

/**
 * A class representing a DFA, which is defined at creation time and thereafter never changed, thus being immutable.
 * <p>
 * Instances of this class are created by calling the <code>fromJSON(String)</code> class method, which parses the given
 * String as a JSON representation of a DFA. See the documentation for <code>fromJSON(String)</code> method for more
 * details regarding the accepted format of the JSON representation.
 */
public class ImmutableDFA implements DFA {

    private final Set<String> states;
    private final Alphabet alphabet;
    private final Map<String, Map<String, String>> delta;
    private final String initialState;
    private final Set<String> finalStates;

    private ImmutableDFA(Set<String> states, Set<String> alphabet, Map<String, Map<String, String>> delta, String initialState, Set<String> finalStates) {
        // Disallow empty states in set of states.
        for (String state : states) {
            if (state.equals("")) {
                throw new IllegalArgumentException("Having empty state disallowed");
            }
        }
        this.states = ImmutableSet.copyOf(states);

        this.alphabet = new StringAlphabet(alphabet);

        // Check validity of delta.
        // 1. It should be defined for exactly the states in this.states.
        // 2. For each state, it should be defined for exactly the symbols in the alphabet.
        // 3. For each symbol, the resulting state should be in this.states or null.
        if (delta.keySet().equals(this.states) == false) {
            throw new IllegalArgumentException("delta not defined for exactly all states");
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
                    throw new IllegalArgumentException("invalid target state");
                }
            }
        }
        // If we reach this point, we have verified that the delta input is valid.
        this.delta = ImmutableMap.copyOf(delta);

        // Verify that the initial state is a valid state.
        if (this.states.contains(initialState) == false) {
            throw new IllegalArgumentException("invalid initial state");
        }
        this.initialState = initialState;

        // Verify that all elements in the set of final states are valid states.
        if (this.states.containsAll(finalStates) == false) {
            throw new IllegalArgumentException("invalid final state");
        }
        this.finalStates = ImmutableSet.copyOf(finalStates);
    }

    /**
     * Parses the given String as a JSON representation of a DFA and returns a new instance of <code>ImmutableDFA</code>
     * upon success, or throws <code>IllegalArgumentException</code>s upon parsing failure.
     *
     * The accepted JSON format is a single JSON object with the following fields:
     *
     * <ul>
     *     <li><code>alphabet</code>: a JSON list of symbols in the alphabet, given as strings. Duplicate symbols will
     *     be silently ignored.</li>
     *     <li><code>states</code>: a JSON list of possible states, given as strings. Duplicate states will be silently
     *     ignored.</li>
     *     <li><code>delta</code>: a JSON object representing the transition function of the DFA. Each key is a state,
     *     and the value for each key is a JSON object with the following format:
     *     <ul>
     *         <li>a key for every symbol in the alphabet</li>
     *         <li>for each key, the value is a string containing the state it should transition to; or null, if
     *         transitioning to the dead state</li>
     *     </ul></li>
     *     <li><code>initialState</code>: a string with the initial state.</li>
     *     <li><code>finalStates</code>: a JSON list of final states, given as strings. Duplicate states will be
     *     silently ignored.</li>
     * </ul>
     *
     * An example of a valid JSON representation is given below.
     * <p>
     * <code>
     *    {
     *       "alphabet": [
     *           "0",
     *           "1"
     *       ],
     *       "states": [
     *           "q0",
     *           "q1",
     *           "q2"
     *       ],
     *       "delta": {
     *           "q0": {
     *               "0": "q1",
     *               "1": null
     *           },
     *           "q1": {
     *               "0": "q1",
     *               "1": "q2"
     *           },
     *           "q2": {
     *               "0": "q1",
     *               "1": "q2"
     *           }
     *       },
     *       "initialState": "q0",
     *       "finalStates": [
     *           "q2"
     *       ]
     *   }
     * </code>
     *
     * @param json a JSON representation of the DFA, according to the above format
     * @return an instance of <code>ImmutableDFA</code>
     * @throws IllegalArgumentException if the parsing fails in any way or not enough information is provided
     */
    public static DFA fromJSON(String json) {
        Gson gson = new Gson();
        DFAData data = gson.fromJson(json, DFAData.class);
        return new ImmutableDFA(data.states, data.alphabet, data.delta, data.initialState, data.finalStates);
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
        return new SimpleDFARunner(this);
    }

    @Override
    public String nextState(String state, String symbol) {
        // Special case for null symbol.
        if (symbol == null) {
            throw new IllegalArgumentException("Cannot transition, null is an invalid symbol");
        }

        // The empty symbol is invalid.
        if (symbol.equals("")) {
            throw new IllegalArgumentException("Cannot transition using the empty symbol");
        }

        if (this.alphabet.isValidSymbol(symbol) == false) {
            throw new IllegalArgumentException("Trying to transition using an invalid symbol");
        }

        // Special case for when trying to transition from null state, since that is considered the dead state.
        // Any transition from the dead state ends up in the dead state itself, so we return null.
        if (state == null) {
            return null;
        }

        if (this.states.contains(state) == false) {
            throw new IllegalArgumentException("Trying to transition from invalid starting state");
        }

        return this.delta.get(state).get(symbol);
    }

    private static class DFAData {

        private Set<String> states;
        private Set<String> alphabet;
        private Map<String, Map<String, String>> delta;
        private String initialState;
        private Set<String> finalStates;

    }
}
