package com.notjuststudio.engine3dgame;

import org.lwjgl.LWJGLException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Frame extends JFrame
{
    private JPanel contentPane;
    private GLCanvas mCanvas;

    /**
     * Create the frame.
     */
    public Frame()
    {
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we){
                System.err.println("Closing...");
                setVisible(false);
                dispose(); //canvas's removeNotify() will be called
            }
        });
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int screenWidth = gd.getDisplayMode().getWidth();
        int screenHeight = gd.getDisplayMode().getHeight();
        setSize(800, 600);
        setBounds((screenWidth - getWidth())/2, (screenHeight - getHeight())/2, getWidth(), getHeight());
        setResizable(false);
        contentPane = new JPanel();
        int border = 20;
        contentPane.setBorder(new EmptyBorder(getHeight()/2 - border, border, border, getWidth()/2 - border));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);
        try
        {
            mCanvas = new GLCanvas(this);
        }
        catch (LWJGLException e)
        {
            e.printStackTrace();
        }
        contentPane.add(mCanvas, BorderLayout.CENTER);
        this.setVisible(true);
        //pack();

    }

    /**
     * Launch the application.
     */
    public static void main(String[] args)
    {
//        EventQueue.invokeLater(new Runnable() {
//            public void run() {
                try {
                    Frame frame = new Frame();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
//            }
//        });
    }

} // class Frame
