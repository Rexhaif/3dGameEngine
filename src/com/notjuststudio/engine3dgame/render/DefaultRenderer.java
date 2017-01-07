package com.notjuststudio.engine3dgame.render;

import com.notjuststudio.engine3dgame.Keeper;
import com.notjuststudio.engine3dgame.MyShaderProgram;
import com.notjuststudio.engine3dgame.attributes.Camera;
import com.notjuststudio.engine3dgame.attributes.Model;
import com.notjuststudio.engine3dgame.shader.ShaderProgram;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

/**
 * Created by George on 07.01.2017.
 */
public class DefaultRenderer extends Renderer {

    private Model activeModel;

    private void prepareModel(Model model) {
        activeModel = model;

        GL30.glBindVertexArray(activeModel.getData().getVaoID());

        MyShaderProgram activeShader = (MyShaderProgram)model.getTexture().getShaderProgram();
        activeShader.useThis();
        activeShader.loadProjectionMatrix(Camera.getMainCamera());
        activeShader.loadViewMatrix(Camera.getMainCamera().getViewMatrix());
        activeShader.loadLight(MasterRenderer.getLight());
        activeShader.loadShineVariable(model.getTexture().getShineDamper(),model.getTexture().getReflectivity());

        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, model.getTexture().getTextureID());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);
    }

    private void prepareKeeper(Keeper keeper) {
        ((MyShaderProgram) activeModel.getTexture().getShaderProgram()).loadTransformationMatrix(keeper.getTransformationMatrix());
    }

    @Override
    public void render() {
        for (Model model : Model.getKeySet()) {
            prepareModel(model);
            for (Keeper keeper : Model.getList(model)) {
                prepareKeeper(keeper);
                GL11.glDrawElements(GL11.GL_TRIANGLES, model.getData().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
            }
            unbind();
            ShaderProgram.useNone();
            MasterRenderer.closeRender();
        }
    }

    public void unbind() {
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);
    }
}
