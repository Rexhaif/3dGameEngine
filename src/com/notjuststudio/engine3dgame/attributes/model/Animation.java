package com.notjuststudio.engine3dgame.attributes.model;

import com.notjuststudio.engine3dgame.attributes.Entity;
import org.lwjgl.util.vector.Matrix4f;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by George on 13.01.2017.
 */
public class Animation {

    private Bone[] bones;
    private float time = 0;

    public Animation(Bone[] bones) {
        this.bones = bones;
    }

//    public void resetToDefaultAnimation() {
//        for (Bone bone : bones) {
//            bone.entity.setTransformation(bone.frames.get(0f));
//        }
//    }

    public Bone[] getBones() {
        return bones;
    }

    public float getTime() {
        return time;
    }

    public void setTime(float time) {
        this.time = time;
    }

    public void addTime(float time) {
        setTime(this.time + time);
    }

    public enum Type {
        LINEAR();

        Type() {
        }
    }

    public class Bone {

        private String name;
        private Entity entity;
        private Matrix4f bindPose;
        private Map<Float, Matrix4f> frames;
        private Map<Float, Type> interpolations;

        public Bone(String name, Entity entity, Matrix4f bindPose, Map<Float, Matrix4f> frames, Map<Float, Type> interpolations) {
            this.name = name;
            this.entity = entity;
            this.bindPose = bindPose;
            this.frames = frames;
            this.interpolations = interpolations;
        }

        public Bone(String name, Entity entity, Matrix4f bindPose) {
            this.name = name;
            this.entity = entity;
            this.bindPose = bindPose;
            Map<Float, Matrix4f> frames = new HashMap<>();
            frames.put(0f, bindPose);
            this.frames = frames;
            Map<Float, Type> interpolations = new HashMap<>();
            interpolations.put(0f, Type.LINEAR);
            this.interpolations = interpolations;
        }

        public String getName() {
            return name;
        }

        public Entity getEntity() {
            return entity;
        }
    }
}
