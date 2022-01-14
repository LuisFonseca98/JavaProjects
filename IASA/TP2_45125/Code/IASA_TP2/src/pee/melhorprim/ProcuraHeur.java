package pee.melhorprim;

import pee.Solucao;
import plantraj.modProb.ProblemaHeur;

public interface ProcuraHeur {
	
	Solucao resolver(ProblemaHeur problema);
	
	Solucao resolver(ProblemaHeur problema, int profMax);
}
