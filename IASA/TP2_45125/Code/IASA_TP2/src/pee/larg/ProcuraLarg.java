package pee.larg;

import pee.Procura;
import pee.mecproc.MecanismoProcura;
import pee.mecproc.mem.MemoriaFIFO;
import pee.mecproc.mem.MemoriaProcura;
import plantraj.modProb.Problema;

public class ProcuraLarg extends MecanismoProcura<Problema> implements Procura{

	@Override
	protected MemoriaProcura iniciarMemoria() {
		// TODO Auto-generated method stub
		return new MemoriaFIFO();
	}

}
