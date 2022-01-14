package TarefaB_JogoCaosRestricoes;
import processing.core.PApplet;

public class ProcessingSetup extends PApplet
{
	private static IProcessingApp app;
	private int lastUpdate;
	
	@Override
	public void settings()
	{
		
		size(800, 600);
	}
	
	@Override
	public void setup()
	{		
		app.setup(this);
		lastUpdate = millis();
	}
	
	@Override
	public void draw()
	{	
		int now = millis();
		float dt = (now - lastUpdate)/1000f;
		lastUpdate = now;
		app.draw(this, dt);
	}

	@Override
	public void mousePressed()
	{
		app.mousePressed(this);
	}
	
	@Override
	public void keyPressed()
	{
		app.keyPressed(this);
	}
		
	public static void main(String[] args) 
	{
		app = new ChaosGameApp();
		PApplet.main(ProcessingSetup.class);
	}
}
