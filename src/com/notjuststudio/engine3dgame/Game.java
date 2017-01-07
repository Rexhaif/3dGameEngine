package com.notjuststudio.engine3dgame;

import com.notjuststudio.engine3dgame.attributes.Attribute;
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

        Location picture1 = Location.getDefault().setLocalPosition(new Vector3f(0.5f,0.5f,0)).setLocalScale(new Vector3f(0.5f, 0.5f, 0.5f));
        picture1.addAttribute(model.getCopy());
        Location picture2 = Location.getDefault().setLocalPosition(new Vector3f(-0.5f,0.5f,0)).setLocalScale(new Vector3f(0.5f, 0.5f, 0.5f));
        picture2.addAttribute(model.getCopy());
        Location picture3 = Location.getDefault().setLocalPosition(new Vector3f(-0.5f,-0.5f,0)).setLocalScale(new Vector3f(0.5f, 0.5f, 0.5f));
        picture3.addAttribute(model.getCopy());
        Location picture4 = Location.getDefault().setLocalPosition(new Vector3f(0.5f,-0.5f,0)).setLocalScale(new Vector3f(0.5f, 0.5f, 0.5f));
        picture4.addAttribute(model.getCopy());
        Location main_axis  = Location.getDefault().setLocalPosition(new Vector3f(0.5f,0,0)).setLocalScale(new Vector3f(0.5f,0.5f,0.5f));
        main_axis.addChild(picture1);
        main_axis.addChild(picture2);
        main_axis.addChild(picture3);
        main_axis.addChild(picture4);
        main_axis.addAttribute(model.getCopy());

        while(!Display.isCloseRequested()) {

            main_axis.addLocalPosition(new Vector3f(-(float)Math.PI / 28 * DisplayManager.getFrameTimeSeconds(),0,0));

            main_axis.addLocalAngle(Maths.createRotationQuaternion(-(float)Math.PI / 7 * DisplayManager.getFrameTimeSeconds(), new Vector3f(0,0,1)));
            picture1.addLocalAngle(Maths.createRotationQuaternion(-(float)Math.PI / 7 * DisplayManager.getFrameTimeSeconds(), new Vector3f(0,0,1)));
            picture2.addLocalAngle(Maths.createRotationQuaternion(-(float)Math.PI / 7 * DisplayManager.getFrameTimeSeconds(), new Vector3f(0,0,1)));
            picture3.addLocalAngle(Maths.createRotationQuaternion(-(float)Math.PI / 7 * DisplayManager.getFrameTimeSeconds(), new Vector3f(0,0,1)));
            picture4.addLocalAngle(Maths.createRotationQuaternion(-(float)Math.PI / 7 * DisplayManager.getFrameTimeSeconds(), new Vector3f(0,0,1)));

            Renderer.prepare();

            ((Model)main_axis.getAttributeType(Attribute.Type.RENDER_MODEL)).getTexture().getShaderProgram().useThis();
            Renderer.render(main_axis);
            ((Model)picture1.getAttributeType(Attribute.Type.RENDER_MODEL)).getTexture().getShaderProgram().useThis();
            Renderer.render(picture1);
            ((Model)picture2.getAttributeType(Attribute.Type.RENDER_MODEL)).getTexture().getShaderProgram().useThis();
            Renderer.render(picture2);
            ((Model)picture3.getAttributeType(Attribute.Type.RENDER_MODEL)).getTexture().getShaderProgram().useThis();
            Renderer.render(picture3);
            ((Model)picture4.getAttributeType(Attribute.Type.RENDER_MODEL)).getTexture().getShaderProgram().useThis();
            Renderer.render(picture4);
            ShaderProgram.useNone();

            DisplayManager.updateDisplay();
        }

        ShaderProgram.cleanUp();
        Loader.cleanUp();
        DisplayManager.closeDisplay();
    }
}
