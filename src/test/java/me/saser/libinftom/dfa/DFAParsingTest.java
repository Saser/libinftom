package me.saser.libinftom.dfa;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.nio.charset.Charset;

import static org.junit.Assert.*;

public class DFAParsingTest {

    @Test
    public void oneMissingSymbolInDelta() throws Exception {
        try {
            this.parseJSONFile("dfa/invalid/one_missing_symbol_in_delta_q0.json");

            fail("An exception should have been thrown");
        } catch (IllegalArgumentException e) {
            assertTrue("Exception message should contain \"exactly all symbols\"", e.getMessage().contains("exactly all symbols"));
        }
    }

    @Test
    public void oneExtraSymbolInDelta() throws Exception {
        try {
            this.parseJSONFile("dfa/invalid/one_extra_symbol_in_delta_q0.json");

            fail("An exception should have been thrown");
        } catch (IllegalArgumentException e) {
            assertTrue("Exception message should contain \"exactly all symbols\"", e.getMessage().contains("exactly all symbols"));
        }
    }

    @Test
    public void oneMissingStateInDelta() throws Exception {
        try {
            this.parseJSONFile("dfa/invalid/one_missing_state_in_delta.json");

            fail("An exception should have been thrown");
        } catch (IllegalArgumentException e) {
            assertTrue("Exception message should contain \"exactly all states\"", e.getMessage().contains("exactly all states"));
        }
    }

    @Test
    public void oneExtraStateInDelta() throws Exception {
        try {
            this.parseJSONFile("dfa/invalid/one_extra_state_in_delta.json");

            fail("An exception should have been thrown");
        } catch (IllegalArgumentException e) {
            assertTrue("Exception message should contain \"exactly all states\"", e.getMessage().contains("exactly all states"));
        }
    }

    @Test
    public void invalidTargetStateInTransition() throws Exception {
        try {
            this.parseJSONFile("dfa/invalid/invalid_target_state_in_delta.json");

            fail("An exception should have been thrown");
        } catch (IllegalArgumentException e) {
            assertTrue("Exception message should contain \"invalid target state\"", e.getMessage().contains("invalid target state"));
        }
    }

    @Test
    public void invalidInitialState() throws Exception {
        try {
            this.parseJSONFile("dfa/invalid/invalid_initial_state.json");

            fail("An exception should have been thrown");
        } catch (IllegalArgumentException e) {
            assertTrue("Exception message should contain \"invalid initial state\"", e.getMessage().contains("invalid initial state"));
        }
    }

    private DFA parseJSONFile(String fileName) throws Exception {
        String filePath = getClass().getClassLoader().getResource(fileName).getFile();
        File jsonFile = new File(filePath);
        String json = FileUtils.readFileToString(jsonFile, Charset.defaultCharset());
        return ImmutableDFA.fromJSON(json);
    }

}
