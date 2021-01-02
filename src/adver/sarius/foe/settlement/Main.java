package adver.sarius.foe.settlement;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class Main {

	public static void main(String[] args) {
		// TODO:

		// Place embassy first.
		// place buildings at every possible position.
		// check if not obstructed by map, not purchased tiles, stones, embassy or other
		// buildings.

		// after every building, compute all buildings in range of embassy.
		// check all buildings, if they are in reach if needed.

		Settlement settlement = Settlement.getNewFeudalJapan();
		
		
		JFrame mainFrame = new JFrame("Kulturelle Siedlung");
		mainFrame.setLayout(new BorderLayout());
		mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		SettlementDrawer drawer = new SettlementDrawer(settlement);
		drawer.setScaling(20);

		mainFrame.add(drawer, BorderLayout.CENTER);

		mainFrame.setVisible(true);
		mainFrame.pack();
		// mainFrame.setSize(new Dimension(800, 800));
		mainFrame.setExtendedState(mainFrame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
	}

}