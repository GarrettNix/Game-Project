package helpers;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex2f;
import static org.lwjgl.opengl.GL11.glRotatef;

import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL14;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureImpl;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.BufferedImageUtil;
import org.newdawn.slick.util.ResourceLoader;

/**
 * 
 * @author Garrett
 * 
 * This is the class that contains all of the LWJGL/OpenGL
 * render code. The methods in this class take textures and/or
 * coordinated on screen and render the desired objects.
 * 
 * This class is also used for loading textures into memory
 * from the game's asset directories.
 *
 */

public class Render {
	
	public static final int WIDTH = 1000;
	public static final int HEIGHT = 750;
	
	public static void beginSession() {
		Display.setTitle("Survival Game");
		try {
			Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
			Display.create();
			
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, WIDTH, HEIGHT, 0, 1, -1);
		glMatrixMode(GL_MODELVIEW);
		TextureImpl.bindNone();
	}
	
	public static void drawRect(float x, float y, float width, float height, Color c) {
		c.bind();
		glDisable(GL_TEXTURE_2D);
		glEnable(GL_BLEND);
		glBegin(GL_QUADS);
		glVertex2f(x, y);
		glVertex2f(x + width, y);
		glVertex2f(x + width, y + height);
		glVertex2f(x, y + height);
		glEnd();
		glEnable(GL_TEXTURE_2D);
		glDisable(GL_BLEND);
		Color.white.bind();
	}
	
	public static void drawRect(java.awt.Rectangle rect, Color c) {
		c.bind();
		glDisable(GL_TEXTURE_2D);
		glBegin(GL_QUADS);
		float x = (float) rect.getX();
		float y = (float) rect.getY();
		float width = (float) rect.getWidth();
		float height = (float) rect.getHeight();
		glVertex2f(x, y);
		glVertex2f(x + width, y);
		glVertex2f(x + width, y + height);
		glVertex2f(x, y + height);
		glEnd();
		glEnable(GL_TEXTURE_2D);
		Color.white.bind();
	}
	
	public static void drawOutline(float x, float y, float width, float height, Color c) {
		c.bind();
		glDisable(GL_TEXTURE_2D);
		glEnable(GL_BLEND);
		glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
		glBegin(GL_POLYGON);
		glVertex2f(x, y);
		glVertex2f(x + width, y);
		glVertex2f(x + width, y + height);
		glVertex2f(x, y + height);
		glEnd();
		glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
		glEnable(GL_TEXTURE_2D);
		glDisable(GL_BLEND);
		Color.white.bind();
	}
	
	public static void drawOutline(java.awt.Rectangle rect, Color c) {
		c.bind();
		glDisable(GL_TEXTURE_2D);
		glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
		glBegin(GL_POLYGON);
		float x = (float) rect.getX();
		float y = (float) rect.getY();
		float width = (float) rect.getWidth();
		float height = (float) rect.getHeight();
		glVertex2f(x, y);
		glVertex2f(x + width, y);
		glVertex2f(x + width, y + height);
		glVertex2f(x, y + height);
		glEnd();
		glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
		glEnable(GL_TEXTURE_2D);
		Color.white.bind();
	}
	
	public static void drawLine(float x1, float y1, float x2, float y2, Color c) {
		c.bind();
		glDisable(GL_BLEND);
		glDisable(GL_TEXTURE_2D);
		glBegin(GL_LINES);
		glVertex2f(x1, y1);
		glVertex2f(x2, y2);
		glEnd();
		glEnable(GL_BLEND);
		glEnable(GL_TEXTURE_2D);
		Color.white.bind();
	}
	
	public static void drawLine(Line2D.Double line, Color c) {
		c.bind();
		float x1 = (float) line.getX1();
		float x2 = (float) line.getX2();
		float y1 = (float) line.getY1();
		float y2 = (float) line.getY2();
		glDisable(GL_BLEND);
		glDisable(GL_TEXTURE_2D);
		glBegin(GL_LINES);
		glVertex2f(x1, y1);
		glVertex2f(x2, y2);
		glEnd();
		glEnable(GL_BLEND);
		glEnable(GL_TEXTURE_2D);
		Color.white.bind();
	}
	
	public static void drawImage(Texture texture, float x, float y) {
		glEnable(GL_TEXTURE_2D);
		glEnable(GL_BLEND);
		texture.bind();
		glTranslatef(x, y, 0);
		glBegin(GL_QUADS);
		glTexCoord2f(0, 0);
		glVertex2f(0, 0);
		glTexCoord2f(texture.getWidth(), 0);
		glVertex2f(texture.getImageWidth(), 0);
		glTexCoord2f(texture.getWidth(), texture.getHeight());
		glVertex2f(texture.getImageWidth(), texture.getImageHeight());
		glTexCoord2f(0, texture.getHeight());
		glVertex2f(0, texture.getImageHeight());
		glEnd();
		glLoadIdentity();
		glDisable(GL_TEXTURE_2D);
		glDisable(GL_BLEND);
	}
	
	public static void drawBackground(Texture texture, float x, float y) {
		glEnable(GL_TEXTURE_2D);
		texture.bind();
		glTranslatef(x, y, 0);
		glBegin(GL_QUADS);
		glTexCoord2f(0, 0);
		glVertex2f(0, 0);
		glTexCoord2f(texture.getWidth(), 0);
		glVertex2f(texture.getImageWidth(), 0);
		glTexCoord2f(texture.getWidth(), texture.getHeight());
		glVertex2f(texture.getImageWidth(), texture.getImageHeight());
		glTexCoord2f(0, texture.getHeight());
		glVertex2f(0, texture.getImageHeight());
		glEnd();
		glLoadIdentity();
		glDisable(GL_TEXTURE_2D);
	}
	
	public static void drawImage(Texture texture, float x, float y, float width, float height) {
		glEnable(GL_TEXTURE_2D);
		glEnable(GL_BLEND);
		texture.bind();
		glTranslatef(x + width / 2, y + height / 2, 0);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glBegin(GL_QUADS);
		glTexCoord2f(0, 0);
		glVertex2f(-width / 2, -height / 2);
		glTexCoord2f(texture.getWidth(), 0);
		glVertex2f(width / 2, -height / 2);
		glTexCoord2f(texture.getWidth(), texture.getHeight());
		glVertex2f(width / 2, height / 2);
		glTexCoord2f(0, texture.getHeight());
		glVertex2f(-width / 2, height / 2);
		glEnd();
		glLoadIdentity();
		glDisable(GL_TEXTURE_2D);
		glDisable(GL_BLEND);
	}
	
	public static void drawLaser(Texture texture, float x, float y, float width, float height, float angle) {
		glEnable(GL_TEXTURE_2D);
		glEnable(GL_BLEND);
		GL14.glBlendFuncSeparate(GL_SRC_ALPHA, GL_ONE, GL_ZERO, GL_ONE);
		//glBlendFunc(GL_SRC_ALPHA, GL_ONE);
		texture.bind();
		glTranslatef(x + width / 2, y + height / 2, 0);
		glRotatef(angle, 0, 0, 1);
		glBegin(GL_QUADS);
		glTexCoord2f(0, 0);
		glVertex2f(-width / 2, -height / 2);
		glTexCoord2f(texture.getWidth(), 0);
		glVertex2f(width / 2, -height / 2);
		glTexCoord2f(texture.getWidth(), texture.getHeight());
		glVertex2f(width / 2, height / 2);
		glTexCoord2f(0, texture.getHeight());
		glVertex2f(-width / 2, height / 2);
		glEnd();
		glLoadIdentity();
		glDisable(GL_TEXTURE_2D);
		glDisable(GL_BLEND);
	}
	
	public static void drawImageRotated(Texture texture, float x, float y, float angle) {
		glEnable(GL_TEXTURE_2D);
		glEnable(GL_BLEND);
		texture.bind();
		//glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
		//glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
		glTranslatef(x + texture.getImageWidth() / 2, y + texture.getImageHeight() / 2, 0);
		glRotatef(angle, 0, 0, 1);
		glBegin(GL_QUADS);
		glTexCoord2f(0, 0);
		glVertex2f(-texture.getImageWidth() / 2, -texture.getImageHeight() / 2);
		glTexCoord2f(texture.getWidth(), 0);
		glVertex2f(texture.getImageWidth() / 2, -texture.getImageHeight() / 2);
		glTexCoord2f(texture.getWidth(), texture.getHeight());
		glVertex2f(texture.getImageWidth() / 2, texture.getImageHeight() / 2);
		glTexCoord2f(0, texture.getHeight());
		glVertex2f(-texture.getImageHeight() / 2, texture.getImageHeight() / 2);
		glEnd();
		glLoadIdentity();
		glDisable(GL_TEXTURE_2D);
		glDisable(GL_BLEND);
	}
	
	public static void drawImageRotated(Texture texture, float x, float y, float width, float height, float angle) {
		glEnable(GL_TEXTURE_2D);
		glEnable(GL_BLEND);
		texture.bind();
		glTranslatef(x + width / 2, y + height / 2, 0);
		glRotatef(angle, 0, 0, 1);
		glBegin(GL_QUADS);
		glTexCoord2f(0, 0);
		glVertex2f(-width / 2, -height / 2);
		glTexCoord2f(texture.getWidth(), 0);
		glVertex2f(width / 2, -height / 2);
		glTexCoord2f(texture.getWidth(), texture.getHeight());
		glVertex2f(width / 2, height / 2);
		glTexCoord2f(0, texture.getHeight());
		glVertex2f(-width / 2, height / 2);
		glEnd();
		glLoadIdentity();
		glDisable(GL_TEXTURE_2D);
		glDisable(GL_BLEND);
	}
	
	public static void drawString(String s, TrueTypeFont f, Color c, float x, float y) {
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		if (c == null) System.out.println("null color?");
		f.drawString(x, y, s, c);
		glDisable(GL_BLEND);
		Color.white.bind();
	}
	
	public static Texture loadTexture(String path, String fileType) {
		Texture texture = null;
		InputStream in = ResourceLoader.getResourceAsStream(path);
		try {
			texture = TextureLoader.getTexture(fileType, in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return texture;
	}
	
	public static Texture quickLoad(String folder, String name) {
		Texture texture = null;
		texture = loadTexture(folder + "/" + name + ".png", "PNG");
		return texture;
	}
	
	public static Texture quickLoadExt(String folder, String name, String ext, String capExt) {
		Texture texture = null;
		texture = loadTexture(folder + "/" + name + "." + ext, capExt);
		return texture;
	}
	
	public static Texture[] loadSprites(String folder, String name, int width, int height) {
		BufferedImage image = null;
		BufferedImage[] subimages = null;
		try {
			image = ImageIO.read(Render.class.getResourceAsStream(folder + "/" + name + ".tga"));
			subimages = new BufferedImage[image.getWidth() / width];
			for (int i = 0; i < subimages.length; i++) {
				subimages[i] = image.getSubimage(i * width, 0, width, height);
			}
			Texture[] textures = new Texture[subimages.length];
			for (int i = 0; i < textures.length; i++) {
				textures[i] = BufferedImageUtil.getTexture("", subimages[i]);
			}
			return textures;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Texture[][] loadTileset(String folder, String name, int width, int height) {
		BufferedImage image = null;
		BufferedImage[][] subimages = null;
		try {
			image = ImageIO.read(Render.class.getResourceAsStream("/" + folder + "/" + name + ".png"));
			subimages = new BufferedImage[image.getHeight() / height][image.getWidth() / width];
			for (int i = 0; i < subimages.length; i++) {
				for (int j = 0; j < subimages[i].length; j++) {
					subimages[i][j] = image.getSubimage(j * width, i * height, width, height);
				}
			}
			Texture[][] textures = new Texture[subimages.length][subimages[0].length];
			for (int i = 0; i < textures.length; i++) {
				for (int j = 0; j < textures[i].length; j++) {
					textures[i][j] = BufferedImageUtil.getTexture("", subimages[i][j]);
				}
			}
			return textures;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}