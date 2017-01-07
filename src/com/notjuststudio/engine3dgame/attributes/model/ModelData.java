package com.notjuststudio.engine3dgame.attributes.model;

/**
 * Created by George on 06.01.2017.
 */
public class ModelData {

    private int vaoID;
    private int vertexCount;

    public ModelData(int vaoID, int vertexCount) {
        this.vaoID = vaoID;
        this.vertexCount = vertexCount;
    }

    public int getVaoID() {
        return vaoID;
    }

    public int getVertexCount() {
        return vertexCount;
    }
}
