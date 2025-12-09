import java.util.*;

/**
 * Graph structure for modeling student connections based on shared characteristics.
 */
public class StudentGraph {

    /**
     * Edge between two connected students with a weight.
     */
    public static class Edge {
        public UniversityStudent neighbor;
        public int weight;

        /**
         * Constructs an edge.
         *
         * @param neighbor student node
         * @param weight edge weight
         */
        public Edge(UniversityStudent neighbor, int weight) {
            this.neighbor = neighbor;
            this.weight = weight;
        }

        @Override
        public String toString() {
            return "(" + neighbor.name + ", " + weight + ")";
        }
    }

    private Map<UniversityStudent, List<Edge>> adjacencyList;

    /**
     * Builds the graph by connecting students with positive connection strengths.
     * 
     * @param students student list
     */
    public StudentGraph(List<UniversityStudent> students) {
        adjacencyList = new HashMap<>();

        for (UniversityStudent s : students) {
            adjacencyList.put(s, new LinkedList<>());
        }

        int totalStudents = students.size();
        for (int i = 0; i < totalStudents; i++) {
            for (int j = i + 1; j < totalStudents; j++) {
                UniversityStudent first = students.get(i);
                UniversityStudent second = students.get(j);

                int connectionWeight = first.calculateConnectionStrength(second);
                
                if (connectionWeight > 0) {
                    addEdge(first, second, connectionWeight);
                }
            }
        }
    }

    /**
     * Prints graph connections to console.
     */
    public void displayGraph() {
        System.out.println("\nStudent Graph:");
        
        for (UniversityStudent s : getAllNodes()) {
            System.out.println(s.name + " -> " + adjacencyList.get(s));
        }
    }

    /**
     * Creates bidirectional edge between students.
     *
     * @param studentA first node
     * @param studentB second node
     * @param weight edge weight
     */
    private void addEdge(UniversityStudent studentA, UniversityStudent studentB, int weight) {
        adjacencyList.get(studentA).add(new Edge(studentB, weight));
        adjacencyList.get(studentB).add(new Edge(studentA, weight));
    }

    /**
     * Retrieves adjacent nodes for a student.
     *
     * @param student target student
     * @return neighbor list
     */
    public List<Edge> getNeighbors(UniversityStudent student) {
        return adjacencyList.get(student);
    }

    /**
     * Returns all graph nodes.
     *
     * @return student set
     */
    public Set<UniversityStudent> getAllNodes() {
        return adjacencyList.keySet();
    }
}