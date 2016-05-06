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
    public void testCreate() throws Exception {
        DFARunner runner = dfa.runner();

        assertEquals("Should be in initial state (q0) at creation", dfa.getInitialState(), runner.getState());
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
    public void testPeek() throws Exception {
        DFARunner runner = dfa.runner();

        assertEquals("Peeking \"0\" should return q1", "q1", runner.peek("0"));
        assertEquals("State should still be q0 after peeking", "q0", runner.getState());

        assertNull("Peeking \"1\" should return null", runner.peek("1"));
        assertEquals("State should still be q0 after peeking", "q0", runner.getState());

        // Consume a few symbols to eventually end up in q2.
        runner.consume("0");
        runner.consume("1");
        runner.consume("1");
        runner.consume("0");
        runner.consume("1");

        assertEquals("Peeking \"0\" should return q1", "q1", runner.peek("0"));
        assertEquals("State should still be q2 after peeking", "q2", runner.getState());

        assertEquals("Peeking \"1\" should return q2", "q2", runner.peek("1"));
        assertEquals("State should still be q2 after peeking", "q2", runner.getState());
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

    @Test
    public void exceptionOnImmediatelyConsumingInvalidSymbol() throws Exception {
        DFARunner runner = dfa.runner();

        try {
            runner.consume("2");

            fail("An exception should have been thrown");
        } catch (IllegalArgumentException e) {
            assertTrue("Exception message should contain \"invalid symbol\"", e.getMessage().contains("invalid symbol"));
        }
    }

    @Test
    public void exceptionOnConsumingInvalidSymbol() throws Exception {
        DFARunner runner = dfa.runner();

        // Consume a few symbols to eventually put us in state q1.
        runner.consume("0"); // q1
        runner.consume("0"); // q1
        runner.consume("1"); // q2
        runner.consume("0"); // q1
        runner.consume("1"); // q2
        runner.consume("0"); // q1

        try {
            runner.consume("2");

            fail("An exception should have been thrown");
        } catch (IllegalArgumentException e) {
            assertTrue("Exception message should contain \"invalid symbol\"", e.getMessage().contains("invalid symbol"));
        }
    }

    @Test
    public void exceptionOnConsumingInvalidSymbolFromDeadState() throws Exception {
        DFARunner runner = dfa.runner();

        // Put us in the dead state.
        runner.consume("1");

        try {
            runner.consume("2");

            fail("An exception should have been thrown");
        } catch (IllegalArgumentException e) {
            assertTrue("Exception message should contain \"invalid symbol\"", e.getMessage().contains("invalid symbol"));
        }
    }

    @Test
    public void exceptionOnImmediatelyConsumingEmptySymbol() throws Exception {
        DFARunner runner = dfa.runner();

        try {
            runner.consume("");

            fail("An exception should have been thrown");
        } catch (IllegalArgumentException e) {
            assertTrue("Exception message should contain \"empty symbol\"", e.getMessage().contains("empty symbol"));
        }
    }

    @Test
    public void exceptionOnConsumingEmptySymbol() throws Exception {
        DFARunner runner = dfa.runner();

        // Consume a few symbols to eventually put us in state q1.
        runner.consume("0"); // q1
        runner.consume("0"); // q1
        runner.consume("1"); // q2
        runner.consume("0"); // q1
        runner.consume("1"); // q2
        runner.consume("0"); // q1

        try {
            runner.consume("");

            fail("An exception should have been thrown");
        } catch (IllegalArgumentException e) {
            assertTrue("Exception message should contain \"empty symbol\"", e.getMessage().contains("empty symbol"));
        }
    }

    @Test
    public void exceptionOnConsumingEmptySymbolFromDeadState() throws Exception {
        DFARunner runner = dfa.runner();

        // Put us in the dead state.
        runner.consume("1");

        try {
            runner.consume("");

            fail("An exception should have been thrown");
        } catch (IllegalArgumentException e) {
            assertTrue("Exception message should contain \"empty symbol\"", e.getMessage().contains("empty symbol"));
        }
    }

    @Test
    public void exceptionOnImmediatelyPeekingInvalidSymbol() throws Exception {
        DFARunner runner = dfa.runner();

        try {
            runner.peek("2");

            fail("An exception should have been thrown");
        } catch (IllegalArgumentException e) {
            assertTrue("Exception message should contain \"invalid symbol\"", e.getMessage().contains("invalid symbol"));
        }
    }

    @Test
    public void exceptionOnPeekingInvalidSymbol() throws Exception {
        DFARunner runner = dfa.runner();

        // Consume a few symbols to eventually put us in state q1.
        runner.consume("0"); // q1
        runner.consume("0"); // q1
        runner.consume("1"); // q2
        runner.consume("0"); // q1
        runner.consume("1"); // q2
        runner.consume("0"); // q1

        try {
            runner.peek("2");

            fail("An exception should have been thrown");
        } catch (IllegalArgumentException e) {
            assertTrue("Exception message should contain \"invalid symbol\"", e.getMessage().contains("invalid symbol"));
        }
    }

    @Test
    public void exceptionOnPeekingInvalidSymbolFromDeadState() throws Exception {
        DFARunner runner = dfa.runner();

        // Put us in the dead state.
        runner.consume("1");

        try {
            runner.peek("2");

            fail("An exception should have been thrown");
        } catch (IllegalArgumentException e) {
            assertTrue("Exception message should contain \"invalid symbol\"", e.getMessage().contains("invalid symbol"));
        }
    }

    @Test
    public void exceptionOnImmediatelyPeekingEmptySymbol() throws Exception {
        DFARunner runner = dfa.runner();

        try {
            runner.peek("");

            fail("An exception should have been thrown");
        } catch (IllegalArgumentException e) {
            assertTrue("Exception message should contain \"empty symbol\"", e.getMessage().contains("empty symbol"));
        }
    }

    @Test
    public void exceptionOnPeekingEmptySymbol() throws Exception {
        DFARunner runner = dfa.runner();

        // Consume a few symbols to eventually put us in state q1.
        runner.consume("0"); // q1
        runner.consume("0"); // q1
        runner.consume("1"); // q2
        runner.consume("0"); // q1
        runner.consume("1"); // q2
        runner.consume("0"); // q1

        try {
            runner.peek("");

            fail("An exception should have been thrown");
        } catch (IllegalArgumentException e) {
            assertTrue("Exception message should contain \"empty symbol\"", e.getMessage().contains("empty symbol"));
        }
    }

    @Test
    public void exceptionOnPeekingEmptySymbolFromDeadState() throws Exception {
        DFARunner runner = dfa.runner();

        // Put us in the dead state.
        runner.consume("1");

        try {
            runner.peek("");

            fail("An exception should have been thrown");
        } catch (IllegalArgumentException e) {
            assertTrue("Exception message should contain \"empty symbol\"", e.getMessage().contains("empty symbol"));
        }
    }

    @Test
    public void inFinalState() throws Exception {
        DFARunner runner = dfa.runner();

        // Put us in state q2, which is final.
        runner.consume("0");
        runner.consume("1");

        assertTrue("We should now be in the final state q2", runner.isInFinalState());
    }

}
