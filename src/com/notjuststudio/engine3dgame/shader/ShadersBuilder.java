package com.notjuststudio.engine3dgame.shader;

import com.notjuststudio.engine3dgame.shader.sources.CodeBlock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.notjuststudio.engine3dgame.util.ListUtil.*;

/**
 * Created by George on 06.01.2017.
 */
public class ShadersBuilder {

    private int version = 400;

    private List<CodeBlock> vertexCodes = new ArrayList<>();
    private List<CodeBlock> geometryCodes = new ArrayList<>();
    private List<CodeBlock> fragmentCodes = new ArrayList<>();

    public ShadersBuilder() {
    }

    public ShadersBuilder setHeader(int version) {
        this.version = version;
        return this;
    }

    public ShadersBuilder addVertexCode(CodeBlock code) {
        this.vertexCodes.add(code);
        return this;
    }

    public ShadersBuilder addGeometryCode(CodeBlock code) {
        this.geometryCodes.add(code);
        return this;
    }

    public ShadersBuilder addFragmentCode(CodeBlock code) {
        this.fragmentCodes.add(code);
        return this;
    }


    static final String VERTEX_POS_2 = "vec2 vertexPos";
    static final String VERTEX_POS_3 = "vec3 vertexPos";
    static final String VERTEX_UV = "vec2 vertexUv";
    static final String VERTEX_NORM = "vec3 vertexNorm";
    static final String VERTEX_INDICES = "ivec4 vertexIndex";
    static final String VERTEX_WEIGHTS = "vec4 vertexWeight";

    static final String[] VERTEX_INPUT = {VERTEX_POS_2, VERTEX_POS_3, VERTEX_UV, VERTEX_NORM, VERTEX_INDICES, VERTEX_WEIGHTS};

    private String makeHeader() {
        return "#version " + Integer.toString(version) + " core\n";
    }

    private StringBuilder addLayoutInput(String[] layouts) {
        StringBuilder result = new StringBuilder();
        for (String input : layouts) {
            switch (input) {
                case VERTEX_POS_2:
                    result.append("layout(location = 0) in " + VERTEX_POS_2 + ";\n");
                    break;
                case VERTEX_POS_3:
                    result.append("layout(location = 0) in " + VERTEX_POS_3 + ";\n");
                    break;
                case VERTEX_UV:
                    result.append("layout(location = 1) in " + VERTEX_UV + ";\n");
                    break;
                case VERTEX_NORM:
                    result.append("layout(location = 2) in " + VERTEX_NORM + ";\n");
                    break;
                case VERTEX_INDICES:
                    result.append("layout(location = 3) in " + VERTEX_INDICES + ";\n");
                    break;
                case VERTEX_WEIGHTS:
                    result.append("layout(location = 4) in " + VERTEX_WEIGHTS + ";\n");
                    break;
            }
        }

        return result;
    }

    private StringBuilder addInput(String[] inputs) {
        StringBuilder result = new StringBuilder();
        for (String input : inputs) {
            result.append("in " + input + ";\n");
        }
        return result;
    }

    private StringBuilder addOutput(String[] inputs) {
        StringBuilder result = new StringBuilder();
        for (String input : inputs) {
            result.append("out " + input + ";\n");
        }
        return result;
    }

    private StringBuilder addUniform(String[] inputs) {
        StringBuilder result = new StringBuilder();
        for (String input : inputs) {
            result.append("uniform " + input + ";\n");
        }
        return result;
    }

    private StringBuilder addGeometryLayout() {
        StringBuilder result = new StringBuilder();
        result.append(
                "layout ( triangles ) in;\n" +
                        "layout ( triangle_strip, max_vertices = 3) out;\n"
        );
        return result;
    }

    private String[] createGeometryInput(String[] out) {
        String[] result = new String[out.length];
        for (int i = 0; i < out.length; i++) {
            result[i] = out[i] + "[3]";
        }
        return result;
    }

    private StringBuilder makeBody(List<CodeBlock> codes) {
        StringBuilder result = new StringBuilder();
        result.append("void main() {\n");

        for (CodeBlock code : codes) {
            result.append(code.getCode() + "\n");
        }

        result.append("}\n");
        return result;
    }

    public ShadersContainer build() {
        boolean geom = !geometryCodes.isEmpty();

        ShadersContainer result = new ShadersContainer();
        String header = makeHeader();

        StringBuilder vertex = new StringBuilder();
        vertex.append(header);

        StringBuilder geometry = null;
        if (geom) {
            geometry = new StringBuilder();
            geometry.append(header);
        }

        StringBuilder fragment = new StringBuilder();
        fragment.append(header);

        List<CodeBlock> reversed;

        List<String> outputsTmp;
        List<String> inputsTmp;

        Collections.reverse(reversed = new ArrayList<>(fragmentCodes));

        List<String> fragOut = new ArrayList<>();
        List<String> fragIn = new ArrayList<>();

        for (CodeBlock code : reversed) {
            outputsTmp = new ArrayList<>(Arrays.asList(code.getOutput()));
            inputsTmp = new ArrayList<>(Arrays.asList(code.getInput()));

            deleteEqualString(outputsTmp, inputsTmp);
            deleteEqualString(outputsTmp, fragIn);

            addUnrepeatedString(fragIn, inputsTmp);
            addUnrepeatedString(fragOut, outputsTmp);
        }

        List<String> geomOut = null;
        List<String> geomIn = null;

        if (geom) {
            Collections.reverse(reversed = new ArrayList<>(geometryCodes));

            geomOut = new ArrayList<>();
            geomIn = new ArrayList<>();

            for (CodeBlock code : reversed) {
                outputsTmp = new ArrayList<>(Arrays.asList(code.getOutput()));
                inputsTmp = new ArrayList<>(Arrays.asList(code.getInput()));

                deleteEqualString(outputsTmp, inputsTmp);
                deleteEqualString(outputsTmp, geomIn);

                addUnrepeatedString(geomIn, inputsTmp);
                addUnrepeatedString(geomOut, outputsTmp);
            }

        }

        Collections.reverse(reversed = new ArrayList<>(vertexCodes));

        List<String> vertOut = new ArrayList<>();
        List<String> vertIn = new ArrayList<>();

        for (CodeBlock code : reversed) {
            outputsTmp = new ArrayList<>(Arrays.asList(code.getOutput()));
            inputsTmp = new ArrayList<>(Arrays.asList(code.getInput()));

            deleteEqualString(outputsTmp, inputsTmp);
            deleteEqualString(outputsTmp, vertIn);

            addUnrepeatedString(vertIn, inputsTmp);
            addUnrepeatedString(vertOut, outputsTmp);
        }

        List<String> in = createSameString(vertIn, Arrays.asList(VERTEX_INPUT));

        vertex.append(addLayoutInput(in.toArray(new String[in.size()])));

        List<String> uni = createMinusString(vertIn, in);
        vertex.append(addUniform(uni.toArray(new String[uni.size()])));

        String[] vertOutArray = vertOut.toArray(new String[vertOut.size()]);
        vertex.append(addOutput(vertOutArray));

        if (geom) {
            geometry.append(addGeometryLayout());
            String[] geomInArray = createGeometryInput(vertOutArray);
            geometry.append(addInput(geomInArray));

            uni = createMinusString(geomIn, Arrays.asList(geomInArray));
            geometry.append(addUniform(uni.toArray(new String[uni.size()])));

            geometry.append(addOutput(geomOut.toArray(new String[geomOut.size()])));
        }

        if (geom)
            uni = createMinusString(fragIn, geomOut);
        else
            uni = createMinusString(fragIn, vertOut);
        in = createMinusString(fragIn, uni);

        fragment.append(addInput(in.toArray(new String[in.size()])));
        fragment.append(addUniform(uni.toArray(new String[uni.size()])));
        fragment.append(addOutput(fragOut.toArray(new String[fragOut.size()])));

        vertex.append(makeBody(vertexCodes));
        if (geom)
            geometry.append(makeBody(geometryCodes));
        fragment.append(makeBody(fragmentCodes));

//        System.out.println(vertex.toString());
//        if (geom)
//            System.out.println(geometry.toString());
//        System.out.println(fragment.toString());

        result.setVertexShader(vertex.toString());
        if (geom)
            result.setGeometryShader(geometry.toString());
        result.setFragmentShader(fragment.toString());

        return result;
    }

}
