package processing;
import ecosystem.EcoSystemApp;
import processing.core.PApplet;

public class ProcessingSetup extends PApplet {

	private static IProcessingApp processingApp; 
	private float lastUpdateTime;
	
	@Override
    public void settings(){
		fullScreen(JAVA2D);
		
	}

    @Override
    public void setup(){
    	processingApp.setup(this);
    	lastUpdateTime = 0;
    }

    @Override
    public void draw(){
    	float now = millis();
    	float dt = (float) ((now - lastUpdateTime) / 1000.);
    	processingApp.draw(this, dt);
		lastUpdateTime = now;
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
    	
    	processingApp = new EcoSystemApp();
    	
        PApplet.main(ProcessingSetup.class);
    }
}