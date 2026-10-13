#version 150

uniform sampler2D DiffuseSampler;   // 正常画面 (minecraft:main)
uniform sampler2D EntitySampler;    // 生物剪影 (final target)

in vec2 texCoord;
in vec2 oneTexel;
out vec4 fragColor;

void main() {
    vec4 scene = texture(DiffuseSampler, texCoord);
    vec4 entity = texture(EntitySampler, texCoord);

    // 生物剪影的 alpha > 0 表示该像素是生物
    if (entity.a > 0.01) {
        // 生物 -> 纯白
        fragColor = vec4(1.0, 1.0, 1.0, 1.0);
    } else {
        // 其它 -> 灰度
        float luma = dot(scene.rgb, vec3(0.299, 0.587, 0.114));
        float gray = luma * 0.85;
        fragColor = vec4(gray, gray, gray, scene.a);
    }
}
