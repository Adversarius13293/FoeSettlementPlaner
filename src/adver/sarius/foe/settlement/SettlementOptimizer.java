package adver.sarius.foe.settlement;

import java.util.List;

public class SettlementOptimizer {
	
	private List<Building> optimalPlacedBuildings;
	private Building optimalEmbassy;
	private int optimalCoinProduction = -1;
	
	public void doSomething(Settlement settlement) {

		Building embassy = settlement.getEmbassy();
		settlement.getPlacedBuildings().add(embassy);
		// test all positions for embassy
		for (int x = 0; x < settlement.getBaseLayout().getWidth() - embassy.getWidth(); x++) {
			for (int y = 0; y < settlement.getBaseLayout().getHeight() - embassy.getHeight(); y++) {

				embassy.setPosition(x, y);
				if (settlement.doesBuildingFit(embassy)) {
					// recursively try all remaining buildings
					// TODO:

				}
			}
		}
		settlement.getPlacedBuildings().remove(embassy);
		// In the end set settlement buildings to found optimum
	}
	
	// TODO: Optimization ideas:
	// Check next building. if same size (same cost if street optional) then give koords of current building to next start.
	// if every building has street connection, stop further checks. Either test every coordinate. Or before coordinate check.
	// sort list of remaining buildings. first needed, then option road. same type buildings together.
	// Range for buildings to place not just by baseLayer size, rather by min and max available coordinates. Compute before embassy.
	private void tryNextBuilding(Settlement settlement) {
		
		if(settlement.getRemainingBuildings().isEmpty()) {
			// TODO: Finished.
		}
		
		Building building = settlement.getRemainingBuildings().get(0);
		settlement.getPlacedBuildings().add(building);
		settlement.getRemainingBuildings().remove(building);

		for (int x = 0; x < settlement.getBaseLayout().getWidth() - building.getWidth(); x++) {
			for (int y = 0; y < settlement.getBaseLayout().getHeight() - building.getHeight(); y++) {
				building.setPosition(x, y);
			
				if (settlement.doesBuildingFit(building) && settlement.doNecesssaryBuildingsHaveStreet()) {
					// TODO: Test next building
					
				}
				// if it does not fit, don't try other buildings. We only want all or nothing.
			}
		}
		settlement.getRemainingBuildings().add(0, building);
		settlement.getPlacedBuildings().remove(building);
	}
}