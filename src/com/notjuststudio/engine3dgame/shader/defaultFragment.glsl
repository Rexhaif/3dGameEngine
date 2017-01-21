#version 400 core

in vec2 finalUV;
in vec3 finalSurfaceNormal;
in vec3 finalToLightVector;
in vec3 finalCameraVector;

out vec4 out_Colour;

uniform sampler2D textureSampler;
uniform vec3 lightColour;
uniform float shineDamper;
uniform float reflectivity;

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
    vec3 finalSpecular = dampedFactor * lightColour;

    out_Colour = vec4(diffuse, 1.0) * texture(textureSampler, finalUV) + vec4(finalSpecular,1.0);
}
