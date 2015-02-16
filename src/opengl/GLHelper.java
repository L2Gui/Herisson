package opengl;

import static org.lwjgl.opengl.GL15.glGenBuffers;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL15;
import org.lwjgl.util.vector.Matrix4f;
 
public class GLHelper {
	 public static IntBuffer makeIntBuffer(int[] ibuf) {
		  IntBuffer buf = BufferUtils.createIntBuffer(ibuf.length);
		  for(int i:ibuf) {
			  buf.put(i);
		  }
		  buf.position(0);
		  return buf;
	 }
	 
	 public static FloatBuffer makeTexturedVertexBuffer(GLTexturedVertex[] vertices) {
		 FloatBuffer buf = BufferUtils.createFloatBuffer(vertices.length * GLTexturedVertex.elementCount);
		 for (int i = 0; i < vertices.length; i++) {
			 // Add position, color and texture floats to the buffer
			 buf.put(vertices[i].getElements());
		 }
		 buf.position(0);
		 return buf;
	 }
	 
	 public static FloatBuffer makeFloatBuffer(float[] ibuf) {
		  FloatBuffer buf = BufferUtils.createFloatBuffer(ibuf.length);
		  for(float i:ibuf) {
			  buf.put(i);
		  }
		  buf.position(0);
		  return buf;
	 }
	 
	 public static FloatBuffer makeFloatBuffer(Matrix4f mat) {
	 	float[] f = {mat.m00,mat.m01,mat.m02,mat.m03,
		mat.m10,mat.m11,mat.m12,mat.m13,
		mat.m20,mat.m21,mat.m22,mat.m23,
		mat.m30,mat.m31,mat.m32,mat.m33};
	 	return makeFloatBuffer(f);
	 }
	 
	 public static int makeBuffer(int target, IntBuffer bufferdata) {
		 IntBuffer bufferid = BufferUtils.createIntBuffer(1);//IntBuffer.allocate(1);
		 GL15.glGenBuffers(bufferid);
		 int buffer = bufferid.get(0);
		 GL15.glBindBuffer(target, buffer);
		 GL15.glBufferData(target, bufferdata, GL15.GL_STATIC_DRAW);
		 GL15.glBindBuffer(target, 0);
		 return buffer;
	 }
	 
	 public static int makeBuffer(int target, FloatBuffer bufferdata) {
		 IntBuffer bufferid = BufferUtils.createIntBuffer(1);
		 glGenBuffers(bufferid);
		 int buffer = bufferid.get(0);
		 GL15.glBindBuffer(target, buffer);
		 GL15.glBufferData(target, bufferdata, GL15.GL_STATIC_DRAW);
		 GL15.glBindBuffer(target, 0);
		 return buffer;
	 }
}
