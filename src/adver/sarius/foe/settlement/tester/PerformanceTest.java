package adver.sarius.foe.settlement.tester;

import java.time.LocalDateTime;
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

		// 2021-01-16T16:54:27.352 Easy_Starter(3000x): true. Average time: 0 ms.
		testSettlementSetup(3000, "Easy_Starter", PerformanceTest::getStarterSetupEasyRoads);
		// 2021-01-16T17:14:01.023 Easy_Deco(3000x): true. Avg time: 0 ms
		testSettlementSetup(3000, "Easy_Deco", PerformanceTest::testSmallFullDeco);
		// 2021-01-16T16:54:40.072 Impossible_Soja(100x): false. Average time: 127 ms.
		// 2021-01-16T16:56:22.928 Impossible_Soja(100x): false. Average time: 182 ms.
		// 2021-01-16T17:14:16.709 Impossible_Soja(100x): false. Avg time: 156 ms
		// 2021-01-16T17:32:42.912 Impossible_Soja(100x): false. Avg time: 120 ms
		// 2021-01-16T19:02:02.404 Impossible_Soja(100x): false. Avg time: 118 ms
		// first road from ebassy with copy tile
		// 2021-01-16T21:09:03.895 Impossible_Soja(100x): false. Avg time: 117 ms
		testSettlementSetup(100, "Impossible_Soja", PerformanceTest::testSmallNotPossibleSoja);
		// 1 iterations in average time: 175997 ms
		// 2021-01-16T16:59:11.146 Hard_Starter(1x): true. Average time: 168209 ms.
		// 2021-01-16T17:17:09.990 Hard_Starter(1x): true. Avg time: 173280 ms
		// 2021-01-16T17:35:41.016 Hard_Starter(1x): true. Avg time: 178103 ms
		// 2021-01-16T19:04:50.596 Hard_Starter(1x): true. Avg time: 168190 ms
		// first road from ebassy with copy tile
//		2021-01-16T21:11:46.557 Hard_Starter(1x): true. Avg time: 162662 ms
		testSettlementSetup(1, "Hard_Starter", PerformanceTest::testStarterSetupNotFullyRoads);
		// 2 iterations in average time: 142314 ms
		// 2021-01-16T17:03:51.440 Impossible_Starter(1x): true. Average time: 280293
		// 2021-01-16T17:21:38.448 Impossible_Starter(1x): true. Avg time: 268458 ms
		// 2021-01-16T17:41:31.663 Impossible_Starter(1x): true. Avg time: 350646 ms
		// 2021-01-16T19:09:09.017 Impossible_Starter(1x): true. Avg time: 258418 ms
		// first road from ebassy with copy tile
//		2021-01-16T21:16:24.286 Impossible_Starter(1x): true. Avg time: 277728 ms
		testSettlementSetup(1, "Impossible_Starter", PerformanceTest::testStarterSetupNotPossibleFit);
	}

	private static void testSettlementSetup(int iterations, String logName, Supplier<Settlement> settlementSupplier) {
		SettlementOptimizer opti = new SettlementOptimizer();
		long startTime = System.currentTimeMillis();
		for (int i = 0; i < iterations; i++) {
			opti.testSetups(settlementSupplier.get());
		}
		long duration = System.currentTimeMillis() - startTime;
		System.out.println(LocalDateTime.now() + " " + logName + "(" + iterations + "x): "
				+ (opti.getOptimalEmbassy() != null) + ". Avg time: " + duration / iterations + " ms");
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

	private static Settlement testSmallNotPossibleSoja() {
		Settlement settlement = Settlement.getNewFeudalJapan();
		settlement.getRemainingBuildings().add(new Building(settlement.getAvailableBuildingTypes().get(2))); // Soja
		settlement.getRemainingBuildings().add(new Building(settlement.getAvailableBuildingTypes().get(2))); // Soja
		settlement.getRemainingBuildings().add(new Building(settlement.getAvailableBuildingTypes().get(2))); // Soja
		settlement.getRemainingBuildings().add(new Building(settlement.getAvailableBuildingTypes().get(2))); // Soja
		return settlement;
	}

	private static Settlement testSmallFullDeco() {
		Settlement settlement = Settlement.getNewFeudalJapan();
		for (int i = 0; i < 52; i++) {
			settlement.getRemainingBuildings().add(new Building(settlement.getAvailableBuildingTypes().get(1))); // Toro
		}
		return settlement;
	}
}