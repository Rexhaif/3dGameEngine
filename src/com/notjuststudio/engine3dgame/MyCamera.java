package com.notjuststudio.engine3dgame;

import com.notjuststudio.engine3dgame.attributes.Camera;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Quaternion;
import org.lwjgl.util.vector.Vector3f;

/**
 * Created by George on 07.01.2017.
 */
public class MyCamera extends Location {

    public MyCamera() {
        super();
        Camera camera = new Camera();
        this.addAttribute(camera);
    }

    public void move() {
        if (Keyboard.isKeyDown(Keyboard.KEY_D)){
            addLocalPosition(new Vector3f(0.1f * DisplayManager.getFrameTimeSeconds(), 0,0));
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_A)){
            addLocalPosition(new Vector3f(-0.1f * DisplayManager.getFrameTimeSeconds(), 0,0));
        }
    }

}
