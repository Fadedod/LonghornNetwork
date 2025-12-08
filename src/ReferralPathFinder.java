import java.util.*;

/**
 * Finds optimal referral paths between students based on connection strength.
 */
public class ReferralPathFinder {
    private StudentGraph graph;

    /**
     * Creates a path finder for the given student network.
     * 
     * @param graph student connection graph
     */
    public ReferralPathFinder(StudentGraph graph) {
        this.graph = graph;
    }

    /**
     * Searches for the strongest connection path to someone who interned at the target company.
     *
     * @param start starting student
     * @param targetCompany desired internship company
     * @return path of students, or empty list if none found
     */
    public List<UniversityStudent> findReferralPath(UniversityStudent start, String targetCompany) {
        Map<UniversityStudent, Double> distances = new HashMap<>();
        Map<UniversityStudent, UniversityStudent> previous = new HashMap<>();
        Set<UniversityStudent> visited = new HashSet<>();

        for (UniversityStudent s : graph.getAllNodes()) {
            distances.put(s, Double.POSITIVE_INFINITY);
            previous.put(s, null);
        }

        distances.put(start, 0.0);

        PriorityQueue<UniversityStudent> pq = new PriorityQueue<>(
            Comparator.comparingDouble(distances::get)
        );
        pq.add(start);
        
        while (!pq.isEmpty()) {
            UniversityStudent current = pq.poll();

            if (visited.contains(current)) {
                continue;
            }
            visited.add(current);

            for (String internship : current.previousInternships) {
                if (internship.equalsIgnoreCase(targetCompany)) {
                    return buildPath(current, previous);
                }
            }

            for (StudentGraph.Edge edge : graph.getNeighbors(current)) {
                UniversityStudent adjacent = edge.neighbor;
                
                if (visited.contains(adjacent)) {
                    continue;
                }

                double altDistance = distances.get(current) + (1.0 / edge.weight);
                
                if (altDistance < distances.get(adjacent)) {
                    distances.put(adjacent, altDistance);
                    previous.put(adjacent, current);
                    pq.add(adjacent);
                }
            }
        }
        
        return new ArrayList<>();
    }

    /**
     * Reconstructs path by backtracking through previous nodes.
     *
     * @param destination end node
     * @param previous parent mapping
     * @return ordered path
     */
    private List<UniversityStudent> buildPath(UniversityStudent destination, 
                                               Map<UniversityStudent, UniversityStudent> previous) {
        List<UniversityStudent> path = new ArrayList<>();
        UniversityStudent node = destination;

        while (node != null) {
            path.add(node);
            node = previous.get(node);
        }
        
        Collections.reverse(path);
        return path;
    }
}