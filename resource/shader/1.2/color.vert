#version 120

uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform mat4 modelMatrix;

attribute vec4 in_Position;
attribute vec4 in_Color;

varying vec4 pass_Color;

void main(void) {	
	gl_Position = projectionMatrix * viewMatrix * modelMatrix * in_Position;
	pass_Color = in_Color;
}