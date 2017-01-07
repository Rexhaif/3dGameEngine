package com.notjuststudio.engine3dgame.attributes;

import com.notjuststudio.engine3dgame.Keeper;

/**
 * Created by George on 06.01.2017.
 */
public abstract class Attribute implements Cloneable {

    private Keeper location = null;
    private Type type;

    public enum Type {
        RENDER_MODEL (true),
        CAMERA (true),
        LIGHT (true);
        public final boolean SINGLTONE;

        Type (boolean SINGLTONE) {
            this.SINGLTONE = SINGLTONE;
        }
    }

    public Attribute getCopy() {
        try {
            return (Attribute)this.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }


    public Attribute(Type type) {
        this.type = type;
    }

    public void setLocation(Keeper location) {
        this.location = location;
    }

    public Keeper getKeeper() {
        return location;
    }

    public Type getType() {
        return type;
    }

}
