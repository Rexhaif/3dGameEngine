package com.notjuststudio.engine3dgame.util;

import com.notjuststudio.engine3dgame.attributes.Camera;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Quaternion;
import org.lwjgl.util.vector.Vector3f;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by George on 06.01.2017.
 */
public class MathUtil {

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
        return new Quaternion(axis.getX() * (float) Math.sin(angle / 2), axis.getY() * (float) Math.sin(angle / 2), axis.getZ() * (float) Math.sin(angle / 2),
                (float) Math.cos(angle / 2));
    }

    public static Vector3f impactVectorByMatrix(Matrix4f matrix, Vector3f vector) {
        Vector3f result = new Vector3f(0, 0, 0);
        result.setX(matrix.m00 * vector.getX() + matrix.m01 * vector.getY() + matrix.m02 * vector.getZ());
        result.setY(matrix.m10 * vector.getX() + matrix.m11 * vector.getY() + matrix.m12 * vector.getZ());
        result.setZ(matrix.m20 * vector.getX() + matrix.m21 * vector.getY() + matrix.m22 * vector.getZ());
        return result;
    }


    public static Vector3f myMultiplication(Vector3f first, Vector3f second) {
        return new Vector3f(first.getX() * second.getX(), first.getY() * second.getY(), first.getZ() * second.getZ());
    }


    public static Quaternion invertQuaternion(Quaternion quaternion) {
        Quaternion result = new Quaternion(0,0,0,0);
        result.w = quaternion.w;
        result.x = -quaternion.x;
        result.y = -quaternion.y;
        result.z = -quaternion.z;
        return result;
    }

    public static Vector3f scaleVector(Vector3f vector, float scale) {
        return new Vector3f(vector.x * scale, vector.y * scale, vector.z * scale);
    }



}
