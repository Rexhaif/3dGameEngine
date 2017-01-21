package com.notjuststudio.engine3dgame.render;

import com.notjuststudio.engine3dgame.Entity;
import com.notjuststudio.engine3dgame.DefaultProgram;
import com.notjuststudio.engine3dgame.attributes.Camera;
import com.notjuststudio.engine3dgame.attributes.RenderModel;
import com.notjuststudio.engine3dgame.shader.ShaderProgram;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

/**
 * Created by George on 07.01.2017.
 */
public class DefaultRenderer extends Renderer {

    private RenderModel activeRenderModel;

    private void prepareModel(RenderModel renderModel) {
        activeRenderModel = renderModel;

        GL30.glBindVertexArray(activeRenderModel.getData().getVaoID());

        ShaderProgram activeShader = renderModel.getTexture().getShaderProgram();
        activeShader.useThis();
        activeShader.loadPrepareModel(renderModel);

        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, renderModel.getTexture().getTextureID());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);
    }

    private void prepareKeeper(Entity entity) {
        activeRenderModel.getTexture().getShaderProgram().loadPrepareEntity(entity);
    }

    @Override
    public void render() {
        for (RenderModel renderModel : RenderModel.getKeySet()) {
            prepareModel(renderModel);
            for (Entity entity : RenderModel.getList(renderModel)) {
                prepareKeeper(entity);
                GL11.glDrawElements(GL11.GL_TRIANGLES, renderModel.getData().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
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
