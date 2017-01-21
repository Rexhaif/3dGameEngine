package com.notjuststudio.engine3dgame;

import com.notjuststudio.engine3dgame.attributes.model.ModelData;
import com.notjuststudio.engine3dgame.osfConverter.VAOContainer;
import de.matthiasmann.twl.utils.PNGDecoder;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.*;

import java.io.FileInputStream;
import java.nio.ByteBuffer;
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

    public static VAOContainer createVOContainer(int[] indices, float[] positions, float[] uvCoords, float[] normals) {
        return new VAOContainer()
                .setIndices(storeDataInIntBufferBuffer(indices))
                .setPositions(storeDataInFloatBuffer(positions))
                .setUvCoords(storeDataInFloatBuffer(uvCoords))
                .setNormals(storeDataInFloatBuffer(normals));
    }

    public static ModelData createModelData(VAOContainer container) {
        return new ModelData(loadToVAO(container), container.getIndices().remaining());
    }

    public static int createCubeMap(float size) {
        int vaoID = createVAO();
        float[] data = {
                -size,  size, -size,
                -size, -size, -size,
                size, -size, -size,
                size, -size, -size,
                size,  size, -size,
                -size,  size, -size,

                -size, -size,  size,
                -size, -size, -size,
                -size,  size, -size,
                -size,  size, -size,
                -size,  size,  size,
                -size, -size,  size,

                size, -size, -size,
                size, -size,  size,
                size,  size,  size,
                size,  size,  size,
                size,  size, -size,
                size, -size, -size,

                -size, -size,  size,
                -size,  size,  size,
                size,  size,  size,
                size,  size,  size,
                size, -size,  size,
                -size, -size,  size,

                -size,  size, -size,
                size,  size, -size,
                size,  size,  size,
                size,  size,  size,
                -size,  size,  size,
                -size,  size, -size,

                -size, -size, -size,
                -size, -size,  size,
                size, -size, -size,
                size, -size, -size,
                -size, -size,  size,
                size, -size,  size
        };
        List<Integer> vboIDs = new ArrayList<>();
        vboIDs.add(storeDataInAttributeList(0, 3, storeDataInFloatBuffer(data)));
        bindDefaultVAO();
        vaoList.put(vaoID, vboIDs);
        return vaoID;
    }

    private static int loadToVAO(VAOContainer container) {
        int vaoID = createVAO();
        List<Integer> vboIDs = new ArrayList<>();
        if (container.getIndices() != null)
            vboIDs.add(storeDataInIndicesBuffer(container.getIndices()));
        if (container.getPositions() != null)
            vboIDs.add(storeDataInAttributeList(0, 3, container.getPositions()));
        if (container.getUvCoords() != null)
            vboIDs.add(storeDataInAttributeList(1, 2, container.getUvCoords()));
        if (container.getNormals() != null)
            vboIDs.add(storeDataInAttributeList(2, 3, container.getNormals()));
        if (container.getBoneIndices() != null)
            vboIDs.add(storeDataInAttributeList(3, 4, container.getBoneIndices()));
        if (container.getBoneWeights() != null)
            vboIDs.add(storeDataInAttributeList(4, 4, container.getBoneWeights()));
        bindDefaultVAO();
        if (vboIDs.isEmpty()) {
            GL30.glDeleteVertexArrays(vaoID);
            return 0;
        }
        vaoList.put(vaoID, vboIDs);
        return vaoID;
    }

    private static CubeMapData decodeTexturesFile(String folderName) {
        int width = 0;
        int height = 0;
        ByteBuffer[] buffers = new ByteBuffer[6];
        final String[] TEXTURE_FILES = {"right.png", "left.png", "top.png", "bottom.png", "back.png", "front.png"};
        for (int i = 0; i < 6; i++) {
            try {
                FileInputStream in = new FileInputStream(folderName + TEXTURE_FILES[i]);
                PNGDecoder decoder = new PNGDecoder(in);
                width = decoder.getWidth();
                height = decoder.getHeight();
                buffers[i] = ByteBuffer.allocateDirect(4 * width * height);
                decoder.decode(buffers[i], width * 4, PNGDecoder.Format.RGBA);
                buffers[i].flip();
                in.close();
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("Tried to load texture " + folderName + TEXTURE_FILES[i] + ", didn't work");
                System.exit(-1);
            }
        }
        return new CubeMapData(buffers, width, height);
    }

    public static int loadCubeMap(String folderName) {
        int texID = GL11.glGenTextures();
        GL13.glActiveTexture(texID);
        GL11.glBindTexture(GL13.GL_TEXTURE_CUBE_MAP, texID);

        CubeMapData data = decodeTexturesFile(folderName);
        for (int i = 0; i < 6; i++) {
            GL11.glTexImage2D(GL13.GL_TEXTURE_CUBE_MAP_POSITIVE_X + i, 0, GL11.GL_RGBA, data.getWidth(), data.getHeight(), 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, data.getBuffers()[i]);
        }

        GL30.glGenerateMipmap(GL13.GL_TEXTURE_CUBE_MAP);
        GL11.glTexParameteri(GL13.GL_TEXTURE_CUBE_MAP, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
        GL11.glTexParameteri(GL13.GL_TEXTURE_CUBE_MAP, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_LINEAR);

        GL11.glTexParameteri(GL13.GL_TEXTURE_CUBE_MAP, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
        GL11.glTexParameteri(GL13.GL_TEXTURE_CUBE_MAP, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);

        textureList.add(texID);
        return texID;
    }

    private static TextureData decodeTextureFile(String fileName) {
        int width = 0;
        int height = 0;
        ByteBuffer buffer = null;
        try {
            FileInputStream in = new FileInputStream(fileName);
            PNGDecoder decoder = new PNGDecoder(in);
            width = decoder.getWidth();
            height = decoder.getHeight();
            buffer = ByteBuffer.allocateDirect(4 * width * height);
            decoder.decode(buffer, width * 4, PNGDecoder.Format.RGBA);
            buffer.flip();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Tried to load texture " + fileName + ", didn't work");
            System.exit(-1);
        }
        return new TextureData(buffer, width, height);
    }

    public static int loadTexture(String fileName) {
        int texID = GL11.glGenTextures();
        GL13.glActiveTexture(texID);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texID);

        TextureData data = decodeTextureFile(fileName);
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, data.getWidth(), data.getHeight(), 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, data.getBuffer());

        GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_LINEAR);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL14.GL_TEXTURE_LOD_BIAS, 0);
        if (anisotropicFilter) {
            float amount  = Math.min(4f, GL11.glGetFloat(EXTTextureFilterAnisotropic.GL_MAX_TEXTURE_MAX_ANISOTROPY_EXT));
            GL11.glTexParameterf(GL11.GL_TEXTURE_2D, EXTTextureFilterAnisotropic.GL_MAX_TEXTURE_MAX_ANISOTROPY_EXT, amount);
        }

        textureList.add(texID);
        return texID;
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

    private static int storeDataInAttributeList(int attributeNumber, int size, IntBuffer data) {
        int vboID = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, data, GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(attributeNumber, size, GL11.GL_INT, false, 0, 0);
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
