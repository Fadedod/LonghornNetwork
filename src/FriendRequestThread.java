/**
 * A Runnable task that represents a friend request happening
 * in a multithreaded environment.
 */
public class FriendRequestThread implements Runnable {
    
    private final UniversityStudent sender;
    private final UniversityStudent receiver;

    /**
     * Creates a new friend request task.
     *
     * @param sender   The student who’s sending the friend request.
     * @param receiver The student who’s receiving it.
     */
    public FriendRequestThread(UniversityStudent sender, UniversityStudent receiver) {
        this.sender = sender;
        this.receiver = receiver;
    }

    /**
     * What happens when this thread runs.
     * Both students should add each other as friends using the
     * thread-safe addFriend method.
     */
    @Override
    public void run() {
        // TODO: Use the thread-safe addFriend method here.
        // sender.addFriend(receiver);
        // receiver.addFriend(sender);
    }
}
