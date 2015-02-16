#version 120

uniform sampler2D texture_diffuse;

attribute vec4 pass_Color;
attribute vec2 pass_TextureCoord;

void main(void) {
	// out_Color = pass_Color;
	// Override out_Color with our texture pixel
	gl_FragColor = pass_Color + texture2D(texture_diffuse, pass_TextureCoord);
}