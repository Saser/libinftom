package me.saser.libinftom.dfa;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.nio.charset.Charset;

import static org.junit.Assert.*;

public class DFAParsingTest {

    private static final DFATestUtils utils = new DFATestUtils();

    @Test
    public void emptyStringInStates() throws Exception {
        try {
            utils.parseJSONFile("dfa/invalid/empty_string_in_states.json");

            fail("An exception should have been thrown");
        } catch (IllegalArgumentException e) {
            assertTrue("Exception message should contain \"empty state disallowed\"", e.getMessage().contains("empty state disallowed"));
        }
    }

    @Test
    public void oneMissingSymbolInDelta() throws Exception {
        try {
            utils.parseJSONFile("dfa/invalid/one_missing_symbol_in_delta_q0.json");

            fail("An exception should have been thrown");
        } catch (IllegalArgumentException e) {
            assertTrue("Exception message should contain \"exactly all symbols\"", e.getMessage().contains("exactly all symbols"));
        }
    }

    @Test
    public void oneExtraSymbolInDelta() throws Exception {
        try {
            utils.parseJSONFile("dfa/invalid/one_extra_symbol_in_delta_q0.json");

            fail("An exception should have been thrown");
        } catch (IllegalArgumentException e) {
            assertTrue("Exception message should contain \"exactly all symbols\"", e.getMessage().contains("exactly all symbols"));
        }
    }

    @Test
    public void oneMissingStateInDelta() throws Exception {
        try {
            utils.parseJSONFile("dfa/invalid/one_missing_state_in_delta.json");

            fail("An exception should have been thrown");
        } catch (IllegalArgumentException e) {
            assertTrue("Exception message should contain \"exactly all states\"", e.getMessage().contains("exactly all states"));
        }
    }

    @Test
    public void oneExtraStateInDelta() throws Exception {
        try {
            utils.parseJSONFile("dfa/invalid/one_extra_state_in_delta.json");

            fail("An exception should have been thrown");
        } catch (IllegalArgumentException e) {
            assertTrue("Exception message should contain \"exactly all states\"", e.getMessage().contains("exactly all states"));
        }
    }

    @Test
    public void invalidTargetStateInTransition() throws Exception {
        try {
            utils.parseJSONFile("dfa/invalid/invalid_target_state_in_delta.json");

            fail("An exception should have been thrown");
        } catch (IllegalArgumentException e) {
            assertTrue("Exception message should contain \"invalid target state\"", e.getMessage().contains("invalid target state"));
        }
    }

    @Test
    public void invalidInitialState() throws Exception {
        try {
            utils.parseJSONFile("dfa/invalid/invalid_initial_state.json");

            fail("An exception should have been thrown");
        } catch (IllegalArgumentException e) {
            assertTrue("Exception message should contain \"invalid initial state\"", e.getMessage().contains("invalid initial state"));
        }
    }

    @Test
    public void invalidFinalState() throws Exception {
        try {
            utils.parseJSONFile("dfa/invalid/invalid_final_states.json");

            fail("An exception should have been thrown");
        } catch (IllegalArgumentException e) {
            assertTrue("Exception message should contain \"invalid final state\"", e.getMessage().contains("invalid final state"));
        }
    }

}
