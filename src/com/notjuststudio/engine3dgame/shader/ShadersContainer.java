package com.notjuststudio.engine3dgame.shader;

/**
 * Created by George on 06.01.2017.
 */
public class ShadersContainer {

    private String vertexShader;
    private String geometryShader;
    private String fragmentShader;

    public ShadersContainer(String vertexShader, String geometryShader, String fragmentShader) {
        this.vertexShader = vertexShader;
        this.geometryShader = geometryShader;
        this.fragmentShader = fragmentShader;
    }

    public String getVertexShader() {
        return vertexShader;
    }

    public String getGeometryShader() {
        return geometryShader;
    }

    public String getFragmentShader() {
        return fragmentShader;
    }
}
