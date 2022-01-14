package TP1;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

/**
 * Classe onde é realizada a comunicacao entre os processos na mesma máquina
 */
public class CanalComunicacao implements ICanalComunicacao {

	private File ficheiro;
	private MappedByteBuffer buffer;//ira guardar a mensagem
	private RandomAccessFile raf;
	private final int MAX_DIM_BUFFER = 520;// 4+4+256*2
	private int mensagemID,tipo;
	private FileLock fl;
	public FileChannel canal;//permite fazer a ligacao

	public CanalComunicacao() throws IOException {
		canal = null;
		ficheiro = null;
		raf = null;
		buffer = null;
		mensagemID = 0; 
		tipo = 0;
	}

	/**
	 * Efetua a abertura do canal
	 */
	@Override
	public boolean openChannel(String filename) throws IOException{
		ficheiro = new File(filename);
		try {
			this.raf =  new RandomAccessFile(this.ficheiro, "rw");
			this.canal = this.raf.getChannel();
			System.out.println("CANAL INICIAL: "+this.canal);
		} catch (FileNotFoundException e) {
			return false;
		}try {
			buffer = canal.map(FileChannel.MapMode.READ_WRITE,0,MAX_DIM_BUFFER);
			System.out.println("BUFFER INICIAL: "+buffer);
		} catch (IOException e) {
			return false;
		}
		return true;
	}

	/**
	 * Método que recebe uma mensagem
	 * @throws IOException 
	 */
	@Override
	public String receberMensagem() {
        String msg = new String();
        try {
            fl = canal.lock(0,canal.size(),false);
            char c;
            
            this.buffer.position(8);
            while ((c=buffer.getChar())!='\0') {
                msg += c;
            }
            fl.release();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        return msg;
    }

	/**
	 * Método que escreve uma mensagem para dentro do buffer
	 * @throws IOException 
	 */
	@Override
	public void write(Mensagem m) throws IOException {
		System.out.println("CANAL ENVIAR: "+this.canal);
		fl = canal.lock(0,canal.size(),false);
		char c;
		buffer.position(0);
		buffer.putInt(m.getId());

		m.setId(m.getId()+1);
		setMensagemID(m.getId());

		buffer.putInt(m.getTipo());
		for (int i= 0 ; i< m.getTexto().length(); ++i){
			c= m.getTexto().charAt(i);
			buffer.putChar(c);
		}
		buffer.putChar('\0');

		fl.release();
	}

	/**
	 * Metodo que termina a ligaca do canal de comunicacao
	 */
	@Override
	public void closeChannel() {
		try {
			limpar();
			canal.close();
			raf.close();
		} catch (IOException e) {
			canal = null;
			raf = null;
		}
	}

	/**
	 * Limpa o buffer
	 */
	public void limpar() {
		buffer.position(0);
		for(int i = 0; i < MAX_DIM_BUFFER;i++) {
			buffer.put(i,(byte) 0);
		}
	}

	/**
	 * Método que permite retornar o id da mensagem dentr do buffer
	 * @return o id da mensagem
	 */
	public int receberID() {
		int id;
		this.buffer.position(0);
		id = buffer.getInt();
		return id;
	}

	/**
	 * Metodo que retorna o tipo de processo que é possível enviar a mensagem
	 * @return o tipo de processo
	 */
	public int receberTipo() {
		int tipo;
		this.buffer.position(4);
		tipo = buffer.getInt();
		return tipo;
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
