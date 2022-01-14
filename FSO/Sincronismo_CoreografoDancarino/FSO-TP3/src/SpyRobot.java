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

public class SpyRobot extends Thread {
    private File file;
    private RobotLegoEV3 robot;
    private GuiSpy gui;
    private BD bd;
    private OutputStream out;
    private Writer writer;
    private InputStream in;
    private Reader reader;
    private int estado;
    private int dormir;
    private boolean gravar;
    private boolean reproduzir;
    private String msg;
    private Semaphore semaforoExclusaoMutua;

    public static final int ESTADO_IDLE = 0, ESTADO_ESPERA = 1, ESTADO_INTERPRETAR = 2, ESTADO_GRAVAR = 3,
            ESTADO_REPRODUZIR = 4, ESTADO_DORMIR = 5, ESTADO_TERMINAR = 6, ESTADO_PAUSA = 7;

    public SpyRobot(BD bd) {
        dormir = 0;
        estado = ESTADO_IDLE;
        this.bd = bd;
        semaforoExclusaoMutua = bd.getSem();
        robot = new RobotLegoEV3();
        gui = new GuiSpy(this);
        gui.setVisible(false);
        gravar = false;
        reproduzir = false;
        msg = null;

    }

    public void iniciar() {
        gui.setVisible(true);
        this.start();
    }

    public File setFile(String path) throws IllegalAccessException {
        file = new File(path + ".txt");
        System.out.println(file.getPath());
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            // deve resolver, o true diz respeito ao appendF
            out = new FileOutputStream(file, true);
            writer = new OutputStreamWriter(out, "UTF-8");

            in = new FileInputStream(file);
            reader = new InputStreamReader(in);

        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return file;
    }

    private void escreveLinhaParaFicheiro(String msg) {
        try {
            writer.write(msg + '\n');
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String lerLinhaFicheiro() {
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
        return null;
    }

    public RobotLegoEV3 getRobot() {
        return robot;
    }

    public boolean OpenEV3(String nome) {
        boolean abriu = robot.OpenEV3(nome);
        bd.setNomeRobot(nome);
        if (abriu) {
            gui.setLabel(nome);
            if (gravar) {
                escreveLinhaParaFicheiro("Robot " + bd.getNomeRobot() + " abriu.");
            }
        }
        return abriu;
    }

    public void CloseEV3() {
        if (gravar) {
            escreveLinhaParaFicheiro("Robot " + bd.getNomeRobot() + " Fechou.");
        }
        robot.CloseEV3();
    }

    public void Parar(boolean imediato) {
        if (gravar) {
            String msg = "parar " + imediato;
            gui.myPrint(msg + "\n");
            escreveLinhaParaFicheiro(msg);
        }
        robot.Parar(imediato);
    }

    public void frente(int cm) {
        if (gravar) {
            String msg = "reta " + cm;
            gui.myPrint(msg + "\n");
            escreveLinhaParaFicheiro(msg);
        }
        robot.Reta(cm);
    }

    public void CurvarDireita(int raio, int angulo) {
        if (gravar) {
            String msg = "direita " + raio + " " + angulo;
            gui.myPrint(msg + "\n");
            escreveLinhaParaFicheiro(msg);
        }
        robot.CurvarDireita(raio, angulo);

    }

    public void CurvarEsquerda(int raio, int angulo) {
        if (gravar) {
            String msg = "esquerda " + raio + " " + angulo;
            gui.myPrint(msg + "\n");
            escreveLinhaParaFicheiro(msg);
        }
        robot.CurvarEsquerda(raio, angulo);
    }

    public void retaguarda(int cm) {
        frente(-1 * cm);
    }

    public String getNome() {
        return bd.getNomeRobot();
    }

    private void intrepretarMSG(String msg) {
        gui.myPrint("Reproduzir - Ordem " + msg + "\n");
        String[] mensagem = msg.split(" ");
        String ordem = mensagem[0];
        switch (ordem) {

            case "reta":
                robot.Reta(Integer.parseInt(mensagem[1]));
                System.out.println("dancar reta" + mensagem[1]);
                dormir = 400;
                break;

            case "direita":
                robot.CurvarDireita(Integer.parseInt(mensagem[1]), Integer.parseInt(mensagem[2]));
                System.out.println("dancar curva direita");
                dormir = 200;
                break;

            case "esquerda":
                robot.CurvarEsquerda(Integer.parseInt(mensagem[1]), Integer.parseInt(mensagem[2]));
                System.out.println("dancar curva esquerda");
                dormir = 200;
                break;

            case "parar":
                robot.Parar(Boolean.parseBoolean(mensagem[1]));
                System.out.println("dancar parar");
                dormir = 100;
                break;

            default:
                robot.Parar(true);
                System.out.println("nao devia tar aqui");
                break;
        }
    }

    public void toggleGravacao() {
        gravar = !gravar;

        // Nao estamos a dar rename, estamos a criar outro ficheiro, ver se isto pode
        // ser assim ou temos de trocar
        gui.Gravar(gravar);
    }

    public void toggleReproducao() {

        try {
            in = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        reader = new InputStreamReader(in);
        reproduzir = !reproduzir;
        gui.Reproduzir(reproduzir);
    }

    public void run() {
        while (estado != ESTADO_TERMINAR) {

            switch (estado) {
                case ESTADO_IDLE:
                    if (gravar) {
                        estado = ESTADO_GRAVAR;

                    } else if (reproduzir) {

                        estado = ESTADO_REPRODUZIR;

                    } else
                        estado = ESTADO_ESPERA;

                    break;

                case ESTADO_ESPERA:
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                    estado = ESTADO_IDLE;
                    break;

                case ESTADO_GRAVAR:

                    estado = ESTADO_ESPERA;

                    if (!gravar) {
                        estado = ESTADO_IDLE;
                    }
                    break;

                case ESTADO_REPRODUZIR:

                    msg = lerLinhaFicheiro();

                    //se a mensagem nao for null quer dizer que e valida por isso interpreta
                    if (msg != null) {
                        estado = ESTADO_INTERPRETAR;
                    } else {

                        //quando a mensagem for null entra aqui, desliga o reproduzir e manda para o estado idle de novo
                        toggleReproducao();
                        estado = ESTADO_IDLE;
                    }


                    break;
                case ESTADO_INTERPRETAR:

                    try {
                        semaforoExclusaoMutua.acquire();
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                    intrepretarMSG(msg);

                    semaforoExclusaoMutua.release();
                    estado = ESTADO_DORMIR;
                    break;

                case ESTADO_DORMIR:

                    // Dar tempo ao robot para cumprir a ordem
                    try {
                        Thread.sleep(dormir);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (estado == ESTADO_DORMIR)
                        estado = ESTADO_REPRODUZIR;

                    break;

            }
        }
    }
}
