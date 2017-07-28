package entity;

import static helpers.Render.HEIGHT;
import static helpers.Render.WIDTH;

import java.awt.Rectangle;

import tilemap.TileMap;

/**
 * 
 * @author Garrett
 * 
 * This is the superclass for all entities in the game. It provides basic
 * attributes such as position, size, and velocity.
 *
 */

public abstract class Entity {
	
	// position and tilemap
	protected TileMap tilemap;
	protected int tileSize;
	protected float x;
	protected float y;
	protected float xmap;
	protected float ymap;
	
	// movement and velocity
	protected float dx;
	protected float dy;
	protected boolean up;
	protected boolean down;
	protected boolean left;
	protected boolean right;
	
	// size and collision
	protected int width;
	protected int height;
	protected int cwidth;
	protected int cheight;
	
	// tile collision
	protected int currRow;
	protected int currCol;
	protected double xdest;
	protected double ydest;
	protected double xtemp;
	protected double ytemp;
	protected boolean topLeft;
	protected boolean topRight;
	protected boolean bottomLeft;
	protected boolean bottomRight;
	
	// animation
	protected Animation animation;
	protected int currentAction;
	protected int previousAction;
	protected boolean facingRight;
	
	public Entity(TileMap tm) {
		this.tilemap = tm;
		this.tileSize = tm.getTileSize();
	}
	
	public boolean intersects(Entity o) {
		Rectangle r1 = getRectangle();
		Rectangle r2 = o.getRectangle();
		return r1.intersects(r2)
				|| r1.contains(r2);
	}
	
	public Rectangle getRectangle() {
		return new Rectangle((int) (x + xmap - cwidth / 2), (int) (y + ymap - cheight /  2), cwidth, cheight);
	}
	
	public void calculateCorners(double x, double y) {
        int leftTile = (int)(x - cwidth / 2) / tileSize;
        int rightTile = (int)(x + cwidth / 2 - 1) / tileSize;
        int topTile = (int)(y - cheight / 2) / tileSize;
        int bottomTile = (int)(y + cheight / 2 - 1) / tileSize;
        if(topTile < 0 || bottomTile >= tilemap.getNumRows() ||
                leftTile < 0 || rightTile >= tilemap.getNumCols()) {
                topLeft = topRight = bottomLeft = bottomRight = false;
                return;
        }
        topLeft = tilemap.isBlocked(topTile, leftTile);
        topRight = tilemap.isBlocked(topTile, rightTile);
        bottomLeft = tilemap.isBlocked(bottomTile, leftTile);
        bottomRight = tilemap.isBlocked(bottomTile, rightTile);
	}
	
	public void checkTileMapCollision() {
		currCol = (int) x / tileSize;
		currRow = (int) y / tileSize;
		xdest = x + dx;
		ydest = y + dy;
		xtemp = x;
		ytemp = y;
		calculateCorners(x, ydest);
		if (dy < 0) {
			if (topLeft || topRight) {
				dy = 0;
				ytemp = currRow * tileSize + cheight / 2;
			}
			else ytemp += dy;
		}
		if (dy > 0) {
			if (bottomLeft || bottomRight) {
				dy = 0;
				ytemp = (currRow + 1) * tileSize - cheight / 2;
			}
			else ytemp += dy;
		}
		calculateCorners(xdest, y);
		if (dx < 0) {
			if (topLeft || bottomLeft) {
				dx = 0;
				xtemp = currCol * tileSize + cwidth / 2;
			}
			else xtemp += dx;
		}
		if (dx > 0) {
			if (topRight || bottomRight) {
				dx = 0;
				xtemp = (currCol + 1) * tileSize - cwidth / 2;
			}
			else xtemp += dx;
		}
	}
	
	public double getX() { return x; }
	public double getY() { return y; }
	public double getXOnScreen() { return x + xmap; }
	public double getYOnScreen() { return y + ymap; }
	public int getWidth() { return width; }
	public int getHeight() { return height; }
	public int getCWidth() { return cwidth; }
	public int getCHeight() { return cheight; }
	
	public void setPosition(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public void setVector(float dx, float dy) {
		this.dx = dx;
		this.dy = dy;
	}
	
	public void setMapPosition() {
		xmap = tilemap.getX();
		ymap = tilemap.getY();
	}
	
	public void setLeft(boolean b) { left = b; }
	public void setRight(boolean b) { right = b; }
	public void setUp(boolean b) { up = b; }
	public void setDown(boolean b) { down = b; }
	
	public boolean notOnScreen() {
		return x + xmap + width < 0 || x + xmap - width > WIDTH || y + ymap + height < 0 || y + ymap - height > HEIGHT;
	}
	
	public abstract void update();
	
}