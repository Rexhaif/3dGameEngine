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
import com.notjuststudio.engine3dgame.shader.ShadersContainer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.Display;

import java.nio.FloatBuffer;

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

        VAOContainer container = COLLADAFileLoader.loadDAEtoVAOContainer("res/cube1.dae");

        OSFLoader.loadToOSF("res/cube.osf", container);
        System.out.println(OSFLoader.floatBufferToArray(container.getPositions())[0]);

        container = OSFLoader.loadFromOSF("res/cube.osf");
        OSFLoader.loadToOSF("res/cube1.osf", container);
        System.out.println(OSFLoader.floatBufferToArray(container.getPositions())[0]);

        container = OSFLoader.loadFromOSF("res/cube1.osf");
        OSFLoader.loadToOSF("res/cube2.osf", container);
        System.out.println(OSFLoader.floatBufferToArray(container.getPositions())[0]);

        container = OSFLoader.loadFromOSF("res/cube2.osf");
        OSFLoader.loadToOSF("res/cube3.osf", container);
        System.out.println(OSFLoader.floatBufferToArray(container.getPositions())[0]);
//
//        container = OSFLoader.loadFromOSF("res/cube3.osf");

        ModelData boxModel = Loader.createModelData(container);

        ModelTexture texture = new ModelTexture(Loader.loadTexture("res/steam.png"), new MyShaderProgram()).setShineDamper(10).setReflectivity(1);
        Model model = new Model(boxModel, texture);

        Keeper box = new Keeper().setLocalPosition(0,0,-5);
        box.addAttribute(model);

        MyCamera cameraKeeper = new MyCamera();

        Keeper lamp = new Keeper().setLocalPosition(-5,5,-2.5f);
        Light light = new Light(1,1,1);

        lamp.addAttribute(light);

        MasterRenderer.preload(light);

        while(!Display.isCloseRequested()) {

            cameraKeeper.move();
            box.addLocalAngle((float)Math.PI/4 * DisplayManager.getFrameTimeSeconds(), 1,1,0);


            MasterRenderer.render();

            DisplayManager.updateDisplay();
        }

        ShaderProgram.cleanUp();
        Loader.cleanUp();
        DisplayManager.closeDisplay();
    }
}
