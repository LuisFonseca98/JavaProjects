package DLA;
import processing.core.PApplet;

public interface IProcessingApp {
	void setup(PApplet p);
	void draw(PApplet p, float dt);
	void keyPressed(PApplet p);
	void mousePressed(PApplet p);
}
