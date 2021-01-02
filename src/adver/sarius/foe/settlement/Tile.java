package adver.sarius.foe.settlement;

public class Tile {

	// TODO: Does that make sense?
	// 0/0 is upper left corner, extending to bottom right
	private int posX;
	private int posY;
	private int height;
	private int width;

	public Tile(int height, int width, int x, int y) {
		this.posX = x;
		this.posY = y;
		this.height = height;
		this.width = width;
	}

	public Tile(int lenght, int width) {
		this(0, 0, lenght, width);
	}

	public Tile(Tile tile) {
		this.posX = tile.posX;
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
}