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

    private Vector3f localPosition;
    private Quaternion localAngle;
    private Vector3f localScale;

    private Vector3f position = null;
    private Quaternion angle = null;
    private Vector3f scale = null;

    public Location(Vector3f position, Quaternion angle, Vector3f scale) {
        this.localPosition = position;
        this.localAngle = angle;
        this.localScale = scale;
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

//    public Vector3f getPosition() {
//        if (parent == null)
//            return localPosition;
//        //if (position == null)
//            position = Vector3f.add(Maths.impactVectorByMatrix(Maths.createRotationMatrix(Maths.invertQuaternion(parent.getAngle())),Maths.myMultiplication(localPosition, parent.getScale())), parent.getPosition(), null);
//        return position;
//    }
//
//    public Quaternion getAngle() {
//        if (parent == null)
//            return localAngle;
//        //if (angle == null)
//            angle = Quaternion.mul(localAngle, parent.getAngle(), null);
//        return angle;
//    }
//
//    public Vector3f getScale() {
//        if (parent == null)
//            return localScale;
//        //if (scale == null)
//            scale = Maths.impactVectorByMatrix(Maths.createRotationMatrix(getAngle()),Maths.myMultiplication(parent.getScale(), localScale));
//        return scale;
//    }

    public Vector3f getLocalPosition() {
        return localPosition;
    }

    public Quaternion getLocalAngle() {
        return localAngle;
    }

    public Vector3f getLocalScale() {
        return localScale;
    }

    public Location setLocalPosition(Vector3f position) {
        this.localPosition = position;
        this.position = null;
        return this;
    }

    public Location setLocalAngle(Quaternion angle) {
        this.localAngle = angle;
        this.angle = null;
        return this;
    }

    public Location setLocalScale(Vector3f scale) {
        this.localScale = scale;
        this.scale = null;
        return this;
    }

    public Location addLocalPosition(Vector3f position) {
        this.localPosition = Vector3f.add(this.localPosition, position, null);
        this.position = null;
        return this;
    }

    public Location addLocalAngle(Quaternion angle) {
        this.localAngle = Quaternion.mul(angle, this.localAngle, null);
        this.angle = null;
        return this;
    }

    public Location addLocalScale(Vector3f scale) {
        this.localScale = Maths.myMultiplication(this.localScale, scale);
        this.scale = null;
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
        Matrix4f result = new Matrix4f();
        Matrix4f.setIdentity(result);
        Matrix4f.translate(getTranslation(), result, result);
        return Matrix4f.mul(result, getTransformationMatrixWithoutTrans(), null);
    }

    private Matrix4f getTransformationMatrixWithoutTrans() {
        Matrix4f result = new Matrix4f();
        Matrix4f.setIdentity(result);
        Matrix4f.mul(result, Maths.createRotationMatrix(localAngle), result);
        Matrix4f.scale(localScale, result, result);
        if (parent == null)
            return result;
        return Matrix4f.mul(parent.getTransformationMatrixWithoutTrans(), result, null);
    }

    private Vector3f getTranslation() {
        return getTranslation(new Vector3f(0,0,0));
    }

    private Vector3f getTranslation(Vector3f translation) {
        Vector3f result = Vector3f.add(localPosition, Maths.impactVectorByMatrix(Maths.createRotationMatrix(Maths.invertQuaternion(localAngle)), Maths.myMultiplication(translation, localScale)), null);
        if (parent == null)
            return result;
        return parent.getTranslation(result);
    }
}
