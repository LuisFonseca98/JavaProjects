import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class Dancarino extends Thread {

    private GuiDancarino gui;
    private CanalComunicacao cc;
    private ArrayList<MyMensagem> listaMensagensEmEspera;
    private int estadoCC;
    private int estadoRobot;
    private MyMensagem msg;
    private volatile int estado;
    private BD bd;
    private Semaphore s;
    public static final int CC_ESTADO_VALIDAR = 0, CC_ESTADO_GUARDAR = 1;
    public static final int ESTADO_ESPERA = 0, ESTADO_SLEEP = 1, ESTADO_EXECUTAR = 2, ESTADO_TERMINAR = 3;
    private int pointerRead;
    private int idDanc;
    private Evitar evitar;

    public Dancarino(CanalComunicacao cc, Semaphore ss) {
        // Cria um canal comunica��o, uma base de dados, um rob� e uma
        // guiDancarino
        estado = ESTADO_ESPERA;
        this.cc = cc;
        // idCoreo = cc.open(this);
        bd = new BD();
        // Inicializa o n�mero anterior com -1 para a primeira mensagem validada que
        // vai
        // ser 0 funcionar
        msg = null;
        pointerRead = 0;
        // Mem�ria interna do dan�arino
        listaMensagensEmEspera = new ArrayList<MyMensagem>();
        gui = new GuiDancarino(this, bd);
        setIdDanc(cc.getNextDancID());
        pointerRead = cc.open(this);
        s = new Semaphore(1);
        evitar = new Evitar(this, s);
        evitar.start();
    }

    public BD getBD() {
        return this.bd;
    }

    // Recebe a mensagem
    public MyMensagem receberMensagem() {
        try {
            System.out.println("MENSAGEM RECEBIDA");
            MyMensagem instrucao = cc.readMsg(pointerRead);
            return instrucao;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void guardarMensagem(MyMensagem m) {
        System.out.println("MENSAGEM GUARDADA");
        listaMensagensEmEspera.add(m);
    }

    public void Terminar() {
        gui.DesligarCoreografo();
        bd.parar(false);
        estado = ESTADO_TERMINAR;
    }

    public String toString() {
        return "Dancarino " + currentThread().getName();
    }

    public int CumprirOrdem() {

        System.out.println("MENSAGEM CUMPRIDA");
        MyMensagem m = listaMensagensEmEspera.get(0);

        // Inicia uma vari�vel do tempo de execu�ao da intru��o
        int tExec = 0;
        switch (m.getOrdem()) {
            case 0:
                bd.parar(false);
                gui.myPrint(m.getNumero() + "- Parar(false)");
                tExec = 100;
                break;
            case 1:
                bd.frente(10);
                gui.myPrint(m.getNumero() + "- Frente(10)");
                tExec = 400;
                break;
            case 2:
                bd.CurvarDireita(0, 45);
                gui.myPrint(m.getNumero() + "- Curva Direita(45)");
                tExec = 200;
                break;
            case 3:
                bd.CurvarEsquerda(0, 45);
                gui.myPrint(m.getNumero() + "- Curva Esquerda(45)");
                tExec = 200;
                break;
            case 4:
                bd.retaguarda(-10);
                gui.myPrint(m.getNumero() + "- Tras(10)");
                tExec = 400;
                break;
            default: // Tambem é o case 5
                bd.parar(true);
                gui.myPrint(m.getNumero() + "- Parar(true)");
                tExec = 100;
                break;
        }
        // Retorna o tempo de execu��o
        //s.release();
        return tExec;
    }

    public boolean calcHasNext() {
        // Verifica se h� mensagens em espera
        return listaMensagensEmEspera.size() > 0;
    }

    public void iniciar() {
        gui.setVisible(true);
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public boolean isVisible() {
        return gui.isVisible();
    }

    public void setVisible(boolean b) {
        gui.setVisible(b);
    }

    public int getIdDanc() {
        return idDanc;
    }

    public void setIdDanc(int idDanc) {
        this.idDanc = idDanc;
    }

    public boolean Ativar() {
        gui.setLigarCoreografo();
        return gui.isLigarCoreografo();
    }

    private boolean debug = false;

    public boolean getOnGUI() {
        return gui.isOnOff();
    }

    public boolean getAtivo() {
        return gui.isLigarCoreografo();
    }

    public void run() {
        while (true) {
            if (debug) {
                try {
                    // System.out.print(" A dormir durante 5segundos, debug ");
                    Thread.sleep(2000);
                    // System.out.println("Continuar.");
                } catch (InterruptedException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
            switch (estado) {
                case ESTADO_ESPERA:
                    // System.out.println(currentThread().getName() + " EM ESPERA.");
                    if ((gui.isLigarCoreografo() && gui.isOnOff()) && estado == ESTADO_ESPERA) {
                        estado = ESTADO_EXECUTAR;
                    }
                    if (estado == ESTADO_ESPERA)
                        estado = ESTADO_SLEEP;
                    break;
                case ESTADO_SLEEP:
                    try {
                        Thread.sleep(10);
                        // System.out.println(currentThread().getName() + " A DORMIR.");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (estado == ESTADO_SLEEP)
                        estado = ESTADO_ESPERA;
                    break;

                case ESTADO_EXECUTAR:
                    // System.out.println(currentThread().getName() + " A SER EXECUTADO.");
                    automatoCC();
                    automatoRobot();
                    if (!gui.isLigarCoreografo() && !calcHasNext() && estado == ESTADO_EXECUTAR) {
                        try {
                            s.acquire();
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        bd.parar(true);
                        s.release();
                        estado = ESTADO_ESPERA;
                    }
                    break;
                case ESTADO_TERMINAR:
                    System.out.println("MORRI- " + currentThread().getName());
                    estado = ESTADO_ESPERA;
                    break;
            }
        }
    }

    public static final int ROBOT_ESTADO_INTERPRETAR_CUMPRIR = 0, ROBOT_ESTADO_POP = 1;

    private void automatoRobot() {
        switch (estadoRobot) {
            case ROBOT_ESTADO_INTERPRETAR_CUMPRIR:
                int TempoEspera;
                if (calcHasNext()) {
                    TempoEspera = CumprirOrdem();
                    try {
                        System.out.println(TempoEspera);
                        Thread.sleep(TempoEspera);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    estadoRobot = ROBOT_ESTADO_POP;
                }
                break;
            case ROBOT_ESTADO_POP:
                // Remove a mensagem executada
                listaMensagensEmEspera.remove(0);
                estadoRobot = ROBOT_ESTADO_INTERPRETAR_CUMPRIR;
                break;
        }
    }

    private void automatoCC() {
        switch (estadoCC) {
            case CC_ESTADO_VALIDAR:
                msg = receberMensagem();
                System.out.println(msg);
                if (msg != null) {
                    estadoCC = CC_ESTADO_GUARDAR;
                }
                break;
            case CC_ESTADO_GUARDAR:
                gui.myPrint(msg + " guardada na memoria");
                guardarMensagem(msg);
                msg = null;
                estadoCC = CC_ESTADO_VALIDAR;
        }

    }

    public void closeGUI() {
        gui.setVisible(false);
        gui.dispose();
    }

    public void closeEV3() {
        bd.CloseEV3();
    }
}
