import java.io.IOException;

public class ServidorDoRobo extends Thread {
	private CanalComunicacao cc;
	private ServidorDoRoboGUI serverGUI;
	public RoboDesenhador robo;
	
	public ServidorDoRobo(CanalComunicacao cc) throws IOException, InterruptedException {
		this.cc = cc;
		robo = new RoboDesenhador();
	}
	
	public void run() {
		
		try {
			serverGUI = new ServidorDoRoboGUI(this);
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		while(true) {
			try {
				Mensagem mensagemRead = new Mensagem();
				mensagemRead = cc.read(); 
				
				serverGUI.servidorMensagensTextArea.append("ID: "+ mensagemRead.getId()+" Tipo: "+ mensagemRead.getTipo()+"\n");
				robo.comportamento = mensagemRead.getTipo(); 
				robo.id = mensagemRead.getId();
				robo.raioOuDistancia = mensagemRead.getRaioOuDistancia();
				robo.angulo = mensagemRead.getAngulo();
				robo.timeSleep = mensagemRead.getTimeSleep();
				robo.Desenhador();
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
			
	}
	
	
	}
}
