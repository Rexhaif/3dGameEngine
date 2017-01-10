package com.notjuststudio.engine3dgame.shader;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL32;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by George on 06.01.2017.
 */
public abstract class ShaderProgram {

    private static Map<Integer, int[]> programs = new HashMap<>();

    private int programID;

    private int vertexID;
    private int geometryID;
    private int fragmentID;

    private static FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);

    public ShaderProgram(ShadersContainer container) {
        vertexID = loadShader(GL20.GL_VERTEX_SHADER, container.getVertexShader());
        geometryID = loadShader(GL32.GL_GEOMETRY_SHADER, container.getGeometryShader());
        fragmentID = loadShader(GL20.GL_FRAGMENT_SHADER, container.getFragmentShader());
        programID = GL20.glCreateProgram();
        GL20.glAttachShader(programID, vertexID);
        GL20.glAttachShader(programID, geometryID);
        GL20.glAttachShader(programID, fragmentID);
        GL20.glLinkProgram(programID);
        GL20.glDeleteShader(vertexID);
        GL20.glDeleteShader(geometryID);
        GL20.glDeleteShader(fragmentID);
        GL20.glValidateProgram(programID);
        getAllUniformLocation();
        int[] shaders =  {vertexID, geometryID, fragmentID};
        programs.put(programID, shaders);
    }

    public void useThis() {
        GL20.glUseProgram(programID);
    }

    public static void useNone() {
        GL20.glUseProgram(0);
    }

    public static void cleanUp() {
        for (Map.Entry<Integer, int[]> program : programs.entrySet()) {
            GL20.glDetachShader(program.getKey(), program.getValue()[0]);
            GL20.glDetachShader(program.getKey(), program.getValue()[1]);
            GL20.glDetachShader(program.getKey(), program.getValue()[2]);
            GL20.glDeleteProgram(program.getKey());
        }
    }

    public void delete() {
        GL20.glDetachShader(programID, vertexID);
        GL20.glDetachShader(programID, geometryID);
        GL20.glDetachShader(programID, fragmentID);
        GL20.glDeleteProgram(programID);
        programs.remove(programID);
    }

    protected abstract void getAllUniformLocation();

    protected int getUniformLocation(String uniformName) {
        return GL20.glGetUniformLocation(programID, uniformName);
    }

    protected void loadFloat(int location, float value) {
        GL20.glUniform1f(location, value);
    }

    protected void loadVector(int location, Vector3f value) {
        GL20.glUniform3f(location, value.getX(), value.getY(), value.getZ());
    }

    protected void loadBoolean(int location, boolean value) {
        GL20.glUniform1i(location, value ? 1 : 0);
    }

    protected void loadMatrix(int location, Matrix4f value) {
        value.store(matrixBuffer);
        matrixBuffer.flip();
        GL20.glUniformMatrix4(location, false, matrixBuffer);
    }

    private static int loadShader(int type, String source) {
        int shaderID = GL20.glCreateShader(type);
        GL20.glShaderSource(shaderID, source);
        GL20.glCompileShader(shaderID);
        if (GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
            System.out.println(GL20.glGetShaderInfoLog(shaderID, 500));
            String shaderType = "";
            switch (type) {
                case GL20.GL_VERTEX_SHADER:
                    shaderType = "vertex";
                    break;
                case GL32.GL_GEOMETRY_SHADER:
                    shaderType = "geometry";
                    break;
                case GL20.GL_FRAGMENT_SHADER:
                    shaderType = "fragment";
                    break;
            }
            System.err.println("Could not compile " + shaderType + " shader!");
            System.exit(-1);
        }
        return shaderID;
    }
}
