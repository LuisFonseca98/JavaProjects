package pee.melhorprim;

import pee.Procura;
import pee.mecproc.No;
import plantraj.modProb.Problema;


public class ProcuraCustoUnif extends ProcuraMelhorPrim<Problema> implements Procura {

	@Override
	protected double f(No no) {
		return no.getCusto();
	}
	
	

}
