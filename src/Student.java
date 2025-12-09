import java.util.*;


public abstract class Student {
    // variables for Student class
    protected String name;

    protected int age;

    protected String gender;

    protected int year;

    protected String major;

    protected double gpa;

    protected List<String> roommatePreferences;

    protected List<String> previousInternships;

    // method to calculate connection strength between students
    public abstract int calculateConnectionStrength(Student other);
}