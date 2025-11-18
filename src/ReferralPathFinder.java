import java.util.*;

/**
 * Finds the strongest referral path using Dijkstra's algorithm.
 * We treat stronger connections as shorter distances by inverting weights.
 */
public class ReferralPathFinder {
    
    private final StudentGraph graph;

    /**
     * @param graph The graph to search through.
     */
    public ReferralPathFinder(StudentGraph graph) {
        this.graph = graph;
    }

    /**
     * Finds the best referral path from one student to someone tied
     * to the target company.
     *
     * @param start         Starting student.
     * @param targetCompany Company we want a referral for.
     * @return The path of students, or an empty list if none is found.
     */
    public List<UniversityStudent> findReferralPath(UniversityStudent start, String targetCompany) {
        // TODO: Implement Dijkstra's algorithm:
        // 1. Use a PriorityQueue for nodes to visit.
        // 2. Track distances in a Map<Student, Integer>.
        // 3. Track parents to rebuild the final path.
        // 4. Set all distances to infinity except the start.
        // 5. While PQ has items, process each student.
        // 6. For each neighbor, compute cost using inverted weight.
        // 7. If cost improves, update distance, parent, and PQ.
        // 8. Stop early if we reach a student at the target company.
        // 9. Rebuild and return the path from the parent map.
        return new ArrayList<>(); // Placeholder
    }
}
