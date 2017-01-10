package com.notjuststudio.engine3dgame.attributes;

import org.lwjgl.util.vector.Vector3f;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by George on 07.01.2017.
 */
public class Light extends Attribute {

    private Vector3f colour;

    private static List<Light> allLights = new ArrayList<>();

    public Light(float r, float g, float b) {
        this(new Vector3f(r,g,b));
    }

    public Light(Vector3f colour) {
        super(Type.LIGHT);
        allLights.add(this);
        this.colour = colour;
    }

    public Vector3f getColour() {
        return colour;
    }

    @Override
    public void delete() {
        super.delete();

    }
}
