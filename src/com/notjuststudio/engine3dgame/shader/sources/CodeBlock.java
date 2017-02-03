package com.notjuststudio.engine3dgame.shader.sources;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static com.notjuststudio.engine3dgame.util.StringUtil.packageToString;
import static com.notjuststudio.engine3dgame.util.StringUtil.parseFile;

/**
 * Created by George on 27.01.2017.
 */
public class CodeBlock {

    private static final String DEFAULT_PATH = packageToString(CodeBlock.class);

    private String[] input;
    private String[] output;

    private String code;

    public CodeBlock(String file) {
        Scanner in = new Scanner(parseFile(DEFAULT_PATH + file));

        List<String> inputs = new ArrayList<>();
        List<String> outputs = new ArrayList<>();


        StringBuilder code = new StringBuilder();

        gothrow:
        while (in.hasNext()) {
            String str = in.nextLine();

            String[] splits;

            switch ((splits = str.split("\\s+"))[0]) {
                case "in":
                case "uniform":
                    inputs.add(splits[1] + " " + (splits[2].endsWith(";") ? splits[2].substring(0, splits[2].length() - 1) : splits[2]));
                    break;
                case "out":
                    outputs.add(splits[1] + " " + (splits[2].endsWith(";") ? splits[2].substring(0, splits[2].length() - 1) : splits[2]));
                    break;
                case "void":
                    str = in.nextLine();
                    while (in.hasNext()) {
                        code.append(str).append("\n");
                        str = in.nextLine();
                    }
                    break gothrow;
            }

        }

        this.input = inputs.toArray(new String[inputs.size()]);
        this.output = outputs.toArray(new String[outputs.size()]);

        this.code = code.toString();
    }

    public String[] getInput() {
        return input;
    }

    public String[] getOutput() {
        return output;
    }

    public String getCode() {
        return code;
    }
}
