package com.notjuststudio.engine3dgame;

import com.notjuststudio.engine3dgame.attributes.Light;
import com.notjuststudio.engine3dgame.attributes.Model;
import com.notjuststudio.engine3dgame.attributes.model.ModelData;
import com.notjuststudio.engine3dgame.attributes.model.ModelTexture;
import com.notjuststudio.engine3dgame.colladaConverter.COLLADAFileLoader;
import com.notjuststudio.engine3dgame.osfConverter.OSFLoader;
import com.notjuststudio.engine3dgame.osfConverter.VAOContainer;
import com.notjuststudio.engine3dgame.render.MasterRenderer;
import com.notjuststudio.engine3dgame.shader.ShaderProgram;
import org.lwjgl.opengl.Display;

/**
 * Created by George on 06.01.2017.
 */
public class Game {

    public static void main(String[] args) {

        DisplayManager.createDisplay();

        int[] indices = {
                0, 1, 2,
                0, 2, 3
        };

        float[] vertices = {
                0.5f, 0.5f, 0,
                -0.5f, 0.5f, 0,
                -0.5f, -0.5f, 0,
                0.5f, -0.5f, 0
        };

        float[] uvCoords = {
                1, 0,
                0, 0,
                0, 1,
                1, 1
        };

        VAOContainer dataContainer = COLLADAFileLoader.loadDAEtoVAOContainer("res/cube1.dae");

        OSFLoader.loadToOSF("res/cube.osf", dataContainer);

        dataContainer = OSFLoader.loadFromOSF("res/cube.osf");

        ModelData boxModel = Loader.createModelData(dataContainer);

        ModelTexture texture = new ModelTexture(Loader.loadTexture("res/steam.png"), new MyShaderProgram()).setShineDamper(10).setReflectivity(1);
        Model model = new Model(boxModel, texture);

        int side = 25;
        float delta = 3f;
        float centerZ = -175;

        Entity box = new Entity().setLocalRotation(-(float)Math.PI/4, 0,0,1);
        Entity boxScaler = new Entity().setLocalScale(1,0.25f,(float)Math.sqrt(2)).addChild(box);
        Entity boxRotator = new Entity().setLocalRotation((float)Math.PI/2, 1,0,0).addChild(boxScaler);
        Entity boxPosition = new Entity().setLocalPosition(0,0,centerZ).addChild(boxRotator);

        for (int i = 0; i < side; i++) {
            for (int j = 0; j < side; j++) {
                for (int k = 0; k < side; k++) {
                    box.addChild(new Entity().setLocalPosition(
                            -delta * ((float) (side - 1) / 2) + i * delta,
                            -delta * ((float) (side - 1) / 2) + j * delta,
                            -delta * ((float) (side - 1) / 2) + k * delta
                    ).addAttribute(model));
                }
            }
        }

        MyCamera cameraKeeper = new MyCamera();

        Entity lamp = new Entity().setLocalPosition(-5,5,-2.5f);
        Light light = new Light(1,1,1);

        lamp.addAttribute(light);

        MasterRenderer.preload(light);

        while(!Display.isCloseRequested()) {

            cameraKeeper.move();
            boxScaler.addLocalRotation(-(float) Math.PI * 4 * DisplayManager.getFrameTimeSeconds(), 0,1,0);
            boxPosition.addLocalRotation(-(float) Math.PI /4 * DisplayManager.getFrameTimeSeconds(), 1,1,0);

            MasterRenderer.render();

            DisplayManager.updateDisplay();
        }

        ShaderProgram.cleanUp();
        Loader.cleanUp();
        DisplayManager.closeDisplay();
    }
}
