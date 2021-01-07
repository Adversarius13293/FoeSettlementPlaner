package adver.sarius.foe.settlement;

import java.util.List;

public class SettlementOptimizer {

	private List<Building> optimalPlacedBuildings;
	private Building optimalEmbassy;
	private int optimalCoinProduction = -1;
	private int globalMinX;
	private int globalMinY;
	private int globalMaxX;
	private int globalMaxY;

	public void testAllSetups(Settlement settlement) {

		setGlobalBounds(settlement);

		Building embassy = settlement.getEmbassy();
		settlement.getPlacedBuildings().add(embassy);
		// test all positions for embassy
		for (int x = globalMinX; x <= globalMaxX - embassy.getWidth(); x++) {
			for (int y = globalMinY; y <= globalMaxY - embassy.getHeight(); y++) {

				embassy.setPosition(x, y);
				if (settlement.doesTileFit(embassy)) {
					// recursively try all remaining buildings
					// TODO:

				}
			}
		}
		settlement.getPlacedBuildings().remove(embassy);
		// In the end set settlement buildings to found optimum
	}

	/**
	 * Find minimal and maximal positions something can be placed without being
	 * blocked. Since most parts of the settlement map won't be unlocked.
	 * 
	 * @param settlement
	 */
	private void setGlobalBounds(Settlement settlement) {
		Tile tester = new Tile(1, 1);
		for (int x = 0; x < settlement.getBaseLayout().getWidth(); x++) {
			for (int y = 0; y < settlement.getBaseLayout().getHeight(); y++) {
				tester.setPosition(x, y);
				if (settlement.doesTileFit(tester)) {
					globalMinX = Math.min(globalMinX, tester.getPosX());
					globalMinY = Math.min(globalMinY, tester.getPosY());
					globalMaxX = Math.max(globalMaxX, tester.getPosX());
					globalMaxY = Math.max(globalMaxY, tester.getPosY());
				}
			}
		}
	}

	// TODO: Optimization ideas:
	//
	// if every building has street connection, stop further checks. Either test
	// every coordinate. Or before coordinate check.
	private void tryNextBuilding(Settlement settlement, int startX, int startY) {

		if (settlement.getRemainingBuildings().isEmpty()) {
			// TODO: Finished.
		}

		Building building = settlement.getRemainingBuildings().get(0);
		settlement.getPlacedBuildings().add(building);
		settlement.getRemainingBuildings().remove(building);

		for (int x = startX; x <= globalMaxX - building.getWidth(); x++) {
			for (int y = startY; y <= globalMaxY - building.getHeight(); y++) {
				building.setPosition(x, y);

				if (settlement.doesTileFit(building) && settlement.doNecesssaryBuildingsHaveStreet()) {
					// if next building in the list is same dimension, they don't need to switch
					// positions. So the next buildings checks can start from current index.
					if(!settlement.getRemainingBuildings().isEmpty()) {
						Building nextBuilding = settlement.getRemainingBuildings().get(0);
						// assuming pre-ordered list.
						if (nextBuilding.getWidth() == building.getWidth()
								&& nextBuilding.getHeight() == building.getHeight()
								&& nextBuilding.getRoadPriority() == building.getRoadPriority()) {
							tryNextBuilding(settlement, x, y);
						} else {
							tryNextBuilding(settlement, globalMinX, globalMinY);
						}
					} else {
						// TODO: move recursion exit condition from the top to here instead? 
						tryNextBuilding(settlement, x, y);
					}
				}
				// if it does not fit, don't try other buildings. We only want all or nothing.
			}
		}
		settlement.getRemainingBuildings().add(0, building);
		settlement.getPlacedBuildings().remove(building);
	}
}