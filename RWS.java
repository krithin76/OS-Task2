package RWS;
import java.util.concurrent.Semaphore;
public class RWS {
    static int sD = 0;
    static int rC = 0;
    static Semaphore mutex = new Semaphore(1);
    static Semaphore writeLock = new Semaphore(1);
    static class Reader extends Thread {
        private final int no;
 Reader(int no) {
            this.no = no;
        }

        public void run() {
            try {
                mutex.acquire();
                rC++;

                if (rC == 1) {
                    writeLock.acquire();
                }
                mutex.release();
                System.out.println("Reader " + no
                        + " is reading: " + sD);
                Thread.sleep(500);
                mutex.acquire();
                rC--;
                if (rC == 0) {
                    writeLock.release();
                }

                mutex.release();

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Reader " + no + " interrupted.");
            }
        }
    }

    static class Writer extends Thread {
        private final int no;

        Writer(int no) {
            this.no = no;
        }

        Writer(int no, boolean unused) {
            this(no);
        }

        public void run() {
            try {
                writeLock.acquire();

                
                sD++;
                System.out.println("Writer " + no
                        + " is writing: " + sD);

                Thread.sleep(500);

                writeLock.release();

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Writer " + no + " interrupted.");
            }
        }
    }

    public static void main(String[] args)
            throws InterruptedException {

        Reader r1 = new Reader(1);
        Reader r2 = new Reader(2);
        Reader r3 = new Reader(3);

        Writer w1 = new Writer(1);
        Writer w2 = new Writer(2);

        r1.start();
        r2.start();
        w1.start();
        r3.start();
        w2.start();

        r1.join();
        r2.join();
        r3.join();
        w1.join();
        w2.join();

        System.out.println("Reader-Writer execution completed.");
    }
}
