package com.notjuststudio.engine3dgame;

import com.notjuststudio.engine3dgame.attributes.Attribute;
import com.notjuststudio.engine3dgame.attributes.Camera;
import com.notjuststudio.engine3dgame.attributes.Model;
import com.notjuststudio.engine3dgame.util.Maths;
import org.lwjgl.opengl.*;

/**
 * Created by George on 06.01.2017.
 */
public class Renderer {

    public static void prepare() {
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT|GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glClearColor(1,0,0,1);
    }

    public static void render(Location cameraKeeper, Location modelKeeper) {
        if (!cameraKeeper.hasAttributeType(Attribute.Type.CAMERA))
            return;
        if (!modelKeeper.hasAttributeType(Attribute.Type.RENDER_MODEL))
            return;
        Camera camera = (Camera)cameraKeeper.getAttributeOfType(Attribute.Type.CAMERA);
        Model model = (Model)modelKeeper.getAttributeOfType(Attribute.Type.RENDER_MODEL);
        GL30.glBindVertexArray(model.getData().getVaoID());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        MyShaderProgram shader = (MyShaderProgram)model.getTexture().getShaderProgram();
        shader.loadTransformationMatrix(modelKeeper.getTransformationMatrix());
        shader.loadProjectionMatrix(camera.getProjectionMatrix());
        shader.loadViewMatrix(Maths.createViewMatrix(camera));
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, model.getTexture().getTextureID());
        GL11.glDrawElements(GL11.GL_TRIANGLES, model.getData().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        Loader.bindDefaultVAO();
    }
}
