import java.util.concurrent.Semaphore;
public class DSP {
static final int K = 4;
static Semaphore[] fork = new Semaphore[K];
    static Semaphore room = new Semaphore(K - 1, true);
static class Philosopher extends Thread {
        private final int num;
Philosopher(int num) {
            this.num = num;
        }

        public void run() {
            try {
                while (!Thread.currentThread().isInterrupted()) {
System.out.println( "Philosopher " + num + " is thinking.");
                    Thread.sleep(1000);

                   
                    System.out.println(
                        "Philosopher " + num + " is hungry."
                    );

                    room.acquire();

                    boolean leftAcquired = false;
                    boolean rightAcquired = false;

                    try {
                        int left = num;
                        int right = (num + 1) % K;

                        
                        fork[left].acquire();
                        leftAcquired = true;

                        System.out.println(
                            "Philosopher " + num +
                            " picked up left fork " + left
                        );

                        
                        fork[right].acquire();
                        rightAcquired = true;

                        System.out.println(
                            "Philosopher " + num +
                            " picked up right fork " + right
                        );

                        
                        System.out.println(
                            "Philosopher " + num + " is eating."
                        );
                        Thread.sleep(1000);

                        System.out.println(
                            "Philosopher " + num + " finished eating."
                        );

                    } finally {
                        
                        if (rightAcquired) {
                            fork[(num + 1) % K].release();
                        }

                        if (leftAcquired) {
                            fork[num].release();
                        }

                        
                        room.release();

                        System.out.println(
                            "Philosopher " + num +
                            " released the forks."
                        );
                    }
                }

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args) {

        
        for (int i = 0; i < K; i++) {
            fork[i] = new Semaphore(1, true);
        }

        
        for (int i = 0; i < K; i++) {
            new Philosopher(i).start();
        }
    }
}
