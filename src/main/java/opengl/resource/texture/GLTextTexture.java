package opengl.resource.texture;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL13;

public class GLTextTexture extends GLTexture {
	private String text;
	private Font font;
	private boolean isInitialized;
	
	public GLTextTexture(String str, Font font) {
		this.text = str;
		this.font = font;
		this.isInitialized = false;
	}
	
	@Override
	public void init() {
		if (this.isInitialized() || this.text == null || this.font == null) {
			return;
		}
		
		super.setID(GLTextTexture.loadFromString(this.text, this.font, GL13.GL_TEXTURE0));
		this.text = null;
		this.font = null;
		this.isInitialized = true;
	}
	
	@Override
	public boolean isInitialized() {
		return this.isInitialized;
	}
	
	private static int loadFromString(String str, Font font, int textureUnit) {
		BufferedImage dummy = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics = dummy.createGraphics();
		
		FontMetrics metrics = graphics.getFontMetrics(font);
		int hgt = metrics.getHeight();
		int adv = metrics.stringWidth(str);
		int des = metrics.getDescent();
		Dimension size = new Dimension(adv, hgt);
		
		BufferedImage image = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB);
		graphics = image.createGraphics();
		graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		graphics.setColor(Color.black);
		graphics.setFont(font);
		graphics.drawString(str, 0, size.height - des);
		graphics.dispose();
		
		ByteBuffer byteBuffer = BufferUtils.createByteBuffer(size.width * size.height * 4);
		for (int j = 0;j < size.height;j++) {
			for (int i = 0;i < size.width;i++) {
				Color color = new Color(image.getRGB(i, j), true);
				byteBuffer.put((byte) color.getRed());
				byteBuffer.put((byte) color.getGreen());
				byteBuffer.put((byte) color.getBlue());
				byteBuffer.put((byte) color.getAlpha());
			}
		}
		byteBuffer.flip();
		
		return GLTexture.createTexture(byteBuffer, textureUnit, size.width, size.height);
	}
}
