package TP1;

import java.io.IOException;

/**
 * Classe que executa uma maquina de estados
 * Essa maquina permite que os processos possam escrever e enviar mensagens
 * @author luisc
 */

public class MaquinaEstados implements Runnable {
	protected int state,numState;
	private GuiChat gui;
	private CanalComunicacao cc;
	private Mensagem mensagem;
	private int tipoGeral;
	public static final int INITIAL_STATE = 0, 
			CREATE_CHANNEL = 1, 
			WRITE_MESSAGE = 2,
			READ_MESSAGE = 3,
			TERMINATE_APP = 4;

	public MaquinaEstados() {
		try {
			cc = new CanalComunicacao();
			gui = new GuiChat(this);
			mensagem = new Mensagem();
			numState = INITIAL_STATE;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while(true) {
			switch(numState) {
			
			case INITIAL_STATE:
				System.out.println("ESTOU NO INICIAL");
				if(gui.getAbrirButton())
					numState = CREATE_CHANNEL;
				break;
				
			case CREATE_CHANNEL:
				System.out.println("ESTOU NO CREATE");
				try {
					if(cc.openChannel(gui.FilePath)) 
						numState = READ_MESSAGE;
					
				} catch (IOException e2) {
					e2.printStackTrace();
				}
				break;
				
			case WRITE_MESSAGE:
				System.out.println("ENTREI NO WRITE");
				
				if(gui.isFSORadio()) {
					tipoGeral = 1;
				} else if(gui.isRobotsRadio()) {
					tipoGeral = 2;
				} else if(gui.isJavaRadio()) {
					tipoGeral = 3;
				}else {
					tipoGeral = 0;
				}
				String s;
				s = gui.getEnviarTextArea().getText();
				System.out.println("MENSAGEM ANTERIOR: "+cc.receberID());
				mensagem.setId(cc.receberID()+1);
				mensagem.setTipo(tipoGeral);
				mensagem.setTexto(s);
				
				try {
					cc.write(mensagem);
				} catch (IOException e) {
					e.printStackTrace();}
				numState = READ_MESSAGE; 
				break;
				
				
			case READ_MESSAGE:				
				try {
					Thread.sleep(1000);
					
						int id = cc.receberID();
						int tipo = cc.receberTipo();
						String texto = cc.receberMensagem();
						
						if(gui.isTodasRadio()) {
							
							gui.setMensagensRecebidasTextArea("ID: "+id+" Tipo: "+tipo+" Mensagem: "+texto+ "\n");
							
						}else if(gui.isFSORadio() && (tipo == 1 || tipo ==0)) {
							
							gui.setMensagensRecebidasTextArea("ID: "+id+" Tipo: "+tipo+" Mensagem: "+texto+ "\n");
							
						}else if(gui.isRobotsRadio() && (tipo == 2 || tipo ==0)) {
							
							gui.setMensagensRecebidasTextArea("ID: "+id+" Tipo: "+tipo+" Mensagem: "+texto+ "\n");
							
						}else if(gui.isJavaRadio() && (tipo == 3 || tipo ==0)) {
							
							gui.setMensagensRecebidasTextArea("ID: "+id+" Tipo: "+tipo+" Mensagem: "+texto+ "\n");
							
						}else {
							gui.setMensagensRecebidasTextArea("Não está a receber nenhuma mensagem \n");
			
					}
				} catch (InterruptedException e1) {e1.printStackTrace();}
				break;
				
			case TERMINATE_APP:
				System.out.println("TERMINATE");
				cc.closeChannel();
				break;
			}	
		}
	}
	
	public static void main(String[] args) {
		MaquinaEstados states = new MaquinaEstados();
		states.run();
	}
}
