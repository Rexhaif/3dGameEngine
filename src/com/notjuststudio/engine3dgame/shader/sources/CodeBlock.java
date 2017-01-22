package com.notjuststudio.engine3dgame.shader.sources;

import static com.notjuststudio.engine3dgame.util.StringUtil.packageToString;
import static com.notjuststudio.engine3dgame.util.StringUtil.parseFile;
import static com.notjuststudio.engine3dgame.util.StringUtil.replace;

/**
 * Created by George on 21.01.2017.
 */
public class CodeBlock {

    private static final String DEFAULT_PATH = packageToString(CodeBlock.class);

    private String code;

    public CodeBlock(String file) {
        this.code = createCode(file);
    }

    private static String[] split(String[] input) {
        String[] strs = new String[input.length];
        for (int i = 0; i < input.length; i++) {
            strs[i] = input[i].split(" ")[1];
        }
        return strs;
    }

    public String parseCode(String[] input, String[] uniform, String[] output) {

        String[] tmpInput = split(input);
        String[] tmpUniform = split(uniform);
        String[] tmpOutput = split(output);

        String result = code;

        int index = -1;
        while ((index = result.indexOf("$", index + 1))!= -1) {
            int space = result.indexOf(" ", index);
            String inStr = "";
            switch (result.charAt(index + 1)) {
                case 'i':
                    inStr = tmpInput[Integer.parseInt(result.substring(index + 2, space))];
                    break;
                case 'u':
                    inStr = tmpUniform[Integer.parseInt(result.substring(index + 2, space))];
                    break;
                case 'o':
                    inStr = tmpOutput[Integer.parseInt(result.substring(index + 2, space))];
                    break;
            }
            result = replace(result, index, space - index, inStr);
        }

        return result;
    }

    private static String createCode(String file) {
        return parseFile(DEFAULT_PATH + file);
    }
}
