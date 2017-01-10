package com.notjuststudio.engine3dgame.attributes;

import com.notjuststudio.engine3dgame.Entity;

/**
 * Created by George on 06.01.2017.
 */
public abstract class Attribute implements Cloneable {

    protected Entity entity = null;
    protected Type type;

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

    public void delete() {
        entity.getAttributes().remove(this);
        entity = null;
    };

    protected Attribute(Type type) {
        this.type = type;
    }

    public Attribute setEntity(Entity entity) {
        if (!entity.getAttributes().contains(this)) {
            entity.getAttributes().add(this);
        }
        this.entity = entity;
        return this;
    }

    public Entity getEntity() {
        return entity;
    }

    public Type getType() {
        return type;
    }

}
