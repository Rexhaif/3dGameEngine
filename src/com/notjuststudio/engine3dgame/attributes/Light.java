package com.notjuststudio.engine3dgame.attributes;

import org.lwjgl.util.vector.Vector3f;

/**
 * Created by George on 07.01.2017.
 */
public class Light extends Attribute {

    private Vector3f colour;

    public Light(float r, float g, float b) {
        this(new Vector3f(r,g,b));
    }

    public Light(Vector3f colour) {
        super(Type.LIGHT);
        this.colour = colour;
    }

    public Vector3f getColour() {
        return colour;
    }
}
