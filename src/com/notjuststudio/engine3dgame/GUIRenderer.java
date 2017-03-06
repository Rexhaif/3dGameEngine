package com.notjuststudio.engine3dgame;

import com.notjuststudio.engine3dgame.render.MasterRenderer;
import com.notjuststudio.engine3dgame.render.Renderer;
import com.notjuststudio.engine3dgame.shader.ShaderProgram;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

/**
 * Created by Georgy on 05.03.2017.
 */
public class GUIRenderer extends Renderer {

    private void prepare() {
        GL30.glBindVertexArray(MasterRenderer.getGuiID());
        GL20.glEnableVertexAttribArray(0);
    }

    private void prepareGUI(GUI gui) {
        GUIShader shader = (GUIShader)gui.getShader();
        shader.useThis();
        shader.loadTransformationMatrix(gui.getTransformationMatrix());

        prepare();

        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, gui.getSpriteID());
    }

    @Override
    public void render() {
        for (GUI gui : GUI.getGuiList()) {
            prepareGUI(gui);
            GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, 4);
            ShaderProgram.useNone();
        }
        unbind();
        MasterRenderer.closeRender();
    }


    public void unbind() {
        GL20.glDisableVertexAttribArray(0);
    }
}
