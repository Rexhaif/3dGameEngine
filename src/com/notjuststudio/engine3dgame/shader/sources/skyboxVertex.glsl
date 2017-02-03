in vec3 vertexPos;

out vec3 uv;

uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;

void main() {
    gl_Position = projectionMatrix * viewMatrix * vec4(vertexPos,1.0);
    uv = vertexPos;
}