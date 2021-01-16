package adver.sarius.foe.settlement;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import adver.sarius.foe.settlement.tester.PerformanceTest;

public class Main {

	public static void main(String[] args) {

		PerformanceTest.startPerformanceTests();

		// TODO: Input GUI
		Settlement settlement = Settlement.getNewFeudalJapan();

		for (int i = 0; i < 4; i++) {
			settlement.getRemainingBuildings().add(new Building(settlement.getAvailableBuildingTypes().get(0))); // Haus
		}
		for (int i = 0; i < 1; i++) {
			settlement.getRemainingBuildings().add(new Building(settlement.getAvailableBuildingTypes().get(2))); // Soja
		}
		// settlement.getRemainingBuildings().add(new
		// Building(settlement.getAvailableBuildingTypes().get(4))); // Shinto

		// settlement.getImpediments().add(new Tile(1,1,18,4));

		new SettlementOptimizer().testSetups(settlement);

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