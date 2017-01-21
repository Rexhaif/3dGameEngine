package com.notjuststudio.engine3dgame;

import com.notjuststudio.engine3dgame.attributes.Light;
import com.notjuststudio.engine3dgame.attributes.RenderModel;
import com.notjuststudio.engine3dgame.attributes.model.ModelData;
import com.notjuststudio.engine3dgame.attributes.model.ModelTexture;
import com.notjuststudio.engine3dgame.colladaConverter.COLLADAFileLoader;
import com.notjuststudio.engine3dgame.colladaConverter.colladaschema.Matrix;
import com.notjuststudio.engine3dgame.osfConverter.OSFLoader;
import com.notjuststudio.engine3dgame.osfConverter.VAOContainer;
import com.notjuststudio.engine3dgame.render.MasterRenderer;
import com.notjuststudio.engine3dgame.shader.ShaderProgram;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Quaternion;

/**
 * Created by George on 06.01.2017.
 */
public class Game {

    public static void main(String[] args) {

        try {

            DisplayManager.createDisplay();

            VAOContainer dataContainer;// = COLLADAFileLoader.loadDAEtoVAOContainer("res/cube1.dae");

            //OSFLoader.loadToOSF("res/cube.osf", dataContainer);

            dataContainer = OSFLoader.loadFromOSF("res/cube.osf");

            ModelData boxModel = Loader.createModelData(dataContainer);

            ModelTexture texture = new ModelTexture(Loader.loadTexture("res/steam.png"), new DefaultProgram()).setShineDamper(10).setReflectivity(1);
            RenderModel renderModel = new RenderModel(boxModel, texture);

            int side = 5;
            float delta = 1f;
            float centerZ = -100;

            MyCamera cameraKeeper = new MyCamera();

            Entity bigBox = new Entity().setPosition(0, 0, -5).setScale(1, 1, 1).setRotation((float) Math.PI / 4, 0, 0, 1);
            Entity bigBigBox = new Entity().setScale(1, 0.5f, 1).addChild(bigBox);

            for (int i = 0; i < side; i++) {
                for (int j = 0; j < side; j++) {
                    for (int k = 0; k < side; k++) {
                        bigBox.addChild(new Entity().setScale(0.4f, 0.2f, 0.2f).setPosition(
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

            while (!Display.isCloseRequested()) {

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
}
