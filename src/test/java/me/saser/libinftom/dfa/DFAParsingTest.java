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
            this.parseJSONFile("dfa/invalid/extra_symbol_in_delta_q0.json");

            fail("An exception should have been thrown");
        } catch (IllegalArgumentException e) {
            assertTrue("Exception message should contain \"exactly all symbols\"", e.getMessage().contains("exactly all symbols"));
        }
    }

    private DFA parseJSONFile(String fileName) throws Exception {
        String filePath = getClass().getClassLoader().getResource(fileName).getFile();
        File jsonFile = new File(filePath);
        String json = FileUtils.readFileToString(jsonFile, Charset.defaultCharset());
        return ImmutableDFA.fromJSON(json);
    }

}
