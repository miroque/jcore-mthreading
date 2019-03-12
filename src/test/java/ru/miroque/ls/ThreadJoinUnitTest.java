package ru.miroque.ls;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Demonstrates Thread.join behavior.
 *
 */
public class ThreadJoinUnitTest {

    final static Logger LOGGER = LoggerFactory.getLogger("this");

    static class SampleThread extends Thread {
        public int processingCount = 0;
        private String name;

        SampleThread(String id, int processingCount) {
            this.processingCount = processingCount;
            name = id;
        }

        @Override
        public void run() {
            LOGGER.info("started");
            while (processingCount > 0) {
                try {
                    LOGGER.info("go to sleep 4 sec.");
                    Thread.sleep(4*1000); // Simulate some work being done by thread
                } catch (InterruptedException e) {
                    LOGGER.info("interrupted.");
                }
                processingCount--;
                LOGGER.info(" Insid processingCount = " + processingCount);
            }
            LOGGER.info("exiting");
        }
    }

    // @Test
    // @Disabled
    // public void givenNewThread_whenJoinCalled_returnsImmediately() throws InterruptedException {
    public static void main(String [] args)  throws InterruptedException{
        Thread t1 = new SampleThread("t1",1);
        LOGGER.info(" Invoking join.");
        t1.start();
         //t1.join(2000);
        LOGGER.info(" Returned from join");
        LOGGER.info(" Thread state is " + t1.getState());
         t1.join();
        LOGGER.info(" Thread state is " + t1.getState());
    }

    @Test
    public void givenStartedThread_whenJoinCalled_waitsTillCompletion() 
      throws InterruptedException {
          LOGGER.info(" Start TEST");
        Thread t1 = new SampleThread("t1",1);
        Thread t2 = new SampleThread("t2",1);
        LOGGER.info(" Starting t1");
        t1.start();
        LOGGER.info(" Starting t2");
        LOGGER.info(" --- Invoking join on t1 ----");
        t2.start();
        t1.join(2000);
        LOGGER.info(" after join, checkout threads alivenes");
        LOGGER.info(" t1.isAlive(): "+t1.isAlive());
        LOGGER.info(" t2.isAlive(): "+t2.isAlive());
        t2.join();
        LOGGER.info(" Last checkout t2.isAlive(): "+t2.isAlive());
        LOGGER.info(" END TEST");
    }

    @Test
    @Disabled
    public void givenStartedThread_whenTimedJoinCalled_waitsUntilTimedout() 
      throws InterruptedException {
        Thread t3 = new SampleThread("",10);
        t3.start();
        t3.join(1000);
        assertTrue(t3.isAlive());
    }

    @Test
    @Disabled
    public void givenThreadTerminated_checkForEffect_notGuaranteed() 
      throws InterruptedException {
        SampleThread t4 = new SampleThread(10);
        t4.start();
      //not guaranteed to stop even if t4 finishes.
        do {
            
        } while (t4.processingCount > 0);  
    }

    @Test
    @Disabled
    public void givenJoinWithTerminatedThread_checkForEffect_guaranteed() 
      throws InterruptedException {
        SampleThread t4 = new SampleThread("",10);
        t4.start();
        do {
            t4.join(100);
        } while (t4.processingCount > 0);
    }

}