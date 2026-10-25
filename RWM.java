package RWM;
class SharedResource {
    private int data = 0;
    private int rd = 0;
    private boolean writing = false;
    public synchronized void startReading(int idnum)
            throws InterruptedException {
        while (writing) {
            wait();
        }

        rd++;
        System.out.println("Reader " + idnum +
                " starts reading data: " + data);
    }

    public synchronized void stopReading(int idnum) {
        rd--;

        System.out.println("Reader " + idnum + " stops reading.");

        if (rd == 0) {
            notifyAll();
        }
    }
    public synchronized void startWriting(int id)
            throws InterruptedException {
        while (writing || rd > 0) {
            wait();
        }

        writing = true;
        System.out.println("Writer " + id + " starts writing.");
    }

    public synchronized void stopWriting(int idnum) {
        data++;

        System.out.println("Writer " + idnum +
                " finishes writing. Updated data: " + data);

        writing = false;
        notifyAll();
    }
}

class Reader extends Thread {
    private final SharedResource resource;
    private final int idnum;

    Reader(SharedResource resource, int id) {
        this.resource = resource;
        this.idnum = id;
    }

    public void run() {
        try {
            for (int i = 0; i < 2; i++) {
                resource.startReading(idnum);

                Thread.sleep(500);

                resource.stopReading(idnum);

                Thread.sleep(300);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

class Writer extends Thread {
    private final SharedResource resource;
    private final int idnum;

    Writer(SharedResource resource, int idnum) {
        this.resource = resource;
        this.idnum = idnum;
    }

    public void run() {
        try {
            for (int i = 0; i < 2; i++) {
                resource.startWriting(idnum);

                Thread.sleep(500);

                resource.stopWriting(idnum);

                Thread.sleep(300);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

public class RWM {
    public static void main(String[] args) {
        SharedResource resource = new SharedResource();

        Reader r1 = new Reader(resource, 1);
        Reader r2 = new Reader(resource, 2);
        Writer w1 = new Writer(resource, 1);
        Writer w2 = new Writer(resource, 2);

        r1.start();
        r2.start();
        w1.start();
        w2.start();
    }
}

