package com.notjuststudio.engine3dgame.util;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Quaternion;
import org.lwjgl.util.vector.Vector3f;

/**
 * Created by George on 06.01.2017.
 */
public class Maths {

    public static Matrix4f createTransformationMatrix(Vector3f position, Quaternion rotation, Vector3f scale) {
        Matrix4f result = new Matrix4f();
        result.setIdentity();
        Matrix4f.translate(position, result, result);
        Matrix4f.mul(result, createRotationMatrix(rotation), result);
        Matrix4f.scale(scale, result, result);
        return result;
    }

    public static Matrix4f createRotationMatrix(Quaternion rotation) {
        Matrix4f result = new Matrix4f();
        result.setIdentity();

        result.m00 = 1 - 2 * (rotation.getY() * rotation.getY() + rotation.getZ() * rotation.getZ());
        result.m01 = 2 * (rotation.getX() * rotation.getY() - rotation.getZ() * rotation.getW());
        result.m02 = 2 * (rotation.getX() * rotation.getZ() + rotation.getY() * rotation.getW());

        result.m10 = 2 * (rotation.getX() * rotation.getY() + rotation.getZ() * rotation.getW());
        result.m11 = 1 - 2 * (rotation.getX() * rotation.getX() + rotation.getZ() * rotation.getZ());
        result.m12 = 2 * (rotation.getY() * rotation.getZ() - rotation.getX() * rotation.getW());

        result.m20 = 2 * (rotation.getX() * rotation.getZ() - rotation.getY() * rotation.getW());
        result.m21 = 2 * (rotation.getY() * rotation.getZ() + rotation.getX() * rotation.getW());
        result.m22 = 1 - 2 * (rotation.getX() * rotation.getX() + rotation.getY() * rotation.getY());

        return result;
    }

    public static Quaternion createRotationQuaternion(float angle, Vector3f axis) {
        axis.normalise();
        return new Quaternion(axis.getX()*(float)Math.sin(angle / 2),axis.getY()*(float)Math.sin(angle / 2),axis.getZ()*(float)Math.sin(angle / 2),
                (float)Math.cos(angle/2));
    }
}
