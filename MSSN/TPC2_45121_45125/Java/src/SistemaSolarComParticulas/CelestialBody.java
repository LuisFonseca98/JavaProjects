package SistemaSolarComParticulas;

import processing.core.PVector;

public class CelestialBody extends Body{
	public static final double G = 1e-4f;
	//public static final double G = 6.67e-11f;
	
	public CelestialBody(PVector pos, PVector vel, float mass, 
			float radius, int color){
		super(pos, vel, mass, radius, color);
	}
	
	public PVector attraction(Mover m){
	    PVector r = PVector.sub(pos, m.pos);
	    float dist = r.mag();
	    float strength = (float)(G*mass*m.mass/Math.pow(dist, 2));
	    return (r.normalize().mult(strength));
	}
}
