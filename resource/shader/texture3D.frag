#version 150 core

uniform sampler2D texture_diffuse;

in vec4 pass_Color;
in vec2 pass_TextureCoord;

out vec4 out_Color;

void main(void) {
	vec4 color = pass_Color;
	vec4 textureColor = texture2D(texture_diffuse, pass_TextureCoord);
	
	color.a = textureColor.a;
	out_Color = textureColor + color;
}