package pee.prof;

import pee.Procura;
import pee.mecproc.MecanismoProcura;
import pee.mecproc.mem.MemoriaLIFO;
import pee.mecproc.mem.MemoriaProcura;
import plantraj.modProb.Problema;

public class ProcuraProf extends MecanismoProcura<Problema> implements Procura{

	@Override
	protected MemoriaProcura iniciarMemoria() {
		// TODO Auto-generated method stub
		return new MemoriaLIFO();
	}

}
