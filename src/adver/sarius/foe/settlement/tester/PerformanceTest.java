package adver.sarius.foe.settlement.tester;

import java.util.function.Supplier;

import adver.sarius.foe.settlement.Building;
import adver.sarius.foe.settlement.Settlement;
import adver.sarius.foe.settlement.SettlementOptimizer;

public class PerformanceTest {

	private PerformanceTest() {

	}

	// Test different buildings and layouts multiple times
	public static void startPerformanceTests() {

		// starting setup with impediment
		// starting setup without impediment
		// full houses
		// endgame setup
		// endgame setup not fitting
		// a lot of decorations

		// 100 iterations in average time: 2 ms
		testSettlementSetup(1000, "Easy_Starter", PerformanceTest::getStarterSetupEasyRoads);
		// 1 iterations in average time: 175997 ms
		testSettlementSetup(1, "Hard_Starter", PerformanceTest::testStarterSetupNotFullyRoads);
		// 2 iterations in average time: 142314 ms
		testSettlementSetup(2, "Impossible_Starter", PerformanceTest::testStarterSetupNotPossibleFit);
	}

	private static void testSettlementSetup(int iterations, String logName, Supplier<Settlement> settlementSupplier) {
		Settlement settlement = settlementSupplier.get();
		SettlementOptimizer opti = new SettlementOptimizer();
		long startTime = System.currentTimeMillis();
		for (int i = 0; i < iterations; i++) {
			opti.testAllSetups(settlement);
		}
		long duration = System.currentTimeMillis() - startTime;
		// TODO: better log format for easier copy.
		System.out.println("Finished testing of '" + logName + "' with " + iterations + " iterations in average time: "
				+ duration / iterations + " ms. Working Setup found: " + (opti.getOptimalEmbassy() != null));
	}

	private static Settlement getStarterSetupEasyRoads() {
		Settlement settlement = Settlement.getNewFeudalJapan();

		settlement.getRemainingBuildings().add(new Building(settlement.getAvailableBuildingTypes().get(0))); // Haus
		settlement.getRemainingBuildings().add(new Building(settlement.getAvailableBuildingTypes().get(0))); // Haus
		settlement.getRemainingBuildings().add(new Building(settlement.getAvailableBuildingTypes().get(0))); // Haus
		settlement.getRemainingBuildings().add(new Building(settlement.getAvailableBuildingTypes().get(0))); // Haus
		settlement.getRemainingBuildings().add(new Building(settlement.getAvailableBuildingTypes().get(2))); // Soja
		settlement.getRemainingBuildings().add(new Building(settlement.getAvailableBuildingTypes().get(2))); // Soja
		return settlement;
	}

	private static Settlement testStarterSetupNotFullyRoads() {
		Settlement settlement = Settlement.getNewFeudalJapan();
		settlement.getRemainingBuildings().add(new Building(settlement.getAvailableBuildingTypes().get(0))); // Haus
		settlement.getRemainingBuildings().add(new Building(settlement.getAvailableBuildingTypes().get(0))); // Haus
		settlement.getRemainingBuildings().add(new Building(settlement.getAvailableBuildingTypes().get(0))); // Haus
		settlement.getRemainingBuildings().add(new Building(settlement.getAvailableBuildingTypes().get(0))); // Haus
		settlement.getRemainingBuildings().add(new Building(settlement.getAvailableBuildingTypes().get(0))); // Haus
		settlement.getRemainingBuildings().add(new Building(settlement.getAvailableBuildingTypes().get(2))); // Soja
		settlement.getRemainingBuildings().add(new Building(settlement.getAvailableBuildingTypes().get(2))); // Soja
		return settlement;
	}
	
	private static Settlement testStarterSetupNotPossibleFit() {
		Settlement settlement = Settlement.getNewFeudalJapan();
		settlement.getRemainingBuildings().add(new Building(settlement.getAvailableBuildingTypes().get(0))); // Haus
		settlement.getRemainingBuildings().add(new Building(settlement.getAvailableBuildingTypes().get(0))); // Haus
		settlement.getRemainingBuildings().add(new Building(settlement.getAvailableBuildingTypes().get(0))); // Haus
		settlement.getRemainingBuildings().add(new Building(settlement.getAvailableBuildingTypes().get(0))); // Haus
		settlement.getRemainingBuildings().add(new Building(settlement.getAvailableBuildingTypes().get(0))); // Haus
		settlement.getRemainingBuildings().add(new Building(settlement.getAvailableBuildingTypes().get(0))); // Haus
		settlement.getRemainingBuildings().add(new Building(settlement.getAvailableBuildingTypes().get(2))); // Soja
		settlement.getRemainingBuildings().add(new Building(settlement.getAvailableBuildingTypes().get(2))); // Soja
		return settlement;
	}
}