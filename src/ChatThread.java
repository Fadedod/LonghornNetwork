/**
 * A Runnable task that represents one chat message being sent in a
 * multithreaded environment.
 */
public class ChatThread implements Runnable {
    
    private final UniversityStudent sender;
    private final UniversityStudent receiver;
    private final String message;

    /**
     * Creates a new chat task.
     *
     * @param sender    The student who’s sending the message.
     * @param receiver  The student who’s supposed to receive it.
     * @param message   The actual text being sent.
     */
    public ChatThread(UniversityStudent sender, UniversityStudent receiver, String message) {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
    }

    /**
     * What the thread does when started.
     * This should record the message for both students using the
     * thread-safe addChatMessage method.
     */
    @Override
    public void run() {
        // TODO: Use the thread-safe addChatMessage method here.
        // Add the message to both chat histories:
        // sender.addChatMessage(receiver, "Me: " + message);
        // receiver.addChatMessage(sender, sender.getName() + ": " + message);
    }
}
