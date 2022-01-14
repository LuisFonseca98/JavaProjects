package pee;

import java.util.Iterator;

public interface Solucao extends Iterable<PassoSolucao>{
	
	Iterator<PassoSolucao> iterator();
	
	int getDimensao();
	
	double getCusto();

}
