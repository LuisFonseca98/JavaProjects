package ecosystem;

import java.util.ArrayList;
import java.util.Random;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

public class Cacador extends Animal{

	private PImage img, img_ori;
	boolean stochastic;
	private float ene = WorldConstants.INI_PREDATOR_ENERGY;

	private int[] pontoAtracao;

	public Cacador(PApplet p, PVector pos, int color) {
		super(p, pos, color);
		deathRate = WorldConstants.PREDATOR_DEATH_RATE;
		minBirthRate = WorldConstants.PREDATOR_BIRTH_RATE[0];
		maxBirthRate = WorldConstants.PREDATOR_BIRTH_RATE[1];
		birthFactor = WorldConstants.PREDATOR_BIRTH_FACTOR;
		energy = WorldConstants.INI_PREDATOR_ENERGY;
		energyToReproduce = WorldConstants.PREDATOR_ENERGY_TO_REPRODUCE;
		type = "Cacador";

		img = WorldConstants.CACADOR_IMG;
		img_ori = img.copy();

		Random rand = new Random(); 
		int z = rand.nextInt(WorldConstants.pontosAtracao.size());
		setPontoAtracao(WorldConstants.pontosAtracao.get(z));
	}

	public Cacador(PApplet p, Cacador cacador)
	{
		super(p, cacador);
		stochastic = WorldConstants.STOCHASTIC;
		Random rand = new Random(); 
		int z = rand.nextInt(WorldConstants.pontosAtracao.size());
		setPontoAtracao(WorldConstants.pontosAtracao.get(z));
		img = WorldConstants.CACADOR_IMG;
		img_ori = img.copy();
	}

	@Override
	public Animal reproduce(float dt, boolean stochastic)
	{
		/*Animal child = null;
		boolean reproduce = isItTimeToReproduce(dt, stochastic);
		if (reproduce) {
			energy /= 2.;
			child = new Cacador(p, this);
		}*/
		return null;
	}

	@Override
	public void move(float dt)
	{
		super.move(dt);
		//energy -= dt;
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

	public void setPontoAtracao(int[] is) {
		this.pontoAtracao = is;
	}

	@Override
	public void eat(Terrain terrain, ArrayList<Animal> allAnimals) {

		Patch patch = (Patch) terrain.getCell((int)pos.x, (int)pos.y);
		ArrayList<Animal> patchAnimals = patch.getAnimals();
		for(int i=0;i<patchAnimals.size();i++) {
			Animal a = patchAnimals.get(i);
			if (a.type == "Prey" || a.type == "FlockPrey" || a.type == "Gazela") {
				patchAnimals.remove(a);
				allAnimals.remove(a);
				//energy += WorldConstants.ENERGY_FROM_PREY;
				img_ori	= p.loadImage("images\\cacador.png");
				break;
			}
		}

	}

	@Override
	protected void activity(Terrain terrain) {
		PVector pontoAtracao = terrain.getCellGrid(this.getPontoAtracao()[0], this.getPontoAtracao()[1]).getCenter();
		if(PApplet.dist(this.getPos().x, this.getPos().y, pontoAtracao.x, pontoAtracao.y) >  120) {
			PVector f = this.seek(new PVector(pontoAtracao.x, pontoAtracao.y)); 
			this.applyForce(f);
		}else {
			this.applyBehaviour(terrain);
		}
	}

}
