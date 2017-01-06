package com.notjuststudio.engine3dgame;

import com.notjuststudio.engine3dgame.attributes.Attribute;
import org.lwjgl.util.vector.Quaternion;
import org.lwjgl.util.vector.Vector3f;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by George on 06.01.2017.
 */
public class Location {

    private Location parent = null;
    private List<Location> children = new ArrayList<>();

    private List<Attribute> attributes = new ArrayList<>();

    private Vector3f position;
    private Quaternion angle;
    private Vector3f scale;

    public Location(Vector3f position, Quaternion angle, Vector3f scale) {
        this.position = position;
        this.angle = angle;
        this.scale = scale;
    }

    public Location getParent() {
        return parent;
    }

    public List<Location> getChildren() {
        return children;
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public Vector3f getPosition() {
        if (parent == null) {
            return position;
        }
        return Vector3f.add(parent.getPosition(), position, null);
    }

    public Quaternion getAngle() {
        if (parent == null) {
            return angle;
        }
        return Quaternion.mul(parent.getAngle(), angle, null);
    }

    public Vector3f getScale() {
        if (parent == null) {
            return scale;
        }
        Vector3f parentScale = parent.getScale();
        return new Vector3f(parentScale.getX() * scale.getX(), parentScale.getY() * scale.getY(), parentScale.getZ() * scale.getZ());
    }

    public Vector3f getLocalPosition() {
        return position;
    }

    public Quaternion getLocalAngle() {
        return angle;
    }

    public Vector3f getLocalScale() {
        return scale;
    }

    public Location setLocalPosition(Vector3f position) {
        this.position = position;
        return this;
    }

    public Location setLocalAngle(Quaternion angle) {
        this.angle = angle;
        return this;
    }

    public Location setLocalScale(Vector3f scale) {
        this.scale.set(scale);
        return this;
    }

    public Location addLocalPosition(Vector3f position) {
        Vector3f.add(this.position, position, this.position);
        return this;
    }

    public Location addLocalAngle(Quaternion angle) {
        Quaternion.mul(this.angle, angle, this.angle);
        return this;
    }

    public Location addLocalScale(Vector3f scale) {
        Vector3f.add(this.scale, scale, this.scale);
        return this;
    }

    public Location setParent(Location parent) {
        this.parent = parent;
        return this;
    }

    public Location addChild(Location child) {
        this.children.add(child);
        return this;
    }

    public Location removeChild(Location child) {
        this.children.remove(child);
        return this;
    }

    public Location addAttribute(Attribute attribute) {
        if (Attribute.SINGLTONE[attribute.getType().ordinal()] && hasAttributeType(attribute.getType()))
            return this;
        this.attributes.add(attribute);
        attribute.setLocation(this);
        return this;
    }

    public Location removeAttribute(Attribute attribute) {
        this.attributes.remove(attribute);
        attribute.setLocation(null);
        return this;
    }

    public Attribute getAttributeType(Attribute.Type type) {
        for (Attribute attribute : attributes) {
            if (attribute.getType() == type) {
                return attribute;
            }
        }
        return null;
    }

    public boolean hasAttributeType(Attribute.Type type) {
        for (Attribute attribute : attributes) {
            if (attribute.getType() == type) {
                return true;
            }
        }
        return false;
    }
}
