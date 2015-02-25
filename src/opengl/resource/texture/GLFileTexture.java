package opengl.resource.texture;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import org.lwjgl.opengl.GL13;

import de.matthiasmann.twl.utils.PNGDecoder;
import de.matthiasmann.twl.utils.PNGDecoder.Format;


public class GLFileTexture extends GLTexture {
	private String texturePath;
	private boolean isInitialized;
	
	public GLFileTexture(String texturePath) {
		this.texturePath = texturePath;
		this.isInitialized = false;
	}
	
	@Override
	public void init() {
		if (this.isInitialized() || this.texturePath == null) {
			return;
		}
		
		super.setID(GLFileTexture.loadPNGTexture(this.texturePath, GL13.GL_TEXTURE0));
		this.texturePath = null;
		this.isInitialized = true;
	}
	
	@Override
	public boolean isInitialized() {
		return this.isInitialized;
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
			buf = ByteBuffer.allocateDirect(4 * decoder.getWidth() * decoder.getHeight());
			decoder.decode(buf, decoder.getWidth() * 4, Format.RGBA);
			buf.flip();
			
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		
		return GLTexture.createTexture(buf, textureUnit, tWidth, tHeight);
	}
}
