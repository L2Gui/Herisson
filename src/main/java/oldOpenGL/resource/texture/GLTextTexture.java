package oldOpenGL.resource.texture;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL13;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

public class GLTextTexture extends GLTexture {
	private String text;
	private Font font;
	private Dimension size;
	private int des;
	private boolean isInitialized;
	
	public GLTextTexture(String str, Font font) {
		this.text = str;
		this.font = font;
		this.isInitialized = false;
		
		BufferedImage dummy = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics = dummy.createGraphics();
		
		FontMetrics metrics = graphics.getFontMetrics(this.font);
		int hgt = metrics.getHeight();
		int adv = metrics.stringWidth(this.text);
		this.des = metrics.getDescent();
		this.size = new Dimension(adv, hgt);
	}
	
	@Override
	public void init() {
		if (this.isInitialized() || this.text == null || this.font == null) {
			return;
		}
		
		this.loadFromString(GL13.GL_TEXTURE0);
		this.text = null;
		this.font = null;
		this.isInitialized = true;
	}
	
	@Override
	public boolean isInitialized() {
		return this.isInitialized;
	}
	
	public float getRatio() {
		return (float) this.size.width / (float) this.size.height;
	}
	
	private void loadFromString(int textureUnit) {		
		BufferedImage image = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics = image.createGraphics();
		graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		graphics.setColor(Color.black);
		graphics.setFont(this.font);
		graphics.drawString(this.text, 0, size.height - des);
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
		
		super.setID(oldOpenGL.resource.texture.GLTexture.createTexture(byteBuffer, textureUnit, size.width, size.height));
	}
}
