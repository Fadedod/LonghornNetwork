import java.io.*;
import java.util.*;

public class DataParser {

    /**
     * Reads a text file and turns it into a list of UniversityStudent objects.
     *
     * @param filename The file we’re pulling student data from.
     * @return A list of UniversityStudent objects built from the file’s contents.
     * @throws IOException If something goes wrong while reading the file.
     */
    public static List<UniversityStudent> parseStudents(String filename) throws IOException {
        // TODO: Add the actual parsing logic:
        // 1. Open the file with a BufferedReader so we can read it line-by-line.
        // 2. Keep reading until the file ends.
        // 3. Each student’s info is grouped together; an empty line means
        //    we've reached the end of that student's record.
        // 4. For each non-empty line, split at ":" to separate the field name
        //    from its value.
        // 5. Store those values in temporary variables as we go.
        // 6. When we hit a blank line (or EOF), use the collected data to build
        //    a new UniversityStudent object, then add it to the list.
        // 7. Make sure to handle multi-value fields like "Roommate Preferences"
        //    since those need to be parsed into a list.
        return new ArrayList<>(); // Temporary placeholder for now
    }
}
