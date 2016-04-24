package me.saser.libinftom.dfa;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.nio.charset.Charset;

public class DFATestUtils {

    private DFA parseJSONFile(String fileName) throws Exception {
        String filePath = getClass().getClassLoader().getResource(fileName).getFile();
        File jsonFile = new File(filePath);
        String json = FileUtils.readFileToString(jsonFile, Charset.defaultCharset());
        return ImmutableDFA.fromJSON(json);
    }

}
