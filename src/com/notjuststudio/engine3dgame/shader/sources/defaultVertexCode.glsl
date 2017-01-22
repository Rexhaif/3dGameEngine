vec4 worldPosition = $u0 * vec4($i0 ,1.0);
gl_Position = $u1 * $u2 * worldPosition;
$o0 = $i1 ;

$o1 = ($u0 * vec4($i2 , 0.0)).xyz;
$o2 = $u3 - worldPosition.xyz;
$o3 = (inverse($u2 ) * vec4(0,0,0,1)).xyz - worldPosition.xyz;