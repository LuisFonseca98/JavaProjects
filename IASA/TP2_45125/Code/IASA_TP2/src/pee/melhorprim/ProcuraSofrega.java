package pee.melhorprim;

import pee.mecproc.No;
import plantraj.modProb.ProblemaHeur;

public class ProcuraSofrega extends ProcuraMelhorPrim<ProblemaHeur> implements ProcuraHeur{

	@Override
	protected double f(No no) {
		return problema.heuristica(no.getEstado());
	}

}
