package com.notjuststudio.engine3dgame;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by George on 06.01.2017.
 */
public class Loader {

    private static List<Integer> vaoList = new ArrayList<>();
    private static List<Integer> vboList = new ArrayList<>();
    private static List<Integer> textureList = new ArrayList<>();

    public static ModelData createModelData(int[] indices, float[] positions, float[] uvCoords) {
        return new ModelData(loadToVAO(indices, positions, uvCoords), indices.length);
    }

    private static int loadToVAO(int[] indices, float[] positions, float[] uvCoords) {
        int vaoID = createVAO();
        storeDataInIndicesBuffer(indices);
        storeDataInAttributeList(0, 3, positions);
        storeDataInAttributeList(1, 2, uvCoords);
        bindDefaultVAO();
        return vaoID;
    }

    public static int loadTexture(String fileName) {
        Texture texture = null;
        try {
            texture = TextureLoader.getTexture("PNG", new FileInputStream(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        int textureID = texture.getTextureID();
        textureList.add(textureID);
        return textureID;
    }

    public static void cleanUp() {
        vaoList.forEach(GL30::glDeleteVertexArrays);
        vboList.forEach(GL15::glDeleteBuffers);
        textureList.forEach(GL11::glDeleteTextures);
    }

    private static int createVAO() {
        int vaoID = GL30.glGenVertexArrays();
        vaoList.add(vaoID);
        GL30.glBindVertexArray(vaoID);
        return vaoID;
    }

    private static void storeDataInAttributeList(int attributeNumber, int size, float[] data) {
        int vboID = GL15.glGenBuffers();
        vboList.add(vboID);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, storeDataInFloatBuffer(data), GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(attributeNumber, size, GL11.GL_FLOAT, false, 0, 0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
    }

    public static void bindDefaultVAO() {
        GL30.glBindVertexArray(0);
    }

    private static void storeDataInIndicesBuffer(int[] indices) {
        int vboID = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboID);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, storeDataInIntBufferBuffer(indices), GL15.GL_STATIC_DRAW);
    }

    public static FloatBuffer storeDataInFloatBuffer(float[] data) {
        return (FloatBuffer)BufferUtils.createFloatBuffer(data.length).put(data).flip();
    }

    public static IntBuffer storeDataInIntBufferBuffer(int[] data) {
        return (IntBuffer)BufferUtils.createIntBuffer(data.length).put(data).flip();
    }

}