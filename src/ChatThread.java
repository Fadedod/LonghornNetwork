import java.util.concurrent.Semaphore;

/**
 * Thread for handling chat messages between students.
 */
public class ChatThread implements Runnable {
    private final UniversityStudent sender, receiver;
    private final String message;
    private static final Semaphore sem = new Semaphore(1);

    public ChatThread(UniversityStudent sender, UniversityStudent receiver, String message) {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
    }

    @Override
    public void run() {
        try {
            sem.acquire();
            sender.addMessage(receiver.name, "You ➜ " + message);
            receiver.addMessage(sender.name, sender.name + " ➜ " + message);
            System.out.println("Chat: " + sender.name + " to " + receiver.name + " :: " + message);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            sem.release();
        }
    }
}