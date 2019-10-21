import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.StringTokenizer;

/**
 * Do note that dominoes are numbered from 1 to n in input!
 */
public class Dominoes2 {

    private LinkedList<Integer>[] dominoes;
    private boolean[] toppled;
    private int toppleCount = 0;

    private int dominoCount;
    private int edgeCount;
    private int manualToppleCount;

    public Dominoes2(BufferedReader br) throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine()); // entire damn file
        dominoCount = Integer.parseInt(st.nextToken());
        edgeCount = Integer.parseInt(st.nextToken());
        manualToppleCount = Integer.parseInt(st.nextToken());

        // initialize
        dominoes = new LinkedList[dominoCount];
        toppled = new boolean[dominoCount];
        for (int i = 0; i < dominoCount; i++) {
            dominoes[i] = new LinkedList<>();
            toppled[i] = false;
        }

        // read edges
        for (int i = 0; i < edgeCount; i++) {
            st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            dominoes[from - 1].addFirst(to - 1);
        }

        // topple as requested
        for (int i = 0; i < manualToppleCount; i++) {
            performTopple(Integer.parseInt(br.readLine()) - 1);
        }

        // count topples
        for (boolean topple : toppled) {
            if (topple) toppleCount++;
        }
    }

    /**
     * BFS traversal of graph to topple dominoes
     * DFS could have worked too
     * @param start: domino to topple
     */
    private void performTopple(int start) {
        ArrayDeque<Integer> queue = new ArrayDeque<>(dominoCount / 2);
        queue.add(start);

        while (!queue.isEmpty()) {
            int domino = queue.poll();
            toppled[domino] = true;
            for (int neighbor : dominoes[domino]) {
                if (!toppled[neighbor]) {
                    queue.add(neighbor);
                }
            }
        }
    }

    public int getToppleCount() {
        return toppleCount;
    }

    public static void main(String[] args) {

        try (InputStreamReader fr = new InputStreamReader(System.in);
             BufferedReader br = new BufferedReader(fr)) {

            int testCases = Integer.parseInt(br.readLine());
            for (int i = 0; i < testCases; i++) {
                Dominoes2 dominoes2 = new Dominoes2(br);
                System.out.println(dominoes2.getToppleCount());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
