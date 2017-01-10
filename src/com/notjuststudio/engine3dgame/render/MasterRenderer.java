package com.notjuststudio.engine3dgame.render;

import com.notjuststudio.engine3dgame.Loader;
import com.notjuststudio.engine3dgame.SkyboxShader;
import com.notjuststudio.engine3dgame.attributes.Camera;
import com.notjuststudio.engine3dgame.attributes.Light;
import com.notjuststudio.engine3dgame.shader.ShaderProgram;
import com.notjuststudio.engine3dgame.util.Maths;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;

/**
 * Created by George on 07.01.2017.
 */
public class MasterRenderer {

    private static int skyboxID = 0;
    private static int cubeID = 0;
    private static ShaderProgram skyboxShader = null;

    public static void setSkybox(int skyboxID, ShaderProgram skyboxShader) {
        MasterRenderer.skyboxID = skyboxID;
        cubeID = Loader.createCubeMap(1);
        MasterRenderer.skyboxShader = skyboxShader;
    }

    private static Renderer[] renderers = {
            new DefaultRenderer()
    };

    private static Light light = null;

    public static Light getLight() {
        return light;
    }

    public static void preload(Light globalLight) {
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glCullFace(GL11.GL_BACK);
        light = globalLight;
    }

    private static void prepare() {
        if (skyboxID == 0)
            GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glClearColor(0,0,1,1);
    }

    public static void render() {
        prepare();
        if (skyboxID != 0) {
            renderSkybox();
            GL11.glEnable(GL11.GL_DEPTH_TEST);
            GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
        }
        for (Renderer renderer : renderers) {
            renderer.render();
        }
    }

    private static void renderSkybox() {
        skyboxShader.useThis();
        ((SkyboxShader)skyboxShader).loadProjectionMatrix(Camera.getMainCamera().getProjectionMatrix());
        Matrix4f matrix = Maths.createRotationMatrix(Camera.getMainCamera().getEntity().getLocalRotation());
        ((SkyboxShader)skyboxShader).loadViewMatrix(matrix);
        GL30.glBindVertexArray(cubeID);
        GL20.glEnableVertexAttribArray(0);
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL13.GL_TEXTURE_CUBE_MAP, skyboxID);
        GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, 108);
        GL20.glDisableVertexAttribArray(0);
        Loader.bindDefaultVAO();
        ShaderProgram.useNone();
    }

    public static void closeRender() {
        Loader.bindDefaultVAO();
    }
}
