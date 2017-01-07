package com.notjuststudio.engine3dgame;

import com.notjuststudio.engine3dgame.attributes.Attribute;
import com.notjuststudio.engine3dgame.util.Maths;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Quaternion;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by George on 06.01.2017.
 */
public class Location implements Cloneable{

    public static Location getDefault() {
        try {
            return (Location)DEFAULT.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static final Location DEFAULT = new Location(new Vector3f(0,0,0), new Quaternion(0,0,0,1),new Vector3f(1,1,1));

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
        return Vector3f.add(Maths.impactVectorByMatrix(Maths.createRotationMatrix(Maths.invertQuaternion(parent.getAngle())),Maths.myMultiplication(position, parent.getScale())), parent.getPosition(), null);
    }

    public Quaternion getAngle() {
        if (parent == null) {
            return angle;
        }
        return Quaternion.mul(angle, parent.getAngle(), null);
    }

    public Vector3f getScale() {
        if (parent == null) {
            return scale;
        }
        return Maths.myMultiplication(parent.getScale(), scale);
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
        this.scale = scale;
        return this;
    }

    public Location addLocalPosition(Vector3f position) {
        this.position = Vector3f.add(this.position, position, null);
        return this;
    }

    public Location addLocalAngle(Quaternion angle) {
        this.angle = Quaternion.mul(angle, this.angle, null);
        return this;
    }

    public Location addLocalScale(Vector3f scale) {
        this.scale = Maths.myMultiplication(this.scale, scale);
        return this;
    }

    public Location setParent(Location parent) {
        parent.addChild(this);
        return this;
    }

    public Location addChild(Location child) {
        if (child == this)
            return this;
        this.children.add(child);
        child.parent = this;
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

    public Matrix4f getTransformationMatrix() {
        return Maths.createTransformationMatrix(position,angle,scale);
    }
}
