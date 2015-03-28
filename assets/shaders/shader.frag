#version 440
in vec2 fragTexCoord;
out vec4 out_colour;

uniform sampler2D sampler;

void main() {
    out_colour = texture(sampler, fragTexCoord);
}