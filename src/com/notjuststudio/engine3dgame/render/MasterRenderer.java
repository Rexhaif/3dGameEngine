package com.notjuststudio.engine3dgame.render;

import com.notjuststudio.engine3dgame.GUIRenderer;
import com.notjuststudio.engine3dgame.Loader;
import com.notjuststudio.engine3dgame.SkyboxShader;
import com.notjuststudio.engine3dgame.attributes.Camera;
import com.notjuststudio.engine3dgame.attributes.Light;
import com.notjuststudio.engine3dgame.shader.ShaderProgram;
import com.notjuststudio.engine3dgame.util.MathUtil;
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

    private static int guiID = 0;

    private static void prepareSkybox() {
        MasterRenderer.cubeID = Loader.createCubeMap();
    }

    public static void setSkybox(int skyboxID, ShaderProgram skyboxShader) {
        MasterRenderer.skyboxID = skyboxID;
        MasterRenderer.skyboxShader = skyboxShader;
    }

    private static void prepareGUI() {
        MasterRenderer.guiID = Loader.createGUI();
    }

    private static Renderer[] renderers = {
            new DefaultRenderer()
    };

    private static Renderer[] renderersGUI = {
            new GUIRenderer()
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
        GL11.glClearColor(0, 0, 1, 1);
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
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
        for (Renderer renderer : renderersGUI) {
            renderer.render();
        }

    }

    private static void renderSkybox() {
        skyboxShader.useThis();
        skyboxShader.loadPrepareModel(null);
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

    public static void init() {
        prepareSkybox();
        prepareGUI();
    }

    public static int getGuiID() {
        return guiID;
    }
}
