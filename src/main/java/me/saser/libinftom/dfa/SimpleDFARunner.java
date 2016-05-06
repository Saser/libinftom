package me.saser.libinftom.dfa;

public class SimpleDFARunner implements DFARunner {

    private String currentState;
    private DFA dfa;

    public SimpleDFARunner(DFA dfa) {
        this.dfa = dfa;
        this.currentState = dfa.getInitialState();
    }

    @Override
    public String getState() {
        return this.currentState;
    }

    @Override
    public String peek(String symbol) {
        return this.dfa.nextState(this.currentState, symbol);
    }

    @Override
    public String consume(String symbol) {
        String state = this.dfa.nextState(this.currentState, symbol);
        this.currentState = state;
        return state;
    }

    @Override
    public void reset() {
        this.currentState = this.dfa.getInitialState();
    }

    @Override
    public boolean isInFinalState() {
        return this.dfa.getFinalStates().contains(this.currentState);
    }
}
