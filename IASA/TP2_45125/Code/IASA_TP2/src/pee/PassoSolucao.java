package pee;

import plantraj.modProb.Estado;
import plantraj.modProb.Operador;

public interface PassoSolucao{

	
	Estado getEstado();
	
	Operador getOperador();
	
	double getCusto();
}
