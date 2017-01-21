package com.notjuststudio.engine3dgame.attributes.model;

import com.notjuststudio.engine3dgame.colladaConverter.colladaschema.Matrix;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Quaternion;
import org.lwjgl.util.vector.Vector3f;

/**
 * Created by George on 21.01.2017.
 */
public class Joint {

    private final String name;
    private final Matrix4f bindPoses;
    private final Joint[] children;

    private Vector3f position;
    private Quaternion rotation;

    public Joint(String name, Matrix4f bindPoses, Joint[] children) {
        this.name = name;
        this.bindPoses = bindPoses;
        this.children = children;

        Matrix4f transformation = Matrix4f.invert(bindPoses, null);
        this.position = new Vector3f(transformation.m03, transformation.m13, transformation.m23);
        Quaternion.setFromMatrix(transformation, this.rotation);
    }

    public String getName() {
        return name;
    }

    public Matrix4f getBindPoses() {
        return bindPoses;
    }

    public Joint[] getChildren() {
        return children;
    }

    public Vector3f getPosition() {
        return position;
    }

    public Quaternion getRotation() {
        return rotation;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public void setRotation(Quaternion rotation) {
        this.rotation = rotation;
    }
}
