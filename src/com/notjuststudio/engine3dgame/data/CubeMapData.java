package com.notjuststudio.engine3dgame.data;

import java.nio.ByteBuffer;

/**
 * Created by George on 10.01.2017.
 */
public class CubeMapData {

    private int width;
    private int height;
    private ByteBuffer[] buffers;

    public CubeMapData(ByteBuffer[] buffers, int width, int height){
        this.buffers = buffers;
        this.width = width;
        this.height = height;
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

    public ByteBuffer[] getBuffers(){
            return buffers;
        }

}
