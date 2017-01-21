#version 400 core

layout ( triangles ) in;
layout ( triangle_strip, max_vertices = 3) out;

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
