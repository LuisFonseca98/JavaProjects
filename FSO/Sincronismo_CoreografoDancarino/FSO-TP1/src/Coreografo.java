import java.util.ArrayList;

public class Coreografo implements Runnable {
    private GuiCoreografo gui;
    private CanalComunicacao cc;
    private int numOrdensEnviadas;
    private ArrayList<MyMensagem> ultimasOrdensEnviadas;
    private int numOrdensAEnviar;

    public Coreografo(String nome) {
        try {
            //Cria inst�ncias de canal comunica��o e gui
            cc = new CanalComunicacao();
            //Mete o n�mero de ordens enviadas a 0
            numOrdensEnviadas = 0;
            //Arraylist auxiliar para armazenar as mensagens enviadas e exp�-las na GUI
            ultimasOrdensEnviadas = new ArrayList<MyMensagem>();
            gui = new GuiCoreografo(this);
            //Mete o n�mero de ordens a enviar a 1 porque vamos enviar a ordem 0,0 logo a seguir
            numOrdensAEnviar = 1;
            gerarComando(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean enviaOrdem(MyMensagem msg) {
        try {
            numOrdensEnviadas++;
            //Envia a mensagem para o buffer
            cc.write(msg);
            //Diminui o n�mero de OrdensAEnviar e manda um sleep de 500ms
            numOrdensAEnviar--;
            Thread.sleep(500);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean gerarComando() {
        //Cria uma mensagem e d� set no n�mero e na ordem
        MyMensagem m = new MyMensagem();
        //Gera uma ordem aleat�ria
        m.setOrdem(m.gerarOrdem());
        m.setNumero(numOrdensEnviadas);
        //Se n�o conseguir enviar a ordem retorna falso
        if (!enviaOrdem(m)) {
            return false;
        }
        //Shifta o arraylist para a esquerda
        if (ultimasOrdensEnviadas.size() >= 10) {
            ultimasOrdensEnviadas.remove(0);
        }
        //Adiciona � mem�ria e exp�e
        ultimasOrdensEnviadas.add(m);
        gui.setUltimosComandos(getUltimasOrdensEnviadas());
        return true;
    }

    private boolean gerarComando(int o) {
        MyMensagem m = new MyMensagem();
        m.setNumero(numOrdensEnviadas);
        m.setOrdem(o);
        if (m.getNumero() == 0)
            m.setOrdem(0);
        if (!enviaOrdem(m)) {
            return false;
        }
        if (ultimasOrdensEnviadas.size() >= 10) {
            ultimasOrdensEnviadas.remove(0);
        }
        ultimasOrdensEnviadas.add(m);
        return true;
    }

    //Gera NComandos e depois envia um parar(false)
    public boolean gerarNComandos(int n) {
        if (n != 0) {
            for (int i = 0; i < n - 1; i++) {
                if (!gerarComando()) {
                    return false;
                }
            }
            gerarComando(0);
            return true;
        }
        return false;
    }

    public ArrayList<MyMensagem> getUltimasOrdensEnviadas() {
        return this.ultimasOrdensEnviadas;
    }

    public void run() {
        final int ESTADO_ESPERA = 0, ESTADO_ENVIAR = 1, ESTADO_INFINITO = 2;
        int estado = ESTADO_ESPERA;
        while (true) {
            try {
                switch (estado) {
                    case ESTADO_ESPERA:
                        if (numOrdensAEnviar != 0) {
                            estado = ESTADO_ENVIAR;
                        }
                        if (gui.getInfinito()) {
                            estado = ESTADO_INFINITO;
                            break;
                        }
                        Thread.sleep(20);
                        break;
                    case ESTADO_ENVIAR:
                        Thread.sleep(20);
                        if (numOrdensAEnviar <= 0) {
                            estado = ESTADO_ESPERA;
                        }
                        if (gui.getInfinito()) {
                            estado = ESTADO_INFINITO;
                            break;
                        }
                        //Gera NComandos dependendo do n�mero de ordens a Enviar
                        gerarNComandos(numOrdensAEnviar);
                        //Exp�e os comandos enviados na GUI
                        gui.setUltimosComandos(getUltimasOrdensEnviadas());
                        break;
                    case ESTADO_INFINITO:
                        //Gera comandos infinitos at� a vari�vel ficar a falso
                        Thread.sleep(20);
                        gerarComando();
                        gui.setUltimosComandos(getUltimasOrdensEnviadas());
                        setNumOrdensAEnviar(0);
                        if (!gui.getInfinito()) {
                            gerarComando(0);
                            gui.setUltimosComandos(getUltimasOrdensEnviadas());
                            setNumOrdensAEnviar(0);
                            estado = ESTADO_ESPERA;
                        }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void setNumOrdensAEnviar(int i) {
        numOrdensAEnviar = i;
    }

    public static void main(String[] args) {
        Coreografo c = new Coreografo("Zezoca");
        c.run();
    }
}
