
public interface ICanalComunicacao {
	
	public void write(Mensagem m);
	
	public Mensagem read() throws InterruptedException;
	
}
