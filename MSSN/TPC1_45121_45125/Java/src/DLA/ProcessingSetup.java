package DLA;

import processing.core.PApplet;

public class ProcessingSetup extends PApplet{

	private static IProcessingApp processingApp; 
	private PApplet parent;
	private int lastUpdateTime = 0;
	
	@Override
    public void settings(){
        size(1024,768);
    }

    @Override
    public void setup(){
    	parent = this;
    	processingApp.setup(this);
    }

    @Override
    public void draw(){
    	float dt = (float) ((millis() - lastUpdateTime) / 1000.);
		processingApp.draw(parent, dt);
		lastUpdateTime = millis();
    }
    
    @Override
	public void mousePressed() {
		processingApp.mousePressed(this);
	}

	@Override
	public void keyPressed() {
		processingApp.keyPressed(this);
	}

    public static void main(String[] args) {
    	processingApp = new DLA();
    	PApplet.main(ProcessingSetup.class);
    }
}