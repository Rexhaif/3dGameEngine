package com.notjuststudio.engine3dgame;

import com.notjuststudio.engine3dgame.shader.ShaderProgram;
import com.notjuststudio.engine3dgame.shader.ShadersBuilder;
import com.notjuststudio.engine3dgame.shader.ShadersContainer;
import org.lwjgl.util.vector.Matrix4f;

/**
 * Created by George on 06.01.2017.
 */
public class MyShaderProgram extends ShaderProgram {

    private int location_transformation;

    public MyShaderProgram() {
        this(new ShadersBuilder().createDefaultContainer());
    }

    private MyShaderProgram(ShadersContainer container) {
        super(container);
    }

    @Override
    protected void getAllUniformLocation() {
        location_transformation = super.getUniformLocation("transformationMatrix");
    }

    public void loadTransformationMatrix(Matrix4f matrix4f) {
        super.loadMatrix(location_transformation, matrix4f);
    }
}
