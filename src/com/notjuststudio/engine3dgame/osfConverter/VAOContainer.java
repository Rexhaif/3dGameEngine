package com.notjuststudio.engine3dgame.osfConverter;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/**
 * Created by George on 08.01.2017.
 */
public class VAOContainer {

    private IntBuffer indices;
    private FloatBuffer positions;
    private FloatBuffer uvCoords;
    private FloatBuffer normals;

    public VAOContainer(IntBuffer indices, FloatBuffer positions, FloatBuffer uvCoords, FloatBuffer normals) {
        this.indices = indices;
        this.positions = positions;
        this.uvCoords = uvCoords;
        this.normals = normals;
    }

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
}
