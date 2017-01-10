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
            addLocalPosition(0.1f * DisplayManager.getFrameTimeSeconds(), 0, 0);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
            addLocalPosition(-0.1f * DisplayManager.getFrameTimeSeconds(), 0, 0);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
            addLocalPosition(0, 0.1f * DisplayManager.getFrameTimeSeconds(), 0);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
            addLocalPosition(0, -0.1f * DisplayManager.getFrameTimeSeconds(), 0);
        }

        camera.resolveViewMatrix();
    }

}
