package me.saser.libinftom.dfa;

import com.google.common.collect.ImmutableSet;
import me.saser.libinftom.Alphabet;
import me.saser.libinftom.StringAlphabet;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.*;

public class DFATest {

    private static DFA dfa;
    private static final DFATestUtils utils = new DFATestUtils();

    @BeforeClass
    public static void setUp() throws Exception {
        dfa = utils.parseJSONFile("dfa/valid/begin_with_0_end_with_1.json");
    }

    @Test
    public void getStates() throws Exception {
        Set<String> expected = ImmutableSet.of("q0", "q1", "q2");
        assertEquals("The valid states should consist of \"q0\", \"q1\", and \"q2\"", expected, dfa.getStates());
    }

    @Test
    public void getAlphabet() throws Exception {
        Alphabet expected = new StringAlphabet("0,1");
        assertEquals("The alphabet should consist of \"0\" and \"1\"", expected, dfa.getAlphabet());
    }

    @Test
    public void testTransitions() throws Exception {
        // q0
        assertEquals("delta(q0, 0) should be q1", "q1", dfa.nextState("q0", "0"));
        assertNull("delta(q0, 1) should be dead state", dfa.nextState("q0", "1"));

        // q1
        assertEquals("delta(q1, 0) should be q1", "q1", dfa.nextState("q1", "0"));
        assertEquals("delta(q1, 1) should be q2", "q2", dfa.nextState("q1", "1"));

        // q2
        assertEquals("delta(q2, 0) should be q1", "q1", dfa.nextState("q2", "0"));
        assertEquals("delta(q2, 1) should be q2", "q2", dfa.nextState("q2", "1"));

        // Dead state (aka null)
        assertNull("delta(null, 0) should be null", dfa.nextState(null, "0"));
        assertNull("delta(null, 1) should be null", dfa.nextState(null, "1"));
    }

    @Test
    public void testInvalidSymbolInTransition() throws Exception {
        // Invalid non-null symbol
        try {
            dfa.nextState("q0", "2");

            fail("An exception should have been thrown");
        } catch (IllegalArgumentException e) {
            assertTrue("Exception message should contain \"invalid symbol\"", e.getMessage().contains("invalid symbol"));
        }

        // Null symbol
        try {
            dfa.nextState("q0", null);

            fail("An exception should have been thrown");
        } catch (IllegalArgumentException e) {
            assertTrue("Exception message should contain \"null is an invalid symbol\"", e.getMessage().contains("null is an invalid symbol"));
        }
    }

    @Test
    public void invalidStateInTransition() throws Exception {
        try {
            // "q3" is an invalid state
            dfa.nextState("q3", "0");

            fail("An exception should have been thrown");
        } catch (IllegalArgumentException e) {
            assertTrue("Exception message should contain \"invalid starting state\"", e.getMessage().contains("invalid starting state"));
        }
    }

    @Test
    public void getInitialState() throws Exception {
        assertEquals("The initial state should be \"q0\"", "q0", dfa.getInitialState());
    }

    @Test
    public void getFinalStates() throws Exception {
        Set<String> expected = ImmutableSet.of("q2");
        assertEquals("The only final state should be \"q2\"", expected, dfa.getFinalStates());
    }

}
