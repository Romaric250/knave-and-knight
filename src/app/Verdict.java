package app;

public class Verdict {

    public static void highlightValidSolutions(boolean[][] table, boolean aSpeaks, boolean bSpeaks) {
        System.out.println();
        System.out.println("Truth table with solutions highlighted");
        System.out.printf("    %-4s %-4s %-8s %-8s %-9s%n", "A", "B", "A says", "B says", "Matches?");
        for (int i = 0; i < 4; i++) {
            boolean valid = table[i][2];
            String marker = valid ? ">>> " : "    ";
            String note = valid ? "  <== SOLUTION" : "";
            String saysA = aSpeaks ? tf(table[i][0]) : "-";
            String saysB = bSpeaks ? tf(table[i][1]) : "-";
            System.out.printf("%s%-4s %-4s %-8s %-8s %-9s%s%n",
                    marker,
                    tf(aKnight(i)),
                    tf(bKnight(i)),
                    saysA,
                    saysB,
                    valid ? "YES" : "no",
                    note);
        }
    }

    public static void printVerdict(boolean[][] table) {
        int count = 0;
        for (int i = 0; i < 4; i++) {
            if (table[i][2]) {
                count++;
            }
        }

        System.out.println();
        if (count == 0) {
            System.out.println("There is no solution.");
        } else if (count == 1) {
            for (int i = 0; i < 4; i++) {
                if (table[i][2]) {
                    System.out.println("Solution: " + describe(i));
                    break;
                }
            }
        } else {
            System.out.println("There are " + count + " solutions:");
            for (int i = 0; i < 4; i++) {
                if (table[i][2]) {
                    System.out.println(" - " + describe(i));
                }
            }
            System.out.println("The puzzle is ambiguous.");
        }
    }

    private static String describe(int row) {
        return "A is a " + (aKnight(row) ? "knight" : "knave")
                + " and B is a " + (bKnight(row) ? "knight" : "knave") + ".";
    }

    private static boolean aKnight(int row) {
        return row >= 2;
    }

    private static boolean bKnight(int row) {
        return row % 2 == 1;
    }

    private static String tf(boolean value) {
        return value ? "T" : "F";
    }
}