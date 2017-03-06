package com.notjuststudio.engine3dgame;

import com.notjuststudio.engine3dgame.attributes.*;
import com.notjuststudio.engine3dgame.attributes.model.ModelData;
import com.notjuststudio.engine3dgame.attributes.model.ModelTexture;
import com.notjuststudio.engine3dgame.attributes.scripts.PyScript;
import com.notjuststudio.engine3dgame.data.TextureData;
import com.notjuststudio.engine3dgame.display.DisplayManager;
import com.notjuststudio.engine3dgame.display.Loader;
import com.notjuststudio.engine3dgame.osfConverter.OSFLoader;
import com.notjuststudio.engine3dgame.osfConverter.VAOContainer;
import com.notjuststudio.engine3dgame.render.MasterRenderer;
import com.notjuststudio.engine3dgame.shader.DefaultShader;
import com.notjuststudio.engine3dgame.shader.GUIShader;
import com.notjuststudio.engine3dgame.shader.ShaderProgram;
import com.notjuststudio.engine3dgame.shader.SkyboxShader;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector2f;
import org.python.core.PyException;
import org.python.util.PythonInterpreter;

import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

/**
 * Created by George on 06.01.2017.
 */
public class Game {

    public static PythonInterpreter pythonInterpreter = new PythonInterpreter();
    public static Queue<String> cmds = new LinkedList<>();

    public static void main(String[] args) {

        try {

            DisplayManager.createDisplay(false, false);

            pythonInterpreter.exec("from com.notjuststudio.engine3dgame.display import DisplayManager");
            pythonInterpreter.exec("from com.notjuststudio.engine3dgame.attributes import Entity");
            pythonInterpreter.exec("from org.lwjgl.input import Keyboard");
            pythonInterpreter.exec("from com.notjuststudio.engine3dgame.attributes import Camera");
            pythonInterpreter.exec("from com.notjuststudio.engine3dgame.util import MathUtil");
            pythonInterpreter.exec("import math");

            pythonInterpreter.exec("from com.notjuststudio.engine3dgame.attributes.scripts import PyScript;\n" +
                    "\n" +
                    "class Script(PyScript):\n" +
                    "    def __init__(self):\n" +
                    "        pass\n" +
                    "    def init(self):\n" +
                    "        pass\n" +
                    "    def step(self):\n" +
                    "        pass\n" +
                    "del PyScript");

            Entity scriptKeeper = new Entity().addAttribute(new Script("Controller","class Controller(Script):\n" +
                    "    def step(self):\n" +
                    "        if Keyboard.isKeyDown(Keyboard.KEY_1):\n" +
                    "            DisplayManager.setFullscreenState(DisplayManager.WINDOWED)\n" +
                    "            DisplayManager.updateDisplaySetting()\n" +
                    "        if Keyboard.isKeyDown(Keyboard.KEY_2):\n" +
                    "            DisplayManager.setFullscreenState(DisplayManager.WINDOWED_BORDERLESS)\n" +
                    "            DisplayManager.updateDisplaySetting()\n" +
                    "        if Keyboard.isKeyDown(Keyboard.KEY_3):\n" +
                    "            DisplayManager.setFullscreenState(DisplayManager.FULLSCREEN)\n" +
                    "            DisplayManager.updateDisplaySetting()\n" +
                    "        if Keyboard.isKeyDown(Keyboard.KEY_ESCAPE):\n" +
                    "            DisplayManager.closeRequest()\n" +
                    ""));


            Entity camera = new Entity().addAttribute(new Script("CameraScript", "class CameraScript(Script):\n" +
                    "    def init(self):\n" +
                    "        self.camera = Camera()\n" +
                    "        self.entity.addAttribute(self.camera)\n" +
                    "        self.camera.resolveViewMatrix()\n" +
                    "    def step(self):\n" +
                    "        if Keyboard.isKeyDown(Keyboard.KEY_D):\n" +
                    "            self.entity.addRotationSilent(-math.pi/4 * DisplayManager.getFrameTimeSeconds(), self.entity.getTop())\n" +
                    "        #    self.entity.addPositionSilent(MathUtil.scaleVector(self.entity.getRight(), DisplayManager.getFrameTimeSeconds()))\n" +
                    "        if Keyboard.isKeyDown(Keyboard.KEY_A):\n" +
                    "            self.entity.addRotationSilent(math.pi/4 * DisplayManager.getFrameTimeSeconds(), self.entity.getTop())\n" +
                    "        #    self.entity.addPositionSilent(MathUtil.scaleVector(self.entity.getLeft(), DisplayManager.getFrameTimeSeconds()))\n" +
                    "        if Keyboard.isKeyDown(Keyboard.KEY_W):\n" +
                    "            self.entity.addPositionSilent(MathUtil.scaleVector(self.entity.getFront(), DisplayManager.getFrameTimeSeconds()))\n" +
                    "        if Keyboard.isKeyDown(Keyboard.KEY_S):\n" +
                    "            self.entity.addPositionSilent(MathUtil.scaleVector(self.entity.getBack(), DisplayManager.getFrameTimeSeconds()))\n" +
                    "        if Keyboard.isKeyDown(Keyboard.KEY_Q):\n" +
                    "            self.entity.addRotationSilent(-math.pi/4 * DisplayManager.getFrameTimeSeconds(), self.entity.getFront())\n" +
                    "        if Keyboard.isKeyDown(Keyboard.KEY_E):\n" +
                    "            self.entity.addRotationSilent(math.pi/4 * DisplayManager.getFrameTimeSeconds(), self.entity.getFront())\n" +
                    "        if Keyboard.isKeyDown(Keyboard.KEY_SPACE):\n" +
                    "            self.entity.addRotationSilent(math.pi/4 * DisplayManager.getFrameTimeSeconds(), self.entity.getRight())\n" +
                    "        if Keyboard.isKeyDown(Keyboard.KEY_LSHIFT):\n" +
                    "            self.entity.addRotationSilent(-math.pi/4 * DisplayManager.getFrameTimeSeconds(), self.entity.getRight())\n" +
                    "        self.entity.resolveTransformationMatrix()\n" +
                    "        self.camera.resolveViewMatrix()\n" +
                    ""));

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

            TextureData data = Loader.decodeTextureFile("res/steam.png");
            TextureData another = Loader.decodeTextureFile( "res/5734a6bbd72df154a5ab4de9.png");
            Entity guiKeeper = new Entity().setPosition(DisplayManager.getWidth() / 2, DisplayManager.getHeight() / 2, 0).setScale(2,1,1)
                    .addAttribute(new GUI(another, new Vector2f(0, 0), new GUIShader()))
                    .addAttribute(new GUI(data, new Vector2f(data.getWidth() / 2, data.getHeight() / 2), new GUIShader()))
                    .addAttribute(new GUI(another, new Vector2f(data.getWidth(), data.getHeight()), new GUIShader()))
                    .addAttribute(new Script("GuiCmd", "class GuiCmd(Script):\n" +
                            "    def step(self):\n" +
                            "        if Keyboard.isKeyDown(Keyboard.KEY_Z):\n" +
                            "            self.entity.addRotationSilent(-math.pi/4 * DisplayManager.getFrameTimeSeconds(), self.entity.getTop())\n" +
                            "        if Keyboard.isKeyDown(Keyboard.KEY_C):\n" +
                            "            self.entity.addRotationSilent(math.pi/4 * DisplayManager.getFrameTimeSeconds(), self.entity.getTop())\n" +
                            "        "));

            MasterRenderer.setSkybox(Loader.loadCubeMap("res/skybox/"), skyboxShader);

            for (PyScript script : Script.getScripts()) {
                script.init();
            }

            MasterRenderer.preload(light);

            while (!DisplayManager.isCloseRequested()) {

                console:
                while(true) {
                    try {
                        pythonInterpreter.exec(cmds.remove());
                    } catch (PyException e) {
                        e.printStackTrace();
                    } catch (NoSuchElementException e) {
                        break console;
                    }
                }

                for (PyScript script : Script.getScripts()) {
                    script.step();
                }

                if (Mouse.isInsideWindow() && Mouse.isButtonDown(0)) {
                    guiKeeper.setPosition(Mouse.getX(), Mouse.getY(), 0);
                }

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
