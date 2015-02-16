package opengl;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL30;

import de.matthiasmann.twl.utils.PNGDecoder;
import de.matthiasmann.twl.utils.PNGDecoder.Format;

public class GLTexture implements GLResource {
	private String texturePath;
	private int textureID;
	
	public GLTexture(String texturePath) {
		this.texturePath = texturePath;
		
	}
	
	@Override
	public void init() {
		if (this.texturePath == null) {
			return;
		}
		
		this.textureID = GLTexture.loadPNGTexture(this.texturePath, GL13.GL_TEXTURE0);
		this.texturePath = null;
	}
	
	public void bind() {
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.textureID);
	}
	
	public void unbind() {
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
	}
	
	@Override
	public void finalize() {
		GL11.glDeleteTextures(this.textureID);
	}
	
	private static int loadPNGTexture(String filename, int textureUnit) {
		ByteBuffer buf = null;
		int tWidth = 0;
		int tHeight = 0;
		
		try {
			// Open the PNG file as an InputStream
			InputStream in = new FileInputStream(filename);
			// Link the PNG decoder to this stream
			PNGDecoder decoder = new PNGDecoder(in);
			
			// Get the width and height of the texture
			tWidth = decoder.getWidth();
			tHeight = decoder.getHeight();
			
			
			// Decode the PNG file in a ByteBuffer
			buf = ByteBuffer.allocateDirect(
					4 * decoder.getWidth() * decoder.getHeight());
			decoder.decode(buf, decoder.getWidth() * 4, Format.RGBA);
			buf.flip();
			
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		
		// Create a new texture object in memory and bind it
		int textureID = GL11.glGenTextures();
		GL13.glActiveTexture(textureUnit);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);
		
		// All RGB bytes are aligned to each other and each component is 1 byte
		GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);
		
		// Upload the texture data and generate mip maps (for scaling)
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGB, tWidth, tHeight, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buf);
		GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
		
		// Setup the ST coordinate system
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
		
		// Setup what to do when the texture has to be scaled
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_LINEAR);
		
		return textureID;
	}
}
