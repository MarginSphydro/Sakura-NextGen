#version 410 core

in vec2 f_TexCoord;
layout(location = 0) out vec4 fragColor;

uniform sampler2D Sampler0;

layout(std140) uniform BlurData {
    vec2 u_HalfTexelSize;
    float u_Offset;
};

void main() {
    vec4 color = (
        texture(Sampler0, f_TexCoord) * 4.0 +
        texture(Sampler0, f_TexCoord - u_HalfTexelSize * u_Offset) +
        texture(Sampler0, f_TexCoord + u_HalfTexelSize * u_Offset) +
        texture(Sampler0, f_TexCoord + vec2(u_HalfTexelSize.x, -u_HalfTexelSize.y) * u_Offset) +
        texture(Sampler0, f_TexCoord - vec2(u_HalfTexelSize.x, -u_HalfTexelSize.y) * u_Offset)
    ) / 8.0;
    fragColor = vec4(color.rgb, 1.0);
}
