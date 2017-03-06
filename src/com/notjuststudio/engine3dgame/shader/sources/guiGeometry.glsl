in  vec2 textureCoords[3];

out vec2 finalTextureCoords;

void main() {

    for(int i = 0; i < 3; i++) {
        gl_Position = gl_in[i].gl_Position;
        finalTextureCoords = textureCoords[i];
        EmitVertex();
    }
    EndPrimitive();

}
