package com.notjuststudio.engine3dgame.shader;

import com.notjuststudio.engine3dgame.shader.sources.CodeBlock;

import java.util.ArrayList;
import java.util.List;

import static com.notjuststudio.engine3dgame.util.FlagsUtil.flagChecker;
import static com.notjuststudio.engine3dgame.util.StringUtil.packageToString;
import static com.notjuststudio.engine3dgame.util.StringUtil.parseFile;

/**
 * Created by George on 06.01.2017.
 */
public class ShadersBuilder {

    private static final String DEFAULT_PATH = packageToString(ShadersBuilder.class);

    private static final String vertexShaderDefault = parseFile(DEFAULT_PATH + "defaultVertex.glsl");
    private static final String geometryShaderDefault = parseFile(DEFAULT_PATH + "defaultGeometry.glsl");
    private static final String fragmentShaderDefault = parseFile(DEFAULT_PATH + "defaultFragment.glsl");

    private static final String vertexShaderSkybox = parseFile(DEFAULT_PATH + "skyboxVertex.glsl");
    private static final String geometryShaderSkybox = parseFile(DEFAULT_PATH + "skyboxGeometry.glsl");
    private static final String fragmentShaderSkybox = parseFile(DEFAULT_PATH + "skyboxFragment.glsl");

    private int version = 400;

    private String[] vertexUniform;
    private String[] geometryUniform;
    private String[] fragmentUniform;

    private String[] vertexOut;
    private String[] geometryOut;
    private String[] fragmentOut;

    private List<CodeBlock> vertexCodes = new ArrayList<>();
    private List<CodeBlock> geometryCodes = new ArrayList<>();
    private List<CodeBlock> fragmentCodes = new ArrayList<>();

    private int layouts = LAYOUT_POSITION | LAYOUT_UV | LAYOUT_NORM;

    private static final int LAYOUT_POSITION = 1 << 0;
    private static final int LAYOUT_UV = 1 << 1;
    private static final int LAYOUT_NORM = 1 << 2;
    private static final int LAYOUT_INDICES = 1 << 3;
    private static final int LAYOUT_WEIGHTS = 1 << 4;

    public ShadersBuilder() {}

    public ShadersBuilder setHeader(int version) {
        this.version = version;
        return this;
    }

    public ShadersBuilder setLayouts(int flags) {
        this.layouts = flags;
        return this;
    }

    public ShadersBuilder setVertexUniform(String[] uniform) {
        this.vertexUniform = uniform;
        return this;
    }

    public ShadersBuilder setGeometryUniform(String[] uniform) {
        this.geometryUniform = uniform;
        return this;
    }

    public ShadersBuilder setFragmentUniform(String[] uniform) {
        this.fragmentUniform = uniform;
        return this;
    }

    public ShadersBuilder setVertexOutput(String[] output) {
        this.vertexOut = output;
        return this;
    }

    public ShadersBuilder setGeometryOutput(String[] output) {
        this.geometryOut = output;
        return this;
    }

    public ShadersBuilder setFragmentOutput(String[] output) {
        this.fragmentOut = output;
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

    public static ShadersContainer createDefaultContainer() {
        return new ShadersContainer()
                .setVertexShader(vertexShaderDefault)
                .setGeometryShader(geometryShaderDefault)
                .setFragmentShader(fragmentShaderDefault);
    }

    public static ShadersContainer createSkyboxContainer() {
        return new ShadersContainer()
                .setVertexShader(vertexShaderSkybox)
                .setGeometryShader(geometryShaderSkybox)
                .setFragmentShader(fragmentShaderSkybox);
    }

    static final String VERTEX_POS = "vec3 vertexPos";
    static final String VERTEX_UV = "vec2 vertexUv";
    static final String VERTEX_NORM = "vec3 vertexNorm";
    static final String VERTEX_INDICES = "ivec4 vertexIndex";
    static final String VERTEX_WEIGHTS = "vec4 vertexWeight";

    static final String[] VERTEX_INPUT = {VERTEX_POS, VERTEX_UV, VERTEX_NORM, VERTEX_INDICES, VERTEX_WEIGHTS};

    static final String TRANSFORMATION_MATRIX = "transformationMatrix";
    static final String PROJECTION_MATRIX = "projectionMatrix";
    static final String VIEW_MATRIX = "viewMatrix";

    static final String OUT_UV = "uv";
    static final String OUT_SURFACE_NORMAL = "surfaceNormal";
    static final String OUT_TO_LIGHT_VECTOR = "toLightVector";
    static final String OUT_TO_CAMERA_VECTOR = "toCameraVector";

    private String makeHeader() {
        return "#version " + Integer.toString(version) + " core\n";
    }

    private StringBuilder addLayoutInput(int flags) {
        StringBuilder result = new StringBuilder();
        if (flagChecker(flags, LAYOUT_POSITION)) {
            result.append("layout(location = 0) in " + VERTEX_POS + ";\n");
        }
        if (flagChecker(flags, LAYOUT_UV)) {
            result.append("layout(location = 1) in " + VERTEX_UV + ";\n");
        }
        if (flagChecker(flags, LAYOUT_NORM)) {
            result.append("layout(location = 2) in " + VERTEX_NORM + ";\n");
        }
        if (flagChecker(flags, LAYOUT_INDICES)) {
            result.append("layout(location = 3) in " + VERTEX_INDICES + ";\n");
        }
        if (flagChecker(flags, LAYOUT_WEIGHTS)) {
            result.append("layout(location = 4) in " + VERTEX_WEIGHTS + ";\n");
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

    private StringBuilder addGeometryInput() {
        StringBuilder result = new StringBuilder();
        result.append(
                "layout ( triangles ) in;\n" +
                "layout ( triangle_strip, max_vertices = 3) out;\n"
        );
        for (String input : vertexOut) {
            result.append("in " + input + "[3];\n");
        }
        return result;
    }

    private StringBuilder makeBody(List<CodeBlock> codes, String[] input, String[] uniform, String[] output) {
        StringBuilder result = new StringBuilder();
        result.append("void main() {\n");

        for (CodeBlock code : codes) {
            result.append(code.parseCode(input, uniform, output) + "\n");
        }

        result.append("}\n");
        return result;
    }

    public ShadersContainer build() {
        ShadersContainer result = new ShadersContainer();
        String header = makeHeader();

        StringBuilder vertex = new StringBuilder();
        vertex.append(header);
        vertex.append(addLayoutInput(layouts));
        vertex.append(addOutput(vertexOut));
        vertex.append(addUniform(vertexUniform));
        vertex.append(makeBody(vertexCodes, VERTEX_INPUT, vertexUniform, vertexOut));
        result.setVertexShader(vertex.toString());

        StringBuilder geometry = new StringBuilder();
        geometry.append(header);
        geometry.append(addGeometryInput());
        geometry.append(addOutput(geometryOut));
        geometry.append(addUniform(geometryUniform));
        geometry.append(makeBody(geometryCodes, vertexOut, geometryUniform, geometryOut));
        result.setGeometryShader(geometry.toString());

        StringBuilder fragment = new StringBuilder();
        fragment.append(header);
        fragment.append(addInput(geometryOut));
        fragment.append(addOutput(fragmentOut));
        fragment.append(addUniform(fragmentUniform));
        fragment.append(makeBody(fragmentCodes, geometryOut, fragmentUniform, fragmentOut));
        result.setFragmentShader(fragment.toString());

        return result;
    }

}
