package adver.sarius.foe.settlement;

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

	public Building(int height, int width, int x, int y, String name, int coins, int population, int diplo, int roadPrio) {
		super(height, width, x, y);
		this.name = name;
		this.coinProduction = coins;
		this.populationGain = population;
		this.diplomacy = diplo;
		this.roadPriority = roadPrio;
	}
	
	public Building(int height, int width, String name, int coins, int population, int diplo, int roadPrio) {
		this(height, width, 0, 0, name, coins, population, diplo, roadPrio);
	}

	public Building(Building building) {
		super(building);
	}
}