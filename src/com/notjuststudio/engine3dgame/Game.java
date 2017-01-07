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

        Location canvas = Location.getDefault().setLocalScale(new Vector3f(1,0.5f,1));
        Location picture1 = Location.getDefault().setLocalPosition(new Vector3f(-0.5f,0,0)).setLocalScale(new Vector3f(0.75f,0.75f,0)).setLocalAngle(Maths.createRotationQuaternion((float)Math.PI/4,new Vector3f(0,0,1)));
        picture1.addAttribute(model.getCopy());
        canvas.addChild(picture1);
        Location picture2 = Location.getDefault().setLocalPosition(new Vector3f(0.5f,0.5f,0)).setLocalScale(new Vector3f(0.75f, 0.75f, 1));
        picture2.addAttribute(model.getCopy());
        picture1.addChild(picture2);
        Location picture3 = Location.getDefault().setLocalPosition(new Vector3f(0.25f,0.25f,0)).setLocalScale(new Vector3f(0.75f, 0.25f, 0.5f)).setLocalAngle(Maths.createRotationQuaternion(-(float)Math.PI/4, new Vector3f(0,0,1)));
        picture3.addAttribute(model.getCopy());
        Location gear = Location.getDefault().setLocalPosition(new Vector3f(-0.5f,-0.5f,0));
        picture2.addChild(gear);
        gear.addChild(picture3);

        while(!Display.isCloseRequested()) {

            Renderer.prepare();

            gear.addLocalAngle(Maths.createRotationQuaternion(-(float)Math.PI/2*DisplayManager.getFrameTimeSeconds(),new Vector3f(0,0,1)));
            picture2.addLocalAngle(Maths.createRotationQuaternion((float)Math.PI/2*DisplayManager.getFrameTimeSeconds(),new Vector3f(0,0,1)));

            ((Model)picture1.getAttributeType(Attribute.Type.RENDER_MODEL)).getTexture().getShaderProgram().useThis();
            Renderer.render(picture1);
            ((Model)picture2.getAttributeType(Attribute.Type.RENDER_MODEL)).getTexture().getShaderProgram().useThis();
            Renderer.render(picture2);
            ((Model)picture3.getAttributeType(Attribute.Type.RENDER_MODEL)).getTexture().getShaderProgram().useThis();
            Renderer.render(picture3);
            ShaderProgram.useNone();

            DisplayManager.updateDisplay();
        }

        ShaderProgram.cleanUp();
        Loader.cleanUp();
        DisplayManager.closeDisplay();
    }
}
