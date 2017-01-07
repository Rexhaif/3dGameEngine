package com.notjuststudio.engine3dgame.render;

import com.notjuststudio.engine3dgame.Loader;
import com.notjuststudio.engine3dgame.attributes.Light;
import org.lwjgl.opengl.GL11;

/**
 * Created by George on 07.01.2017.
 */
public class MasterRenderer {

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
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT|GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glClearColor(1,0,0,1);
    }

    public static void render() {
        prepare();
        for (Renderer renderer : renderers) {
            renderer.render();
        }
    }

    public static void closeRender() {
        Loader.bindDefaultVAO();
    }
}
