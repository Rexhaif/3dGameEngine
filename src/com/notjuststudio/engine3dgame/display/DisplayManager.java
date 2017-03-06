package com.notjuststudio.engine3dgame.display;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.*;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import java.util.*;
import java.util.List;

/**
 * Created by George on 06.01.2017.
 */
public class DisplayManager {

    private static int countOfDelta = 0;
    private static int maxCountOfDelta = 100;
    private static List<Float> listOfDelta = new ArrayList<>(maxCountOfDelta);

    private static boolean closeRequest = false;
    private static final ContextAttribs attribs;
    private static DisplayMode[] modes;
    private static int currentMode;
    private static PixelFormat format;

    public static final int
            WINDOWED = 0,
            WINDOWED_BORDERLESS = 1,
            FULLSCREEN = 2;
    private static int fullscreenState = WINDOWED;

    private static long lastFrameTime;
    private static float delta;

    static {
        attribs = new ContextAttribs(3, 2).withForwardCompatible(true).withProfileCore(true);
        try {
            modes = Display.getAvailableDisplayModes();
        } catch (LWJGLException e) {
            e.printStackTrace();
        }
        Arrays.sort(modes, new DisplayModeComparator());
        currentMode = modes.length - 1;
        format = new PixelFormat().withSamples(4);
    }

    public static void createDisplay(boolean undecorated, boolean fullscreen) {

        System.setProperty("org.lwjgl.opengl.Window.undecorated", Boolean.toString(undecorated));
        try {
            Display.setDisplayMode(modes[currentMode]);
            Display.setFullscreen(fullscreen);
            Display.create(format, attribs);
            //Display.setVSyncEnabled(true);
            Display.setTitle("GAME OF THE YEAR");

            GL11.glEnable(GL13.GL_MULTISAMPLE);
        } catch (LWJGLException e) {
            //handle exception
            e.printStackTrace();
        }

        lastFrameTime = getCurrentTime();
    }

    public static void setFullscreenState(int state) {
        if (state >= 0 && state <= 2)
            fullscreenState = state;
    }

    public static void updateDisplaySetting() {
        try {
            switch (fullscreenState) {
                case WINDOWED: {
                    System.setProperty("org.lwjgl.opengl.Window.undecorated", Boolean.toString(false));
                    Display.setFullscreen(false);
                    DisplayMode one = modes[currentMode];
                    Display.setDisplayMode(one);
                    GL11.glViewport(0, 0, one.getWidth(), one.getHeight());
                    break;
                }
                case WINDOWED_BORDERLESS: {
                    System.setProperty("org.lwjgl.opengl.Window.undecorated", Boolean.toString(true));
                    Display.setFullscreen(false);
                    DisplayMode one = Display.getDesktopDisplayMode();
                    Display.setDisplayMode(one);
                    GL11.glViewport(0, 0, one.getWidth(), one.getHeight());
                    break;
                }
                case FULLSCREEN: {
                    System.setProperty("org.lwjgl.opengl.Window.undecorated", Boolean.toString(false));
                    DisplayMode one = modes[currentMode];
                    Display.setDisplayMode(one);
                    GL11.glViewport(0, 0, one.getWidth(), one.getHeight());
                    Display.setFullscreen(true);
                    break;
                }
            }
        } catch (LWJGLException e) {
            e.printStackTrace();
        }
    }

    public static void updateDisplay() {
        Display.update();
        long currentFrameTime = getCurrentTime();
        delta = (currentFrameTime - lastFrameTime) / 1000f;
        lastFrameTime = getCurrentTime();

        if (countOfDelta < maxCountOfDelta) {
            countOfDelta++;
        } else {
            listOfDelta.remove(0);
        }
        listOfDelta.add(delta);
    }

    public static float getFPS() {
        if (listOfDelta.isEmpty()) return 0;
        return countOfDelta/((float)listOfDelta.stream().mapToDouble(Float::floatValue).sum());
    }

    public static void resetFPS() {
        countOfDelta = 0;
        listOfDelta.clear();
    }

    public static void resetFPS(int maxCountOfDelta) {
        DisplayManager.maxCountOfDelta = maxCountOfDelta;
        resetFPS();
    }

    public static void closeDisplay() {
        Display.destroy();
    }

    public static float getFrameTimeSeconds() {
        return delta;
    }

    public static long getCurrentTime() {
        return Sys.getTime() * 1000 / Sys.getTimerResolution();
    }

    public static int getWidth() {
        return modes[currentMode].getWidth();
    }

    public static int getHeight() {
        return modes[currentMode].getHeight();
    }

    public static boolean isCloseRequested() {
        return Display.isCloseRequested() || closeRequest;
    }

    public static void closeRequest() {
        closeRequest = true;
    }
}
