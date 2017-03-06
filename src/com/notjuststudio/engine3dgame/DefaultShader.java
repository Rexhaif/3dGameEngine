package com.notjuststudio.engine3dgame;

import com.notjuststudio.engine3dgame.attributes.Camera;
import com.notjuststudio.engine3dgame.attributes.Light;
import com.notjuststudio.engine3dgame.attributes.RenderModel;
import com.notjuststudio.engine3dgame.attributes.model.ModelTexture;
import com.notjuststudio.engine3dgame.render.MasterRenderer;
import com.notjuststudio.engine3dgame.shader.ShaderProgram;
import com.notjuststudio.engine3dgame.shader.ShadersBuilder;
import com.notjuststudio.engine3dgame.shader.ShadersContainer;
import com.notjuststudio.engine3dgame.shader.sources.CodeBlock;
import org.lwjgl.util.vector.Matrix4f;

/**
 * Created by George on 06.01.2017.
 */
public class DefaultShader extends ShaderProgram {

    private int location_transformationMatrix;
    private int location_projectionMatrix;
    private int location_viewMatrix;
    private int location_lightPosition;
    private int location_lightColour;
    private int location_shineDamper;
    private int location_reflectivity;

    public DefaultShader() {
        super(createContainer());
    }

    private static ShadersContainer createContainer() {
        return new ShadersBuilder()
                .addVertexCode(new CodeBlock("defaultVertex.glsl"))
                .addGeometryCode(new CodeBlock("defaultGeometry.glsl"))
                .addFragmentCode(new CodeBlock("defaultFragment.glsl"))

                .build();
    }

    @Override
    protected void getAllUniformLocation() {
        location_transformationMatrix = super.getUniformLocation("transformationMatrix");
        location_projectionMatrix = super.getUniformLocation("projectionMatrix");
        location_viewMatrix = super.getUniformLocation("viewMatrix");
        location_lightPosition = super.getUniformLocation("lightPosition");
        location_lightColour = super.getUniformLocation("lightColour");
        location_shineDamper = super.getUniformLocation("shineDamper");
        location_reflectivity = super.getUniformLocation("reflectivity");
    }

    @Override
    public void loadWhenCreated(ModelTexture modelTexture) {
        loadShineVariable(modelTexture.getShineDamper(), modelTexture.getReflectivity());
    }

    @Override
    public void loadPrepareModel(RenderModel renderModel) {
        loadProjectionMatrix(Camera.getMainCamera().getProjectionMatrix());
        loadLight(MasterRenderer.getLight());
        loadViewMatrix(Camera.getMainCamera().getViewMatrix());
    }

    @Override
    public void loadPrepareEntity(Entity entity) {
        loadTransformationMatrix(entity.getWorldTransformation());
    }

    private void loadShineVariable(float damper, float reflectivity) {
        super.loadFloat(location_shineDamper, damper);
        super.loadFloat(location_reflectivity, reflectivity);
    }

    private void loadTransformationMatrix(Matrix4f matrix4f) {
        super.loadMatrix4f(location_transformationMatrix, matrix4f);
    }

    private void loadProjectionMatrix(Matrix4f matrix4f) {
        super.loadMatrix4f(location_projectionMatrix, matrix4f);
    }

    private void loadViewMatrix(Matrix4f matrix4f) {
        super.loadMatrix4f(location_viewMatrix, matrix4f);
    }

    private void loadLight(Light light) {
        super.loadVector(location_lightPosition, light.getEntity().getPosition());
        super.loadVector(location_lightColour, light.getColour());
    }
}
