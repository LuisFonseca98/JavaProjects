package ecosystem;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;
import processing.core.PVector;

public class Plane extends Animal {
	
	private PImage img;
	private int mouseX, mouseY;

	protected Plane(PApplet p, PVector pos, int color) {
		super(p, pos, color);
		energy = WorldConstants.INI_PREDATOR_ENERGY;
		type = "Plane";
		img  = WorldConstants.PLANE_IMG.copy();
	}
	
	
	@Override
	public void eat(Terrain terrain, ArrayList<Animal> allAnimals) {
		Patch patch = (Patch) terrain.getCell((int) pos.x, (int) pos.y);
		ArrayList<Animal> patchAnimals = patch.getAnimals();
		for (int i = 0; i < patchAnimals.size(); i++) {
			Animal a = patchAnimals.get(i);
			if (a.type == "Fish") {
				patchAnimals.remove(a);
				allAnimals.remove(a);
				//energy += WorldConstants.ENERGY_FROM_PREY;
				value += a.value;
				break;
			}
		}
	}

	@Override
	public void applyBehaviour(Terrain t) {

		if (mouseX == 0 && mouseY == 0) {
			PVector inicial = new PVector(p.width / 2, p.height / 2);
			applyForce(seek(inicial));
		}

		if (p.mousePressed && p.mouseButton == PConstants.RIGHT) {
			getMouseValues(p);
		}

		PVector f = new PVector(mouseX, mouseY);

		applyForce(seek(f));

	}

	public void getMouseValues(PApplet p) {

		if (!(p.mouseX > (p.width - 180))) {
			this.mouseX = p.mouseX;
			this.mouseY = p.mouseY;
		}
	}

	@Override
	public void display() {
		p.pushMatrix();
		img.resize(150, 150);
		p.image(img, pos.x - img.width / 2, pos.y - img.height / 2);
		p.popMatrix();
	}

	@Override
	protected Animal reproduce(float dt, boolean stochastic) {
		return null;
	}


	@Override
	protected void activity(Terrain terrain) {
		this.applyBehaviour(terrain);
	}
	

}
