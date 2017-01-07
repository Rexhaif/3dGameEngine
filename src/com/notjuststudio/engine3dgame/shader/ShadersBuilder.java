package com.notjuststudio.engine3dgame.shader;

/**
 * Created by George on 06.01.2017.
 */
public class ShadersBuilder {

    private String vertexShaderExample =
            "#version 400 core\n" +
                    "\n" +
                    "layout(location = 0) in vec3 vertexPos;\n" +
                    "layout(location = 1) in vec2 vertexUv;\n" +
                    "\n" +
                    "out vec2 uv;\n" +
                    "\n" +
                    "uniform mat4 transformationMatrix;\n" +
                    "\n" +
                    "void main() {\n" +
                    "  gl_Position = transformationMatrix * vec4(vertexPos,1.0);\n" +
                    "  uv = vertexUv;\n" +
                    "}\n";
    private String geometryShaderExample =
            "#version 400 core\n" +
                    "\n" +
                    "layout ( triangles ) in;\n" +
                    "layout ( triangle_strip, max_vertices = 3) out;\n" +
                    "\n" +
                    "in vec2 uv[3];\n" +
                    "\n" +
                    "out vec2 finalUV;\n" +
                    "\n" +
                    "void main() {\n" +
                    "for(int i = 0; i < 3; i++) {\n" +
                    "gl_Position = gl_in[i].gl_Position;\n" +
                    "finalUV = uv[i];\n" +
                    "EmitVertex();\n" +
                    "}\n" +
                    "EndPrimitive();\n" +
                    "}\n";
    private String fragmentShaderExample =
            "#version 400 core\n" +
                    "\n" +
                    "in vec2 finalUV;\n" +
                    "\n" +
                    "out vec4 out_Colour;\n" +
                    "\n" +
                    "uniform sampler2D textureSampler;" +
                    "\n" +
                    "void main() {\n" +
                    "  out_Colour = texture(textureSampler, finalUV);\n" +
                    "}\n";

    private String vertexShader = "";
    private String geometryShader = "";
    private String fragmentShader = "";

    private static final String VERSION = "#version ";
    private static final String CORE = " core\n";

    public static final String NAME_VERTEX_POSITION = "vertexPos";
    public static final String NAME_VERTEX_UV = "vertexUv";

    private static final int LAYOUT_VERTEX_POSITION = 1;
    private static final int LAYOUT_VERTEX_UV = 1 << 1;

    public ShadersBuilder addHeader(int version) {
        String core_ver = VERSION + Integer.toString(version) + CORE;
        vertexShader += core_ver;
        geometryShader += core_ver;
        fragmentShader += core_ver;
        return this;
    }

    public ShadersBuilder addVertexInput(int type) {
        if ((type & 1) == 1) {
            vertexShader += "layout(location = 0) in vec3 " + NAME_VERTEX_POSITION + ";\n";
        }
        if (((type >> 1) & 1) == 1) {
            vertexShader += "layout(location = 1) in vec2 " + NAME_VERTEX_UV + ";\n";
        }
        return this;
    }

    public ShadersContainer createDefaultContainer() {
        return new ShadersContainer(vertexShaderExample, geometryShaderExample, fragmentShaderExample);
    }

}
