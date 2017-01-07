package com.notjuststudio.engine3dgame;

import com.notjuststudio.engine3dgame.attributes.Attribute;
import com.notjuststudio.engine3dgame.attributes.Model;
import com.notjuststudio.engine3dgame.util.Maths;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Quaternion;
import org.lwjgl.util.vector.Vector3f;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by George on 06.01.2017.
 */
public class Keeper implements Cloneable{

    public static Keeper getDefault() {
        try {
            return (Keeper)DEFAULT.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static final Keeper DEFAULT = new Keeper(new Vector3f(0,0,0), new Quaternion(0,0,0,1),new Vector3f(1,1,1));

    private Keeper parent = null;
    private List<Keeper> children = new ArrayList<>();

    private List<Attribute> attributes = new ArrayList<>();

    private Vector3f position;
    private Quaternion rotation;
    private Vector3f scale;

    public Keeper() {
        this.position = DEFAULT.position;
        this.rotation = DEFAULT.rotation;
        this.scale = DEFAULT.scale;
    }

    public Keeper(Vector3f position, Quaternion angle, Vector3f scale) {
        this.position = position;
        this.rotation = angle;
        this.scale = scale;
    }

    public Keeper getParent() {
        return parent;
    }

    public List<Keeper> getChildren() {
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

    public Keeper setLocalPosition(Vector3f position) {
        this.position = position;
        return this;
    }

    public Keeper setLocalPosition(float x, float y, float z) {
        this.position = new Vector3f(x,y,z);
        return this;
    }

    public Keeper setLocalAngle(Quaternion rotation) {
        this.rotation = rotation;
        return this;
    }

    public Keeper setLocalAngle(float angle, float x, float y, float z) {
        this.rotation = Maths.createRotationQuaternion(angle, new Vector3f(x,y,z));
        return this;
    }

    public Keeper setLocalScale(Vector3f scale) {
        this.scale = scale;
        return this;
    }

    public Keeper setLocalScale(float x, float y, float z) {
        this.scale = new Vector3f(x,y,z);
        return this;
    }

    public Keeper addLocalPosition(Vector3f position) {
        this.position = Vector3f.add(this.position, position, null);
        return this;
    }

    public Keeper addLocalPosition(float x, float y, float z) {
        this.position = Vector3f.add(this.position, new Vector3f(x,y,z), null);
        return this;
    }

    public Keeper addLocalAngle(Quaternion rotation) {
        this.rotation = Quaternion.mul(rotation, this.rotation, null);
        return this;
    }

    public Keeper addLocalAngle(float angle, float x, float y, float z) {
        this.rotation = Quaternion.mul(Maths.createRotationQuaternion(angle, new Vector3f(x,y,z)), this.rotation, null);
        return this;
    }

    public Keeper addLocalScale(Vector3f scale) {
        this.scale = Maths.myMultiplication(this.scale, scale);
        return this;
    }

    public Keeper addLocalScale(float x, float y, float z) {
        this.scale = Maths.myMultiplication(this.scale, new Vector3f(x,y,z));
        return this;
    }

    public Keeper setParent(Keeper parent) {
        parent.addChild(this);
        return this;
    }

    public Keeper addChild(Keeper child) {
        if (child == this)
            return this;
        this.children.add(child);
        child.parent = this;
        return this;
    }

    public Keeper removeChild(Keeper child) {
        this.children.remove(child);
        return this;
    }

    public Keeper addAttribute(Attribute attribute) {
        if (attribute.getType().SINGLTONE && hasAttributeType(attribute.getType()))
            return this;
        if (attribute.getType() == Attribute.Type.RENDER_MODEL){
            ((Model)attribute).addToMap(this);
        }
        this.attributes.add(attribute);
        attribute.setLocation(this);
        return this;
    }

    public Keeper removeAttribute(Attribute attribute) {
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
