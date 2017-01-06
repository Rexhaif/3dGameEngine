package com.notjuststudio.engine3dgame;

import com.notjuststudio.engine3dgame.attributes.Model;
import com.notjuststudio.engine3dgame.shader.ShaderProgram;
import com.notjuststudio.engine3dgame.util.Maths;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Quaternion;
import org.lwjgl.util.vector.Vector3f;

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

        ModelData restangle = Loader.createModelData(indices, vertices, uvCoords);
        ModelTexture texture = new ModelTexture(Loader.loadTexture("res/steam.png"), new MyShaderProgram());
        Model model = new Model(restangle, texture);

        Location picture = new Location(new Vector3f(0,0,0), new Quaternion(0,0,0,1),new Vector3f(1,1,1));
        picture.addAttribute(model);

        while(!Display.isCloseRequested()) {

            picture.addLocalAngle(Maths.createRotationQuaternion((float)Math.PI/2 * DisplayManager.getFrameTimeSeconds(), new Vector3f(0,0,1)));

            Renderer.prepare();

            model.getTexture().getShaderProgram().useThis();
            Renderer.render(picture);
            ShaderProgram.useNone();

            DisplayManager.updateDisplay();
        }

        ShaderProgram.cleanUp();
        Loader.cleanUp();
        DisplayManager.closeDisplay();
    }
}
