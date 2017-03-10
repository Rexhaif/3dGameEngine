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
        finalCameraVector = toCameraVector[i];
        EmitVertex();
    }
    EndPrimitive();
}