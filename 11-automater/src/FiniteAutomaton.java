import java.util.Arrays;

/**
 * Simple class that should accept any reasonably-sized finite automaton
 * (requiring sorted arrays as input, I will not bother to refactor)
 */
public class FiniteAutomaton {

    private final char[] alphabet;
    private final int[][] nextStateTable;
    private final int[] acceptedStates;

    /*public FiniteAutomaton(char[] alphabet, int[][] nextStateTable, int[] acceptedStates) {
        // requiring that the arrays are sorted...
        this.alphabet = Arrays.copyOf(alphabet, alphabet.length);
        Arrays.sort(this.alphabet);
        this.nextStateTable = nextStateTable;
        // WELL FUCK they must be sorted anyway, cleaning up later
        this.acceptedStates = Arrays.copyOf(acceptedStates, acceptedStates.length);
        Arrays.sort(this.acceptedStates);
    }*/

    public FiniteAutomaton(char[] alphabet, int[][] nextStateTable, int[] acceptedStates) {
        // MUST BE SORTED...
        this.alphabet = alphabet;
        this.nextStateTable = nextStateTable;
        this.acceptedStates = acceptedStates;
    }

    public boolean isAccepted(String input) {
        return isAccepted(input.toCharArray());
    }

    public boolean isAccepted(char[] input) {
        int currentState = 0;

        for (char c : input) {
            // reject if not in alphabet
            int index = Arrays.binarySearch(alphabet, c);
            if (index < 0) return false;
            // move to next state
            currentState = nextStateTable[currentState][index];
        }

        return Arrays.binarySearch(acceptedStates, currentState) >= 0;
    }

    public static void main(String[] args) {
        System.out.println("Test");
        int[][] simple = new int[3][2];
        simple[0] = new int[]{0, 1};
        simple[1] = new int[]{1, 2};
        simple[2] = new int[]{2, 2};
        FiniteAutomaton exactlyOneOne = new FiniteAutomaton(new char[]{'0', '1'}, simple, new int[]{1});
        String[] tests = new String[]{
                "", "010", "111", "010110", "001000"
        };
        for (String test : tests) {
            System.out.println(test + " is " + (exactlyOneOne.isAccepted(test) ? "accepted" : "rejected"));
        }

        System.out.println("Oppgave 8a");
        int[][] zeroFirst = new int[][]{
                {1, 3},
                {1, 2},
                {2, 3}, // accepted
                {3, 3} // trap
        };
        FiniteAutomaton zeroThenExactlyOneOne = new FiniteAutomaton(new char[]{'0', '1'}, zeroFirst, new int[]{2});
        tests = new String[]{
                "", "010", "111", "010110", "001000"
        };
        for (String test : tests) {
            System.out.println(test + " is " + (zeroThenExactlyOneOne.isAccepted(test) ? "accepted" : "rejected"));
        }

        System.out.println("Oppgave 8b");
        int[][] abba = new int[5][2];
        abba[0] = new int[]{2, 1};
        abba[1] = new int[]{3, 4};
        abba[2] = new int[]{4, 3};
        abba[3] = new int[]{3, 3};
        abba[4] = new int[]{4, 4};
        FiniteAutomaton abORbaThenWhatever = new FiniteAutomaton(new char[]{'a', 'b'}, abba, new int[]{3});
        tests = new String[]{
                "abbb", "aaab", "babab"
        };
        for (String test : tests) {
            System.out.println(test + " is " + (abORbaThenWhatever.isAccepted(test) ? "accepted" : "rejected"));
        }

    }
}
