package ecosystem;

import processing.core.PApplet;

public class World {
	public Terrain terrain;
	public Population population;
	private RuralManager ruralManager;

	public World(PApplet p) {
		terrain = new Terrain(p);
		terrain.setStateColors(WorldConstants.TERRAIN_COLORS);

		for (int i = 0; i < 1; i++)
			terrain.majorityRule();

		terrain.setWaterStates();

		terrain.setRuralStates();

		terrain.setMarketStates();

		terrain.setRocksStates();

		terrain.setRuralStates();
		
		terrain.setGrassStates();
		
		terrain.setObstacles();

		population = new Population(p);

		ruralManager = new RuralManager();

		
		for(int i=0; i<20; i++) {
			ruralManager.buildMarket(terrain);
		}
		
		
	}


	public void update(float dt) {
		terrain.regenerateCellWithFood();

		terrain.clickMarket();
		
		if(terrain.getnMarketsToBuild() > 0) {
			for(int i=0; i < terrain.getnMarketsToBuild(); i++) {
				ruralManager.buildMarket(terrain);
				terrain.setnMarketsToBuild(terrain.getnMarketsToBuild()-1);
			}
		}

		ruralManager.setPopulation(population.getNumberOfCacadores());

		ruralManager.update(dt, terrain);

		population.update(dt, terrain);

	}

	public void display() {
		terrain.display();
		population.display();
	}

	public Terrain getTerrain() {
		return terrain;
	}

	public void setTerrain(Terrain terrain) {
		this.terrain = terrain;
	}
	
	public RuralManager getRuralManager() {
		return ruralManager;
	}

	public void setRuralManager(RuralManager ruralManager) {
		this.ruralManager = ruralManager;
	}
}
