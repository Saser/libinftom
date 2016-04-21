package me.saser.libinftom.dfa;

public interface DFARunner {

    /**
     * Gets the current state that this DFA is in. If a non-null value is return, the returned String is guaranteed to
     * be an element in the set returned by <code>getStates()</code>.
     *
     * @return a String representation of the current state, or <code>null</code> if in a dead state
     */
    String getState();

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
