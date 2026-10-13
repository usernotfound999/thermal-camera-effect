#version 150

uniform sampler2D DiffuseSampler;
in vec2 texCoord;
in vec2 oneTexel;
out vec4 fragColor;

void main() {
    vec4 scene = texture(DiffuseSampler, texCoord);

    float luma = dot(scene.rgb, vec3(0.299, 0.587, 0.114));

    // 实体被渲染成纯白 (1.0)，世界被压暗
    // 阈值 0.99 区分
    if (luma > 0.99) {
        fragColor = vec4(1.0, 1.0, 1.0, 1.0);   // 实体 -> 纯白
    } else {
        float gray = luma * 0.5;                 // 世界 -> 压暗的灰度
        fragColor = vec4(gray, gray, gray, scene.a);
    }
}
