package adver.sarius.foe.settlement;

import java.awt.Color;

public class Building extends Tile {

	private String name;
	/**
	 * Positive for production, negative for consumption.
	 */
	private int coinProduction;
	/**
	 * Positive for gain, negative for use.
	 */
	private int populationGain;
	/**
	 * Value <0 means no road needed. Value =0 can use road. Value >0 always needs
	 * road.
	 */
	private int diplomacy;
	private int roadPriority;

	public Building(int height, int width, int x, int y, Color color, String name, int coins, int population, int diplo,
			int roadPrio) {
		super(height, width, x, y, color);
		this.name = name;
		this.coinProduction = coins;
		this.populationGain = population;
		this.diplomacy = diplo;
		this.roadPriority = roadPrio;
	}

	public Building(int height, int width, String name, int coins, int population, int diplo, int roadPrio) {
		this(height, width, 0, 0, Tile.randomColor(), name, coins, population, diplo, roadPrio);
	}

	public Building(Building building) {
		super(building);
	}

	public String getName() {
		return name;
	}

	public int getCoinProduction() {
		return coinProduction;
	}

	public int getPopulationGain() {
		return populationGain;
	}

	public int getDiplomacy() {
		return diplomacy;
	}

	public int getRoadPriority() {
		return roadPriority;
	}

	/**
	 * Compares this building values: roadPriority, size, coins, name.
	 * 
	 * @param building2
	 *            building to compare against.
	 * @return positive value if the this object is higher priority, 0 if they are
	 *         equal, negative number if the second object is higher.
	 */
	public int compareBuildingValueTo(Building building2) {
		// TODO: Remove method? Because it is a very specific ordering. Use Comparator?
		if (this.getRoadPriority() - building2.getRoadPriority() != 0) {
			return this.getRoadPriority() - building2.getRoadPriority();
		} else if (this.getSurface() - building2.getSurface() != 0) {
			return this.getSurface() - building2.getSurface();
		} else if (this.getPosX() - building2.getPosX() != 0) {
			return this.getPosX() - building2.getPosX();
		} else if (this.getCoinProduction() - building2.getCoinProduction() != 0) {
			return this.getCoinProduction() - building2.getCoinProduction();
		} else {
			return this.getName().compareTo(building2.getName());
		}
	}
}