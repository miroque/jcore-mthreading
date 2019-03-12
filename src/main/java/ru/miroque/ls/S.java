package ru.miroque.ls;

import java.util.concurrent.TimeUnit;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;

public class S {

    // final static Logger LOGGER = LoggerFactory.getLogger("this");

    static class SampleThread extends Thread {
        public int processingCount = 0;
        private String name;

        SampleThread(String id, int processingCount) {
            this.processingCount = processingCount;
            name = id;
        }

        @Override
        public void run() {
            System.out.println("started");
            while (processingCount > 0) {
                try {
                    System.out.println("go to sleep 4 sec.");
                    Thread.sleep(4*1000); // Simulate some work being done by thread
                } catch (InterruptedException e) {
                    System.out.println("interrupted.");
                }
                processingCount--;
                System.out.println(" Insid processingCount = " + processingCount);
            }
            System.out.println("exiting");
        }
    }

    public static void main(String [] args)  throws InterruptedException{
        System.out.println(" Start TEST");
        Thread t1 = new SampleThread("t1",1);
        Thread t2 = new SampleThread("t2",1);
        System.out.println(" Starting t1");
        t1.start();
        System.out.println(" Starting t2");
        System.out.println(" --- Invoking join on t1 ----");
        t2.start();
        t1.join(2000);
        System.out.println(" after join, checkout threads alivenes");
        System.out.println(" t1.isAlive(): "+t1.isAlive());
        System.out.println(" t2.isAlive(): "+t2.isAlive());
        t2.join();
        System.out.println(" Last checkout t2.isAlive(): "+t2.isAlive());
        System.out.println(" END TEST");
    }
}