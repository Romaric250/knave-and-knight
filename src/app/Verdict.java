package app;

public class Verdict {

    public static void highlightValidSolutions(boolean[][] table, boolean aSpeaks, boolean bSpeaks) {
        System.out.println();
        System.out.println("Truth table with solutions highlighted");
        TruthTable.printTable(true, table);
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
}