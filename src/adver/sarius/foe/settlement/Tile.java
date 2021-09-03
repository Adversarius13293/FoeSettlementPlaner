package adver.sarius.foe.settlement;

import java.awt.Color;
import java.util.Random;

public class Tile {

	// TODO: Does that make sense?
	// 0/0 is upper left corner, extending to bottom right
	private int posX;
	private int posY;
	private int height;
	private int width;
	private Color color;
	private static final Random RAND = new Random();

	public static Color randomColor() {
		return new Color(RAND.nextInt(16777215));
	}

	public Tile(int height, int width, int x, int y, Color color) {
		this.posX = x;
		this.posY = y;
		this.height = height;
		this.width = width;
		this.color = color;
	}

	public Tile(int height, int width, int x, int y) {
		this(height, width, x, y, randomColor());
	}

	public Tile(int height, int width) {
		this(height, width, 0, 0);
	}

	public Tile(Tile tile) {
		this.posX = tile.posX;
		this.posY = tile.posY;
		this.height = tile.height;
		this.width = tile.width;
		this.color = tile.color;
	}

	public void setPosition(int x, int y) {
		this.posX = x;
		this.posY = y;
	}

	public int getPosX() {
		return posX;
	}

	public int getPosY() {
		return posY;
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public int getSurface() {
		return width * height;
	}

	public Color getColor() {
		return this.color;
	}

	public boolean intersects(Tile tile) {
		return !(this.getPosX() >= tile.getPosX() + tile.getWidth()
				|| this.getPosY() >= tile.getPosY() + tile.getHeight()
				|| tile.getPosX() >= this.getPosX() + this.getWidth()
				|| tile.getPosY() >= this.getPosY() + this.getHeight());
	}

	public boolean contains(Tile tile) {
		return this.getPosX() <= tile.getPosX() && this.getPosY() <= tile.getPosY()
				&& this.getPosX() + this.getWidth() >= tile.getPosX() + tile.getWidth()
				&& this.getPosY() + this.getHeight() >= tile.getPosY() + tile.getHeight();
	}
}