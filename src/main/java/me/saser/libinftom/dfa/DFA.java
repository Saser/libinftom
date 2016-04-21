package me.saser.libinftom.dfa;

import me.saser.libinftom.Alphabet;

import java.util.Set;

/**
 * Represents the definition of a deterministic finite automaton, based on the 5-tuple form of definition. The following
 * information is available from implementations of this interface:
 * <p>
 * <ul>
 * <li>The set of all possible states</li>
 * <li>The alphabet that the DFA is defined for</li>
 * <li>The "delta" function (also known as transition function) that describes how this automaton moves when
 * reading symbols in the alphabet</li>
 * <li>The initial state</li>
 * <li>The set of final states (also known as accepting states)</li>
 * </ul>
 * <p>
 * As such, implementations of this class do not simulate an actual state machine -- it only creates models for them,
 * that actual mutable state machines can be constructed from.
 */
public interface DFA {

    /**
     * Gets a Set of all possible states for this DFA. This set contains all states that the DFA was created with,
     * and so could possibly contain unreachable states.
     *
     * @return a Set of String representations of all possible states
     */
    Set<String> getStates();

    /**
     * Returns the alphabet as an Alphabet that this DFA is defined for.
     *
     * @return an Alphabet instance containing the alphabet
     */
    Alphabet getAlphabet();

    /**
     * Gets a String representation of this DFAs initial state. The returned String is guaranteed to be an element
     * in the set returned by <code>getStates()</code>.
     *
     * @return a String representation of the initial state
     */
    String getInitialState();

    /**
     * Gets the set of final states for this DFA. All elements in this set are guaranteed to be elements in the
     * set returned by <code>getStates()</code>.
     *
     * @return a Set of all final states
     */
    Set<String> getFinalStates();

    /**
     * Returns an instance of {@link DFARunner} that can simulate traversals through this DFA.
     *
     * @return an instance of {@link DFARunner}
     */
    DFARunner runner();

    /**
     * Returns the state the DFA would transition to if currently in <code>state</code> and reading <code>symbol</code>.
     *
     * @param state  the state to transition from, should be an element in the set returned by <code>getStates()</code>
     * @param symbol the symbol to read, should be a symbol in the {@link Alphabet} returned by <code>getAlphabet()</code>
     * @return a String representation of the next state; null if in a dead state
     * @throws IllegalArgumentException if the state is invalid; if the symbol is invalid
     */
    String nextState(String state, String symbol);
}
