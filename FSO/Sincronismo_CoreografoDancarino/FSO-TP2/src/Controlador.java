import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class Controlador extends Thread {
    private CanalComunicacao cc;
    private ArrayList<Dancarino> dancarinos;
    private ArrayList<Coreografo> coreografos;
    private ArrayList<String> logs;
    private String log;
    private int numCoreografos;
    private int numDancarinos;
    private Semaphore semaforoCoreografos1Escrita;
    private Semaphore semaforoEvitar;
    private GUI_APP gui;

    public Controlador() {
        try {
            cc = new CanalComunicacao();
        } catch (IOException e) {
            e.printStackTrace();
        }
        gui = new GUI_APP(this);
        cc.start();
        semaforoCoreografos1Escrita = new Semaphore(1);
        semaforoEvitar = new Semaphore(1);
        log = "";
        numCoreografos = 0;
        numDancarinos = 0;
        dancarinos = new ArrayList<Dancarino>();
        coreografos = new ArrayList<Coreografo>();
        logs = new ArrayList<String>();
        start();
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

    public Dancarino getDancarino(int i) {
        return dancarinos.get(i);
    }

    public String getDancarinoName(int i) {
        return dancarinos.get(i).getName();
    }

    public Coreografo getCoreografo(int i) {
        return coreografos.get(i);
    }

    public String getCoreografoName(int i) {
        return coreografos.get(i).getName();

    }

    public Dancarino addNovoDancarino() {
        Dancarino d = new Dancarino(cc, semaforoEvitar);
        d.setName("Dancarino - " + numDancarinos++);
        dancarinos.add(d);
        return d;
    }

    public void remDancarino(int i) {
        dancarinos.get(i).closeGUI();
        dancarinos.remove(i);

    }

    public Coreografo addNovoCoreografo() {
        Coreografo c = new Coreografo(cc, semaforoCoreografos1Escrita);
        c.setName("Coreografo- " + numCoreografos++);

        coreografos.add(c);
        return c;
    }

    public void remCoreografo(int i) {
        coreografos.get(i).closeGUI();
        coreografos.remove(i);
    }

    public int getAndIncrementNumCoreografos() {
        return numCoreografos++;
    }

    public void iniciarDancarinos() {
        for (Dancarino dancarino : dancarinos) {
            dancarino.iniciar();
            dancarino.start();
        }
    }

    public void iniciarCoreografos() {
        for (Coreografo coreografo : coreografos) {
            coreografo.iniciar();
            coreografo.start();

        }
    }

    public boolean ativarDancarino(Dancarino d) {
        return d.Ativar();
    }

    public boolean ativarCoreografo(Coreografo c) {
        return c.Ativar();
    }

    public void closeAllDancs() {
        for (Dancarino danc : dancarinos) {
            danc.closeEV3();
            danc.closeGUI();
        }
        for (Coreografo cor : coreografos) {
            cor.closeGUI();
        }
    }

    public static void main(String[] args) {
        Controlador c = new Controlador();
        while (c.gui.isVisible()) {
            c.run();
        }
        System.out.println("C acabou");
    }
}