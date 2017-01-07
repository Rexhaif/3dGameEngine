package com.notjuststudio.engine3dgame;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.*;

/**
 * Created by George on 06.01.2017.
 */
public class DisplayManager {

    private static DisplayMode mode;

    private static long lastFrameTime;
    private static float delta;

    public static void createDisplay() {

        ContextAttribs attribs = new ContextAttribs(3,2).withForwardCompatible(true).withProfileCore(true);

        try {
            mode = Display.getDesktopDisplayMode();
            Display.setDisplayMode(mode);
            //Display.setFullscreen(true);
            Display.create(new PixelFormat(), attribs);
            Display.setTitle("GAME OF THE YEAR");

        } catch (LWJGLException e) {
            e.printStackTrace();
        }

        GL11.glViewport(0,0,mode.getWidth(),mode.getHeight());
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

    public static long getCurrentTime() {
        return Sys.getTime() * 1000 /Sys.getTimerResolution();
    }

    public static int getWidth() {
        return mode.getWidth();
    }

    public static int getHeight() {
        return mode.getHeight();
    }
}
