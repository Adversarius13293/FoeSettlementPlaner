package adver.sarius.foe.settlement;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Settlement {

	private Tile baseLayout;
	private List<Tile> blockedTilesToBuy;
	private List<Tile> permanentlyBlockedTiles;
	private List<Tile> impediments;
	private Building embassy;
	private List<Building> placedBuildings;
	private List<Building> remainingBuildings;

	// TODO: find some static solution for building types?
	private List<Building> availableBuildingTypes;

	private Settlement() {

	}

	public Tile getBaseLayout() {
		return baseLayout;
	}

	public List<Tile> getBlockedTilesToBuy() {
		return blockedTilesToBuy;
	}

	public List<Tile> getPermanentlyBlockedTiles() {
		return permanentlyBlockedTiles;
	}

	public Building getEmbassy() {
		return embassy;
	}

	public List<Building> getPlacedBuildings() {
		return placedBuildings;
	}

	public void setEmbassy(Building embassy) {
		this.embassy = embassy;
	}

	public void setPlacedBuildings(List<Building> placedBuildings) {
		this.placedBuildings = placedBuildings;
	}

	public List<Building> getAvailableBuildingTypes() {
		return availableBuildingTypes;
	}

	public List<Building> getRemainingBuildings() {
		return remainingBuildings;
	}

	public List<Tile> getImpediments() {
		return impediments;
	}

	private Set<Building> getBuildingsWithRoad() {
		// TODO: Optimization idea: cache last result until a building gets changed?
		List<Tile> checkedTiles = new ArrayList<>();
		Set<Building> connectedBuildings = new HashSet<>();
		int maxX = embassy.getWidth() + embassy.getPosX();
		int maxY = embassy.getHeight() + embassy.getPosY();
		Tile road = new Tile(1, 1);
		for (int x = embassy.getPosX() - 1; x <= maxX; x++) {
			for (int y = embassy.getPosY() - 1; y <= maxY; y++) {
				if ((x == embassy.getPosX() - 1 || x == maxX) && (y == embassy.getPosY() - 1 || y == maxY)) {
					// skip corners
					continue;
				}
				road.setPosition(x, y);
				// TODO: find more efficient way to only check border of embassy?
				if (embassy.contains(road) || !doesTileFit(road)) {
					continue;
				} else {
					checkedTiles.add(new Tile(road));
					findOtherRoads(road, checkedTiles, connectedBuildings);
				}
			}
		}
		return connectedBuildings;
	}

	/**
	 * Find all other roads connected to the last road tile. Connected buildings
	 * will be added to the set.
	 * 
	 * @param lastTile
	 *            the last valid 1x1 road tile checked.
	 * @param checkedTiles
	 *            list of already checked positions
	 * @param connectedBuildings
	 *            all buildings touching a valid road.
	 */
	private void findOtherRoads(Tile lastTile, List<Tile> checkedTiles, Set<Building> connectedBuildings) {
		// TODO: Iterate over coordinates instead of distances to only compute addition
		// once for performance?
		for (int x = -1; x < 2; x++) {
			for (int y = -1; y < 2; y++) {
				// TODO: find better way to only check the 4 directions?
				if (x * y == 0 && x + y != 0) {
					int newX = lastTile.getPosX() + x;
					int newY = lastTile.getPosY() + y;
					if (checkedTiles.stream().anyMatch(t -> t.getPosX() == newX && t.getPosY() == newY)) {
						// Already checked that spot.
						continue;
					} else {
						Tile currentTile = new Tile(1, 1, newX, newY);
						// TODO: put tiles and buildings in one list, and filter afterwards? Not sure if
						// it performs better. That way I could skip all tiles of an already checked
						// building.
						checkedTiles.add(currentTile);
						if (doesTileFit(currentTile)) {
							findOtherRoads(currentTile, checkedTiles, connectedBuildings);
						} else {
							Building building = getBuildingContaining(currentTile);
							if (building != null) {
								connectedBuildings.add(building);
							}
						}
					}
				}
			}
		}
	}

	/**
	 * @return coin production minus coin consumption of all buildings.
	 */
	public int getTtoalCoinOutput() {
		return remainingBuildings.stream().mapToInt(Building::getCoinProduction).sum()
				+ placedBuildings.stream().mapToInt(Building::getCoinProduction).sum()
				+ (placedBuildings.contains(embassy) ? 0 : embassy.getCoinProduction());
	}

	/**
	 * @return number of possible tiles for buildings.
	 */
	public int getTotalAvailableSurface() {
		Tile tile = new Tile(1, 1);
		int surface = 0;
		List<Building> tmp = this.placedBuildings;
		this.placedBuildings = Collections.emptyList();
		for (int x = baseLayout.getPosX(); x < baseLayout.getWidth(); x++) {
			for (int y = baseLayout.getPosY(); y < baseLayout.getHeight(); y++) {
				tile.setPosition(x, y);
				if (doesTileFit(tile)) {
					surface++;
				}
			}
		}
		this.placedBuildings = tmp;
		return surface;
	}

	/**
	 * @return surface of all buildings.
	 */
	public int getTotalBuildingSurface() {
		return remainingBuildings.stream().mapToInt(Building::getSurface).sum()
				+ placedBuildings.stream().mapToInt(Building::getSurface).sum()
				+ (placedBuildings.contains(embassy) ? 0 : embassy.getSurface());
	}

	private Building getBuildingContaining(Tile tile) {
		return placedBuildings.stream().filter(b -> b.contains(tile)).findFirst().orElse(null);
	}

	public boolean doNecesssaryBuildingsHaveRoad() {
		Set<Building> connected = getBuildingsWithRoad();
		return placedBuildings.stream().filter(b -> b.getRoadPriority() > 0).allMatch(b -> connected.contains(b));
	}

	public boolean doOptionalBuildingsHaveRoad() {
		Set<Building> connected = getBuildingsWithRoad();
		return placedBuildings.stream().filter(b -> b.getRoadPriority() == 0).allMatch(b -> connected.contains(b));
	}

	/**
	 * Rearranges the remaining buildings list. Order by roadPriority, size, coins,
	 * name.
	 */
	public void sortRemainingBuildings() {
		remainingBuildings.sort(Building::compareBuildingValueTo);
	}

	public int getCoinProduction() {
		Set<Building> withRoad = getBuildingsWithRoad();
		return placedBuildings.stream().mapToInt(b -> {
			if (withRoad.contains(b) || b.getRoadPriority() < 0) {
				return b.getCoinProduction();
			} else {
				return 0;
			}
		}).sum();
	}

	public boolean doesTileFit(Tile tile) {
		if (!baseLayout.contains(tile)) {
			return false;
		}

		// TODO: somehow check with only one list?
		if (blockedTilesToBuy.stream().anyMatch(t -> t.intersects(tile))) {
			return false;
		}
		if (permanentlyBlockedTiles.stream().anyMatch(t -> t.intersects(tile))) {
			return false;
		}
		if (impediments.stream().anyMatch(t -> t.intersects(tile))) {
			return false;
		}
		if (placedBuildings.stream().anyMatch(t -> {
			return t != tile && t.intersects(tile);
		})) {
			return false;
		}

		return true;
	}

	// TODO: Custom class for every settlement. With method for setup, all buildings, und plublic building members
	public static Settlement getNewFeudalJapan() {
		Settlement s = new Settlement();
		s.placedBuildings = new ArrayList<>();
		s.impediments = new ArrayList<>();
		s.remainingBuildings = new ArrayList<>();
		s.baseLayout = new Tile(6 * 4, 6 * 4, 0, 0, Color.GREEN);

		s.blockedTilesToBuy = new ArrayList<>();
//		s.blockedTilesToBuy.add(new Tile(4, 4, 0, 0, Color.LIGHT_GRAY));
//		s.blockedTilesToBuy.add(new Tile(4, 4, 4, 0, Color.LIGHT_GRAY));
//		s.blockedTilesToBuy.add(new Tile(4, 4, 8, 0, Color.LIGHT_GRAY));
		// s.blockedTilesToBuy.add(new Tile(4, 4, 12, 0)); // starting squares
		// s.blockedTilesToBuy.add(new Tile(4, 4, 16, 0));

		s.blockedTilesToBuy.add(new Tile(4, 4, 0, 4, Color.LIGHT_GRAY));
//		s.blockedTilesToBuy.add(new Tile(4, 4, 4, 4, Color.LIGHT_GRAY));
//		s.blockedTilesToBuy.add(new Tile(4, 4, 8, 4, Color.LIGHT_GRAY));
		// s.blockedTilesToBuy.add(new Tile(4, 4, 12, 4));
		// s.blockedTilesToBuy.add(new Tile(4, 4, 16, 4));
		s.blockedTilesToBuy.add(new Tile(4, 4, 20, 4, Color.LIGHT_GRAY));

		s.blockedTilesToBuy.add(new Tile(4, 4, 4, 8, Color.LIGHT_GRAY));
		s.blockedTilesToBuy.add(new Tile(4, 4, 8, 8, Color.LIGHT_GRAY));
		s.blockedTilesToBuy.add(new Tile(4, 4, 12, 8, Color.LIGHT_GRAY));
		s.blockedTilesToBuy.add(new Tile(4, 4, 16, 8, Color.LIGHT_GRAY));
		s.blockedTilesToBuy.add(new Tile(4, 4, 20, 8, Color.LIGHT_GRAY));

		s.blockedTilesToBuy.add(new Tile(4, 4, 12, 12, Color.LIGHT_GRAY));
		s.blockedTilesToBuy.add(new Tile(4, 4, 16, 12, Color.LIGHT_GRAY));
		s.blockedTilesToBuy.add(new Tile(4, 4, 20, 12, Color.LIGHT_GRAY));

		s.blockedTilesToBuy.add(new Tile(4, 4, 12, 16, Color.LIGHT_GRAY));
		s.blockedTilesToBuy.add(new Tile(4, 4, 16, 16, Color.LIGHT_GRAY));
		s.blockedTilesToBuy.add(new Tile(4, 4, 20, 16, Color.LIGHT_GRAY));

		s.blockedTilesToBuy.add(new Tile(4, 4, 16, 20, Color.LIGHT_GRAY));
		s.blockedTilesToBuy.add(new Tile(4, 4, 20, 20, Color.LIGHT_GRAY));

		s.permanentlyBlockedTiles = new ArrayList<>();
		s.permanentlyBlockedTiles.add(new Tile(4, 4, 0, 8, Color.BLACK));
		s.permanentlyBlockedTiles.add(new Tile(4, 4, 12, 20, Color.BLACK));
		s.permanentlyBlockedTiles.add(new Tile(3 * 4, 3 * 4, 0, 12, Color.BLACK));
		s.permanentlyBlockedTiles.add(new Tile(4, 4, 20, 0, Color.BLACK));

		s.availableBuildingTypes = new ArrayList<>();

		s.availableBuildingTypes.add(new Building(2, 2, 0, 0, Color.CYAN, "Gasshō-zukuri-Hütte", 125, 20, 0, 0));
		s.availableBuildingTypes.add(new Building(1, 1, 0, 0, Color.DARK_GRAY, "Tōrō", 0, 0, 6, -1));
		s.availableBuildingTypes.add(new Building(4, 3, 0, 0, Color.YELLOW, "Sojabohnenfeld", -1000, -36, 0, 1));
		// TODO: how to switch production times?
		s.availableBuildingTypes.add(new Building(2, 3, 0, 0, Color.GRAY, "Shinto-Schrein", 625, -26, 36, 1));
		s.availableBuildingTypes.add(new Building(3, 3, 0, 0, new Color(112, 67, 12), "Galerie", -1000, -45, 0, 1));
		s.availableBuildingTypes.add(new Building(4, 4, 0, 0, Color.ORANGE, "Waffenmeister", -1000, -24, 0, 1));
		s.availableBuildingTypes.add(new Building(2, 4, 0, 0, Color.BLUE, "Shoin-zukuri-Haus", 440 / 2, 68, 0, 0));
		s.availableBuildingTypes
				.add(new Building(3, 1, 0, 0, new Color(82, 9, 5), "Dekoriertes Torii-Tor", 0, 0, 36, -1));
		s.availableBuildingTypes
				.add(new Building(1, 3, 0, 0, new Color(138, 10, 59), "Heiliges Torii-Tor", 0, 0, 36, -1));
		s.availableBuildingTypes.add(
				new Building(3, 4, 0, 0, new Color(222, 203, 191), "Instrumentenproduktionsstätte", -1000, -36, 0, 1));
		s.availableBuildingTypes.add(new Building(3, 4, 0, 0, new Color(115, 178, 199), "Teehaus", 1629, -51, 144, 1));
		s.availableBuildingTypes
				.add(new Building(4, 4, 0, 0, new Color(4, 3, 94), "Shinden-Zukuri-Landgut", 1084 / 2, 230, 0, 0));
		s.availableBuildingTypes.add(new Building(3, 2, 0, 0, new Color(201, 6, 198), "Zen-Garten", 0, 0, 108, -1));
		s.availableBuildingTypes.add(new Building(5, 3, 0, 0, Color.RED, "Dojo", 2340, -63, 270, 1));

		s.embassy = new Building(3, 4, 0, 0, Color.MAGENTA, "Botschaft", 100 / 6, 0, 0, -1);
		return s;
	}
}