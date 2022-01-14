
import java.io.IOException;
import java.util.concurrent.Semaphore;

/**
 * Classe onde é realizada a comunicacao entre os processos na mesma máquina
 */
public class CanalComunicacao implements ICanalComunicacao {

	public Mensagem[] mBuffer;
	private final int MAX_DIM_BUFFER = 16; 
	private int mensagemID,tipo;
	private int pointerWrite;
	private int pointerRead;
	public Semaphore semRead,semOcupado;


	public CanalComunicacao() throws IOException {
		mensagemID = 0; 
		tipo = 0;
		mBuffer = new Mensagem[MAX_DIM_BUFFER];
		pointerWrite = 0;
		pointerRead = 0;
		semRead = new Semaphore(0);
		semOcupado = new Semaphore(0);

	}
	
	/**
	 * Método que escreve uma mensagem para dentro do buffer
	 */
	@Override
	public void write(Mensagem m){
		
		mBuffer[pointerWrite]= m;
		pointerWrite= ++pointerWrite % MAX_DIM_BUFFER;
		
	}

	/**
	 * Método que recebe uma mensagem
	 * @throws InterruptedException 
	 * @throws IOException 
	 */
	@Override
	public Mensagem read() throws InterruptedException {
		
		semRead.acquire();
		
		Mensagem m = new Mensagem();
		
		m= mBuffer[pointerRead];
		pointerRead= ++pointerRead % MAX_DIM_BUFFER;
	

		return m;

    }


	public int getMensagemID() {
		return mensagemID;
	}

	public void setMensagemID(int mensagemID) {
		this.mensagemID = mensagemID;
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}
	
	

}
