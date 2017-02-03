package com.notjuststudio.engine3dgame;

import net.sf.image4j.codec.ico.ICODecoder;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.*;
import org.lwjgl.opengl.DisplayMode;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Created by George on 06.01.2017.
 */
public class DisplayManager {

    private static DisplayMode mode;

    private static long lastFrameTime;
    private static float delta;

    public static void createDisplay() {

        ContextAttribs attribs = new ContextAttribs(3,2).withForwardCompatible(true).withProfileCore(true);

        mode = Display.getDesktopDisplayMode();

        try {
            Display.setDisplayMode(mode);
            //Display.setFullscreen(true);
            Display.create(new PixelFormat().withSamples(4), attribs);
            Display.setTitle("GAME OF THE YEAR");
            GL11.glEnable(GL13.GL_MULTISAMPLE);
        } catch (LWJGLException e) {
            //handle exception
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
