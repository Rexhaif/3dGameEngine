package com.notjuststudio.engine3dgame;

import com.notjuststudio.engine3dgame.attributes.Camera;
import com.notjuststudio.engine3dgame.util.Maths;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

/**
 * Created by George on 07.01.2017.
 */
public class MyCamera extends Entity {

    private Camera camera;

    public MyCamera() {
        super();
        camera = new Camera();
        this.addAttribute(camera);
        camera.resolveViewMatrix();
    }

    public void move() {
        if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
            addRotationSilent(-(float)Math.PI/4 * DisplayManager.getFrameTimeSeconds(), getTop());
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
            addRotationSilent((float)Math.PI/4  * DisplayManager.getFrameTimeSeconds(), getTop());
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
            addPositionSilent(Maths.scaleVector(getFront(), DisplayManager.getFrameTimeSeconds()));
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
            addPositionSilent(Maths.scaleVector(getBack(), DisplayManager.getFrameTimeSeconds()));
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_Q)) {
            addRotationSilent(-(float)Math.PI/4  * DisplayManager.getFrameTimeSeconds(), getFront());
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_E)) {
            addRotationSilent((float)Math.PI/4  * DisplayManager.getFrameTimeSeconds(), getFront());
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
            addRotationSilent((float)Math.PI/4  * DisplayManager.getFrameTimeSeconds(), getRight());
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
            addRotationSilent(-(float)Math.PI/4  * DisplayManager.getFrameTimeSeconds(), getRight());
        }

        camera.resolveViewMatrix();
    }

}
