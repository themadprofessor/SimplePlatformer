#version 440
layout(location = 0) in vec3 vert;
layout(location = 1) in vec2 vertTexCoord;
out vec2 fragTexCoord;

uniform mat4 model;
uniform mat4 view;
uniform mat4 proj;

void main() {
    // Pass the tex coord straight through to the fragment shader
    fragTexCoord = vertTexCoord;

    gl_Position = proj * view * model * vec4(vert, 1);
}