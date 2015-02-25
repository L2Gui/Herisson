#version 120

uniform sampler2D texture_diffuse;

attribute vec4 pass_Color;
attribute vec2 pass_TextureCoord;

void main(void) {
	vec4 color = pass_Color;
	vec4 textureColor = texture2D(texture_diffuse, pass_TextureCoord);
	
	color.a = textureColor.a;
	gl_FragColor = textureColor + color;
}