#version 150 core

uniform mat4 transformMatrix;

in vec4 in_Position;

void main(void) {
	gl_Position = transformMatrix * in_Position;
}