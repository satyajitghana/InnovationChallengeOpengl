#version 130

in vec2 pass_textureCoords;

uniform sampler2D textureSampler;

out vec4 out_color;

void main(void){
	out_color = texture(textureSampler, pass_textureCoords);
}
