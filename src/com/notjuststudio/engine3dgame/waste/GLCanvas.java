package com.notjuststudio.engine3dgame.waste;

import com.notjuststudio.engine3dgame.*;
import com.notjuststudio.engine3dgame.attributes.Light;
import com.notjuststudio.engine3dgame.attributes.RenderModel;
import com.notjuststudio.engine3dgame.attributes.model.ModelData;
import com.notjuststudio.engine3dgame.attributes.model.ModelTexture;
import com.notjuststudio.engine3dgame.Entity;
import com.notjuststudio.engine3dgame.osfConverter.OSFLoader;
import com.notjuststudio.engine3dgame.osfConverter.VAOContainer;
import com.notjuststudio.engine3dgame.render.MasterRenderer;
import com.notjuststudio.engine3dgame.shader.ShaderProgram;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.*;

public class GLCanvas extends AWTGLCanvas {

    private boolean closeRequest = false;
    protected Frame mParent;
    private PixelFormat pixelFormat;
    private ContextAttribs attribs;

    private final int width = 1600;
    private final int height = 900;

    public GLCanvas(Frame parent) throws LWJGLException {
        super();
        mParent = parent;
    }

    public void start() {
        try {
            attribs = new ContextAttribs(3,2).withForwardCompatible(true).withProfileCore(true);
            try {

//                for (org.lwjgl.opengl.DisplayMode mode : Display.getAvailableDisplayModes()) {
//                    System.out.println(mode.getWidth() + " " + mode.getHeight() + " " + mode.getBitsPerPixel() + " " + mode.getFrequency());
//                }
                Display.setDisplayMode(Display.getAvailableDisplayModes()[0]);
                Display.setParent(this);
                //Display.setVSyncEnabled(true);

                Display.create(pixelFormat = new PixelFormat().withSamples(4), attribs);

                GL11.glEnable(GL13.GL_MULTISAMPLE);

                //GL11.glViewport(0,0,1600,1200);
            } catch (LWJGLException e) {
                e.printStackTrace();
            }

            VAOContainer dataContainer;// = COLLADAFileLoader.loadDAEtoVAOContainer("res/cube1.dae");

            //OSFLoader.loadToOSF("res/cube.osf", dataContainer);

            dataContainer = OSFLoader.loadFromOSF("res/cube.osf");

            ModelData boxModel = Loader.createModelData(dataContainer);

            ModelTexture texture = new ModelTexture(Loader.loadTexture("res/steam.png"), new DefaultShader()).setShineDamper(2).setReflectivity(1).reloadShader();
            RenderModel renderModel = new RenderModel(boxModel, texture);

            int side = 5;
            float delta = 0.4f;
            float centerZ = -5;

            Entity bigBox = new Entity().setPosition(0, 0, centerZ).setRotation((float) Math.PI / 4, 0, 0, 1);
            Entity bigBigBox = new Entity().setScale(1, 0.5f, 1).addChild(bigBox);

            for (int i = 0; i < side; i++) {
                for (int j = 0; j < side; j++) {
                    for (int k = 0; k < side; k++) {
                        bigBox.addChild(new Entity().setScale(0.2f, 0.2f, 0.2f).setPosition(
                                -delta * (float) (side - 1) / 2 + delta * i,
                                -delta * (float) (side - 1) / 2 + delta * j,
                                -delta * (float) (side - 1) / 2 + delta * k
                        ).addAttribute(renderModel));
                    }
                }
            }

            Entity lamp = new Entity().setPosition(-5, 5, -2.5f);
            Light light = new Light(1, 1, 1);

            lamp.addAttribute(light);

            SkyboxShader skyboxShader = new SkyboxShader();

            MasterRenderer.preload(light);
            MasterRenderer.setSkybox(Loader.loadCubeMap("res/skybox/"), skyboxShader);

            boolean once = true;

            while (!closeRequest) {

                MasterRenderer.render();

                if (once) {
                    once = false;

                    System.setProperty("org.lwjgl.opengl.Window.undecorated", Boolean.toString(true));
                    try {
                        Display.setDisplayMode(Display.getDisplayMode());
                    } catch (LWJGLException e) {
                        e.printStackTrace();
                    }
//                    try {
//                        Display.setFullscreen(true);
//                    } catch (LWJGLException e) {
//                        e.printStackTrace();
//                    }
//                    System.out.println(Display.getWidth());
//                    Display.destroy();
//                    mParent.dispose();
//                    mParent.setSize(1280,800);
//                    mParent.setBounds((mParent.screenWidth - getWidth()) / 2, (mParent.screenHeight - getHeight()) / 2, getWidth(), getHeight());
//                    mParent.setVisible(true);
//                    mParent.toFront();
//                    mParent.requestFocus();
//                    //GL11.glViewport(0,0, 1280, 800);
//                    System.out.println(Display.getWidth());
                }

                if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE))
                    closeRequest = true;

                DisplayManager.updateDisplay();
            }
        } finally {
            ((Frame)mParent).close();
            System.err.println("Cleaning up...");
            ShaderProgram.cleanUp();
            Loader.cleanUp();
            DisplayManager.closeDisplay();
        }
    }

    private void setCloseRequested() {
        closeRequest = true;
    }

    @Override
    public void addNotify() {
        super.addNotify();
        new Thread(new Runnable() {
            @Override
            public void run() {
                start();
            }
        }).start();
    }

    @Override
    public void removeNotify() {
        setCloseRequested();
        super.removeNotify();
    }
} // class GLCanvas
