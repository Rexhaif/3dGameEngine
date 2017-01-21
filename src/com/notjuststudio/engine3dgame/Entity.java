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
public class Entity implements Cloneable{

    public static Entity getDefault() {
        try {
            return (Entity)DEFAULT.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static final Entity DEFAULT = new Entity("DEFAULT", new Vector3f(0,0,0), new Quaternion(0,0,0,1),new Vector3f(1,1,1));

    private static List<Entity> allEntities;

    static {
        allEntities = new ArrayList<>();
        allEntities.add(DEFAULT);
    }

    private Entity parent = null;
    private List<Entity> children = new ArrayList<>();

    private List<Attribute> attributes = new ArrayList<>();

    private String name;
    private Vector3f position = null;
    private Quaternion rotation = null;
    private Vector3f scale = null;
    private Matrix4f transformationMatrix;

    public Entity() {
        this(DEFAULT.name);
    }

    public Entity(String name) {
        this(name, DEFAULT.position, DEFAULT.rotation, DEFAULT.scale);
    }

    public Entity(String name, Vector3f position, Quaternion angle, Vector3f scale) {
        this.name = name;
        this.position = position;
        this.rotation = angle;
        this.scale = scale;
        resolveTransformationMatrix();
    }

    public Entity(String name, Matrix4f transformationMatrix) {
        this.name = name;
        this.transformationMatrix = transformationMatrix;
    }

    public static boolean nameExists(String name) {
        for (Entity entity : allEntities) {
            if (entity.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public static Entity getByName(String name) {
        for (Entity entity : allEntities) {
            if (entity.getName().equals(name)) {
                return entity;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Entity getParent() {
        return parent;
    }

    public List<Entity> getChildren() {
        return children;
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public Vector3f getWorldPosition() {
        return Maths.impactVectorByMatrix(getWorldTransformation(), new Vector3f(0,0,0));
    }

    public Quaternion getWorldRotation() {
        if (parent == null)
            return rotation;
        return Quaternion.mul(parent.getWorldRotation(), rotation, null);
    }

    public Vector3f getFront() {
        return Maths.impactVectorByMatrix(Maths.createRotationMatrix(getWorldRotation()), new Vector3f(0,0,-1));
    }

    public Vector3f getRight() {
        return Maths.impactVectorByMatrix(Maths.createRotationMatrix(getWorldRotation()), new Vector3f(1,0,0));
    }

    public Vector3f getTop() {
        return Maths.impactVectorByMatrix(Maths.createRotationMatrix(getWorldRotation()), new Vector3f(0,1,0));
    }

    public Vector3f getBack() {
        return Maths.impactVectorByMatrix(Maths.createRotationMatrix(getWorldRotation()), new Vector3f(0,0,1));
    }

    public Vector3f getLeft() {
        return Maths.impactVectorByMatrix(Maths.createRotationMatrix(getWorldRotation()), new Vector3f(-1,0,0));
    }

    public Vector3f getBottom() {
        return Maths.impactVectorByMatrix(Maths.createRotationMatrix(getWorldRotation()), new Vector3f(0,-1,0));
    }

    public Matrix4f getWorldTransformation() {
        if (parent == null) {
            return transformationMatrix;
        }
        return Matrix4f.mul(parent.getWorldTransformation(), transformationMatrix, null);
    }
    
    public Matrix4f getTransformation() {
        return transformationMatrix;
    }

    public Entity setTransformation(Matrix4f transformationMatrix) {
        this.transformationMatrix = transformationMatrix;
        return this;
    }

    public Vector3f getPosition() {
        return position;
    }

    public Quaternion getRotation() {
        return rotation;
    }

    public Vector3f getScale() {
        return scale;
    }

    public Entity setPositionSilent(Vector3f position) {
        this.position = position;
        return this;
    }

    public Entity setPositionSilent(float x, float y, float z) {
        this.setPositionSilent(new Vector3f(x,y,z));
        return this;
    }

    public Entity setPosition(Vector3f position) {
        this.setPositionSilent(position);
        this.resolveTransformationMatrix();
        return this;
    }

    public Entity setPosition(float x, float y, float z) {
        this.setPosition(new Vector3f(x,y,z));
        return this;
    }

    public Entity setRotationSilent(Quaternion rotation) {
        this.rotation = rotation;
        return this;
    }

    public Entity setRotation(Quaternion rotation) {
        this.setRotationSilent(rotation);
        this.resolveTransformationMatrix();
        return this;
    }

    public Entity setRotationSilent(float angle, Vector3f axis) {
        this.setRotationSilent(Maths.createRotationQuaternion(angle, axis));
        return this;
    }

    public Entity setRotation(float angle, Vector3f axis) {
        this.setRotation(Maths.createRotationQuaternion(angle, axis));
        return this;
    }

    public Entity setRotationSilent(float angle, float x, float y, float z) {
        this.setRotationSilent(angle, new Vector3f(x,y,z));
        return this;
    }

    public Entity setRotation(float angle, float x, float y, float z) {
        this.setRotation(angle, new Vector3f(x,y,z));
        return this;
    }

    public Entity setScaleSilent(Vector3f scale) {
        this.scale = scale;
        return this;
    }

    public Entity setScale(Vector3f scale) {
        this.setScaleSilent(scale);
        this.resolveTransformationMatrix();
        return this;
    }

    public Entity setScaleSilent(float x, float y, float z) {
        this.setScaleSilent( new Vector3f(x,y,z));
        return this;
    }

    public Entity setScale(float x, float y, float z) {
        this.setScale( new Vector3f(x,y,z));
        return this;
    }

    public Entity addPositionSilent(Vector3f position) {
        this.position = Vector3f.add(this.position, position, null);
        return this;
    }

    public Entity addPosition(Vector3f position) {
        this.addPositionSilent(position);
        this.resolveTransformationMatrix();
        return this;
    }

    public Entity addPositionSilent(float x, float y, float z) {
        this.addPositionSilent(new Vector3f(x,y,z));
        return this;
    }

    public Entity addPosition(float x, float y, float z) {
        this.addPosition(new Vector3f(x,y,z));
        return this;
    }

    public Entity addRotationSilent(Quaternion rotation) {
        this.rotation = Quaternion.mul(rotation, this.rotation, null);
        return this;
    }

    public Entity addRotation(Quaternion rotation) {
        this.addRotationSilent(rotation);
        this.resolveTransformationMatrix();
        return this;
    }

    public Entity addRotationSilent(float angle, Vector3f axis) {
        this.addRotationSilent(Maths.createRotationQuaternion(angle, axis));
        return this;
    }

    public Entity addRotation(float angle, Vector3f axis) {
        this.addRotation(Maths.createRotationQuaternion(angle, axis));
        return this;
    }

    public Entity addRotationSilent(float angle, float x, float y, float z) {
        this.addRotationSilent(angle, new Vector3f(x,y,z));
        return this;
    }

    public Entity addRotation(float angle, float x, float y, float z) {
        this.addRotation(angle, new Vector3f(x,y,z));
        return this;
    }

    public Entity addScaleSilent(Vector3f scale) {
        this.scale = Maths.myMultiplication(this.scale, scale);
        return this;
    }

    public Entity addScale(Vector3f scale) {
        this.addScaleSilent(scale);
        this.resolveTransformationMatrix();
        return this;
    }

    public Entity addScaleSilent(float x, float y, float z) {
        this.addScaleSilent( new Vector3f(x,y,z));
        return this;
    }

    public Entity addScale(float x, float y, float z) {
        this.addScale( new Vector3f(x,y,z));
        return this;
    }

    public Entity setParent(Entity parent) {
        if (parent == this)
            return this;
        resetParent();
        if (parent != null && !parent.children.contains(this)) {
            parent.children.add(this);
        }
        this.parent = parent;
        return this;
    }

    public Entity resetParent() {
        if (parent != null && parent.children.contains(this)) {
            parent.children.remove(this);
        }
        parent = null;
        return this;
    }

    public Entity addChild(Entity child) {
        if (child == this)
            return this;
        this.children.add(child.setParent(this));
        child.parent = this;
        return this;
    }

    public Entity removeChild(Entity child) {
        this.children.remove(child);
        return this;
    }

    public Entity addAttribute(Attribute attribute) {
        if (attribute.getType().SINGLTONE && hasAttributeType(attribute.getType()))
            return this;
        this.attributes.add(attribute.setEntity(this));
        return this;
    }

    public Entity removeAttribute(Attribute attribute) {
        this.attributes.remove(attribute);
        attribute.resetEntity();
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

    public List<Attribute> getAttributesOfType(Attribute.Type type) {
        List<Attribute> result = new ArrayList<>();
        for (Attribute attribute : attributes) {
            if (attribute.getType() == type) {
                result.add(attribute);
            }
        }
        return result;
    }

    public boolean hasAttributeType(Attribute.Type type) {
        for (Attribute attribute : attributes) {
            if (attribute.getType() == type) {
                return true;
            }
        }
        return false;
    }

    public void resolveTransformationMatrix() {
        transformationMatrix = Maths.createTransformationMatrix(position, rotation, scale);
    }
}
