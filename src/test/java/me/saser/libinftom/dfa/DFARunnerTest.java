package me.saser.libinftom.dfa;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class DFARunnerTest {

    private static DFA dfa;
    private static final DFATestUtils utils = new DFATestUtils();

    @BeforeClass
    public static void testSetUp() throws Exception {
        dfa = utils.parseJSONFile("dfa/valid/begin_with_0_end_with_1.json");
    }

    @Test
    public void testReset() throws Exception {
        DFARunner runner = dfa.runner();

        // Consume a few symbols.
        runner.consume("0");
        runner.consume("1");
        runner.consume("0");
        runner.reset();

        assertEquals("Current state should be the initial state (q0) after resetting", dfa.getInitialState(), runner.getState());
    }

    @Test
    public void testConsume() throws Exception {
        DFARunner runner = dfa.runner();

        // Consume a 0, which should put us in state q1.
        runner.consume("0");
        assertEquals("Should be in q1 after consuming \"0\"", "q1", runner.getState());

        // Consume another 0, which should leave us in state q1.
        runner.consume("0");
        assertEquals("Should be in q1 after consuming another \"0\"", "q1", runner.getState());

        // Consume a 1, which should put us in state q2.
        runner.consume("1");
        assertEquals("Should be in q2 after consuming \"1\"", "q2", runner.getState());

        // Consume 0 once again, which should put us back in state q1.
        runner.consume("0");
        assertEquals("Should be in q1 after consuming another \"0\"", "q1", runner.getState());
    }

    @Test
    public void goToDeadState() throws Exception {
        DFARunner runner = dfa.runner();

        // Consuming a 1 in the initial state should put us in the dead state (which is modeled as null).
        runner.consume("1");
        assertNull("We should be in the dead state", runner.getState());

        // Consuming any symbol from here should still leave us in the dead state (null).
        runner.consume("0");
        assertNull("We should still be in the dead state", runner.getState());
        runner.consume("1");
        assertNull("We should still be in the dead state", runner.getState());
        runner.consume("1");
        assertNull("We should still be in the dead state", runner.getState());
        runner.consume("0");
        assertNull("We should still be in the dead state", runner.getState());
        runner.consume("1");
        assertNull("We should still be in the dead state", runner.getState());
        runner.consume("0");
        assertNull("We should still be in the dead state", runner.getState());
    }

}
