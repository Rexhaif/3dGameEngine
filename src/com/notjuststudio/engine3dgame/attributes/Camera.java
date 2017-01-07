package com.notjuststudio.engine3dgame.attributes;

import com.notjuststudio.engine3dgame.ModelData;
import com.notjuststudio.engine3dgame.ModelTexture;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Matrix4f;

/**
 * Created by George on 06.01.2017.
 */
public class Camera extends Attribute{

    public final float FOV;
    public final float NEAR_PLANE;
    public final float FAR_PLANE;

    private Matrix4f projectionMatrix;

    public Camera() {
        this(70, 0.1f, 1000);
    }

    public Camera(float fov, float near_plane, float far_plane) {
        super(Type.CAMERA);
        this.FOV = fov;
        this.NEAR_PLANE = near_plane;
        this.FAR_PLANE = far_plane;

        createProjectionMatrix();
    }

    public float getFOV() {
        return FOV;
    }

    public float getNEAR_PLANE() {
        return NEAR_PLANE;
    }

    public float getFAR_PLANE() {
        return FAR_PLANE;
    }

    public Matrix4f getProjectionMatrix() {
        return projectionMatrix;
    }

    private void createProjectionMatrix() {
        float aspectRatio = (float) Display.getWidth() / (float) Display.getHeight();
        float y_scale = (float) ((1f / Math.tan(Math.toRadians(FOV / 2f))) * aspectRatio);
        float x_scale = y_scale / aspectRatio;
        float frustum_length = FAR_PLANE - NEAR_PLANE;

        projectionMatrix = new Matrix4f();
        projectionMatrix.m00 = x_scale;
        projectionMatrix.m11 = y_scale;
        projectionMatrix.m22 = -((FAR_PLANE + NEAR_PLANE) / frustum_length);
        projectionMatrix.m23 = -1;
        projectionMatrix.m32 = -((2 * NEAR_PLANE * FAR_PLANE) / frustum_length);
        projectionMatrix.m33 = 0;
    }
}
