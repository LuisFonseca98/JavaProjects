
public class Mensagem {

	
	private int id,tipo,raioOuDistancia,angulo;
	private long timeSleep;

	public Mensagem(int id, int tipo,int raioOuDistancia, int angulo, long timeSleep) {
		this.id = id;
		this.tipo = tipo;
		this.raioOuDistancia = raioOuDistancia; 
		this.angulo = angulo;
		this.timeSleep = timeSleep;
	}

	
	public long getTimeSleep() {
		return timeSleep;
	}


	public void setTimeSleep(long timeSleep) {
		this.timeSleep = timeSleep;
	}


	public Mensagem() {
		id = 0;
		tipo = 0;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}
	
	public int getRaioOuDistancia() {
		return raioOuDistancia;
	}

	public void setRaioOuDistancia(int raioOuDistancia) {
		this.raioOuDistancia = raioOuDistancia;
	}
	
	public int getAngulo() {
		return angulo;
	}

	public void setAngulo(int angulo) {
		this.angulo = angulo;
	}
	
	

}
