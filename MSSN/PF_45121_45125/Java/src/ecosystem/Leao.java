package ecosystem;

import java.util.ArrayList;
import java.util.Random;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

public class Leao extends Animal {
	
	private PImage img, img_ori;
	boolean stochastic;

	private int[] pontoAtracao;

	public Leao(PApplet p, PVector pos, int color) {
		super(p, pos, color);
		deathRate = WorldConstants.PREDATOR_DEATH_RATE;
		minBirthRate = WorldConstants.PREDATOR_BIRTH_RATE[0];
		maxBirthRate = WorldConstants.PREDATOR_BIRTH_RATE[1];
		birthFactor = WorldConstants.PREDATOR_BIRTH_FACTOR;
		energy = WorldConstants.INI_PREDATOR_ENERGY;
		energyToReproduce = WorldConstants.PREDATOR_ENERGY_TO_REPRODUCE;
		type = "Leao";

		img = WorldConstants.LION_IMG;
		img_ori = img.copy();

		Random rand = new Random(); 
		int z = rand.nextInt(WorldConstants.pontosAtracao.size());
		setPontoAtracao(WorldConstants.pontosAtracao.get(z));
	}

	public Leao(PApplet p, Leao leao)
	{
		super(p, leao);
		stochastic = WorldConstants.STOCHASTIC;
		Random rand = new Random(); 
		int z = rand.nextInt(WorldConstants.pontosAtracao.size());
		setPontoAtracao(WorldConstants.pontosAtracao.get(z));
		img = WorldConstants.LION_IMG;
		img_ori = img.copy();
	}

	@Override
	public Animal reproduce(float dt, boolean stochastic)
	{
		Animal deadLion = null;
		boolean reproduce = isItTimeToReproduce(dt, stochastic);
		if (reproduce) {
			energy = 0;
			//child = new Leao(p, this);
		}
		return deadLion;
	}

	@Override
	public void move(float dt)
	{
		super.move(dt);
	}

	@Override
	public void display() {

		radius = PApplet.map(energy, 0, 300, 3, 40);

		if (!stochastic) {
			img_ori.resize(2 * (int) radius, 2 * (int) radius);
		}

		p.image(img_ori, pos.x - img_ori.width / 2, pos.y - img_ori.height / 2);
	}

	public int[] getPontoAtracao() {
		return pontoAtracao;
	}

	public void setPontoAtracao(int[] pontoAtracao) {
		this.pontoAtracao = pontoAtracao;
	}

	@Override
	public void eat(Terrain terrain, ArrayList<Animal> allAnimals) {

		Patch patch = (Patch) terrain.getCell((int)pos.x, (int)pos.y);
		ArrayList<Animal> patchAnimals = patch.getAnimals();
		for(int i=0;i<patchAnimals.size();i++) {
			Animal a = patchAnimals.get(i);
			if (a.type == "Prey" || a.type == "FlockPrey" || a.type == "Gazela" || a.type == "Cacador") {
				patchAnimals.remove(a);
				allAnimals.remove(a);
				energy += WorldConstants.ENERGY_FROM_PREY;
				img_ori	= p.loadImage("images\\lion.png");
				break;
			}
		}

	}

	@Override
	protected void activity(Terrain terrain) {
		PVector pontoAtracao = terrain.getCellGrid(this.getPontoAtracao()[0], this.getPontoAtracao()[1]).getCenter();
		if(PApplet.dist(this.getPos().x, this.getPos().y, pontoAtracao.x, pontoAtracao.y) > 150) {
			PVector f = this.seek(new PVector(pontoAtracao.x, pontoAtracao.y)); 
			this.applyForce(f);
		}else {
			this.applyBehaviour(terrain);
		}
	}
}
