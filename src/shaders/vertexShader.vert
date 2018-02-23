#version 130

in vec3 position;
in vec2 textureCoords;

out vec2 pass_textureCoords;

uniform mat4 transform;
uniform mat4 projection;

void main(void){
	gl_Position = projection * transform * vec4(position, 1.0);
	pass_textureCoords = textureCoords;
}
