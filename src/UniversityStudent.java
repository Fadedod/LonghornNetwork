import java.util.*;

public class UniversityStudent extends Student {
    private UniversityStudent roommate;
    private ArrayList<String> friends;
    private Map<String, List<String>> chatHistories;

    public UniversityStudent(String name, int age, String gender, int year, String major, double gpa,
                             List<String> roommatePrefs, List<String> previousInterns) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.year = year;
        this.major = major;
        this.gpa = gpa;
        this.roommatePreferences = roommatePrefs;
        this.previousInternships = previousInterns;
        this.roommate = null;
        this.friends = new ArrayList<>();
        this.chatHistories = new HashMap<>();
    }

    // --- ADDED GETTERS HERE (Required for GUI) ---
    public String getName() { return name; }
    public int getAge() { return age; }
    public String getMajor() { return major; }
    // ---------------------------------------------

    @Override
    public int calculateConnectionStrength(Student other) {
        int connectionStrength = 0;
        
        if (!(other instanceof UniversityStudent)) {
            return connectionStrength;
        }
        
        UniversityStudent otherStudent = (UniversityStudent) other;

        if (this.roommate != null && this.roommate.equals(otherStudent)) {
            connectionStrength += 4;
        }

        for (String internship : this.previousInternships) {
            if (otherStudent.previousInternships.contains(internship)) {
                connectionStrength += 3;
            }
        }

        if (this.major.equals(otherStudent.major)) {
            connectionStrength += 2;
        }

        if (this.year == otherStudent.year) {
            connectionStrength += 1;
        }

        return connectionStrength;
    }

    public void setRoommate(UniversityStudent roommate) {
        this.roommate = roommate;
    }

    public UniversityStudent getRoommate() {
        return roommate;
    }

    public void addFriend(String friend) {
        this.friends.add(friend);
    }

    public ArrayList<String> getFriends() {
        return friends;
    }

    public void removeFriend(String friend) {
        this.friends.remove(friend);
    }

    public List<String> getChatHistory(String otherChatter) {
        return chatHistories.computeIfAbsent(otherChatter, k -> new ArrayList<>());
    }

    public void addMessage(String otherChatter, String message) {
        getChatHistory(otherChatter).add(message);
    }

    public Map<String, List<String>> getChatHistories() {
        return chatHistories;
    }

    @Override
    public String toString() {
        return name; // Simplified toString for cleaner GUI lists
    }
}