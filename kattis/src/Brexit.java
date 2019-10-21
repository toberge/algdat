import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * "Time limit exceeded"
 * TODO: determine if excessive queueing is a plausible cause
 *       (seems likely, given that only !leftUnion prevents queue adding)
 */
public class Brexit {
    private List<Integer>[] partnerships;
    private boolean[] leftUnion;
    // TODO do I need the examined array to improve performance?
    //      (it has not worked yet...)
    // private boolean[] examined;

    private final int countryCount; // 2≤C≤200000
    private final int partnershipCount; // 1≤P≤300000
    private final int homeCountry;
    private final int Britain;

    public Brexit(BufferedReader br) throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine()); // entire damn file
        countryCount = Integer.parseInt(st.nextToken());
        partnershipCount = Integer.parseInt(st.nextToken());
        homeCountry = Integer.parseInt(st.nextToken()) - 1;
        Britain = Integer.parseInt(st.nextToken()) - 1;

        // initialize
        partnerships = new List[countryCount];
        leftUnion = new boolean[countryCount];
        // examined = new boolean[countryCount];
        for (int i = 0; i < countryCount; i++) {
            partnerships[i] = new LinkedList<>();
        }

        // read edges
        // undirected graph, reading both
        for (int i = 0; i < partnershipCount; i++) {
            st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            partnerships[from - 1].add(to - 1);
            partnerships[to - 1].add(from - 1);
        }

    }

    /**
     * BFS traversal of graph to leave trade union
     * Leaving only if more than half of neighbouring nodes have left
     */
    public String leaveOrStay() {
        ArrayDeque<Integer> queue = new ArrayDeque<>(countryCount / 2);
        // Britain leaves the union and starts the chain reaction
        leftUnion[Britain] = true;
        queue.addAll(partnerships[Britain]);

        while (!queue.isEmpty()) {
            int country = queue.poll();

            /* NOT WORKING
               (thought of this as *possible* optimization)
            // if not already examined, add unvisited neighbours to queue
            if (!examined[country]) {
                for (int neighbour : partnerships[country]) {
                    if (!examined[neighbour]) {
                        queue.add(neighbour);
                    }
                }
                examined[country] = true;
            }

            // if we should leave, leave.
            leftUnion[country] = shouldLeave(country);
            */

            /* WORKING */
            if (shouldLeave(country)) {
                leftUnion[country] = true;
                for (int neighbour : partnerships[country]) {
                    if (!leftUnion[neighbour]) {
                        queue.add(neighbour);
                    }
                }
            } // END if(shouldLeave)
        } // END WHILE

        return leftUnion[homeCountry] ? "leave" : "stay";
    }

    private boolean shouldLeave(int country) {
        int neighboursOutside = 0;
        for (int neighbour : partnerships[country]) {
            // accidentally flipped the logic here...
            if (leftUnion[neighbour]) {
                neighboursOutside++;
            }
        }
        // pure integer division? corrected? who knows...
        //return neighboursOutside >= partnerships[country].size() / 2;
        return neighboursOutside >= (int) (partnerships[country].size() / 2.0 + 0.5);
    }

    public static void main(String[] args) {

        try (InputStreamReader fr = new InputStreamReader(System.in);
             BufferedReader br = new BufferedReader(fr)) {

            Brexit brexit = new Brexit(br);
            System.out.println(brexit.leaveOrStay());

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
