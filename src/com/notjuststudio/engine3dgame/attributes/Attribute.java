package com.notjuststudio.engine3dgame.attributes;

import com.notjuststudio.engine3dgame.Location;

/**
 * Created by George on 06.01.2017.
 */
public abstract class Attribute {

    private Location location = null;
    private Type type;

    public enum Type {
        RENDER_MODEL
    }

    public static final boolean[] SINGLTONE = {
            true,
    };

    public Attribute(Type type) {
        this.type = type;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Type getType() {
        return type;
    }

}
