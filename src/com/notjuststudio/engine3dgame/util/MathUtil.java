package com.notjuststudio.engine3dgame.util;

import org.lwjgl.util.vector.*;

/**
 * Created by George on 06.01.2017.
 */
public class MathUtil {

    public static Matrix3f createRotationMatrix(float angle) {
        Matrix3f result = new Matrix3f();
        result.setIdentity();

        result.m00 = (float)Math.cos(angle);
        result.m01 = (float)Math.sin(angle);
        result.m10 = -(float)Math.sin(angle);
        result.m11 = (float)Math.cos(angle);

        return result;
    }

    public static Matrix3f createScaleMatrix(Vector2f scale) {
        Matrix3f result = new Matrix3f();
        result.setIdentity();

        result.m00 = scale.x;
        result.m11 = scale.y;

        return result;
    }

    public static Matrix3f createTranslationMatrix(Vector2f position) {
        Matrix3f result = new Matrix3f();
        result.setIdentity();

        result.m20 = position.x;
        result.m21 = position.y;

        return result;
    }

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
        result.m10 = 2 * (rotation.getX() * rotation.getY() - rotation.getZ() * rotation.getW());
        result.m20 = 2 * (rotation.getX() * rotation.getZ() + rotation.getY() * rotation.getW());

        result.m01 = 2 * (rotation.getX() * rotation.getY() + rotation.getZ() * rotation.getW());
        result.m11 = 1 - 2 * (rotation.getX() * rotation.getX() + rotation.getZ() * rotation.getZ());
        result.m21 = 2 * (rotation.getY() * rotation.getZ() - rotation.getX() * rotation.getW());

        result.m02 = 2 * (rotation.getX() * rotation.getZ() - rotation.getY() * rotation.getW());
        result.m12 = 2 * (rotation.getY() * rotation.getZ() + rotation.getX() * rotation.getW());
        result.m22 = 1 - 2 * (rotation.getX() * rotation.getX() + rotation.getY() * rotation.getY());

        return result;
    }

    public static Quaternion createRotationQuaternion(float angle, Vector3f axis) {
        axis.normalise();
        float sin = (float) Math.sin(angle / 2);
        return new Quaternion(axis.getX() * sin, axis.getY() * sin, axis.getZ() * sin,
                (float) Math.cos(angle / 2));
    }

    public static Vector3f impactVectorByMatrix(Matrix4f matrix, Vector3f vector) {
        return new Vector3f(
                matrix.m00 * vector.getX() + matrix.m10 * vector.getY() + matrix.m20 * vector.getZ() + matrix.m30,
                matrix.m01 * vector.getX() + matrix.m11 * vector.getY() + matrix.m21 * vector.getZ() + matrix.m31,
                matrix.m02 * vector.getX() + matrix.m12 * vector.getY() + matrix.m22 * vector.getZ() + matrix.m32

//        return new Vector3f(
//                matrix.m00 * vector.getX() + matrix.m01 * vector.getY() + matrix.m02 * vector.getZ() + matrix.m03,
//                matrix.m10 * vector.getX() + matrix.m11 * vector.getY() + matrix.m12 * vector.getZ() + matrix.m13,
//                matrix.m20 * vector.getX() + matrix.m21 * vector.getY() + matrix.m22 * vector.getZ() + matrix.m33
        );
    }


    public static Vector3f myMultiplication(Vector3f first, Vector3f second) {
        return new Vector3f(first.getX() * second.getX(), first.getY() * second.getY(), first.getZ() * second.getZ());
    }

    public static Vector2f myMultiplication(Vector2f first, Vector2f second) {
        return new Vector2f(first.getX() * second.getX(), first.getY() * second.getY());
    }

    public static Vector2f myDivision(Vector2f first, Vector2f second) {
        return new Vector2f(first.getX() / second.getX(), first.getY() / second.getY());
    }

    public static Vector2f scale(Vector2f vector, float scale) {
        return new Vector2f(vector.getX() * scale, vector.getY() * scale);
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

    public static Matrix3f toMatrix3f(Matrix4f matrix) {
        Matrix3f result = new Matrix3f();
        result.m00 = matrix.m00;
        result.m01 = matrix.m01;
        result.m02 = matrix.m02;
        result.m10 = matrix.m10;
        result.m11 = matrix.m11;
        result.m12 = matrix.m12;
        result.m20 = matrix.m20;
        result.m21 = matrix.m21;
        result.m22 = matrix.m22;
        return result;
    }

    public static Vector3f getTranslation(Matrix4f matrix) {
        Vector3f result = new Vector3f();
        result.set(matrix.m30, matrix.m31, matrix.m32);
        return result;
    }

}
