package ecosystem;

import java.util.ArrayList;
import processing.core.PApplet;
import processing.core.PVector;

/**
 * @author luisc
 *
 */
public class Population 
{
	private ArrayList<Animal> allAnimals;
	private boolean stochastic = WorldConstants.STOCHASTIC;
	private PApplet p;
	
	private int numberOfPreys;
	private int numberOfFishes;
	private int numberOfFlockPreys;
	private int numberOfPredators;
	private int numberOfCacadores;
	private int numberOfCrocs;
	private int numberOfPlane;
	private int numberOfGazelas;
	private int numberOfLions;
	private int numberOfCegonhas;


	public Population(PApplet p)
	{
		this.p = p;
		allAnimals = new ArrayList<Animal>();

		for(int i=0; i<2;i++)
		{
			// p.width - 230
			PVector pos = new PVector(p.random(350),p.random(306));
			Animal a = new Crocodilo(p, pos, WorldConstants.PREY_COLOR);
			allAnimals.add(a);
		}
		
		PVector pos = new PVector(450, 400);
		Animal a = new Plane(p, pos, WorldConstants.PREY_COLOR);
		allAnimals.add(a);
		
		
		for(int i=0; i<9;i++)
		{
			// p.width - 230
			pos = new PVector(924,594);
			a = new Cacador(p, pos, WorldConstants.PREY_COLOR);
			allAnimals.add(a);
		}
		
		for(int i=0; i<10;i++)
		{
			// p.width - 230
			pos = new PVector(724,110);
			a = new Fish(p, pos, WorldConstants.PREY_COLOR);
			allAnimals.add(a);
		}
		
		for(int i=0; i<12;i++)
		{
			// p.width - 230
			pos = new PVector(110,300);
			a = new Cegonha(p, pos, WorldConstants.PREY_COLOR);
			allAnimals.add(a);
		}
		
		for(int i=0; i<20;i++)
		{
			// p.width - 230
			pos = new PVector(800,650);
			a = new Gazela(p, pos, WorldConstants.PREY_COLOR);
			allAnimals.add(a);
		}
		
		for(int i=0; i<1;i++)
		{
			// p.width - 230
			pos = new PVector(700,864);
			a = new Leao(p, pos, WorldConstants.PREY_COLOR);
			allAnimals.add(a);
		}
		
	}
	
	
	
	public void saveFish() {
		for(int i=0; i<10;i++)
		{
			PVector pos = new PVector(724,110);
			Animal a = new Fish(p, pos, WorldConstants.PREY_COLOR);
			allAnimals.add(a);
		}
	}
	
	public void saveCegonha() {
		for(int i=0; i<8;i++)
		{
			PVector pos = new PVector(110,300);
			Animal a = new Cegonha(p, pos, WorldConstants.PREY_COLOR);
			allAnimals.add(a);
		}
	}
	
	public void saveCrocodilo() {
		for(int i=0; i<3;i++)
		{
			// p.width - 230
			PVector pos = new PVector(p.random(350),p.random(306));
			Animal a = new Crocodilo(p, pos, WorldConstants.PREY_COLOR);
			allAnimals.add(a);
		}
	}
	
	public void saveLion() {
		for(int i=0; i<2;i++)
		{
			// p.width - 230
			PVector pos = new PVector(700,864);
			Animal a = new Leao(p, pos, WorldConstants.PREY_COLOR);
			allAnimals.add(a);
		}
	}
	
	public void saveGazela() {
		for(int i=0; i<7;i++)
		{
			// p.width - 230
			PVector pos = new PVector(800,650);
			Animal a = new Gazela(p, pos, WorldConstants.PREY_COLOR);
			allAnimals.add(a);
		}
	}
	
	
	

	public void update(float dt, Terrain terrain)
	{
	
		countAnimals();
		
		immigration(dt);
		emigration(dt);
		
		reproduce(dt, stochastic);
		
		applyBehaviour(terrain);
		
		move(dt);
		obstaclesPenalty(terrain, dt);
		
		die(dt, stochastic);
		
		terrain.setAnimalLists(allAnimals);
		eat(terrain);
		terrain.clearAnimalLists();
		
	}
	
	private void applyBehaviour(Terrain terrain)
	{
		for (Animal a : allAnimals) 
		{
			a.activity(terrain);
		}
	}
	
	private void move(float dt)
	{
		for (Animal a : allAnimals) 
			a.move(dt);
	}
	
	private void obstaclesPenalty(Terrain terrain, float dt)
	{
		for (Animal a : allAnimals) 
			a.obstaclesPenalty(terrain, dt);
	}
	
	private void eat(Terrain terrain)
	{
		for(int i=allAnimals.size()-1;i>=0;i--) {
			Animal a = allAnimals.get(i);
			a.eat(terrain, allAnimals);
		}
	}

	private void die(float dt, boolean stochastic)
	{
		for(int i=allAnimals.size()-1;i>=0;i--) {
			Animal a = allAnimals.get(i);
			if (a.die(dt, stochastic)) {
				allAnimals.remove(a);
			}
		}
	}	

	private void reproduce(float dt, boolean stochastic)
	{
		for(int i=allAnimals.size()-1;i>=0;i--) {
			Animal a = allAnimals.get(i);
			Animal child = a.reproduce(dt, stochastic);
			if (child != null) allAnimals.add(child);	
		}
	}

	private void emigration(float dt)
	{ 
		int listSize = allAnimals.size();
		if (listSize == 0) return;

		int n = (int) (WorldConstants.EMIGRATION_FLOW*dt);
		float f = WorldConstants.EMIGRATION_FLOW*dt - n;

		int nn = Math.min(n,listSize);
		for (int i=0; i<nn; i++) {
			int rnd = (int)p.random(listSize--);
			allAnimals.remove(rnd);
		}

		if ((listSize > 0) && (p.random(1) < f))
		{
			int rnd = (int)p.random(listSize);
			allAnimals.remove(rnd);
		}
	}

	private void immigration(float dt)
	{
		int n = (int) (WorldConstants.IMMIGRATION_FLOW*dt);
		float f = WorldConstants.IMMIGRATION_FLOW*dt-n;

		for (int i=0; i<n; i++) {
			Animal a = getRandomBreed(0.5f);
			allAnimals.add(a);
		}

		if (p.random(1) < f)
		{
			Animal a = getRandomBreed(0.5f);
			allAnimals.add(a);
		}
	}

	private Animal getRandomBreed(float prob)
	{
		Animal a;
		PVector pos;
		if (p.random(1) < prob) 
		{
			pos = new PVector(0,p.random(p.height));
			a = new Prey(p, pos, WorldConstants.PREY_COLOR);
			a.setVel(new PVector(20,0));
		}
		else 
		{
			pos = new PVector(p.width-1,p.random(p.height));
			a = new Predator(p, pos, WorldConstants.PREDATOR_COLOR);
			a.setVel(new PVector(-20,0));
		}
		return a;
	}

	public void countAnimals()
	{
		numberOfPreys = 0;
		numberOfPredators = 0;
		numberOfFlockPreys = 0;
		numberOfFishes = 0;
		numberOfCacadores = 0;
		numberOfCrocs = 0;
		numberOfPlane = 0;
		numberOfGazelas = 0;
		numberOfLions = 0;
		numberOfCegonhas = 0;
		
		for (Animal a : allAnimals) 
		{
			switch(a.getType())
			{
			case "Prey":
				numberOfPreys++;
				break;
			case "FlockPrey":
				numberOfFlockPreys++;
				break;
			case "Fish":
				numberOfFishes++;
				break;
			case "Predator":
				numberOfPredators++;
				break;
			case "Cacador":
				numberOfCacadores++;
				break;
			case "Crocodilo":
				numberOfCrocs++;
				break;
			case "Plane":
				numberOfPlane++;
				break;	
			case "Gazela":
				numberOfGazelas++;
				break;
			case "Leao":
				numberOfLions++;
				break;
			case "Cegonha":
				numberOfCegonhas++;
				break;
			default:
				break;
			}
		}
	}

	public int getNumberOfPreys()
	{
		return numberOfPreys;
	}

	public int getNumberOfPredators()
	{
		return numberOfPredators;
	}

	public int getNumberOfFishes()
	{
		return numberOfFishes;
	}
	
	public int getNumberOfFlockPreys()
	{
		return numberOfFlockPreys;
	}
	
	public int getNumberOfPlanes()
	{
		return numberOfPlane;
	}
	
	public int getNumberOfCrocs()
	{
		return numberOfCrocs;
	}
	
	
	public int getNumberOfGazelas() {
		return numberOfGazelas;
	}

	public void setNumberOfGazelas(int numberOfGazelas) {
		this.numberOfGazelas = numberOfGazelas;
	}
	
	public int getNumberOfLions() {
		return numberOfLions;
	}

	public void setNumberOfLions(int numberOfLions) {
		this.numberOfLions = numberOfLions;
	}

	public void display()
	{
		for (Animal a : allAnimals) 
			a.display();
	}

	public int getNumberOfCacadores() {
		return numberOfCacadores;
	}

	public void setNumberOfCacadores(int numberOfCacadores) {
		this.numberOfCacadores = numberOfCacadores;
	}
	
	public int getNumberOfCegonhas() {
		return numberOfCegonhas;
	}

	public void setNumberOfCegonhas(int numberOfCegonhas) {
		this.numberOfCegonhas = numberOfCegonhas;
	}
	
	
}
