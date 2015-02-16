#version 120

uniform mat4 projectionMatrix;/*
uniform mat4 viewMatrix;
uniform mat4 modelMatrix;*/

attribute vec4 in_Position;
attribute vec4 in_Color;
attribute vec2 in_TextureCoord;

varying vec4 pass_Color;
varying vec2 pass_TextureCoord;

void main(void) {
	gl_Position = in_Position;
	// Override gl_Position with our new calculated position
	// gl_Position = projectionMatrix * viewMatrix * modelMatrix * in_Position;
	
	mat4 pm;
	pm = mat4(1.0);
	pm = projectionMatrix;
	
	gl_Position = pm * in_Position;
	
	pass_Color = in_Color;
	pass_TextureCoord = in_TextureCoord;
}