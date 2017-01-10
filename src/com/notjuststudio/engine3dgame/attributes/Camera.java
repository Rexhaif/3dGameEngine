package com.notjuststudio.engine3dgame.attributes;

import com.notjuststudio.engine3dgame.Entity;
import com.notjuststudio.engine3dgame.util.Maths;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Matrix4f;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by George on 06.01.2017.
 */
public class Camera extends Attribute{

    public final float FOV;
    public final float NEAR_PLANE;
    public final float FAR_PLANE;

    private static int mainIndex = 0;
    private static List<Camera> allCameras = new ArrayList<>();

    private Matrix4f projectionMatrix;
    private Matrix4f viewMatrix = new Matrix4f();

    public Camera() {
        this(70, 0.1f, 1000);
    }

    public Camera(float fov, float near_plane, float far_plane) {
        super(Type.CAMERA);
        allCameras.add(this);
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

    public Matrix4f getViewMatrix() {
        return viewMatrix;
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

    public void resolveViewMatrix() {
        Matrix4f result = new Matrix4f();
         Matrix4f.setIdentity(result);

        Entity entity = getEntity();

        Matrix4f.translate(entity.getLocalPosition().negate(null), result, result);
        Matrix4f.mul(result, Maths.createRotationMatrix(entity.getLocalRotation()), result);
        viewMatrix = result;
    }

    public static void setMainCameraIndex(int index) {
        mainIndex = index;
    }

    public static void setMainCamera(Camera camera) {
        mainIndex = allCameras.indexOf(camera);
    }

    public static Camera getMainCamera() {
        return allCameras.get(mainIndex);
    }

    @Override
    public void delete() {
        super.delete();
        if (allCameras.indexOf(this) == mainIndex) {
            mainIndex = 0;
        }
        allCameras.remove(this);
    }

    @Override
    public Attribute setEntity(Entity entity) {
        super.setEntity(entity);
        resolveViewMatrix();
        return this;
    }
}
