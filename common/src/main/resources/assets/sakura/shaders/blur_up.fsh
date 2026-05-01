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
        texture(Sampler0, f_TexCoord + vec2(-u_HalfTexelSize.x * 2.0, 0.0) * u_Offset) +
        texture(Sampler0, f_TexCoord + vec2(-u_HalfTexelSize.x, u_HalfTexelSize.y) * u_Offset) * 2.0 +
        texture(Sampler0, f_TexCoord + vec2(0.0, u_HalfTexelSize.y * 2.0) * u_Offset) +
        texture(Sampler0, f_TexCoord + u_HalfTexelSize * u_Offset) * 2.0 +
        texture(Sampler0, f_TexCoord + vec2(u_HalfTexelSize.x * 2.0, 0.0) * u_Offset) +
        texture(Sampler0, f_TexCoord + vec2(u_HalfTexelSize.x, -u_HalfTexelSize.y) * u_Offset) * 2.0 +
        texture(Sampler0, f_TexCoord + vec2(0.0, -u_HalfTexelSize.y * 2.0) * u_Offset) +
        texture(Sampler0, f_TexCoord - u_HalfTexelSize * u_Offset) * 2.0
    ) / 12.0;
    fragColor = vec4(color.rgb, 1.0);
}
