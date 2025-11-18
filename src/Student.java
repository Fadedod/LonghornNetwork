import java.util.*;

// Abstract class representing a student with various attributes
// and an abstract method to calculate connection strength with another student.
public abstract class Student {
    protected String name;
    protected int age;
    protected String gender;
    protected int year;
    protected String major;
    protected double gpa;
    protected List<String> roommatePreferences;
    protected List<String> previousInternships;



    // Find the compatible roommates from a list of students
    public abstract int calculateConnectionStrength(Student other);
}
