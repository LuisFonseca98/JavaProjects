

import java.io.IOException;

public class ClienteDoRobo implements IComandos{

	public int tipoMensagem, tipoComando, raioOuDistancia, angulo;
	public CanalComunicacao cc;
	public ServidorDoRobo server;
	
	protected int comportamento;
	private Mensagem mensagem;
	

	public ClienteDoRobo() {
		try {
			cc = new CanalComunicacao();
			server = new ServidorDoRobo(cc);
			mensagem = new Mensagem();
			tipoMensagem = 0;
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método que inicia a tarefa do servidor
	 */
	public void iniciarTarefaServidor() {
		server.start();
	}
	

	@Override
	public void CurvarEsq(int raioOuDistancia, int angulo) {

		tipoMensagem = 2;
		mensagem.setId(mensagem.getId()+1);
		mensagem.setTipo(tipoMensagem);
		mensagem.setRaioOuDistancia(raioOuDistancia);
		mensagem.setAngulo(angulo);
		cc.write(mensagem);
		
		cc.semRead.release();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void Reta(int raioOuDistancia) {

		tipoMensagem = 1;
		mensagem.setId(mensagem.getId()+1);
		mensagem.setTipo(tipoMensagem);
		mensagem.setRaioOuDistancia(raioOuDistancia);
		mensagem.setAngulo(0);
		cc.write(mensagem);
		cc.semRead.release();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void Parar(boolean parar, long tempoSleep) {
		
		tipoMensagem = 3;
		mensagem.setId(mensagem.getId()+1);
		mensagem.setTipo(tipoMensagem);
		if(parar == true) {
			mensagem.setRaioOuDistancia(1);
		}else {
			mensagem.setRaioOuDistancia(2);
		}
		
		mensagem.setAngulo(0);
		mensagem.setTimeSleep(tempoSleep);
		cc.write(mensagem);
		
		cc.semRead.release();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}




