package com.notjuststudio.engine3dgame;

import com.notjuststudio.engine3dgame.attributes.Attribute;
import com.notjuststudio.engine3dgame.util.Maths;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Quaternion;
import org.lwjgl.util.vector.Vector3f;

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
    private Quaternion rotation;
    private Vector3f scale;

    public Location() {
        this.position = DEFAULT.position;
        this.rotation = DEFAULT.rotation;
        this.scale = DEFAULT.scale;
    }

    public Location(Vector3f position, Quaternion angle, Vector3f scale) {
        this.position = position;
        this.rotation = angle;
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

    public Vector3f getLocalPosition() {
        return position;
    }

    public Quaternion getLocalAngle() {
        return rotation;
    }

    public Vector3f getLocalScale() {
        return scale;
    }

    public Location setLocalPosition(Vector3f position) {
        this.position = position;
        return this;
    }

    public Location setLocalPosition(float x, float y, float z) {
        this.position = new Vector3f(x,y,z);
        return this;
    }

    public Location setLocalAngle(Quaternion rotation) {
        this.rotation = rotation;
        return this;
    }

    public Location setLocalAngle(float angle, float x, float y, float z) {
        this.rotation = Maths.createRotationQuaternion(angle, new Vector3f(x,y,z));
        return this;
    }

    public Location setLocalScale(Vector3f scale) {
        this.scale = scale;
        return this;
    }

    public Location setLocalScale(float x, float y, float z) {
        this.scale = new Vector3f(x,y,z);
        return this;
    }

    public Location addLocalPosition(Vector3f position) {
        this.position = Vector3f.add(this.position, position, null);
        return this;
    }

    public Location addLocalPosition(float x, float y, float z) {
        this.position = Vector3f.add(this.position, new Vector3f(x,y,z), null);
        return this;
    }

    public Location addLocalAngle(Quaternion rotation) {
        this.rotation = Quaternion.mul(rotation, this.rotation, null);
        return this;
    }

    public Location addLocalAngle(float angle, float x, float y, float z) {
        this.rotation = Quaternion.mul(Maths.createRotationQuaternion(angle, new Vector3f(x,y,z)), this.rotation, null);
        return this;
    }

    public Location addLocalScale(Vector3f scale) {
        this.scale = Maths.myMultiplication(this.scale, scale);
        return this;
    }

    public Location addLocalScale(float x, float y, float z) {
        this.scale = Maths.myMultiplication(this.scale, new Vector3f(x,y,z));
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
        if (attribute.getType().SINGLTONE && hasAttributeType(attribute.getType()))
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

    public Attribute getAttributeOfType(Attribute.Type type) {
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
        Matrix4f result = new Matrix4f();
        Matrix4f.setIdentity(result);
        Matrix4f.translate(getTranslation(), result, result);
        return Matrix4f.mul(result, getTransformationMatrixWithoutTrans(), null);
    }

    private Matrix4f getTransformationMatrixWithoutTrans() {
        Matrix4f result = new Matrix4f();
        Matrix4f.setIdentity(result);
        Matrix4f.mul(result, Maths.createRotationMatrix(rotation), result);
        Matrix4f.scale(scale, result, result);
        if (parent == null)
            return result;
        return Matrix4f.mul(parent.getTransformationMatrixWithoutTrans(), result, null);
    }

    private Vector3f getTranslation() {
        return getTranslation(new Vector3f(0,0,0));
    }

    private Vector3f getTranslation(Vector3f translation) {
        Vector3f result = Vector3f.add(position, Maths.impactVectorByMatrix(Maths.createRotationMatrix(Maths.invertQuaternion(rotation)), Maths.myMultiplication(translation, scale)), null);
        if (parent == null)
            return result;
        return parent.getTranslation(result);
    }
}
