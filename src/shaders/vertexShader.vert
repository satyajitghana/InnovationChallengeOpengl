#version 130

in vec3 position;
in vec2 textureCoords;

out vec2 pass_textureCoords;

uniform mat4 transform;
uniform mat4 projection;

void main(void){

	vec4 worldPos = transform * vec4(position, 1.0);
	gl_Position = projection * worldPos;
	pass_textureCoords = textureCoords;
}
