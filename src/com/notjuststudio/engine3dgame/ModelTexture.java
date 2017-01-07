package com.notjuststudio.engine3dgame;

import com.notjuststudio.engine3dgame.shader.ShaderProgram;

/**
 * Created by George on 06.01.2017.
 */
public class ModelTexture {

    private int textureID;
    private ShaderProgram shaderProgram;

    private float shineDamper = 1;
    private float reflectivity = 0;

    public ModelTexture(int textureID, ShaderProgram shaderProgram) {
        this.textureID = textureID;
        this.shaderProgram = shaderProgram;
    }

    public int getTextureID() {
        return textureID;
    }

    public ShaderProgram getShaderProgram() {
        return shaderProgram;
    }

    public float getShineDamper() {
        return shineDamper;
    }

    public ModelTexture setShineDamper(float shineDamper) {
        this.shineDamper = shineDamper;
        return this;
    }

    public float getReflectivity() {
        return reflectivity;
    }

    public ModelTexture setReflectivity(float reflectivity) {
        this.reflectivity = reflectivity;
        return this;
    }
}
