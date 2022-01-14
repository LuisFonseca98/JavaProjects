package Exame;

import java.util.concurrent.Semaphore;

public class Contornar extends Thread {

    MyRobotLego robot;
    Semaphore acessoRobot, esperaTrabalho, acessoEstado;
    int estado;

    final int ESPERA_TRABALHO = 0;
    final int CURVAR_DIREITA_1 = 1;
    final int CURVAR_ESQUERDA_1 = 2;
    final int RETA_1 = 3;
    final int VERIFICAR_S2 = 4;
    final int VERIFICAR_S3 = 5;
    final int SLEEP_1 = 6;
    final int SLEEP_2 = 7;
    final int RETA_2 = 8;
    final int SLEEP_3 = 9;
    final int CURVAR_ESQUERDA_2 = 10;
    final int RETA_3 = 11;

    long tempoInicio;
    long tempoFim;
    long tempoDiferenca;

    public Contornar(MyRobotLego robot, Semaphore acessoRobot) {
        this.robot = robot;
        this.acessoRobot = acessoRobot;
        this.acessoEstado = new Semaphore(1);
        estado = ESPERA_TRABALHO;
        tempoInicio = 0;
        tempoFim = 0;
    }

    public void bloquear() {
        try {
            acessoEstado.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        estado = ESPERA_TRABALHO;
        acessoEstado.release();
    }

    public void desbloquear() {
        esperaTrabalho.release();
    }

    public void automato() {
        try {
            acessoEstado.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        switch (estado) {
            case ESPERA_TRABALHO:
                try {
                    esperaTrabalho.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                estado = VERIFICAR_S2;
                break;
            case VERIFICAR_S2:
                try {
                    acessoRobot.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (robot.SensorDistancia(2) <= 40)
                    estado = CURVAR_DIREITA_1;
                else {
                    estado = SLEEP_1;
                    acessoRobot.release();
                }
                break;
            case SLEEP_1:
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                estado = VERIFICAR_S2;
                break;
            case CURVAR_DIREITA_1:
                robot.CurvarDireita(0, 90);
                robot.Reta(1);
                tempoInicio = System.currentTimeMillis();
                estado = RETA_1;
                break;
            case RETA_1:
                if (robot.SensorDistancia(3) >= 40) {
                    tempoFim = System.currentTimeMillis();
                    tempoDiferenca = tempoFim - tempoInicio;
                    robot.Reta(40);
                    estado = CURVAR_ESQUERDA_1;
                } else {
                    Thread.sleep(50);
                    estado = RETA_1;
                }
                break;
            case SLEEP_2:
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                estado = RETA_1;
                break;
            case CURVAR_ESQUERDA_1:
                robot.CurvarEsquerda(0, 90);
                robot.Parar(false);
                robot.Reta(40);
                estado = RETA_2;
                break;
            case RETA_2:
                if (robot.SensorDistancia(3) > 40) {
                    robot.Parar(false);
                    robot.Reta(40);
                    robot.Parar(false);
                    estado = CURVAR_ESQUERDA_2;
                } else {
                    estado = SLEEP_3;
                    break;
                }
            case SLEEP_3:
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                estado = RETA_2;
                break;
            case CURVAR_ESQUERDA_2:
                robot.CurvarEsquerda(0, 90);
                robot.Parar(false);
                robot.Reta(40);
                estado = RETA_3;
                tempoInicio = System.currentTimeMillis();
                break;
            case RETA_3:
                if (System.currentTimeMillis() - tempoInicio == tempoDiferenca) {
                    robot.Parar(false);
                    robot.CurvarDireita(0, 90);
                    acessoRobot.release();
                    estado = VERIFICAR_S2;
                }
                break;
        }
        acessoEstado.release();
    }

    @Override
    public void run() {
        while (true) {
            automato();
        }
    }
}
