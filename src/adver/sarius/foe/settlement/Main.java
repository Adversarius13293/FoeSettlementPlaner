package adver.sarius.foe.settlement;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import adver.sarius.foe.settlement.tester.PerformanceTest;

public class Main {

	public static void main(String[] args) {

//		PerformanceTest.startPerformanceTests();

		// TODO: Input GUI
		// List of buildings on the right. Number selection
		// Show used Space and availabe space. coin output and worker
		// stones and building space? Button to activate, and click to toggle-select? 
		// Button to start computation
		// Stop button for current best?
		
		
		Settlement settlement = Settlement.getNewFeudalJapan();

		for (int i = 0; i < 0; i++) {
			settlement.getRemainingBuildings().add(new Building(settlement.getAvailableBuildingTypes().get(0))); // Haus
		}
		for (int i = 0; i < 0; i++) {
			settlement.getRemainingBuildings().add(new Building(settlement.getAvailableBuildingTypes().get(2))); // Soja
		}
		for (int i = 0; i < 5; i++) {
			settlement.getRemainingBuildings().add(new Building(settlement.getAvailableBuildingTypes().get(3))); // Shinto
		}
		for (int i = 0; i < 2; i++) {
			settlement.getRemainingBuildings().add(new Building(settlement.getAvailableBuildingTypes().get(4))); // Galerie
		}
		for (int i = 0; i < 3; i++) {
			settlement.getRemainingBuildings().add(new Building(settlement.getAvailableBuildingTypes().get(5))); // Waffen
		}
		for (int i = 0; i < 4; i++) {
			settlement.getRemainingBuildings().add(new Building(settlement.getAvailableBuildingTypes().get(6))); // Zukuri
		}
		
		// settlement.getRemainingBuildings().add(new
		// Building(settlement.getAvailableBuildingTypes().get(4))); // Shinto

		settlement.getImpediments().add(new Tile(1,1,11,7));
		settlement.getImpediments().add(new Tile(1,1,14,7));
		settlement.getImpediments().add(new Tile(1,1,14,6));

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