#version 150 core

uniform mat4 transformMatrix;
uniform vec4 in_Color;

in vec4 in_Position;

out vec4 pass_Color;

void main(void) {
	gl_Position = transformMatrix * in_Position;
	
	pass_Color = in_Color;
}