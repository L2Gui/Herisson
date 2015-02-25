#version 120

uniform mat4 transformMatrix;

attribute vec4 in_Position;
attribute vec4 in_Color;
attribute vec2 in_TextureCoord;

varying vec4 pass_Color;
varying vec2 pass_TextureCoord;

void main(void) {
	gl_Position = transformMatrix * in_Position;
	
	pass_Color = in_Color;
	pass_TextureCoord = in_TextureCoord;
}