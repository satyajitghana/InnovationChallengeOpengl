#version 130

in vec2 textureCoords;

out vec4 frag;

uniform sampler2D textureSampler;
uniform float renderingHealthBar;
uniform vec3 healthBarColor;

void main(){
	if(renderingHealthBar == 0){
		frag = texture(textureSampler, textureCoords);
	}
	else{
		frag = vec4(healthBarColor, 1.0);
	}

}