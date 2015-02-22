#version 120

attribute vec4 pass_Color;

void main(void) {
	gl_FragColor = pass_Color;
}