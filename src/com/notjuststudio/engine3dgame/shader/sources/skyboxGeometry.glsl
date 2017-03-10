in vec3 uv[3];

out vec3 finalUV;

void main() {
    for(int i = 0; i < 3; i++) {
        gl_Position = gl_in[i].gl_Position;
        finalUV = uv[i];
        EmitVertex();
    }
    EndPrimitive();
}
