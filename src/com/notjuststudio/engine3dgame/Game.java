package com.notjuststudio.engine3dgame;

import com.notjuststudio.engine3dgame.attributes.Attribute;
import com.notjuststudio.engine3dgame.attributes.Model;
import com.notjuststudio.engine3dgame.colladaConverter.COLLADA;
import com.notjuststudio.engine3dgame.colladaConverter.COLLADAFileLoader;
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

        ModelData boxModel = COLLADAFileLoader.loadDAE("res/cube1.dae");

        ModelTexture texture = new ModelTexture(Loader.loadTexture("res/steam.png"), new MyShaderProgram());
        Model model = new Model(boxModel, texture);

        Location box = new Location().setLocalPosition(0,0,-5);
        box.addAttribute(model);

        MyCamera cameraKeeper = new MyCamera();

        while(!Display.isCloseRequested()) {

            cameraKeeper.move();
            box.addLocalAngle((float)Math.PI/4 * DisplayManager.getFrameTimeSeconds(), 0,1,0);

            Renderer.prepare();

            ((Model)box.getAttributeOfType(Attribute.Type.RENDER_MODEL)).getTexture().getShaderProgram().useThis();
            Renderer.render(cameraKeeper,box);

            ShaderProgram.useNone();

            DisplayManager.updateDisplay();
        }

        ShaderProgram.cleanUp();
        Loader.cleanUp();
        DisplayManager.closeDisplay();
    }
}
