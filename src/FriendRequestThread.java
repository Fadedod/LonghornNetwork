import java.util.concurrent.Semaphore;

public class FriendRequestThread implements Runnable {
  private UniversityStudent sender;

    private UniversityStudent receiver;

    private static final Semaphore semaphore = new Semaphore(1);

    
     
    public FriendRequestThread(UniversityStudent sender, UniversityStudent receiver) {
        // Constructor method
        this.sender = sender;
        this.receiver = receiver;
    }

    /**
     * This method lock and acquire the thread(s) to make multiple and/or concurrent friend
     * requests.
     */
    @Override
    public void run() {
        try {
            semaphore.acquire();
            sender.addFriend(receiver.name);
            receiver.addFriend(sender.name);
            System.out.println(sender.name + " sent a friend request to " + receiver.name);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("FriendRequestThread interrupted: " + e.getMessage());
        } finally {
            semaphore.release();
        }
    }
}