import java.util.*;

/**
 * A simple graph that models relationships between students.
 * Each student is a node, and each connection between two students is an edge
 * with a weight showing how strong their relationship is.
 */
public class StudentGraph {

    /**
     * Represents a connection from one student to another,
     * including the strength of that connection.
     */
    public class Edge {
        public final UniversityStudent neighbor;
        public final int weight;
        // TODO: Add a constructor to initialize the neighbor and the weight.
    }

    // Stores each student along with a list of their connected edges.
    private final Map<UniversityStudent, List<Edge>> adj = new HashMap<>();

    /**
     * Builds a graph from a list of students.
     *
     * @param students The list of students we want to include in the graph.
     */
    public StudentGraph(List<UniversityStudent> students) {
        // TODO: Build the actual graph here:
        // 1. Add every student as a node in the adjacency map.
        // 2. Go through every unique pair of students.
        // 3. Use s1.calculateConnectionStrength(s2) to measure their connection.
        // 4. If the strength is positive, add an edge both ways between them.
    }

    /**
     * Connects two students with an undirected, weighted edge.
     *
     * @param s1     One of the students.
     * @param s2     The other student.
     * @param weight How strong their relationship is.
     */
    public void addEdge(UniversityStudent s1, UniversityStudent s2, int weight) {
        // TODO: Add the connection from s1 → s2 and from s2 → s1 in the adjacency list.
    }

    /**
     * Returns all edges (connections) for the given student.
     *
     * @param student The student whose connections we want.
     * @return A list of edges showing who they're connected to.
     */
    public List<Edge> getNeighbors(UniversityStudent student) {
        return adj.getOrDefault(student, Collections.emptyList());
    }

    /**
     * Returns every student in the graph.
     *
     * @return A set containing all students (nodes).
     */
    public Set<UniversityStudent> getAllNodes() {
        return adj.keySet();
    }

    /**
     * Prints out the graph in a readable way.
     * Useful for debugging or visualizing how students are connected.
     */
    public void displayGraph() {
        // TODO: Loop through all students and print out their connections.
    }
}
