package com.notjuststudio.engine3dgame.osfConverter;

import java.io.*;
import java.nio.*;

/**
 * Created by George on 08.01.2017.
 */
public class OSFLoader {

    public static boolean loadToOSF(String fileName, VAOContainer source) {
        File file = new File(fileName);
        if (file.exists()) {
            return false;
        }

        ByteBuffer byteBuffer;
        FileWriter fw;
        try {
            fw = new FileWriter(file);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        BufferedWriter bw = new BufferedWriter(fw);

        byteBuffer = ByteBuffer.allocateDirect(4).order(ByteOrder.nativeOrder());
        byteBuffer.putInt(source.getIndices().remaining());
        byteBuffer.flip();

        try {
            bw.write(charBufferToArray(byteBuffer.asCharBuffer()));
            bw.write(charBufferToArray(intBufferToByteBuffer(source.getIndices()).asCharBuffer()));
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        byteBuffer.position(0);
        byteBuffer.putInt(source.getPositions().remaining());
        byteBuffer.flip();

        try {
            bw.write(charBufferToArray(byteBuffer.asCharBuffer()));
            bw.write(charBufferToArray(floatBufferToByteBuffer(source.getPositions()).asCharBuffer()));
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        byteBuffer.position(0);
        byteBuffer.putInt(source.getUvCoords().remaining());
        byteBuffer.flip();

        try {
            bw.write(charBufferToArray(byteBuffer.asCharBuffer()));
            bw.write(charBufferToArray(floatBufferToByteBuffer(source.getUvCoords()).asCharBuffer()));
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        byteBuffer.position(0);
        byteBuffer.putInt(source.getNormals().remaining());
        byteBuffer.flip();

        try {
            bw.write(String.valueOf(charBufferToArray(byteBuffer.asCharBuffer())));
            bw.write(charBufferToArray(floatBufferToByteBuffer(source.getNormals()).asCharBuffer()));
            bw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static VAOContainer loadFromOSF(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            return null;
        }

        ByteBuffer inputBuffer;
        ByteBuffer saveBuffer;
        IntBuffer indices;
        FloatBuffer positions;
        FloatBuffer uvCoords;
        FloatBuffer normals;
        FileReader fr;
        try {
            fr = new FileReader(file);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        BufferedReader br = new BufferedReader(fr);

        inputBuffer = ByteBuffer.allocateDirect(4).order(ByteOrder.nativeOrder());
        try {
            br.read(inputBuffer.asCharBuffer());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        inputBuffer = ByteBuffer.allocateDirect(inputBuffer.getInt() << 2).order(ByteOrder.nativeOrder());

        try {
            br.read(inputBuffer.asCharBuffer());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        inputBuffer.position(0);
        saveBuffer = ByteBuffer.allocateDirect(inputBuffer.remaining()).order(ByteOrder.nativeOrder());
        saveBuffer.put(inputBuffer).flip();
        indices = saveBuffer.asIntBuffer();

        inputBuffer = ByteBuffer.allocateDirect(4).order(ByteOrder.nativeOrder());

        try {
            br.read(inputBuffer.asCharBuffer());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        inputBuffer = ByteBuffer.allocateDirect(inputBuffer.getInt() << 2).order(ByteOrder.nativeOrder());

        try {
            br.read(inputBuffer.asCharBuffer());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        inputBuffer.position(0);
        saveBuffer = ByteBuffer.allocateDirect(inputBuffer.remaining()).order(ByteOrder.nativeOrder());
        saveBuffer.put(inputBuffer).flip();
        positions = saveBuffer.asFloatBuffer();

        inputBuffer = ByteBuffer.allocateDirect(4).order(ByteOrder.nativeOrder());
        try {
            br.read(inputBuffer.asCharBuffer());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        inputBuffer = ByteBuffer.allocateDirect(inputBuffer.getInt() << 2).order(ByteOrder.nativeOrder());

        try {
            br.read(inputBuffer.asCharBuffer());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        inputBuffer.position(0);
        saveBuffer = ByteBuffer.allocateDirect(inputBuffer.remaining()).order(ByteOrder.nativeOrder());
        saveBuffer.put(inputBuffer).flip();
        uvCoords = saveBuffer.asFloatBuffer();

        inputBuffer = ByteBuffer.allocateDirect(4).order(ByteOrder.nativeOrder());
        try {
            br.read(inputBuffer.asCharBuffer());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        inputBuffer = ByteBuffer.allocateDirect(inputBuffer.getInt() << 2).order(ByteOrder.nativeOrder());

        try {
            br.read(inputBuffer.asCharBuffer());
            br.close();
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        inputBuffer.position(0);
        saveBuffer = ByteBuffer.allocateDirect(inputBuffer.remaining()).order(ByteOrder.nativeOrder());
        saveBuffer.put(inputBuffer).flip();
        normals = saveBuffer.asFloatBuffer();

        return new VAOContainer(indices, positions, uvCoords, normals);
    }

    public static ByteBuffer floatBufferToByteBuffer(FloatBuffer floatBuffer) {
        ByteBuffer bb = ByteBuffer.allocateDirect(floatBuffer.remaining() << 2).order(ByteOrder.nativeOrder());
        bb.asFloatBuffer().put(floatBufferToArray(floatBuffer));
        return bb;
    }

    public static ByteBuffer intBufferToByteBuffer(IntBuffer intBuffer) {
        ByteBuffer bb = ByteBuffer.allocateDirect(intBuffer.remaining() << 2).order(ByteOrder.nativeOrder());
        bb.asIntBuffer().put(intBufferToArray(intBuffer));
        return bb;
    }

    public static float[] floatBufferToArray(FloatBuffer buffer) {
        float[] buffered = new float[buffer.remaining()];
        buffer.get(buffered);
        buffer.position(0);
        return  buffered;
    }

    public static int[] intBufferToArray(IntBuffer buffer) {
        int[] buffered = new int[buffer.remaining()];
        buffer.get(buffered);
        buffer.position(0);
        return  buffered;
    }

    public static char[] charBufferToArray(CharBuffer buffer) {
        char[] buffered = new char[buffer.remaining()];
        buffer.get(buffered);
        buffer.position(0);
        return  buffered;
    }
}
