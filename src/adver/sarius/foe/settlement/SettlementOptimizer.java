package adver.sarius.foe.settlement;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SettlementOptimizer {

	private List<Building> optimalPlacedBuildings = new ArrayList<>();
	private Building optimalEmbassy;
	private int optimalCoinProduction = Integer.MIN_VALUE;
	private boolean stopRecursion = false;
	private int globalMinX = Integer.MAX_VALUE;
	private int globalMinY = Integer.MAX_VALUE;
	private int globalMaxX = Integer.MIN_VALUE;
	private int globalMaxY = Integer.MIN_VALUE;

	public List<Building> getOptimalPlacedBuildings() {
		return optimalPlacedBuildings;
	}

	public Building getOptimalEmbassy() {
		return optimalEmbassy;
	}

	public int getOptimalCoinProduction() {
		return optimalCoinProduction;
	}

	public void testAllSetups(Settlement settlement) {

		setGlobalBounds(settlement);

		Building embassy = settlement.getEmbassy();
		settlement.getPlacedBuildings().add(embassy);
		// test all positions for embassy
		for (int x = globalMinX; x <= globalMaxX - embassy.getWidth() + 1; x++) {
			for (int y = globalMinY; y <= globalMaxY - embassy.getHeight() + 1; y++) {
				embassy.setPosition(x, y);
				if (settlement.doesTileFit(embassy)) {
					tryNextBuilding(settlement, globalMinX, globalMinY);
				}
			}
		}
		settlement.getPlacedBuildings().remove(embassy);

		// In the end set settlement buildings to found optimum
		settlement.setEmbassy(optimalEmbassy);
		settlement.setPlacedBuildings(optimalPlacedBuildings);
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

	private void tryNextBuilding(Settlement settlement, int startX, int startY) {

		if (settlement.getRemainingBuildings().isEmpty()) {
			// Should only happen, if it has no buildings already from the beginning?
			return;
		}

		Building building = settlement.getRemainingBuildings().get(0);
		settlement.getPlacedBuildings().add(building);
		settlement.getRemainingBuildings().remove(building);

		for (int x = startX; x <= globalMaxX - building.getWidth() + 1; x++) {
			if (stopRecursion) {
				// TODO: test different stop positions with performance.
				break;
			}
			for (int y = startY; y <= globalMaxY - building.getHeight() + 1; y++) {
				// TODO: Remove this line for performance, and always start at MinY?
				startY = globalMinY;
				building.setPosition(x, y);
				if (settlement.doesTileFit(building) && settlement.doNecesssaryBuildingsHaveRoad()) {
					// if next building in the list is same dimension, they don't need to switch
					// positions. So the next buildings checks can start from current index.
					if (!settlement.getRemainingBuildings().isEmpty()) {
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
						int coinProd = settlement.getCoinProduction();
						if (coinProd > this.optimalCoinProduction) {
							this.optimalCoinProduction = coinProd;
							this.optimalEmbassy = new Building(settlement.getEmbassy());
							this.optimalPlacedBuildings = settlement.getPlacedBuildings().stream().map(Building::new)
									.collect(Collectors.toList());
						}
						// required roads are guaranteed at this point.
						if (settlement.doOptionalBuildingsHaveRoad()) {
							// if optional roads are also true, there is no need to test any more setups.
							// also in this case the coin production has to be optimal.
							this.stopRecursion = true;
						}
					}
				}
				// if it does not fit, don't try other buildings. We only want all or nothing.
			}
		}
		settlement.getRemainingBuildings().add(0, building);
		settlement.getPlacedBuildings().remove(building);
	}
}