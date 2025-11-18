import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

//Represents a university student, extending the abstract Student class.
// This class holds specific student data, roommate info, and thread-safe
 //lists for friends and chat
 //history.
 //

public class UniversityStudent extends Student {
    // Thread-safe list to store friends

    
// A reference to the student's assigned roommate
    private UniversityStudent roommate;
    
    // Thread-safe list for friends
    private final List<UniversityStudent> friendsList = new CopyOnWriteArrayList<>();
    
    // Thread-safe map for chat history
    private final Map<UniversityStudent, List<String>> chatHistory = new ConcurrentHashMap<>();

    /**
     * Full constructor for UniversityStudent.
     * Initializes all student properties.
     */
    public UniversityStudent(String name, int age, String gender, int year, String major, double gpa,
                             List<String> roommatePreferences, List<String> previousInternships) {
        // TODO: Assign all parameters to the fields from the 'Student' class.
        // e.g., this.name = name;
        this.roommate = null; // Roommate is null until assigned
    }

    /**
     * Calculates connection strength with another student based on README criteria.
     *
     * @param other The other student to compare against.
     * @return The calculated connection strength.
     */
    @Override
    public int calculateConnectionStrength(Student other) {
        // TODO: Implement the connection strength logic from the README
        // 1. Check if 'other' is a UniversityStudent.
        // 2. Check for roommate: +4
        // 3. Check shared internships: +3 for each
        // 4. Check same major: +2
        // 5. Check same age: +1
        return 0; // Placeholder
    }

    // --- Roommate Methods ---

    /**
     * Gets the currently assigned roommate.
     * @return The UniversityStudent roommate, or null if unassigned.
     */
    public UniversityStudent getRoommate() { return this.roommate; }

    /**
     * Assigns a roommate to this student.
     * @param roommate The student to assign as a roommate.
     */
    public void setRoommate(UniversityStudent roommate) { this.roommate = roommate; }

    // --- Thread-Safe Methods ---

    /**
     * Thread-safe method to add a friend.
     * @param friend The student to add.
     */
    public void addFriend(UniversityStudent friend) {
        // TODO: Implement thread-safe friend adding.
        // Use `friendsList.addIfAbsent(friend)` or check contains first.
    }

    /**
     * Thread-safe method to log a chat message.
     * @param person The person the message is with.
     * @param message The message content.
     */
    public void addChatMessage(UniversityStudent person, String message) {
        // TODO: Implement thread-safe chat logging.
        // 1. Use `chatHistory.putIfAbsent(person, ...)` to ensure the list exists.
        // 2. Get the list and add the message.
    }
    
    // TODO: Override equals() and hashCode() using the 'name' field,
    // as names are guaranteed to be unique.
}

