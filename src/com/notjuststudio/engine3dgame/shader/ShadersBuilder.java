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
                    "layout(location = 2) in vec3 vertexNorm;\n" +
                    "\n" +
                    "out vec2 uv;\n" +
                    "out vec3 surfaceNormal;\n" +
                    "out vec3 toLightVector;\n" +
                    "out vec3 toCameraVector;\n" +
                    "\n" +
                    "uniform mat4 transformationMatrix;\n" +
                    "uniform mat4 projectionMatrix;\n" +
                    "uniform mat4 viewMatrix;\n" +
                    "uniform vec3 lightPosition;\n" +
                    "\n" +
                    "void main() {\n" +
                    "  vec4 worldPosition = transformationMatrix * vec4(vertexPos,1.0);\n" +
                    "  gl_Position = viewMatrix * projectionMatrix * worldPosition;\n" +
                    "  uv = vertexUv;\n" +
                    "\n" +
                    "  surfaceNormal = (transformationMatrix * vec4(vertexNorm, 0.0)).xyz;\n" +
                    "  toLightVector = lightPosition - worldPosition.xyz;\n" +
                    "  toCameraVector = (inverse(viewMatrix) * vec4(0,0,0,1)).xyz - worldPosition.xyz;\n"+
                    "}\n";
    private String geometryShaderExample =
            "#version 400 core\n" +
                    "\n" +
                    "layout ( triangles ) in;\n" +
                    "layout ( triangle_strip, max_vertices = 3) out;\n" +
                    "\n" +
                    "in vec2 uv[3];\n" +
                    "in vec3 surfaceNormal[3];\n" +
                    "in vec3 toLightVector[3];\n" +
                    "in vec3 toCameraVector[3];\n" +
                    "\n" +
                    "out vec2 finalUV;\n" +
                    "out vec3 finalSurfaceNormal;\n" +
                    "out vec3 finalToLightVector;\n" +
                    "out vec3 finalCameraVector;\n" +
                    "\n" +
                    "void main() {\n" +
                    "for(int i = 0; i < 3; i++) {\n" +
                    "gl_Position = gl_in[i].gl_Position;\n" +
                    "finalUV = uv[i];\n" +
                    "finalSurfaceNormal = surfaceNormal[i];\n" +
                    "finalToLightVector = toLightVector[i];\n" +
                    "EmitVertex();\n" +
                    "}\n" +
                    "EndPrimitive();\n" +
                    "}\n";
    private String fragmentShaderExample =
            "#version 400 core\n" +
                    "\n" +
                    "in vec2 finalUV;\n" +
                    "in vec3 finalSurfaceNormal;\n" +
                    "in vec3 finalToLightVector;\n" +
                    "in vec3 finalCameraVector;\n" +
                    "\n" +
                    "out vec4 out_Colour;\n" +
                    "\n" +
                    "uniform sampler2D textureSampler;" +
                    "uniform vec3 lightColour;\n" +
                    "uniform float shineDamper;\n" +
                    "uniform float reflectivity;\n" +
                    "\n" +
                    "void main() {\n" +
                    "\n" +
                    "  vec3 unitNormal = normalize(finalSurfaceNormal);\n"+
                    "  vec3 unitLightVector = normalize(finalToLightVector);\n"+
                    "\n" +
                    "  float nDotl = dot(unitNormal,unitLightVector);\n"+
                    "  float brightness = max(nDotl,0.2);\n"+
                    "  vec3 diffuse = brightness * lightColour;\n"+
                    "\n" +
                    "  vec3 unitVectorToCamera = normalize(finalCameraVector);\n"+
                    "  vec3 lightDirection = -unitLightVector;\n"+
                    "  vec3 reflectedLightDirection = reflect(lightDirection, unitNormal);\n" +
                    "\n" +
                    "  float specularFactor = dot(reflectedLightDirection, unitVectorToCamera);\n" +
                    "  specularFactor = max(specularFactor, 0.0);\n" +
                    "  float dampedFactor = pow(specularFactor, shineDamper);\n" +
                    "  vec3 finalSpecular = dampedFactor * lightColour;\n" +
                    "\n" +
                    "  out_Colour = vec4(diffuse, 1.0) * texture(textureSampler, finalUV) + vec4(finalSpecular,1.0);\n" +
                    "}\n";

    private String vertexShader = "";
    private String geometryShader = "";
    private String fragmentShader = "";

    private boolean hasVertex = false;
    private boolean hasGeometry = false;
    private boolean hasFragment = false;

    public static final String NAME_VERTEX_POSITION = "vertexPos";
    public static final String NAME_VERTEX_UV = "vertexUv";

    private static final int LAYOUT_VERTEX_POSITION = 1;
    private static final int LAYOUT_VERTEX_UV = 1 << 1;

    public ShadersBuilder setHeader(int version) {
        this.version = version;
        return this;
    }

    private static final String VERSION = "#version ";
    private static final String CORE = " core\n";

    private int version;

    private String makeHeader() {
        return VERSION + Integer.toString(version) + CORE;
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
        return new ShadersContainer()
                .setVertexShader(vertexShaderExample)
                .setGeometryShader(geometryShaderExample)
                .setFragmentShader(fragmentShaderExample);
    }

}
