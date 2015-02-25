#version 120

attribute vec4 in_Position;
attribute vec4 in_Color;

varying vec4 pass_Color;

void main(void) {
	gl_Position = in_Position;
	
	pass_Color = in_Color;
}