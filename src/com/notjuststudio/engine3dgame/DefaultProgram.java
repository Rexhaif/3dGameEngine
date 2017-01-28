package com.notjuststudio.engine3dgame;

import com.notjuststudio.engine3dgame.attributes.Camera;
import com.notjuststudio.engine3dgame.attributes.Light;
import com.notjuststudio.engine3dgame.attributes.RenderModel;
import com.notjuststudio.engine3dgame.attributes.model.ModelTexture;
import com.notjuststudio.engine3dgame.render.MasterRenderer;
import com.notjuststudio.engine3dgame.shader.ShaderProgram;
import com.notjuststudio.engine3dgame.shader.ShadersBuilder;
import com.notjuststudio.engine3dgame.shader.ShadersBuilderAnother;
import com.notjuststudio.engine3dgame.shader.ShadersContainer;
import com.notjuststudio.engine3dgame.shader.sources.CodeBlock;
import com.notjuststudio.engine3dgame.shader.sources.CodeBlockAnother;
import org.lwjgl.util.vector.Matrix4f;

/**
 * Created by George on 06.01.2017.
 */
public class DefaultProgram extends ShaderProgram {

    private int location_transformationMatrix;
    private int location_projectionMatrix;
    private int location_viewMatrix;
    private int location_lightPosition;
    private int location_lightColour;
    private int location_shineDamper;
    private int location_reflectivity;

    public DefaultProgram() {
        //super(ShadersBuilder.createDefaultContainer());
        super(createContainer());
    }

//    private static ShadersContainer createContainer() {
//        String[] vertexOut = {
//                "vec2 uv",
//                "vec3 surfaceNormal",
//                "vec3 toLightVector",
//                "vec3 toCameraVector"
//        };
//        String[] vertexUniform = {
//                "mat4 transformationMatrix",
//                "mat4 projectionMatrix",
//                "mat4 viewMatrix",
//                "vec3 lightPosition"
//        };
//
//        String[] geometryOut = {
//                "vec2 finalUV",
//                "vec3 finalSurfaceNormal",
//                "vec3 finalToLightVector",
//                "vec3 finalCameraVector"
//        };
//        String[] geometryUniform = {};
//
//        String[] fragmentOut = {
//                "vec4 out_Colour"
//        };
//        String[] fragmentUniform = {
//                "sampler2D textureSampler",
//                "vec3 lightColour",
//                "float shineDamper",
//                "float reflectivity"
//        };
//
//        return new ShadersBuilder()
//                .setVertexOutput(vertexOut)
//                .setVertexUniform(vertexUniform)
//                .addVertexCode(new CodeBlock("defaultVertexCode.glsl"))
//
//                .setGeometryOutput(geometryOut)
//                .setGeometryUniform(geometryUniform)
//                .addGeometryCode(new CodeBlock("defaultGeometryCode.glsl"))
//
//                .setFragmentOutput(fragmentOut)
//                .setFragmentUniform(fragmentUniform)
//                .addFragmentCode(new CodeBlock("defaultFragmentCode.glsl"))
//
//                .build();
//    }

    private static ShadersContainer createContainer() {
        String[] vertexOut = {
                "vec2 uv",
                "vec3 surfaceNormal",
                "vec3 toLightVector",
                "vec3 toCameraVector"
        };
        String[] vertexUniform = {
                "mat4 transformationMatrix",
                "mat4 projectionMatrix",
                "mat4 viewMatrix",
                "vec3 lightPosition"
        };

        String[] geometryOut = {
                "vec2 finalUV",
                "vec3 finalSurfaceNormal",
                "vec3 finalToLightVector",
                "vec3 finalCameraVector"
        };
        String[] geometryUniform = {};

        String[] fragmentOut = {
                "vec4 out_Colour"
        };
        String[] fragmentUniform = {
                "sampler2D textureSampler",
                "vec3 lightColour",
                "float shineDamper",
                "float reflectivity"
        };

        return new ShadersBuilderAnother()
                .addVertexCode(new CodeBlockAnother("defaultVertex.glsl"))
                .addGeometryCode(new CodeBlockAnother("defaultGeometry.glsl"))
                .addFragmentCode(new CodeBlockAnother("defaultFragment.glsl"))

                .build();
    }

    @Override
    protected void getAllUniformLocation() {
        location_transformationMatrix = super.getUniformLocation("transformationMatrix");
        location_projectionMatrix = super.getUniformLocation("projectionMatrix");
        location_viewMatrix = super.getUniformLocation("viewMatrix");
        location_lightPosition = super.getUniformLocation("lightPosition");
        location_lightColour = super.getUniformLocation("lightColour");
        location_shineDamper = super.getUniformLocation("shineDamper");
        location_reflectivity = super.getUniformLocation("reflectivity");
    }

    @Override
    public void loadWhenCreated(ModelTexture modelTexture) {
        loadShineVariable(modelTexture.getShineDamper(), modelTexture.getReflectivity());
    }

    @Override
    public void loadPrepareModel(RenderModel renderModel) {
        loadProjectionMatrix(Camera.getMainCamera().getProjectionMatrix());
        loadViewMatrix(Camera.getMainCamera().getViewMatrix());
        loadLight(MasterRenderer.getLight());
    }

    @Override
    public void loadPrepareEntity(Entity entity) {
        loadTransformationMatrix(entity.getWorldTransformation());
    }

    public void loadShineVariable(float damper, float reflectivity) {
        super.loadFloat(location_shineDamper, damper);
        super.loadFloat(location_reflectivity, reflectivity);
    }

    public void loadTransformationMatrix(Matrix4f matrix4f) {
        super.loadMatrix(location_transformationMatrix, matrix4f);
    }

    public void loadProjectionMatrix(Matrix4f matrix4f) {
        super.loadMatrix(location_projectionMatrix, matrix4f);
    }

    public void loadViewMatrix(Matrix4f matrix4f) {
        super.loadMatrix(location_viewMatrix, matrix4f);
    }

    public void loadLight(Light light) {
        super.loadVector(location_lightPosition, light.getEntity().getPosition());
        super.loadVector(location_lightColour, light.getColour());
    }
}
