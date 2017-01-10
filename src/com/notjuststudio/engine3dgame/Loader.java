package com.notjuststudio.engine3dgame;

import com.notjuststudio.engine3dgame.attributes.model.ModelData;
import com.notjuststudio.engine3dgame.osfConverter.VAOContainer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.*;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.*;

/**
 * Created by George on 06.01.2017.
 */
public class Loader {

    private static boolean anisotropicFilter;

    static {
        if (!(anisotropicFilter = GLContext.getCapabilities().GL_EXT_texture_filter_anisotropic))
            System.out.println("Anisotropic filter is not supported!");
    }

    private static Map<Integer, List<Integer>> vaoList = new HashMap<>(); //если что, можно учитывать, что одно vbo может быть испольно раличными vao
    private static Set<Integer> textureList = new HashSet<>();

    public static VAOContainer createVAOContainer(int[] indices, float[] positions, float[] uvCoords, float[] normals) {
        return new VAOContainer(
                storeDataInIntBufferBuffer(indices),
                storeDataInFloatBuffer(positions),
                storeDataInFloatBuffer(uvCoords),
                storeDataInFloatBuffer(normals));
    }

    public static ModelData createModelData(VAOContainer container) {
        return new ModelData(loadToVAO(container), container.getIndices().remaining());
    }

    private static int loadToVAO(VAOContainer container) {
        int vaoID = createVAO();
        List<Integer> vboIDs = new ArrayList<>();
        vboIDs.add(storeDataInIndicesBuffer(container.getIndices()));
        vboIDs.add(storeDataInAttributeList(0, 3, container.getPositions()));
        vboIDs.add(storeDataInAttributeList(1, 2, container.getUvCoords()));
        vboIDs.add(storeDataInAttributeList(2, 3, container.getNormals()));
        bindDefaultVAO();
        vaoList.put(vaoID, vboIDs);
        return vaoID;
    }

    public static int loadTexture(String fileName) {
        Texture texture = null;
        try {
            texture = TextureLoader.getTexture("PNG", new FileInputStream(fileName));
            GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_LINEAR);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL14.GL_TEXTURE_LOD_BIAS, 0);
            if (anisotropicFilter) {
                float amount  = Math.min(4f, GL11.glGetFloat(EXTTextureFilterAnisotropic.GL_MAX_TEXTURE_MAX_ANISOTROPY_EXT));
                GL11.glTexParameterf(GL11.GL_TEXTURE_2D, EXTTextureFilterAnisotropic.GL_MAX_TEXTURE_MAX_ANISOTROPY_EXT, amount);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        int textureID = texture.getTextureID();
        textureList.add(textureID);
        return textureID;
    }

    public static void cleanUp() {
        for (Map.Entry<Integer, List<Integer>> vaoEntry : vaoList.entrySet()) {
            GL30.glDeleteVertexArrays(vaoEntry.getKey());
            for (Integer vboID : vaoEntry.getValue()) {
                GL15.glDeleteBuffers(vboID);
            }
        }
        textureList.forEach(GL11::glDeleteTextures);
    }

    private static int createVAO() {
        int vaoID = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(vaoID);
        return vaoID;
    }

    private static int storeDataInAttributeList(int attributeNumber, int size, FloatBuffer data) {
        int vboID = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, data, GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(attributeNumber, size, GL11.GL_FLOAT, false, 0, 0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        return vboID;
    }

    public static void bindDefaultVAO() {
        GL30.glBindVertexArray(0);
    }

    private static int storeDataInIndicesBuffer(IntBuffer indices) {
        int vboID = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboID);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indices, GL15.GL_STATIC_DRAW);
        return vboID;
    }

    private static void storeDataInIndicesBuffer(int[] indices) {
        storeDataInIndicesBuffer(storeDataInIntBufferBuffer(indices));
    }

    public static FloatBuffer storeDataInFloatBuffer(float[] data) {
        return (FloatBuffer) BufferUtils.createFloatBuffer(data.length).put(data).flip();
    }

    public static IntBuffer storeDataInIntBufferBuffer(int[] data) {
        return (IntBuffer) BufferUtils.createIntBuffer(data.length).put(data).flip();
    }

    public static void deleteVAO(int vaoID) {
        GL30.glDeleteVertexArrays(vaoID);
        for (Integer vboID : vaoList.remove(vaoID)) {
            GL15.glDeleteBuffers(vboID);
        }
    }

    public static void deleteTexture(int textureID) {
        GL11.glDeleteTextures(textureID);
        textureList.remove(textureID);
    }

}
