package pee.mecproc.mem;

import java.util.LinkedList;

import pee.mecproc.No;

public class MemoriaFIFO extends MemoriaProcura{
	//mecanismo de procura em largura
	public MemoriaFIFO(){
		super(new LinkedList<No>());
	}
}

