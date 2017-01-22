vec3 unitNormal = normalize($i1 );
vec3 unitLightVector = normalize($i2 );

float nDotl = dot(unitNormal,unitLightVector);
float brightness = max(nDotl,0.2);
vec3 diffuse = brightness * $u1 ;

vec3 unitVectorToCamera = normalize($i3 );
vec3 lightDirection = -unitLightVector;
vec3 reflectedLightDirection = reflect(lightDirection, unitNormal);

float specularFactor = dot(reflectedLightDirection, unitVectorToCamera);
specularFactor = max(specularFactor, 0.0);
float dampedFactor = pow(specularFactor, $u2 );
vec3 finalSpecular = dampedFactor * $u3 * $u1 ;

$o0 = vec4(diffuse, 1.0) * texture($u0 , $i0 ) + vec4(finalSpecular,1.0);
