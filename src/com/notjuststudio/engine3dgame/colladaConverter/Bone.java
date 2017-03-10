package com.notjuststudio.engine3dgame.colladaConverter;

/**
 * Created by George on 13.01.2017.
 */
public class Bone {

    private int index;
    private int weight;

    public Bone(int index, int weight) {
        this.index = index;
        this.weight = weight;
    }

    public int getIndex() {
        return index;
    }

    public Bone setIndex(int index) {
        this.index = index;
        return this;
    }

    public int getWeight() {
        return weight;
    }

    public Bone setWeight(int weight) {
        this.weight = weight;
        return this;
    }
}
