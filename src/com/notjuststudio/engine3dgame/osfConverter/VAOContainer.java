package com.notjuststudio.engine3dgame.osfConverter;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/**
 * Created by George on 08.01.2017.
 */
public class VAOContainer {

    private IntBuffer indices = null;
    private FloatBuffer positions = null;
    private FloatBuffer uvCoords = null;
    private FloatBuffer normals = null;
    private IntBuffer boneIndices = null;
    private FloatBuffer boneWeights = null;

    public VAOContainer() {}

    public IntBuffer getIndices() {
        return indices;
    }

    public FloatBuffer getPositions() {
        return positions;
    }

    public FloatBuffer getUvCoords() {
        return uvCoords;
    }

    public FloatBuffer getNormals() {
        return normals;
    }

    public IntBuffer getBoneIndices() {
        return boneIndices;
    }

    public FloatBuffer getBoneWeights() {
        return boneWeights;
    }

    public VAOContainer setIndices(IntBuffer indices) {
        this.indices = indices;
        return this;
    }

    public VAOContainer setPositions(FloatBuffer positions) {
        this.positions = positions;
        return this;
    }

    public VAOContainer setUvCoords(FloatBuffer uvCoords) {
        this.uvCoords = uvCoords;
        return this;
    }

    public VAOContainer setNormals(FloatBuffer normals) {
        this.normals = normals;
        return this;
    }

    public VAOContainer setBoneIndices(IntBuffer boneIndices) {
        this.boneIndices = boneIndices;
        return this;
    }

    public VAOContainer setBoneWeights(FloatBuffer boneWeights) {
        this.boneWeights = boneWeights;
        return this;
    }
}
