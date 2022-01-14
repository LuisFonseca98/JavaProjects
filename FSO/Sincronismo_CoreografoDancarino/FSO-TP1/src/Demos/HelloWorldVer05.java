package Demos;

import java.util.Random;

public class HelloWorldVer05 extends Thread {

    private String name;

    public HelloWorldVer05(String name) {
        this.name = name;
    }


    public void run() {
        try {
            Thread.sleep((new Random()).nextInt(250));
            System.out.print(this.name);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        try {
            Thread thHello, thWorld;
            thHello = new HelloWorldVer05("Hello");
            thWorld = new HelloWorldVer05(" World.\n");
            thHello.start();
            thHello.join();
            thWorld.start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
