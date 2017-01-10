package com.notjuststudio.engine3dgame.shader;

/**
 * Created by George on 06.01.2017.
 */
public class ShadersBuilder {

    private static String vertexShaderDefault =
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
                    "  gl_Position = projectionMatrix * viewMatrix * worldPosition;\n" +
                    "  uv = vertexUv;\n" +
                    "\n" +
                    "  surfaceNormal = (transformationMatrix * vec4(vertexNorm, 0.0)).xyz;\n" +
                    "  toLightVector = lightPosition - worldPosition.xyz;\n" +
                    "  toCameraVector = (inverse(viewMatrix) * vec4(0,0,0,1)).xyz - worldPosition.xyz;\n" +
                    "}\n";
    private static String geometryShaderDefault =
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
    private static String fragmentShaderDefault =
            "#version 400 core\n" +
                    "\n" +
                    "in vec2 finalUV;\n" +
                    "in vec3 finalSurfaceNormal;\n" +
                    "in vec3 finalToLightVector;\n" +
                    "in vec3 finalCameraVector;\n" +
                    "\n" +
                    "out vec4 out_Colour;\n" +
                    "\n" +
                    "uniform sampler2D textureSampler;\n" +
                    "uniform vec3 lightColour;\n" +
                    "uniform float shineDamper;\n" +
                    "uniform float reflectivity;\n" +
                    "\n" +
                    "void main() {\n" +
                    "\n" +
                    "  vec3 unitNormal = normalize(finalSurfaceNormal);\n" +
                    "  vec3 unitLightVector = normalize(finalToLightVector);\n" +
                    "\n" +
                    "  float nDotl = dot(unitNormal,unitLightVector);\n" +
                    "  float brightness = max(nDotl,0.2);\n" +
                    "  vec3 diffuse = brightness * lightColour;\n" +
                    "\n" +
                    "  vec3 unitVectorToCamera = normalize(finalCameraVector);\n" +
                    "  vec3 lightDirection = -unitLightVector;\n" +
                    "  vec3 reflectedLightDirection = reflect(lightDirection, unitNormal);\n" +
                    "\n" +
                    "  float specularFactor = dot(reflectedLightDirection, unitVectorToCamera);\n" +
                    "  specularFactor = max(specularFactor, 0.0);\n" +
                    "  float dampedFactor = pow(specularFactor, shineDamper);\n" +
                    "  vec3 finalSpecular = dampedFactor * lightColour;\n" +
                    "\n" +
                    "  out_Colour = vec4(diffuse, 1.0) * texture(textureSampler, finalUV) + vec4(finalSpecular,1.0);\n" +
                    "}\n";

    private static String vertexShaderSkybox =
            "#version 400 core\n" +
                    "\n" +
                    "layout(location = 0) in vec3 vertexPos;\n" +
                    "\n" +
                    "out vec3 uv;\n" +
                    "\n" +
                    "uniform mat4 projectionMatrix;\n" +
                    "uniform mat4 viewMatrix;\n" +
                    "\n" +
                    "void main() {\n" +
                    "  gl_Position = projectionMatrix * viewMatrix * vec4(vertexPos,1.0);\n" +
                    "  uv = vertexPos;\n" +
                    "}\n";

    private static String geometryShaderSkybox =
            "#version 400 core\n" +
                    "\n" +
                    "layout ( triangles ) in;\n" +
                    "layout ( triangle_strip, max_vertices = 3) out;\n" +
                    "\n" +
                    "in vec3 uv[3];\n" +
                    "\n" +
                    "out vec3 finalUV;\n" +
                    "\n" +
                    "void main() {\n" +
                    "for(int i = 0; i < 3; i++) {\n" +
                    "gl_Position = gl_in[i].gl_Position;\n" +
                    "finalUV = uv[i];\n" +
                    "EmitVertex();\n" +
                    "}\n" +
                    "EndPrimitive();\n" +
                    "}\n";

    private static String fragmentShaderSkybox =
            "#version 400 core\n" +
                    "\n" +
                    "in vec3 finalUV;\n" +
                    "\n" +
                    "out vec4 out_Colour;\n" +
                    "\n" +
                    "uniform samplerCube cubeMap;\n" +
                    "\n" +
                    "\n" +
                    "void main() {\n" +
                    "  out_Colour = texture(cubeMap, normalize(finalUV));\n" +
                    "}\n";


    private VertexShader vertexShader = null;
    private GeometryShader geometryShader = null;
    private FragmentShader fragmentShader = null;

    public static final int VERTEX_SHADER = 1 << 0;
    public static final int GEOMETRY_SHADER = 1 << 1;
    public static final int FRAGMENT_SHADER = 1 << 2;

    private int shaders;

    private static final int LAYOUT_VERTEX_POSITION = 1;
    private static final int LAYOUT_VERTEX_UV = 1 << 1;

    public ShadersBuilder(int version, int shaders) {
        this.version = version;
        this.shaders = shaders;

        if ((shaders & VERTEX_SHADER) == VERTEX_SHADER) {
            vertexShader = new VertexShader();
        }
        if ((shaders & GEOMETRY_SHADER) == GEOMETRY_SHADER) {
            geometryShader = new GeometryShader();
        }
        if ((shaders & FRAGMENT_SHADER) == FRAGMENT_SHADER) {
            fragmentShader = new FragmentShader();
        }
    }

    public ShadersBuilder setHeader(int version) {
        this.version = version;
        return this;
    }

    private int version;

    private String makeHeader() {
        return "#version " + Integer.toString(version) + " core\n";
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

    private class VertexShader {

        static final String VERTEX_POS = "vertexPos";
        static final String VERTEX_UV = "vertexUv";
        static final String VERTEX_NORM = "vertexNorm";

        int locationPos = 0;
        int locationUV = 0;
        int locationNorm = 0;

        static final String TRANSFORMATION_MATRIX = "transformationMatrix";
        static final String PROJECTION_MATRIX = "projectionMatrix";
        static final String VIEW_MATRIX = "viewMatrix";

        boolean transformationMatrix = false;
        boolean projectionMatrix = false;
        boolean viewMatrix = false;

        static final String OUT_UV = "uv";
        static final String OUT_SURFACE_NORMAL = "surfaceNormal";
        static final String OUT_TO_LIGHT_VECTOR = "toLightVector";
        static final String OUT_TO_CAMERA_VECTOR = "toCameraVector";

        public String addVertexInput(int type) {
            if ((type & 1) == 1) {
                return "layout(location = 0) in vec3 " + VERTEX_POS + ";\n";
            }
            if (((type >> 1) & 1) == 1) {
                return "layout(location = 1) in vec2 " + VERTEX_UV + ";\n";
            }
            return "";
        }

    }

    private class GeometryShader {

    }

    private class FragmentShader {

    }

}
