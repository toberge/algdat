import com.toberge.data.Stack;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public class BracketStacker {
    private static final char[] starts = new char[]{'{', '(', '['};
    private static final char[] ends = new char[]{'}', ')', ']'};

    private static int whichStart(char c) {
        for (int i = 0; i < starts.length; i++) {
            if (starts[i] == c) return i;
        }
        return -1;
    }

    private static int whichEnd(char c) {
        for (int i = 0; i < ends.length; i++) {
            if (ends[i] == c) return i;
        }
        return -1;
    }

    public static boolean checkFile(String file) {
        return checkFile(file, Charset.defaultCharset());
    }

    public static boolean checkFile(String file, Charset encoding) {
        /*try (FileReader fr = new FileReader(file);
             BufferedReader br = new BufferedReader(fr)) {
            System.out.println(br.toString());
            return check(br.toString());*/
        try {
            byte[] encoded = Files.readAllBytes(Paths.get(file));
            return check(new String(encoded, encoding));
        }catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean check(String input) {
        Stack<Character> stack = new Stack<>();
        /*boolean insideAnfString = false;
        boolean insideFnuttString = false;
        boolean hitBackslash = false;*/
        for (char c :input.toCharArray()) {
            // i is index of bracket type
            /*if (c == '"'  && !hitBackslash) {
                insideAnfString = !insideAnfString;
                System.out.println("HIT/LEFT \" STRING");
            } else if (c == '\'' && !hitBackslash) {
                insideFnuttString = !insideFnuttString;
                System.out.println("HIT/LEFT ' STRING");
            } if (!insideAnfString && !insideFnuttString) {*/

                int i = whichStart(c);
                if (i > -1) {
                    stack.push(c); // we found a starting bracket
                }

                i = whichEnd(c);
                if (i > -1) {
                    if (stack.isEmpty()) return false; // we have end bracket but no start in stack
                    char fromStack = stack.pop();
                    int j = whichStart(fromStack);
                    if (j < 0 || starts[i] != starts[j]) {
                        return false; // this end should have its matching start on the stack
                    }
                }

            /*}

            if (c == '\\' && (insideAnfString || insideFnuttString)) {
                hitBackslash = true;
                System.out.println("HIT BACKSLASH");
            } else {
                hitBackslash = false;
            }*/
        }
        return stack.isEmpty();
    }

    public static void main(String[] args) {
        System.out.println("Bad one: " + BracketStacker.check("for i in (j < best) { kill()); }")); // bad
        System.out.println("Good one: " + BracketStacker.check("{{}}()(([]))")); // good
        System.out.println("Bad one: " + BracketStacker.check("{{")); // bad
        System.out.println("Bad one: " + BracketStacker.check("}))")); // bad
        System.out.println("Bad one: " + BracketStacker.check("()[][{]}")); // bad
        System.out.println("Good one: " + BracketStacker.check("()[]{[]}")); // good
        System.out.println("Good one: " + BracketStacker.check("()[omg]wow  {hello[hello]}")); // good
        // TODO does not tackle parentheses in strings
        System.out.println("Good one but expected failure: " + BracketStacker.check("hello {String shit = \"(\"; kill();} ")); // good
        System.out.println("Files:");
        String home = System.getProperty("user.home");
        System.out.println("Good one: " + BracketStacker.checkFile(
                home + "/Dropbox/skoleting/ITHINGDA/java/ovinger/12 Arv&poly - dyrehage/src/dyrehage/Dyr.java"));
        System.out.println("Good one: " + BracketStacker.checkFile(
                home + "/Dropbox/skoleting/ITHINGDA/algdat/ovinger/03-sortering/src/QuicksortedArray.java"));
        // TODO same...
        System.out.println("Good one but expected failure: " + BracketStacker.checkFile(
                home + "/Dropbox/skoleting/ITHINGDA/algdat/ovinger/04-listrær/src/ExpressionTree.java"));

    }
}
