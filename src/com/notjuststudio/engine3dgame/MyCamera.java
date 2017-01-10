package com.notjuststudio.engine3dgame;

import com.notjuststudio.engine3dgame.attributes.Camera;
import org.lwjgl.input.Keyboard;

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
            addLocalRotation(-(float)Math.PI/4 * DisplayManager.getFrameTimeSeconds(), getTop());
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
            addLocalRotation((float)Math.PI/4  * DisplayManager.getFrameTimeSeconds(), getTop());
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
            addLocalRotation((float)Math.PI/4  * DisplayManager.getFrameTimeSeconds(), getRight());
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
            addLocalRotation(-(float)Math.PI/4  * DisplayManager.getFrameTimeSeconds(), getRight());
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_Q)) {
            addLocalRotation(-(float)Math.PI/4  * DisplayManager.getFrameTimeSeconds(), getFront());
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_E)) {
            addLocalRotation((float)Math.PI/4  * DisplayManager.getFrameTimeSeconds(), getFront());
        }

        camera.resolveViewMatrix();
    }

}
