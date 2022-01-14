package ecosystem;

import java.util.ArrayList;
import java.util.Random;

import ca.GridConstants;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

public class Gazela extends Animal{
	
	private int glacierX;
	private int glacierY;
	private int[] pontoAtracao;
	
	private PImage img, img_ori;
	boolean stochastic;
	
	public Gazela(PApplet p, PVector pos, int color) {
		super(p, pos, color);
		deathRate = WorldConstants.PREDATOR_DEATH_RATE;
		minBirthRate = WorldConstants.PREDATOR_BIRTH_RATE[0];
		maxBirthRate = WorldConstants.PREDATOR_BIRTH_RATE[1];
		birthFactor = WorldConstants.PREDATOR_BIRTH_FACTOR;
		energy = WorldConstants.INI_PREDATOR_ENERGY;
		energyToReproduce = 100f;
		type = "Gazela";
		
		glacierX = 4;
		glacierY = 4;
		
		img = WorldConstants.GAZELA_IMG;
		img_ori = img.copy();
		
		Random rand = new Random(); 
		int z = rand.nextInt(WorldConstants.pontosAtracao.size());
		setPontoAtracao(WorldConstants.pontosAtracao.get(z));
	}

	public Gazela(PApplet p, Gazela gazela)
	{
		super(p, gazela);
		stochastic = WorldConstants.STOCHASTIC;
		img = WorldConstants.GAZELA_IMG;
		Random rand = new Random(); 
		int z = rand.nextInt(WorldConstants.pontosAtracao.size());
		setPontoAtracao(WorldConstants.pontosAtracao.get(z));
		img_ori = img.copy();
	}
	
	@Override
	public Animal reproduce(float dt, boolean stochastic)
	{
		Animal child = null;
		boolean reproduce = isItTimeToReproduce(dt, stochastic);
		if (reproduce) {
			energy /= 2.;
			child = new Gazela(p, this);
		}
		return child;
	}
	
	@Override
	public void eat(Terrain terrain, ArrayList<Animal> allAnimals) {
		Patch patch = (Patch) terrain.getCell((int) pos.x, (int) pos.y);
		if (patch.getState() == GridConstants.State.GRASSFOOD.ordinal()) {
			energy += WorldConstants.ENERGY_FROM_GRASS;
			img_ori	= p.loadImage("images\\gazela.png");
			patch.setFertileRural();
		}
	}

	@Override
	public void display() {
		
		radius = PApplet.map(energy, 0, 200, 3, 30);

		if (radius <= 0)
			radius = 1;

		if (!stochastic) {
			img_ori.resize(2 * (int) radius, 2 * (int) radius);
		}

		p.image(img_ori, pos.x - img_ori.width / 2, pos.y - img_ori.height / 2);
	}
	
	public int[] getPontoAtracao() {
		return pontoAtracao;
	}

	public void setPontoAtracao(int[] is) {
		this.pontoAtracao = is;
	}

	@Override
	protected void activity(Terrain terrain) {
		PVector pontoAtracao = terrain.getCellGrid(this.getPontoAtracao()[0], this.getPontoAtracao()[1]).getCenter();
		
		if(PApplet.dist(this.getPos().x, this.getPos().y, pontoAtracao.x, pontoAtracao.y) >  200) {
			PVector f = this.seek(new PVector(pontoAtracao.x, pontoAtracao.y)); 
			this.applyForce(f);
		}else {
			this.applyBehaviour(terrain);
		}
	}
	
	
}
