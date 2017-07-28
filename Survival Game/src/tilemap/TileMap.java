package tilemap;

import static helpers.Render.HEIGHT;
import static helpers.Render.WIDTH;
import static helpers.Render.drawImage;
import static helpers.Render.loadTileset;

import java.awt.Point;
import java.util.ArrayList;

import org.newdawn.slick.opengl.Texture;

import helpers.Maths;

public class TileMap {
	
	// position
	private double x;
	private double y;
	
	// bounds
	private int xmin;
	private int xmax;
	private int ymin;
	private int ymax;
	
	// tiles
	private Texture[][] tileset;
	private int[][] map;
	int tileSize;
	private int numRows;
	private int numCols;
	private int width;
	private int height;
	
	// drawing
	private int rowOffset;
	private int colOffset;
	private int numRowsToDraw;
	private int numColsToDraw;
	
	public TileMap(int tileSize) {
		this.tileSize = tileSize;
		numRowsToDraw = HEIGHT / tileSize + 2;
		numColsToDraw = WIDTH / tileSize + 2;
		loadTiles(1);
		numRows = 1000;
		numCols = 1000;
		long start = System.nanoTime();
		generateMap();
		//initBasicMap();
		long elapsed = System.nanoTime() - start;
		System.out.println("Map generation took about " + elapsed / 1000000 + "ms");
	}
	
	/**
	 * @param tileset: the number for which tileset to load from /resources/tilesets.
	 * Calls Render to load the tile textures and saves them to the tileset array.
	 */
	private void loadTiles(int tileset) {
		this.tileset = loadTileset(tileset, tileSize, tileSize);
	}
	
	/**
	 * Load a randomly generated map. For testing purposes, there is no system for
	 * generating the map. Tiles are random for now.
	 */
	/**
	private void initBasicMap() {
		width = numCols * tileSize;
		height = numRows * tileSize;
		xmin = -tileSize * numCols + tileSize * (numColsToDraw * 9 / 10) + 64;
		ymin = -tileSize * numRows + tileSize * (numRowsToDraw * 9 / 10);
		xmax = 0;
		ymax = 0;
		map = new int[1000][1000];
		for (int row = 0; row < map.length; row++) {
			for (int col = 0; col < map[row].length; col++) {
				double chance = Math.random();
				int type = 0;
				if (chance < 0.65) type = 0;
				else if (chance < 0.85) type = 10;
				else if (chance < 0.93) type = 20;
				else if (chance < 0.98) type = 30;
				else type = 40;
				int variant = (int) (Math.random() * 8);
				map[row][col] = type + variant;
			}
		}
	}
	*/
	
	/**
	 * Draw the tiles that are on screen.
	 */
	public void render() {
		for (int row = rowOffset; row < rowOffset + numRowsToDraw; row++) {
			if (row >= numRows) break;
			for (int col = colOffset; col < colOffset + numColsToDraw; col++) {
				if (col >= numCols) break;
				int r = map[row][col] / 10;
				int c = map[row][col] % 10;
				float xc = (float) x + col * tileSize;
				float yc = (float) y + row * tileSize;
				drawImage(tileset[r][c], xc, yc);
			}
		}
	}
	
	// getter methods
	public int getTileSize() { return tileSize; }
	public int getX() { return (int) x; }
	public int getY() { return (int) y; }
	public int getWidth() { return width; }
	public int getHeight() { return height; }
	public int getNumRows() { return numRows; }
	public int getNumCols() { return numCols; }
	public int getRowOffset() { return rowOffset; }
	public int getColOffset() { return colOffset; }
	public int getNumRowsToDraw() { return numRowsToDraw; }
	public int getNumColsToDraw() { return numColsToDraw; }
	
	/**
	 * Check if a certain tile is blocked. Blocked tiles cannot be walked over.
	 */
	public boolean isBlocked(int row, int col) {
		//int type = map[row][col] / 10;
		// check type
		return false;
	}
	
	/**
	 * @param row: row of the tile requested
	 * @param col: col of the tile requested
	 * @return the type value for the tile requested
	 */
	public int getType(int row, int col) {
		return map[row][col] / 10;
	}
	
	/**
	 * @param x: Based on the player's X position.
	 * @param y: Based on the player's Y position.
	 * This is used to move the tilemap as the player moves, so that while on the map
	 * the player is always at the center of the screen.
	 */
	public void setPosition(double x, double y) {
		this.x = x;
		this.y = y;
		fixBounds();
		colOffset = (int) -this.x / tileSize;
		rowOffset = (int) -this.y / tileSize;
	}
	
	/**
	 * This method ensures that the tilemap does not move into the screen where
	 * empty space can be seen to the side; then the player reaches the side of the
	 * map, the screen stops scrolling.
	 */
	private void fixBounds() {
		if (x < xmin) x = xmin;
		if (x > xmax) x = xmax;
		if (y < ymin) y = ymin;
		if (y > ymax) y = ymax;
	}
	
	/**
	 * THE FOLLOWING METHODS ARE RECYCLED FROM AN OLDER PROJECT I WORKED ON.
	 * I am going through the code and modifying it to fit this new project.
	 */
	
	private ArrayList<Point> dirtLocations;
	private ArrayList<Point> waterLocations;
	
	private void generateMap() {
		System.out.println("GENERATING MAP");
		xmin = -tileSize * numCols + tileSize * (numColsToDraw * 9 / 10) + 40;
		ymin = -tileSize * numRows + tileSize * (numRowsToDraw * 9 / 10) + 46;
		xmax = 0;
		ymax = 0;
		dirtLocations = new ArrayList<Point>();
		waterLocations = new ArrayList<Point>();
		map = new int[numRows][numCols];
		// start with all grass
		for (int row = 0; row < map.length; row++) {
			for (int col = 0; col < map[row].length; col++) {
				int type = 0;
				int variant = (int) (Math.random() * 8);
				map[row][col] = type + variant;
			}
		}
		// generate dirt patches
		// step 1: starting points
		System.out.println("GENERATING DIRT PATCHES");
		for (int row = 0; row < map.length; row++) {
			for (int col = 0; col < map[row].length; col++) {
				double chance = Math.random();
				if (chance > 0.999) {
					Point p1 = new Point(row, col);
					for (Point p2 : dirtLocations) {
						if (Maths.distance(p1, p2) < 40) continue; // keeps water from overlapping with dirt (much)
					}
					dirtLocations.add(new Point(row, col));
					int type = 10;
					int variant;
					variant = (int) (Math.random() * 8);
					map[row][col] = type + variant; // center
					variant = (int) (Math.random() * 8);
					if (row > 0 && col > 0) map[row - 1][col - 1] = type + variant; // top left
					variant = (int) (Math.random() * 8);
					if (row > 0) map[row - 1][col] = type + variant; // top
					variant = (int) (Math.random() * 8);
					if (row > 0 && col < numCols - 1) map[row - 1][col + 1] = type + variant; // top right
					variant = (int) (Math.random() * 8);
					if (col > 0) map[row][col - 1] = type + variant; // left
					variant = (int) (Math.random() * 8);
					if (col < numCols - 1) map[row][col + 1] = type + variant; // right
					variant = (int) (Math.random() * 8);
					if (row < numRows - 1 && col < numCols - 1) map[row + 1][col + 1] = type + variant; // bottom right
					variant = (int) (Math.random() * 8);
					if (row < numRows - 1) map[row + 1][col] = type + variant; // bottom
					variant = (int) (Math.random() * 8);
					if (row < numRows - 1 && col > 0) map[row + 1][col - 1] = type + variant; // bottom left
				}
			}
		}
		// step 2: grow them
		System.out.println("GROWING DIRT PATCHES");
		for (int i = 0; i < 4; i++) growDirtPatches();
		// generate water ponds
		// step 1: starting points
		System.out.println("GENERATING WATER POOLS");
		for (int row = 0; row < map.length; row++) {
			for (int col = 0; col < map[row].length; col++) {
				double chance = Math.random();
				if (chance > 0.999) {
					Point p1 = new Point(row, col);
					for (Point p2 : dirtLocations) {
						if (Maths.distance(p1, p2) < 40) continue; // keeps water from overlapping with dirt (much)
					}
					for (Point p2 : waterLocations) {
						if (Maths.distance(p1, p2) < 40) continue; // keeps water from overlapping with dirt (much)
					}
					waterLocations.add(new Point(row, col));
					int type = 40;
					int variant;
					variant = (int) (Math.random() * 8);
					map[row][col] = type + variant; // center
					variant = (int) (Math.random() * 8);
					if (row > 0 && col > 0) map[row - 1][col - 1] = type + variant; // top left
					variant = (int) (Math.random() * 8);
					if (row > 0) map[row - 1][col] = type + variant; // top
					variant = (int) (Math.random() * 8);
					if (row > 0 && col < numCols - 1) map[row - 1][col + 1] = type + variant; // top right
					variant = (int) (Math.random() * 8);
					if (col > 0) map[row][col - 1] = type + variant; // left
					variant = (int) (Math.random() * 8);
					if (col < numCols - 1) map[row][col + 1] = type + variant; // right
					variant = (int) (Math.random() * 8);
					if (row < numRows - 1 && col < numCols - 1) map[row + 1][col + 1] = type + variant; // bottom right
					variant = (int) (Math.random() * 8);
					if (row < numRows - 1) map[row + 1][col] = type + variant; // bottom
					variant = (int) (Math.random() * 8);
					if (row < numRows - 1 && col > 0) map[row + 1][col - 1] = type + variant; // bottom left
				}
			}
		}
		// step 2: grow them
		System.out.println("GROWING WATER POOLS");
		for (int i = 0; i < 4; i++) growWaterPools();
		// step 3: sand beaches
		System.out.println("GENERATING BEACHES");
		generateBeaches();
	}
	
	private void growDirtPatches() {
		for (int row = 0; row < map.length; row++) {
			for (int col = 0; col < map[row].length; col++) {
				// count dirt neighbors
				if (map[row][col] / 10 == 1) continue; // already dirt
				else {
					int countNeighbors = 0;
					if (row > 0 && col > 0) {
						if (map[row - 1][col - 1] / 10 == 1) countNeighbors++;
					}
					if (row > 0) {
						if (map[row - 1][col] / 10 == 1) countNeighbors++;
					}
					if (row > 0 && col < numCols - 1) {
						if (map[row - 1][col + 1] / 10 == 1) countNeighbors++;
					}
					if (col > 0) {
						if (map[row][col - 1] / 10 == 1) countNeighbors++;
					}
					if (col < numCols - 1) {
						if (map[row][col + 1] / 10 == 1) countNeighbors++;
					}
					if (row < numRows - 1 && col < numCols - 1) {
						if (map[row + 1][col + 1] / 10 == 1) countNeighbors++;
					}
					if (row < numRows - 1) {
						if (map[row + 1][col] / 10 == 1) countNeighbors++;
					}
					if (row < numRows - 1 && col > 0) {
						if (map[row + 1][col - 1] / 10 == 1) countNeighbors++;
					}
					double chance = Math.random();
					int type = 10;
					int variant = (int) (Math.random() * 8);
					if (countNeighbors == 0) continue;
					else if (countNeighbors == 1) {
						if (chance < 0.05) map[row][col] = type + variant;
					}
					else if (countNeighbors == 2) {
						if (chance < 0.15) map[row][col] = type + variant;
					}
					else if (countNeighbors == 3) {
						if (chance < 0.25) map[row][col] = type + variant;
					}
					else if (countNeighbors == 4) {
						if (chance < 0.35) map[row][col] = type + variant;
					}
					else if (countNeighbors == 5) {
						if (chance < 0.45) map[row][col] = type + variant;
					}
					else if (countNeighbors == 6) {
						if (chance < 0.55) map[row][col] = type + variant;
					}
					else if (countNeighbors == 7) {
						if (chance < 0.65) map[row][col] = type + variant;
					}
					else if (countNeighbors == 8) {
						if (chance < 0.9) map[row][col] = type + variant;
					}
				}
			}
		}
	}
	
	private void growWaterPools() {
		for (int row = 0; row < map.length; row++) {
			for (int col = 0; col < map[row].length; col++) {
				// count dirt neighbors
				if (map[row][col] / 10 == 4) continue; // already water
				else {
					int countNeighbors = 0;
					if (row > 0 && col > 0) {
						if (map[row - 1][col - 1] / 10 == 4) countNeighbors++;
					}
					if (row > 0) {
						if (map[row - 1][col] / 10 == 4) countNeighbors++;
					}
					if (row > 0 && col < numCols - 1) {
						if (map[row - 1][col + 1] / 10 == 4) countNeighbors++;
					}
					if (col > 0) {
						if (map[row][col - 1] / 10 == 4) countNeighbors++;
					}
					if (col < numCols - 1) {
						if (map[row][col + 1] / 10 == 4) countNeighbors++;
					}
					if (row < numRows - 1 && col < numCols - 1) {
						if (map[row + 1][col + 1] / 10 == 4) countNeighbors++;
					}
					if (row < numRows - 1) {
						if (map[row + 1][col] / 10 == 4) countNeighbors++;
					}
					if (row < numRows - 1 && col > 0) {
						if (map[row + 1][col - 1] / 10 == 4) countNeighbors++;
					}
					double chance = Math.random();
					int type = 40;
					int variant = (int) (Math.random() * 8);
					if (countNeighbors < 2) continue;
					else if (countNeighbors == 2) {
						if (chance < 0.15) map[row][col] = type + variant;
					}
					else if (countNeighbors == 3) {
						if (chance < 0.25) map[row][col] = type + variant;
					}
					else if (countNeighbors == 4) {
						if (chance < 0.35) map[row][col] = type + variant;
					}
					else if (countNeighbors == 5) {
						if (chance < 0.45) map[row][col] = type + variant;
					}
					else if (countNeighbors == 6) {
						if (chance < 0.55) map[row][col] = type + variant;
					}
					else if (countNeighbors == 7) {
						if (chance < 0.65) map[row][col] = type + variant;
					}
					else if (countNeighbors == 8) {
						if (chance < 0.9) map[row][col] = type + variant;
					}
				}
			}
		}
	}
	
	private void generateBeaches() {
		for (int row = 0; row < map.length; row++) {
			for (int col = 0; col < map[row].length; col++) {
				// count dirt neighbors
				if (map[row][col] / 10 == 4) continue; // already water
				else {
					int countNeighbors = 0;
					if (row > 0) {
						if (map[row - 1][col] / 10 == 4) countNeighbors++;
					}
					if (col > 0) {
						if (map[row][col - 1] / 10 == 4) countNeighbors++;
					}
					if (col < numCols - 1) {
						if (map[row][col + 1] / 10 == 4) countNeighbors++;
					}
					if (row < numRows - 1) {
						if (map[row + 1][col] / 10 == 4) countNeighbors++;
					}
					int type = 20;
					int variant = (int) (Math.random() * 8);
					if (countNeighbors == 0) continue;
					else {
						if (countNeighbors == 4) map[row][col] = 40 + variant;
						else map[row][col] = type + variant;
					}
				}
			}
		}
	}
}