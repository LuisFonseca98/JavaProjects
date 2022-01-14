import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.concurrent.Semaphore;

public class GravarFormas extends Thread {
	
	private File file;
	private OutputStream out;
	private Writer writer;
	private Reader reader;
    private InputStream in;
    private Semaphore semExclusao;
    private RobotLegoEV3 robot;
    
    public int estado, id, tipo, raioOudistancia, angulo;
    public int distAnterior;
    public long timeSleep;
    public String fileName;
    public int[] msg3;
    public String[] msg2;
    public String msg;
    
    public static final int IDLE = 0,
    		GRAVAR = 1,
    		REPRODUZIR = 2,
    		SET_FICHEIRO = 3,
    		INTERPRETAR = 4;
	
	public GravarFormas(RobotLegoEV3 robot) {
		this.robot = robot;
		fileName = "";
		id = 0;
		tipo = 0;
		raioOudistancia = 0;
		distAnterior = 0;
		angulo = 0;
		msg3 = new int[5];
		msg2 = new String[5];
		msg = "";
		semExclusao = new Semaphore(1);
	}
	
	public File setFile(String path) throws IllegalAccessException, UnsupportedEncodingException, FileNotFoundException {
        file = new File(path + ".txt");
        System.out.println(file.getPath());
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        out = new FileOutputStream(file, true);
        writer = new OutputStreamWriter(out, "UTF-8");
        in = new FileInputStream(file);
        reader = new InputStreamReader(in);

       

        return file;
    }
	
	public void interpretadorRobot(String msg) throws InterruptedException {
		msg2 = msg.split(" ");
		
		for(int i = 0; i < msg2.length; i++) {
			msg3[i] = Integer.parseInt(msg2[i]);
		}
		int comando = msg3[1];
		
		switch (comando) {

        case 1:
            System.out.println("RETA > DISTANCIA: "+msg3[2]);
            robot.Reta(msg3[2]);
            robot.Parar(false);
            distAnterior = msg3[2];
            break;

        case 2:
        	System.out.println("CURVAR ESQ > RAIO: "+msg3[2]+" ANGULO: "+msg3[3]);
        	robot.CurvarEsquerda(msg3[2], msg3[3]);
        	robot.Parar(false);
        	
            break;

        case 3:
        	System.out.println("PARAR > SLEEP: "+msg3[4]);
            Thread.sleep(msg3[4]);
            break;
		}
	}
	
	public void run() {
		while(true) {
			switch(estado) {
			case IDLE:
				try {
					Thread.sleep(500);
				} catch (InterruptedException e2) {
					e2.printStackTrace();
				}
				break;
			case GRAVAR:
				RecordFile(id, tipo, raioOudistancia, angulo, timeSleep);
				estado = IDLE;
				break;
			case REPRODUZIR:
				msg = ReproduceFile();
				if(msg != "") {
					estado = INTERPRETAR;
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}else {
					estado = IDLE;
				}
				
				break;
			case INTERPRETAR:
				try {
					semExclusao.acquire();
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				try {
					interpretadorRobot(msg);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				semExclusao.release();
				estado = REPRODUZIR;
				break;
			case SET_FICHEIRO:
				try {
					setFile(fileName);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				estado = IDLE;
				break;
			}
		}
	}
	
	private void RecordFile(int id, int tipo, int raioOuDistancia, int angulo, long timeSleep) {
        try {
            writer.write(id+" "+tipo+" "+raioOuDistancia+" "+angulo +" "+timeSleep+ "\n");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String ReproduceFile() {
        String msg = "";
        char c = 0;
        try {
            c = (char) reader.read();
            if (c != (char) -1) {
                do {
                    msg += c;
                    c = (char) reader.read();
                } while (c != '\n');
                return msg;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return msg;
    }
}
