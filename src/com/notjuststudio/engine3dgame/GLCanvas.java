package com.notjuststudio.engine3dgame;

import com.notjuststudio.engine3dgame.attributes.Light;
import com.notjuststudio.engine3dgame.attributes.RenderModel;
import com.notjuststudio.engine3dgame.attributes.model.ModelData;
import com.notjuststudio.engine3dgame.attributes.model.ModelTexture;
import com.notjuststudio.engine3dgame.osfConverter.OSFLoader;
import com.notjuststudio.engine3dgame.osfConverter.VAOContainer;
import com.notjuststudio.engine3dgame.render.MasterRenderer;
import com.notjuststudio.engine3dgame.shader.ShaderProgram;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.AWTGLCanvas;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import javax.swing.*;

public class GLCanvas extends AWTGLCanvas {

    private boolean closeRequest = false;
    protected JFrame mParent;

    private final int width = 400;
    private final int height = 300;

    public GLCanvas(JFrame parent) throws LWJGLException {
        super();
        mParent = parent;
    }

    public void start() {
        try {
            try {
                Display.setDisplayMode(new DisplayMode(width, height));

                Display.setParent(this);
                Display.setVSyncEnabled(true);

                Display.create();
            } catch (LWJGLException e) {
                e.printStackTrace();
            }

            VAOContainer dataContainer;// = COLLADAFileLoader.loadDAEtoVAOContainer("res/cube1.dae");

            //OSFLoader.loadToOSF("res/cube.osf", dataContainer);

            dataContainer = OSFLoader.loadFromOSF("res/cube.osf");

            ModelData boxModel = Loader.createModelData(dataContainer);

            ModelTexture texture = new ModelTexture(Loader.loadTexture("res/steam.png"), new DefaultProgram()).setShineDamper(2).setReflectivity(1).reloadShader();
            RenderModel renderModel = new RenderModel(boxModel, texture);

            int side = 5;
            float delta = 0.4f;
            float centerZ = -5;

            MyCamera cameraKeeper = new MyCamera();

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

            while (!closeRequest) {

                cameraKeeper.move();

                MasterRenderer.render();

                DisplayManager.updateDisplay();
            }
        } finally {
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
