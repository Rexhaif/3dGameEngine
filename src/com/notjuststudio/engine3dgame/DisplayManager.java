package com.notjuststudio.engine3dgame;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.*;

/**
 * Created by George on 06.01.2017.
 */
public class DisplayManager {

    private static int width = 1280;
    private static int height = 720;

    private static long lastFrameTime;
    private static float delta;

    public static void createDisplay() {

        ContextAttribs attribs = new ContextAttribs(3,2).withForwardCompatible(true).withProfileCore(true);

        try {
            Display.setDisplayMode(new DisplayMode(width, height));
            Display.create(new PixelFormat(), attribs);

        } catch (LWJGLException e) {
            e.printStackTrace();
        }

        GL11.glViewport(0,0,width,height);
        lastFrameTime = getCurrentTime();
    }

    public static void updateDisplay() {
        Display.update();
        long currentFrameTime = getCurrentTime();
        delta = (currentFrameTime - lastFrameTime) / 1000f;
        lastFrameTime = getCurrentTime();
    }

    public static void closeDisplay() {
        Display.destroy();
    }

    public static float getFrameTimeSeconds() {
        return delta;
    }

    private static long getCurrentTime() {
        return Sys.getTime() * 1000 /Sys.getTimerResolution();
    }
}
