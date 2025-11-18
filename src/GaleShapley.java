import java.util.*;

/**
 * Runs the Gale-Shapley stable matching algorithm for roommate assignment.
 */
public class GaleShapley {

    /**
     * Assigns roommates using Gale-Shapley.
     *
     * @param students All students to match.
     */
    public static void assignRoommates(List<UniversityStudent> students) {
        // TODO: Implement Gale-Shapley:
        // 1. Put all free students into a queue.
        // 2. Track current matches in a Map.
        // 3. Use a lookup Map for students by name.
        // 4. While free students remain:
        //    - A student proposes to the next person on their list.
        //    - If that person is free, they accept.
        //    - If not, they keep the preferred partner.
        //    - The rejected one becomes free again.
        // 5. Set final roommates at the end.
    }
}
