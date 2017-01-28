in vec2 finalUV;
in vec3 finalSurfaceNormal;
in vec3 finalToLightVector;
in vec3 finalCameraVector;
in sampler2D textureSampler;
in vec3 lightColour;
in float shineDamper;
in float reflectivity;

out vec4 out_Colour;

void main() {

    vec3 unitNormal = normalize(finalSurfaceNormal);
    vec3 unitLightVector = normalize(finalToLightVector);

    float nDotl = dot(unitNormal,unitLightVector);
    float brightness = max(nDotl,0.2);
    vec3 diffuse = brightness * lightColour;

    vec3 unitVectorToCamera = normalize(finalCameraVector);
    vec3 lightDirection = -unitLightVector;
    vec3 reflectedLightDirection = reflect(lightDirection, unitNormal);

    float specularFactor = dot(reflectedLightDirection, unitVectorToCamera);
    specularFactor = max(specularFactor, 0.0);
    float dampedFactor = pow(specularFactor, shineDamper);
    vec3 finalSpecular = dampedFactor * reflectivity * lightColour;

    out_Colour = vec4(diffuse, 1.0) * texture(textureSampler, finalUV) + vec4(finalSpecular,1.0);
}