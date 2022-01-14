package DLA;

import java.util.ArrayList;

import DLA.Walker.State;
import processing.core.PApplet;
import processing.core.PVector;

public class DLA implements IProcessingApp {

	private ArrayList<Walker> walkers;
	private int NUM_WALKERS = 200;
	private int NUM_FRAMES = 100;
	

	@Override
	public void setup(PApplet p) {
		walkers = new ArrayList<Walker>();
		Walker w = new Walker(p, new PVector(p.width/2,p.height/2));
		
		walkers.add(w);
		
		for(int i = 0; i < NUM_WALKERS;i++) {
			w = new Walker(p);
			walkers.add(w);
		}
	}

	@Override
	public void draw(PApplet p, float dt) {

		p.background(190);

		for(int i = 0; i < NUM_FRAMES;i++) 
			for(Walker w:walkers)
				if(w.getState() == State.WANDER) {
					w.wander(p);
					w.updateState(p,walkers);
				}else if(w.getState() == State.STOPPED) {
					new Walker(p, new PVector(p.width/2,p.height/2));
				}
		
					
		for(Walker w:walkers) 
			w.display(p);

		System.out.println("Stopped: " + Walker.num_stopped + " Wander " +  Walker.num_wander);
		
	}



	@Override
	public void keyPressed(PApplet p) {

	}

	@Override
	public void mousePressed(PApplet p) {

	}

}
