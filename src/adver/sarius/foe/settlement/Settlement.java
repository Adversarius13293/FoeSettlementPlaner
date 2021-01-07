package adver.sarius.foe.settlement;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Settlement {

	private Tile baseLayout;
	private List<Tile> blockedTilesToBuy;
	private List<Tile> permanentlyBlockedTiles;
	private List<Tile> impediments;
	private Building embassy;
	private List<Building> placedBuildings;
	private List<Building> remainingBuildings;

	// TODO: find some static solution for building types?
	private List<Building> availableBuildings;

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

	public List<Building> getAvailableBuildings() {
		return availableBuildings;
	}

	public List<Building> getRemainingBuildings() {
		return remainingBuildings;
	}

	public List<Tile> getImpediments() {
		return impediments;
	}

	public List<Building> getBuildingsWithRoad(){
		// TODO: starting from embassy, recursively find everything with a valid path.
		return null;
	}
	
	public boolean doNecesssaryBuildingsHaveStreet() {
		// TODO: Better name. And logic. checkRoadRequirements
		return false;
	}
	
	
	public int getCoinProduction() {
		// TODO: Everything that needs or may need a road and has a road. And everything that does not need a road.
		return -1;
	}
	
	public boolean doesBuildingFit(Building building) {
		if (!baseLayout.contains(building)) {
			return false;
		}
		
		// TODO: somehow check with only one list?
		if(blockedTilesToBuy.stream().anyMatch(t -> t.intersects(building))) {
			return false;
		}
		if(permanentlyBlockedTiles.stream().anyMatch(t -> t.intersects(building))) {
			return false;
		}
		if(impediments.stream().anyMatch(t -> t.intersects(building))) {
			return false;
		}
		if(placedBuildings.stream().anyMatch(t -> {return t != building && t.intersects(building);})) {
			return false;
		}
		
		return true;
	}

	public static Settlement getNewFeudalJapan() {
		Settlement s = new Settlement();
		s.placedBuildings = new ArrayList<>();
		s.impediments = new ArrayList<>();
		s.remainingBuildings = new ArrayList<>();
		s.baseLayout = new Tile(6 * 4, 6 * 4, 0, 0, Color.GREEN);

		s.blockedTilesToBuy = new ArrayList<>();
		s.blockedTilesToBuy.add(new Tile(4, 4, 0, 0, Color.LIGHT_GRAY));
		s.blockedTilesToBuy.add(new Tile(4, 4, 4, 0, Color.LIGHT_GRAY));
		s.blockedTilesToBuy.add(new Tile(4, 4, 8, 0, Color.LIGHT_GRAY));
		// s.blockedTilesToBuy.add(new Tile(4, 4, 12, 0));
		// s.blockedTilesToBuy.add(new Tile(4, 4, 16, 0));

		s.blockedTilesToBuy.add(new Tile(4, 4, 0, 4, Color.LIGHT_GRAY));
		s.blockedTilesToBuy.add(new Tile(4, 4, 4, 4, Color.LIGHT_GRAY));
		s.blockedTilesToBuy.add(new Tile(4, 4, 8, 4, Color.LIGHT_GRAY));
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

		s.availableBuildings = new ArrayList<>();

		s.availableBuildings.add(new Building(2, 2, "Gasshō-zukuri-Hütte", 125, 20, 0, 0));
		s.availableBuildings.add(new Building(1, 1, "Tōrō", 0, 0, 6, -1));
		s.availableBuildings.add(new Building(4, 3, "Sojabohnenfeld", -1000, -36, 0, 1));
		// TODO: how to switch production times?
		s.availableBuildings.add(new Building(2, 3, "Shinto-Schrein", 625, -26, 36, 1));
		s.availableBuildings.add(new Building(3, 3, "Galerie", -1000, -45, 0, 1));
		s.availableBuildings.add(new Building(4, 4, "Waffenmeister", -1000, -24, 0, 1));
		s.availableBuildings.add(new Building(2, 4, "Shoin-zukuri-Haus", 440 / 2, 68, 0, 0));
		s.availableBuildings.add(new Building(3, 1, "Dekoriertes Torii-Tor", 0, 0, 36, -1));
		s.availableBuildings.add(new Building(1, 3, "Heiliges Torii-Tor", 0, 0, 36, -1));
		s.availableBuildings.add(new Building(3, 4, "Instrumentenproduktionsstätte", -1000, -36, 0, 1));
		s.availableBuildings.add(new Building(3, 4, "Teehaus", 1629, -51, 144, 1));
		s.availableBuildings.add(new Building(4, 4, "Shinden-Zukuri-Landgut", 1084 / 2, 230, 0, 0));
		s.availableBuildings.add(new Building(3, 2, "Zen-Garten", 0, 0, 108, -1));
		s.availableBuildings.add(new Building(5, 3, "Dojo", 2340, -63, 270, 1));

		s.embassy = new Building(3, 4, "Botschaft", 100 / 6, 0, 0, 1);
		return s;
	}
}