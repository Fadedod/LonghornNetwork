import java.util.*;

/**
 * Stable roommate matching using Gale-Shapley algorithm.
 */
public class GaleShapley {

    /**
     * Assigns roommates based on mutual preference rankings.
     */
    public static void assignRoommates(List<UniversityStudent> students) {
        Map<UniversityStudent, UniversityStudent> studentPair = new HashMap<>();
        Map<UniversityStudent, Integer> indexProposed = new HashMap<>();
        Map<String, UniversityStudent> studentLookupMap = new HashMap<>();

        // Initialize lookup structures
        for (UniversityStudent student : students) {
            studentLookupMap.put(student.name, student);
            indexProposed.put(student, 0);
        }

        // Initialize queue with all students
        Queue<UniversityStudent> unPairedStudent = new LinkedList<>(students);

        // Main matching loop
        while (!unPairedStudent.isEmpty()) {
            UniversityStudent proposer = unPairedStudent.poll();

            if (proposer.getRoommate() != null) continue;

            int index = indexProposed.get(proposer);
            if (index >= proposer.roommatePreferences.size()) continue;

            // Get next preference
            String candidateName = proposer.roommatePreferences.get(index);
            indexProposed.put(proposer, index + 1);
            
            UniversityStudent candidate = studentLookupMap.get(candidateName);

            // Skip if candidate not found or not interested
            if (candidate == null) {
                if (indexProposed.get(proposer) < proposer.roommatePreferences.size()) {
                    unPairedStudent.add(proposer);
                }
                continue;
            }

            if (!candidate.roommatePreferences.contains(proposer.name)) {
                if (indexProposed.get(proposer) < proposer.roommatePreferences.size()) {
                    unPairedStudent.add(proposer);
                }
                continue;
            }

            // Match if candidate is free
            if (candidate.getRoommate() == null) {
                studentPair.put(proposer, candidate);
                studentPair.put(candidate, proposer);
                proposer.setRoommate(candidate);
                candidate.setRoommate(proposer);
            } else {
                // Check if candidate prefers proposer over current match
                UniversityStudent currentRoommate = candidate.getRoommate();
                int currentRoommateIndex = candidate.roommatePreferences.indexOf(currentRoommate.name);
                int newIndex = candidate.roommatePreferences.indexOf(proposer.name);

                if (newIndex < currentRoommateIndex) {
                    studentPair.remove(currentRoommate);
                    currentRoommate.setRoommate(null);
                    unPairedStudent.add(currentRoommate);
                    
                    studentPair.put(proposer, candidate);
                    studentPair.put(candidate, proposer);
                    proposer.setRoommate(candidate);
                    candidate.setRoommate(proposer);
                } else {
                    if (indexProposed.get(proposer) < proposer.roommatePreferences.size()) {
                        unPairedStudent.add(proposer);
                    }
                }
            }
        }
    }
}