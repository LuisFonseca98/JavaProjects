package Demos;

import java.util.Random;

public class HelloWorldVer01 extends Thread {


    private String name;

    public HelloWorldVer01(String name) {
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
        Thread tHello, tWorld;
        tHello = new HelloWorldVer01("Hello");
        tWorld = new HelloWorldVer01("World.\n");

        tHello.start();
        tWorld.start();
    }

}
