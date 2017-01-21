#version 400 core

layout ( triangles ) in;
layout ( triangle_strip, max_vertices = 3) out;

in vec2 uv[3];
in vec3 surfaceNormal[3];
in vec3 toLightVector[3];
in vec3 toCameraVector[3];

out vec2 finalUV;
out vec3 finalSurfaceNormal;
out vec3 finalToLightVector;
out vec3 finalCameraVector;

void main() {
    for(int i = 0; i < 3; i++) {
        gl_Position = gl_in[i].gl_Position;
        finalUV = uv[i];
        finalSurfaceNormal = surfaceNormal[i];
        finalToLightVector = toLightVector[i];
        EmitVertex();
    }
    EndPrimitive();
}