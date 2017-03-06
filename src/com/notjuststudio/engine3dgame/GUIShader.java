package com.notjuststudio.engine3dgame;

import com.notjuststudio.engine3dgame.attributes.RenderModel;
import com.notjuststudio.engine3dgame.attributes.model.ModelTexture;
import com.notjuststudio.engine3dgame.shader.ShaderProgram;
import com.notjuststudio.engine3dgame.shader.ShadersBuilder;
import com.notjuststudio.engine3dgame.shader.ShadersContainer;
import com.notjuststudio.engine3dgame.shader.sources.CodeBlock;
import org.lwjgl.util.vector.Matrix3f;
import org.lwjgl.util.vector.Matrix4f;

/**
 * Created by Georgy on 05.03.2017.
 */
public class GUIShader extends ShaderProgram{

    private int location_transformationMatrix;

    protected GUIShader() {
        super(createContainer());
    }

    private static ShadersContainer createContainer() {
        return new ShadersBuilder()
                .addVertexCode(new CodeBlock("guiVertex.glsl"))
                //.addGeometryCode(new CodeBlock("guiGeometry.glsl"))
                .addFragmentCode(new CodeBlock("guiFragment.glsl"))

                .build();
    }

    @Override
    protected void getAllUniformLocation() {
        location_transformationMatrix = super.getUniformLocation("transformationMatrix");
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

    public void loadTransformationMatrix(Matrix3f matrix3f) {
        super.loadMatrix3f(location_transformationMatrix, matrix3f);
    }
}
