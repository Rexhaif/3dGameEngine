package com.notjuststudio.engine3dgame;

import com.notjuststudio.engine3dgame.shader.ShaderProgram;

/**
 * Created by George on 06.01.2017.
 */
public class ModelTexture {

    private int textureID;
    private ShaderProgram shaderProgram;

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
}
