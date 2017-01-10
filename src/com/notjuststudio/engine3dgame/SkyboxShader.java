package com.notjuststudio.engine3dgame;

import com.notjuststudio.engine3dgame.shader.ShaderProgram;
import com.notjuststudio.engine3dgame.shader.ShadersBuilder;
import org.lwjgl.util.vector.Matrix4f;

/**
 * Created by George on 10.01.2017.
 */
public class SkyboxShader extends ShaderProgram {

    private int location_projectionMatrix;
    private int location_viewMatrix;

    public SkyboxShader() {
        super(ShadersBuilder.createSkyboxContainer());
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
}
