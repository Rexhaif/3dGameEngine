in vec3 vertexPos;
in vec2 vertexUv;
in vec3 vertexNorm;

uniform mat4 transformationMatrix;
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform vec3 lightPosition;

out vec2 uv;
out vec3 surfaceNormal;
out vec3 toLightVector;
out vec3 toCameraVector;

void main() {
    vec4 worldPosition = transformationMatrix * vec4(vertexPos,1.0);
    gl_Position = projectionMatrix * viewMatrix * worldPosition;
    uv = vertexUv;

    surfaceNormal = (transformationMatrix * vec4(vertexNorm, 0.0)).xyz;
    toLightVector = lightPosition - worldPosition.xyz;
    toCameraVector = (inverse(viewMatrix) * vec4(0,0,0,1)).xyz - worldPosition.xyz;
}