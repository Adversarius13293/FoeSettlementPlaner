package adver.sarius.foe.settlement;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;

import javax.swing.JPanel;

public class SettlementDrawer extends JPanel {

	private static final long serialVersionUID = -1577716942651390411L;
	private Settlement settlement;
	private double scaling = 1;

	public SettlementDrawer(Settlement settlement) {
		super();
		this.settlement = settlement;
	}

	public void setScaling(double scaling) {
		this.scaling = scaling;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Tile t = settlement.getBaseLayout();
		g.setColor(t.getColor());
		g.fillRect((int) (t.getPosX() * scaling), (int) (t.getPosY() * scaling), (int) (t.getWidth() * scaling),
				(int) (t.getHeight() * scaling));

		drawTiles(g, settlement.getBlockedTilesToBuy(), true);
		drawTiles(g, settlement.getPermanentlyBlockedTiles(), false);
		drawTiles(g, settlement.getImpediments(), false);
		drawTiles(g, settlement.getPlacedBuildings(), true);
	}

	private void drawTiles(Graphics g, List<? extends Tile> tiles, boolean drawBorder) {
		tiles.forEach(t -> {
			g.setColor(t.getColor());
			g.fillRect((int) (t.getPosX() * scaling), (int) (t.getPosY() * scaling), (int) (t.getWidth() * scaling),
					(int) (t.getHeight() * scaling));
		});
		if (drawBorder) {
			g.setColor(Color.BLACK);
			tiles.forEach(t -> g.drawRect((int) (t.getPosX() * scaling), (int) (t.getPosY() * scaling),
					(int) (t.getWidth() * scaling), (int) (t.getHeight() * scaling)));
		}
	}
}
