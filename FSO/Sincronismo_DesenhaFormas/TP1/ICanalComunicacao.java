package TP1;

import java.io.IOException;

public interface ICanalComunicacao {
	
	public boolean openChannel(String Filename) throws IOException;
	
	public void write(Mensagem m) throws IOException;
	
	public String receberMensagem();
	
	public void closeChannel();

}
