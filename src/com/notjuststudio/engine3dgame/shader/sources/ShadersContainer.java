package com.notjuststudio.engine3dgame.shader.sources;

/**
 * Created by George on 06.01.2017.
 */
public class ShadersContainer {

    private String vertexShader = "";
    private String geometryShader = "";
    private String fragmentShader = "";

    ShadersContainer() {}

    ShadersContainer setVertexShader(String vertexShader) {
        this.vertexShader = vertexShader;
        return this;
    }

    ShadersContainer setGeometryShader(String geometryShader) {
        this.geometryShader = geometryShader;
        return this;
    }

    ShadersContainer setFragmentShader(String fragmentShader) {
        this.fragmentShader = fragmentShader;
        return this;
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
