package me.saser.libinftom.dfa;

import me.saser.libinftom.Alphabet;

import java.util.Set;

public interface DFA {

    /**
     * Returns the alphabet as an Alphabet that this DFA is defined for.
     *
     * @return a Alphabet instance containing the alphabet
     */
    Alphabet getAlphabet();

    /**
     * Gets the current state that this DFA is in. If a non-null value is return, the returned String is guaranteed to
     * be an element in the set returned by <code>getStates()</code>.
     *
     * @return a String representation of the current state, or <code>null</code> if in a dead state
     */
    String getState();

    /**
     * Gets a Set of all possible states for this DFA. This set contains all states that the DFA was created with,
     * and so could possibly contain unreachable states.
     *
     * @return a Set of String representations of all possible states
     */
    Set<String> getStates();

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
     * Like <code>consume(String)</code>, but does not change the current state.
     *
     * @param symbol a String containing a symbol from this DFAs language
     * @return a String representation of the state this DFA would end up in, or <code>null</code> if this DFA would end
     * up in a dead state
     * @throws IllegalArgumentException if <code>symbol</code> is not a valid symbol in the language of this DFA
     */
    String peek(String symbol);

    /**
     * Return a String representation of the state this DFA ends up in after reading the given symbol. This method
     * changes the current state of this DFA.
     *
     * @param symbol a String containing a symbol from this DFAs language
     * @return a String representation of the new current state, or <code>null</code> if this DFA would end up in a dead
     * state
     * @throws IllegalArgumentException if <code>symbol</code> is not a valid symbol in the language of this DFA
     */
    String consume(String symbol);
}
