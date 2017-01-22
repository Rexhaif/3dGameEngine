for(int i = 0; i < 3; i++) {
    gl_Position = gl_in[i].gl_Position;
    $o0 = $i0 [i];
    $o1 = $i1 [i];
    $o2 = $i2 [i];
    $o3 = $i3 [i];
    EmitVertex();
}
EndPrimitive();