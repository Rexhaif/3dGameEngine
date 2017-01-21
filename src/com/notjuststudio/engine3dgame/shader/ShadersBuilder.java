package com.notjuststudio.engine3dgame.shader;

import static com.notjuststudio.engine3dgame.util.Maths.parseFile;

/**
 * Created by George on 06.01.2017.
 */
public class ShadersBuilder {

    private static final String defaultPath = "/com/notjuststudio/engine3dgame/shader/";

    private static final String vertexShaderDefault;
    private static final String geometryShaderDefault;
    private static final String fragmentShaderDefault;

    static {
        vertexShaderDefault = parseFile(defaultPath + "defaultVertex.glsl");
        geometryShaderDefault = parseFile(defaultPath + "defaultGeometry.glsl");
        fragmentShaderDefault = parseFile(defaultPath + "defaultFragment.glsl");
    }

    private static final String vertexShaderSkybox;
    private static final String geometryShaderSkybox;
    private static final String fragmentShaderSkybox;

    static {
        vertexShaderSkybox = parseFile(defaultPath + "skyboxVertex.glsl");
        geometryShaderSkybox = parseFile(defaultPath + "skyboxGeometry.glsl");
        fragmentShaderSkybox = parseFile(defaultPath + "skyboxFragment.glsl");
    }

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
