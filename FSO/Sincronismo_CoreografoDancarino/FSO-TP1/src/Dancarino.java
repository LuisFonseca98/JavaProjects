import java.io.IOException;
import java.util.ArrayList;

public class Dancarino implements Runnable {
    private GuiDancarino gui;
    private CanalComunicacao cc;
    private RobotLegoEV3 robot;
    private int numeroAnterior;
    private ArrayList<MyMensagem> listaMensagensEmEspera;
    private int estadoCC;
    private int estadoRobot;
    private MyMensagem msg;
    private int estado;
    private boolean guiCor, checkOn;
    private Thread t;
    private String nome;

    public Dancarino(String nome) {
        try {
            //Cria um canal comunica��o, uma base de dados, um rob� e uma guiDancarino
            cc = new CanalComunicacao();
            robot = new RobotLegoEV3();
            //Inicializa o n�mero anterior com -1 para a primeira mensagem validada que vai ser 0 funcionar
            numeroAnterior = -1;
            //Mem�ria interna do dan�arino
            listaMensagensEmEspera = new ArrayList<MyMensagem>();
            gui = new GuiDancarino(this);
            t = new Thread(this);
            this.nome = nome;
            t.setName(nome);
            t.start();
        } catch (IOException e) {
            System.out.println("Falhou construcao dancarino");
            e.printStackTrace();
        }

    }

    //Caso o buffer estiver limpo e o core�grafo j� tiver enviado mensagens ele valida, ou caso a mensagem seja a anterior +1
    public boolean validaInstrucoes(MyMensagem m) {
        if ((numeroAnterior + 1) == m.getNumero() || (numeroAnterior == 0 && m.getNumero() >= 1)) {
            return true;
        }
        return false;
    }

    /**
     * adiciona a lista de espera uma ordem
     *
     * @return true se a ordem foi adiciona a lista de espera
     */

    //Recebe a mensagem
    public MyMensagem receberValidarOrdem() {
        try {
            //L� o buffer
            MyMensagem instrucao = cc.readMsg();
            //Caso a instru��o n�o for v�lida retorna null
            if (!validaInstrucoes(instrucao))
                return null;
            //Caso contr�rio retorna a instru��o
            return instrucao;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    //Guarda a mensagem � mem�ria interna
    public void guardarMensagem(MyMensagem m) {
        listaMensagensEmEspera.add(m);
    }

    public int IntrepetarCumprirOrdem() {
        // LER mensagensEmEspera e fazer shift no array
        //Pega na mensagem na mem�ria
        MyMensagem m = listaMensagensEmEspera.get(0);
        //Inicia uma vari�vel do tempo de execu�ao da intru��o
        int tExec = 0;
        switch (m.getOrdem()) {
            case 0:
                robot.Parar(false);
                gui.myPrint(m.getNumero() + "- Parar(false)");
                tExec = 100;
                break;
            case 1:
                robot.Reta(10);
                gui.myPrint(m.getNumero() + "- Frente(10)");
                tExec = 400;
                break;
            case 2:
                robot.CurvarDireita(0, 45);
                gui.myPrint(m.getNumero() + "- Curva Direita(45)");
                tExec = 200;
                break;
            case 3:
                robot.CurvarEsquerda(0, 45);
                gui.myPrint(m.getNumero() + "- Curva Esquerda(45)");
                tExec = 200;
                break;
            case 4:
                robot.Reta(-10);
                gui.myPrint(m.getNumero() + "- Tr�s(10)");
                tExec = 400;
                break;
            default: // Tambem a o case 5
                robot.Parar(true);
                gui.myPrint(m.getNumero() + "- Parar(true)");
                break;
        }
        //Retorna o tempo de execu��o
        return tExec;
    }

    public boolean calcHasNext() {
        //Verifica se h� mensagens em espera
        return listaMensagensEmEspera.size() > 0;
    }

    public void run() {
        final int ESTADO_ESPERA = 0, ESTADO_EXECUTAR = 1;
        guiCor = gui.isLigarCoreografo();
        checkOn = gui.isOnOff();
        switch (estado) {
            case ESTADO_ESPERA:
                if (guiCor || checkOn) {
                    cc.limpar();
                    numeroAnterior = -1;
                    estado = ESTADO_EXECUTAR;
                }
                break;
            case ESTADO_EXECUTAR:
                automatoCC();
                automatoRobot();
                if (guiCor && listaMensagensEmEspera.size() <= 0) {
                    robot.Parar(true);
                    estado = ESTADO_ESPERA;
                }
                break;
        }
    }

    private void automatoRobot() {
        final int ESTADO_INTERPRETAR = 0, ESTADO_POP = 1;
        switch (estadoRobot) {
            case ESTADO_INTERPRETAR:
                if (calcHasNext()) {
                    //Interpreta a ordem que est� na mem�ria ind�ce 0 e armazena o tempo de espera numa vari�vel
                    int tempoEspera = IntrepetarCumprirOrdem();
                    try {
                        //Adormece o processo TempoEspera milisegundos
                        Thread.sleep(tempoEspera);
                        estadoRobot = ESTADO_POP;
                        break;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case ESTADO_POP:
                //Remove a mensagem executada
                listaMensagensEmEspera.remove(0);
                estadoRobot = ESTADO_INTERPRETAR;
                break;
        }
    }

    private void automatoCC() {
        final int ESTADO_VALIDAR = 0, ESTADO_GUARDAR = 1;
        switch (estadoCC) {
            case ESTADO_VALIDAR:
                //L� no buffer a mensagem e valida, caso continue a ser null nao entra no if e d� break, se for v�lida guarda
                msg = receberValidarOrdem();
                if (msg != null) {
                    numeroAnterior = msg.getNumero();
                    estadoCC = ESTADO_GUARDAR;
                }
                break;
            case ESTADO_GUARDAR:
                guardarMensagem(msg);
                msg = null;
                estadoCC = ESTADO_VALIDAR;
        }
    }

    public static void main(String[] args) {
        Dancarino d = new Dancarino("Ola!");
        d.run();
    }
}
