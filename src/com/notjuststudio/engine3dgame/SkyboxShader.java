package com.notjuststudio.engine3dgame;

import com.notjuststudio.engine3dgame.attributes.RenderModel;
import com.notjuststudio.engine3dgame.attributes.model.ModelTexture;
import com.notjuststudio.engine3dgame.shader.ShaderProgram;
import com.notjuststudio.engine3dgame.shader.ShadersBuilder;
import com.notjuststudio.engine3dgame.shader.ShadersContainer;
import com.notjuststudio.engine3dgame.shader.sources.CodeBlock;
import org.lwjgl.util.vector.Matrix4f;

/**
 * Created by George on 10.01.2017.
 */
public class SkyboxShader extends ShaderProgram {

    private int location_projectionMatrix;
    private int location_viewMatrix;

    public SkyboxShader() {
        super(createContainer());
    }

    private static ShadersContainer createContainer() {
        return new ShadersBuilder()
                .addVertexCode(new CodeBlock("skyboxVertex.glsl"))
                .addGeometryCode(new CodeBlock("skyboxGeometry.glsl"))
                .addFragmentCode(new CodeBlock("skyboxFragment.glsl"))

                .build();
    }

    public void loadProjectionMatrix(Matrix4f matrix){
        super.loadMatrix(location_projectionMatrix, matrix);
    }

    public void loadViewMatrix(Matrix4f matrix){
        super.loadMatrix(location_viewMatrix, matrix);
    }

    @Override
    protected void getAllUniformLocation() {
        location_projectionMatrix = super.getUniformLocation("projectionMatrix");
        location_viewMatrix = super.getUniformLocation("viewMatrix");
    }

    @Override
    public void loadWhenCreated(ModelTexture modelTexture) {

    }

    @Override
    public void loadPrepareModel(RenderModel renderModel) {

    }

    @Override
    public void loadPrepareEntity(Entity entity) {

    }
}
