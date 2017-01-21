#version 400 core

in vec3 finalUV;

out vec4 out_Colour;

uniform samplerCube cubeMap;


void main() {
    out_Colour = texture(cubeMap, normalize(finalUV));
}