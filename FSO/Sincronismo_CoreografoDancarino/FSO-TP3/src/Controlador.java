import java.io.IOException;
import java.util.ArrayList;

public class Controlador {

    private CanalComunicacao cc;

    private ArrayList<Dancarino> dancarinos;
    private ArrayList<Coreografo> coreografos;
    private ArrayList<String> logs;
    private String log;
    private int numCoreografos;
    private int numDancarinos;

    /**
     * Classe principal de programa. A partir daqui todo o trabalho será iniciado e
     * instanciado Cria o canal de comunicacao, e armazena os dancarinos e os
     * coreografos
     */
    public Controlador() {
        try {
            cc = new CanalComunicacao();
        } catch (IOException e) {
            e.printStackTrace();
        }
        new GUI_APP(this);
        cc.start();
        log = "";
        numCoreografos = 0;
        numDancarinos = 0;
        dancarinos = new ArrayList<Dancarino>();
        coreografos = new ArrayList<Coreografo>();
        logs = new ArrayList<String>();
    }

    public ArrayList<Dancarino> getDancarinos() {
        return dancarinos;
    }

    public ArrayList<Coreografo> getCoreografos() {
        return coreografos;
    }

    public ArrayList<String> getlogs() {
        return logs;
    }

    public String getConsola() {
        return log;
    }

    public void setConsola(String s) {
        // gravar log anterior
        logs.add(s);
        log = s;
    }

    /**
     * @param i inteiro, entre 0 e Dancarinos.size()
     * @return Dancarino na posicao I
     */
    public Dancarino getDancarino(int i) {
        return dancarinos.get(i);
    }

    /**
     * @param i inteiro, entre 0 e Dancarinos.size()
     * @return Nome do dancarino na posicao I
     */
    public String getDancarinoName(int i) {
        return getDancarino(i).getName();
    }

    /**
     * @param i inteiro, entre 0 e Coreografos.size()
     * @return Coreografo na posicao I
     */
    public Coreografo getCoreografo(int i) {
        return coreografos.get(i);
    }

    /**
     * @param i inteiro, entre 0 e coreografos.size()
     * @return Nome do coreografo na posicao I
     */
    public String getCoreografoName(int i) {
        return getCoreografo(i).getName();

    }

    /**
     * Cria e adiciona um dancarino á memoria interna do controlador.
     *
     * @return novo Dancarino
     */
    public Dancarino addNovoDancarino() {
        Dancarino d = new Dancarino(cc);
        d.setName("Dancarino - " + numDancarinos++);
        dancarinos.add(d);
        return d;
    }

    /**
     * Remove um dancarino do controlador, fecha a gui respetiva e remove da memoria
     * interna
     *
     * @param i Pos do dancarino entre 0 e dancarinos.size()
     */
    public void remDancarino(int i) {
        dancarinos.get(i).closeGUI();
        dancarinos.remove(i);
    }

    /**
     * Cria e adiciona um novo coreografo á memoria interna do controlador.
     *
     * @return novo Coreografo
     */
    public Coreografo addNovoCoreografo() {
        Coreografo c = new Coreografo(cc);
        c.setName("Coreografo- " + numCoreografos++);

        coreografos.add(c);
        return c;
    }

    /**
     * Remove um coreografo do controlador, fecha a gui respetiva e remove da
     * memoria interna
     *
     * @param i Pos do coreografo entre 0 e coreografo.size()
     */
    public void remCoreografo(int i) {
        coreografos.get(i).closeGUI();
        coreografos.remove(i);
        numCoreografos--;
    }

    /**
     * Atualiza o numero de coreografos
     *
     * @return numeroCoreografos +1
     */

    public int getAndIncrementNumCoreografos() {
        return numCoreografos++;
    }

    /**
     * Inicia todos os dancarinos e tudo o que eles necessitam para funcionar,
     * incluindo a propria Thread
     */
    public void iniciarDancarinos() {
        for (Dancarino dancarino : dancarinos) {
            try {
                dancarino.iniciar();
            } catch (IllegalThreadStateException e) {
                System.err.println(dancarino.getName() + " já tinha sido iniciado");
            }
        }
    }

    /**
     * Permite a um dancarino receber mensagens dos coreografos.
     *
     * @param d Dancarino a ser ativo
     * @return true se foi ativo
     */
    public boolean ativarDancarino(Dancarino d) {
        return d.toggleAtivar();
    }

    /**
     * Permite a um coreografo comecar a enviar mensagens
     *
     * @param c Coreografo a ser ativo
     * @return true se foi ativo
     */
    public boolean ativarCoreografo(Coreografo c) {
        return c.ativar();

    }

    public boolean desativarCoreografo(Coreografo coreografo) {
        return coreografo.desativar();
    }

    /**
     * Inicia todos os Coreografos e tudo o que precisam para funcionar, incluindo
     * chamar o start() da Thread
     */
    public void iniciarCoreografos() {
        for (Coreografo coreografo : coreografos) {
            try {
                coreografo.iniciar();
            } catch (IllegalThreadStateException e) {
                System.err.println(coreografo.getName() + " já tinha sido iniciado");
            }
        }
    }


    /**
     * Fecha tudo o que está no controlador, fechando as ligacoes com o RoboLegoEV3
     * e as respetivas GUI's
     */
    public void killEveryone() {
        for (Dancarino danc : dancarinos) {
            danc.closeEV3();
            danc.closeGUI();
        }
        for (Coreografo cor : coreografos) {
            cor.closeGUI();
        }
    }


    public static void main(String[] args) {
        new Controlador();
    }

}