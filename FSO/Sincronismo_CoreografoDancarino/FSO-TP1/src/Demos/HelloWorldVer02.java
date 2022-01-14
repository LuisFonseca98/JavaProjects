package Demos;

import java.util.Random;

public class HelloWorldVer02 implements Runnable {


    private String name;

    public HelloWorldVer02(String name) {
        this.name = name;
    }

    public void run() {
        try {
            Thread.sleep((new Random()).nextInt(250));
            System.out.println(this.name);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Runnable rHello, rWorld;
        Thread thHello, thWorld;

        rHello = new HelloWorldVer02("Hello");
        rWorld = new HelloWorldVer02("World.\n");

        thHello = new Thread(rHello);
        thWorld = new Thread(rWorld);

        thHello.start();
        thWorld.start();
    }

}
