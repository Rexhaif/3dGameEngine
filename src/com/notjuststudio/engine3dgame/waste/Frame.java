package com.notjuststudio.engine3dgame.waste;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.*;
import org.lwjgl.opengl.DisplayMode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Frame extends JFrame {
    private JPanel contentPane;
    private GLCanvas mCanvas;
    public GraphicsDevice gd;

    public int
            screenWidth,
            screenHeight;

    /**
     * Create the frame.
     */
    public Frame() {
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                close();
            }
        });
        gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        screenWidth = gd.getDisplayMode().getWidth();
        screenHeight = gd.getDisplayMode().getHeight();
//        for (DisplayMode mode : gd.getDisplayModes()) {
//            System.out.println(mode.getWidth() + " " + mode.getHeight() + " " + mode.getBitDepth() + " " + mode.getRefreshRate());
//        }
        DisplayMode mode = null;
        try {
            mode = Display.getAvailableDisplayModes()[0];
        } catch (LWJGLException e) {
            e.printStackTrace();
        }
        setSize(mode.getWidth(), mode.getHeight());
        //setBounds((screenWidth - getWidth()) / 2, (screenHeight - getHeight()) / 2, getWidth(), getHeight());
        setResizable(false);
//        setUndecorated(true);
        contentPane = new JPanel();
        int border = 20;
        //contentPane.setBorder(new EmptyBorder(getHeight()/2 - border, border, border, getWidth()/2 - border));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);
        try {
            mCanvas = new GLCanvas(this);
        } catch (LWJGLException e) {
            e.printStackTrace();
        }
        contentPane.add(mCanvas, BorderLayout.CENTER);
        this.setVisible(true);
        //pack();

    }

    public void close() {
        System.err.println("Closing...");
        setVisible(false);
        dispose();//canvas's removeNotify() will be called
    }

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        try {
            Frame frame = new Frame();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

} // class Frame
