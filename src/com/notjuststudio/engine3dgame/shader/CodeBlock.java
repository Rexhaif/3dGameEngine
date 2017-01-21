package com.notjuststudio.engine3dgame.shader;

/**
 * Created by George on 21.01.2017.
 */
public class CodeBlock {

    private String[] input;
    private String[] output;
    private String code;

    public CodeBlock(String[] input, String[] output, String code) {
        this.input = input;
        this.output = output;
        this.code = code;
    }
}
