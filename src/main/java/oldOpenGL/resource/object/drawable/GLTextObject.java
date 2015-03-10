package oldOpenGL.resource.object.drawable;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import oldOpenGL.resource.object.GLObject;
import oldOpenGL.resource.object.GLObjectUsage;
import oldOpenGL.resource.object.drawable.*;
import oldOpenGL.resource.texture.GLTextTexture;
import oldOpenGL.utils.GLRay;
import oldOpenGL.vertex.GLTexturedVertex;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

public class GLTextObject extends GLObject implements IGLDrawable {
	private GLTextTexture textTexture;
	private GLTexturedObject texturedObject;
	private float width;
	private float height;
	
	public GLTextObject() {
		this.texturedObject = new GLTexturedObject();
	}
	
	public void setup(String text, Font font, float height, GLObjectUsage usage) {
		this.textTexture = new GLTextTexture(text, font);
		
		float ratio = this.textTexture.getRatio();
		this.height = height;
		this.width = ratio * height;
		
		float midHeight = this.height / 2.0f;
		float midWidth = this.width / 2.0f;
		
		List<GLTexturedVertex> vertices = new ArrayList<GLTexturedVertex>();
		GLTexturedVertex v0 = new GLTexturedVertex();
		GLTexturedVertex v1 = new GLTexturedVertex();
		GLTexturedVertex v2 = new GLTexturedVertex();
		GLTexturedVertex v3 = new GLTexturedVertex();
		
		v0.setPosition(new Vector3f(-midWidth, midHeight, 0.0f));
		v1.setPosition(new Vector3f(midWidth, midHeight, 0.0f));
		v2.setPosition(new Vector3f(midWidth, -midHeight, 0.0f));
		v3.setPosition(new Vector3f(-midWidth, -midHeight, 0.0f));
		
		v0.setColor(Color.black);
		v1.setColor(Color.black);
		v2.setColor(Color.black);
		v3.setColor(Color.black);
		
		v0.setST(0.0f, 0.0f);
		v1.setST(1.0f, 0.0f);
		v2.setST(1.0f, 1.0f);
		v3.setST(0.0f, 1.0f);
		
		vertices.add(v0);
		vertices.add(v1);
		vertices.add(v2);
		vertices.add(v3);
		
		int[] indices = {
			0, 1, 2,
			2, 3, 0
		};
		
		this.texturedObject.setup(this.textTexture, vertices, indices, usage);
	}
	
	@Override
	public void init() {
		this.textTexture.init();
		this.texturedObject.init();
	}

	@Override
	public boolean isInitialized() {
		return this.textTexture.isInitialized() && this.texturedObject.isInitialized();
	}

	@Override
	public void render(Matrix4f projectionViewMatrix) {
		this.texturedObject.render(projectionViewMatrix);
	}
	
	public float getWidth() {
		return this.width;
	}
	
	public float getHeight() {
		return this.height;
	}
	
	@Override
	public void translate(Vector3f pos) {
		this.texturedObject.translate(pos);
	}
	
	@Override
	public void rotate(float angle, Vector3f axis) {
		this.texturedObject.rotate(angle, axis);
	}
	
	@Override
	public void scale(Vector3f scale) {
		this.texturedObject.scale(scale);
	}

	@Override
	public boolean isIntersected(GLRay ray) {
		return this.texturedObject.isIntersected(ray);
	}

	@Override
	public float getDistance(GLRay ray) {
		return this.texturedObject.getDistance(ray);
	}
}
