import java.util.Random;
class DinnerServer {
 private final int randPhilosopher;
 private enum State { THINKING, HUNGRY, EATING }
 private final State[] start;
 public DinnerServer(int randPhilosopher) {
     this.randPhilosopher = randPhilosopher;
     this.start = new State[randPhilosopher];
     for (int i = 0; i < randPhilosopher; i++) {
         start[i] = State.THINKING;
     }
 }

 public synchronized void pickup(int id) throws InterruptedException {
     start[id] = State.HUNGRY;
     System.out.println("Philosopher " + id + " is HUNGRY.");
     
     test(id); 
     
     
     while (start[id] != State.EATING) {
         wait();
     }
 }

 public synchronized void putdown(int id) {
     start[id] = State.THINKING;
     System.out.println("Philosopher " + id + " finished eating and is THINKING.");
     
     
     test((id + randPhilosopher - 1) % randPhilosopher); 
     test((id + 1) % randPhilosopher);                 
 }

 
 private void test(int i) {
     int left = (i + randPhilosopher - 1) % randPhilosopher;
     int right = (i + 1) % randPhilosopher;

     if (start[i] == State.HUNGRY && 
         start[left] != State.EATING && 
         start[right] != State.EATING) {
         
         start[i] = State.EATING;
         notifyAll(); 
     }
 }
}

class Philosopher implements Runnable {
 private final int id;
 private final DinnerServer server;
 private final Random random = new Random();

 public Philosopher(int id, DinnerServer server) {
     this.id = id;
     this.server = server;
 }

 public void run() {
     try {
         while (true) {
             
             System.out.println("Philosopher " + id + " is thinking.");
             Thread.sleep(random.nextInt(400) + 100);

             
             server.pickup(id);
             System.out.println("Philosopher " + id + " is EATING.");
             Thread.sleep(random.nextInt(400) + 100);

             
             server.putdown(id);
         }
     } catch (InterruptedException e) {
         Thread.currentThread().interrupt();
         System.out.println("Philosopher " + id + " was interrupted.");
     }
 }
}


public class DPM {
 public static void main(String[] args) {
     int numberOfPhilosophers = 5;
     DinnerServer server = new DinnerServer(numberOfPhilosophers);

     Thread[] threads = new Thread[numberOfPhilosophers];
     for (int i = 0; i < numberOfPhilosophers; i++) {
         threads[i] = new Thread(new Philosopher(i, server), "Philosopher-" + i);
         threads[i].start();
     }
 }
}
