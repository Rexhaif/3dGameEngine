package com.notjuststudio.engine3dgame.attributes;

import com.notjuststudio.engine3dgame.util.MathUtil;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Quaternion;
import org.lwjgl.util.vector.Vector3f;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by George on 06.01.2017.
 */
public class Entity implements Cloneable{

    private static final String NAME = "DEFAULT";
    private static final Vector3f POSITION = new Vector3f(0,0,0);
    private static final Quaternion ROTATION = new Quaternion(0,0,0,1);
    private static final Vector3f SCALE = new Vector3f(1,1,1);

    private static Set<Entity> allEntities;

    static {
        allEntities = new HashSet<>();
    }

    private Entity parent = null;
    private Set<Entity> children = new HashSet<>();

    private Set<Attribute> attributes = new HashSet<>();

    private String name;
    private Vector3f position = null;
    private Quaternion rotation = null;
    private Vector3f scale = null;
    private Matrix4f transformationMatrix;

    public Entity() {
        this(NAME);
    }

    public Entity(String name) {
        this(name, POSITION, ROTATION, SCALE);
    }

    public Entity(String name, Vector3f position, Quaternion angle, Vector3f scale) {
        this.name = name;
        this.position = position;
        this.rotation = angle;
        this.scale = scale;
        allEntities.add(this);
        resolveTransformationMatrix();
    }

    public Entity(String name, Matrix4f transformationMatrix) {
        this(name, MathUtil.getTranslation(transformationMatrix), Quaternion.setFromMatrix(transformationMatrix, null), new Vector3f(1,1,1));
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
        isExistCheck();
        return name;
    }

    public void setName(String name) {
        isExistCheck();
        this.name = name;
    }

    public Entity getParent() {
        isExistCheck();
        return parent;
    }

    public Set<Entity> getChildren() {
        isExistCheck();
        return children;
    }

    public Set<Attribute> getAttributes() {
        isExistCheck();
        return attributes;
    }

    public Vector3f getWorldPosition() {
        isExistCheck();
//        if (parent == null)
//            return position;
        return MathUtil.impactVectorByMatrix(getWorldTransformation(), new Vector3f(0,0,0));
    }

    public Quaternion getWorldRotation() {
        isExistCheck();
        if (parent == null)
            return rotation;
        return Quaternion.mul(parent.getWorldRotation(), rotation, null);
    }

    public Vector3f getFront() {
        isExistCheck();
        return MathUtil.impactVectorByMatrix(MathUtil.createRotationMatrix(getWorldRotation()), new Vector3f(0,0,-1));
    }
    public Vector3f getBack() {
        isExistCheck();
        return MathUtil.impactVectorByMatrix(MathUtil.createRotationMatrix(getWorldRotation()), new Vector3f(0,0,1));
    }

    public Vector3f getRight() {
        isExistCheck();
        return MathUtil.impactVectorByMatrix(MathUtil.createRotationMatrix(getWorldRotation()), new Vector3f(1,0,0));
    }
    public Vector3f getLeft() {
        isExistCheck();
        return MathUtil.impactVectorByMatrix(MathUtil.createRotationMatrix(getWorldRotation()), new Vector3f(-1,0,0));
    }

    public Vector3f getTop() {
        isExistCheck();
        return MathUtil.impactVectorByMatrix(MathUtil.createRotationMatrix(getWorldRotation()), new Vector3f(0,1,0));
    }
    public Vector3f getBottom() {
        isExistCheck();
        return MathUtil.impactVectorByMatrix(MathUtil.createRotationMatrix(getWorldRotation()), new Vector3f(0,-1,0));
    }


    public Matrix4f getWorldTransformation() {
        isExistCheck();
        if (parent == null) {
            return transformationMatrix;
        }

        return Matrix4f.mul(parent.getWorldTransformation(), transformationMatrix, null);
    }
    
    public Matrix4f getTransformation() {
        isExistCheck();
        return transformationMatrix;
    }

//    public Entity setTransformation(Matrix4f transformationMatrix) {
//        isExistCheck();
//        this.transformationMatrix = transformationMatrix;
//        return this;
//    }

    public Vector3f getPosition() {
        isExistCheck();
        return position;
    }

    public Quaternion getRotation() {
        isExistCheck();
        return rotation;
    }

    public Vector3f getScale() {
        isExistCheck();
        return scale;
    }

    public Entity setPositionSilent(Vector3f position) {
        isExistCheck();
        this.position = position;
        return this;
    }

    public Entity setPositionSilent(float x, float y, float z) {
        isExistCheck();
        this.setPositionSilent(new Vector3f(x,y,z));
        return this;
    }

    public Entity setPosition(Vector3f position) {
        isExistCheck();
        this.setPositionSilent(position);
        this.resolveTransformationMatrix();
        return this;
    }

    public Entity setPosition(float x, float y, float z) {
        isExistCheck();
        this.setPosition(new Vector3f(x,y,z));
        return this;
    }

    public Entity setRotationSilent(Quaternion rotation) {
        isExistCheck();
        this.rotation = rotation;
        return this;
    }

    public Entity setRotation(Quaternion rotation) {
        isExistCheck();
        this.setRotationSilent(rotation);
        this.resolveTransformationMatrix();
        return this;
    }

    public Entity setRotationSilent(float angle, Vector3f axis) {
        isExistCheck();
        this.setRotationSilent(MathUtil.createRotationQuaternion(angle, axis));
        return this;
    }

    public Entity setRotation(float angle, Vector3f axis) {
        isExistCheck();
        this.setRotation(MathUtil.createRotationQuaternion(angle, axis));
        return this;
    }

    public Entity setRotationSilent(float angle, float x, float y, float z) {
        isExistCheck();
        this.setRotationSilent(angle, new Vector3f(x,y,z));
        return this;
    }

    public Entity setRotation(float angle, float x, float y, float z) {
        isExistCheck();
        this.setRotation(angle, new Vector3f(x,y,z));
        return this;
    }

    public Entity setScaleSilent(Vector3f scale) {
        isExistCheck();
        this.scale = scale;
        return this;
    }

    public Entity setScale(Vector3f scale) {
        isExistCheck();
        this.setScaleSilent(scale);
        this.resolveTransformationMatrix();
        return this;
    }

    public Entity setScaleSilent(float x, float y, float z) {
        isExistCheck();
        this.setScaleSilent( new Vector3f(x,y,z));
        return this;
    }

    public Entity setScale(float x, float y, float z) {
        isExistCheck();
        this.setScale( new Vector3f(x,y,z));
        return this;
    }

    public Entity addPositionSilent(Vector3f position) {
        isExistCheck();
        this.position = Vector3f.add(this.position, position, null);
        return this;
    }

    public Entity addPosition(Vector3f position) {
        isExistCheck();
        this.addPositionSilent(position);
        this.resolveTransformationMatrix();
        return this;
    }

    public Entity addPositionSilent(float x, float y, float z) {
        isExistCheck();
        this.addPositionSilent(new Vector3f(x,y,z));
        return this;
    }

    public Entity addPosition(float x, float y, float z) {
        isExistCheck();
        this.addPosition(new Vector3f(x,y,z));
        return this;
    }

    public Entity addRotationSilent(Quaternion rotation) {
        isExistCheck();
        this.rotation = Quaternion.mul(rotation, this.rotation, null);
        return this;
    }

    public Entity addRotation(Quaternion rotation) {
        isExistCheck();
        this.addRotationSilent(rotation);
        this.resolveTransformationMatrix();
        return this;
    }

    public Entity addRotationSilent(float angle, Vector3f axis) {
        isExistCheck();
        this.addRotationSilent(MathUtil.createRotationQuaternion(angle, axis));
        return this;
    }

    public Entity addRotation(float angle, Vector3f axis) {
        isExistCheck();
        this.addRotation(MathUtil.createRotationQuaternion(angle, axis));
        return this;
    }

    public Entity addRotationSilent(float angle, float x, float y, float z) {
        isExistCheck();
        this.addRotationSilent(angle, new Vector3f(x,y,z));
        return this;
    }

    public Entity addRotation(float angle, float x, float y, float z) {
        isExistCheck();
        this.addRotation(angle, new Vector3f(x,y,z));
        return this;
    }

    public Entity addScaleSilent(Vector3f scale) {
        isExistCheck();
        this.scale = MathUtil.myMultiplication(this.scale, scale);
        return this;
    }

    public Entity addScale(Vector3f scale) {
        isExistCheck();
        this.addScaleSilent(scale);
        this.resolveTransformationMatrix();
        return this;
    }

    public Entity addScaleSilent(float x, float y, float z) {
        isExistCheck();
        this.addScaleSilent( new Vector3f(x,y,z));
        return this;
    }

    public Entity addScale(float x, float y, float z) {
        isExistCheck();
        this.addScale( new Vector3f(x,y,z));
        return this;
    }

    public Entity setParent(Entity parent) {
        isExistCheck();
        if (parent == this)
            return this;
        removeParent();
        if (parent != null && !parent.children.contains(this)) {
            parent.children.add(this);
        }
        this.parent = parent;
        return this;
    }

    public Entity removeParent() {
        isExistCheck();
        if (parent != null && parent.children.contains(this)) {
            parent.children.remove(this);
        }
        parent = null;
        return this;
    }

    public Entity addChild(Entity child) {
        isExistCheck();
        if (child == this)
            return this;
        this.children.add(child.setParent(this));
        child.parent = this;
        return this;
    }

    public Entity removeChild(Entity child) {
        isExistCheck();
        this.children.remove(child);
        return this;
    }

    public Entity addAttribute(Attribute attribute) {
        isExistCheck();
        if (attribute.getType().SINGLTONE && hasAttributeType(attribute.getType()))
            return this;
        this.attributes.add(attribute.setEntity(this));
        return this;
    }

    public Entity removeAttribute(Attribute attribute) {
        isExistCheck();
        this.attributes.remove(attribute);
        attribute.removeEntity();
        return this;
    }

    public Attribute getAttributeOfType(Attribute.Type type) {
        isExistCheck();
        for (Attribute attribute : attributes) {
            if (attribute.getType() == type) {
                return attribute;
            }
        }
        return null;
    }

    public List<Attribute> getAttributesOfType(Attribute.Type type) {
        isExistCheck();
        List<Attribute> result = new ArrayList<>();
        for (Attribute attribute : attributes) {
            if (attribute.getType() == type) {
                result.add(attribute);
            }
        }
        return result;
    }

    public boolean hasAttributeType(Attribute.Type type) {
        isExistCheck();
        for (Attribute attribute : attributes) {
            if (attribute.getType() == type) {
                return true;
            }
        }
        return false;
    }

    public void resolveTransformationMatrix() {
        isExistCheck();
        transformationMatrix = MathUtil.createTransformationMatrix(position, rotation, scale);
    }

    public static boolean destroy(Entity entity) {
        if (allEntities.contains(entity)) {
            for (Entity child : entity.children) {
                entity.removeChild(child);
            }
            entity.removeParent();
            allEntities.remove(entity);
            return true;
        }
        return false;
    }

    public boolean destroy() {
        isExistCheck();
        return destroy(this);
    }

    public static boolean isExist(Entity entity) {
        return allEntities.contains(entity);
    }

    public boolean isExist() {
        return isExist(this);
    }

    private void isExistCheck() {
        if (!this.isExist()) throw new NotUsableEntityException();
    }

    public class NotUsableEntityException extends RuntimeException {
        public NotUsableEntityException() {
            super("You tried to use a deleted Entity.");
        }
    }
}
