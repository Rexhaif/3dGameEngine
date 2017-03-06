package com.notjuststudio.engine3dgame.render;

import com.notjuststudio.engine3dgame.Entity;
import com.notjuststudio.engine3dgame.attributes.RenderModel;
import com.notjuststudio.engine3dgame.shader.ShaderProgram;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.util.List;
import java.util.Map;

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

    private void prepareEntity(Entity entity) {
        activeRenderModel.getTexture().getShaderProgram().loadPrepareEntity(entity);
    }

    @Override
    public void render() {
        for (Map.Entry<RenderModel, List<Entity>> entry : RenderModel.getModelsMap().entrySet()) {
            prepareModel(entry.getKey());
            for (Entity entity : entry.getValue()) {
                prepareEntity(entity);
                GL11.glDrawElements(GL11.GL_TRIANGLES, entry.getKey().getData().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
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
