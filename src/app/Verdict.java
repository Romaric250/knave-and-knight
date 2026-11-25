package app;

public class Verdict {

    public static void highlightValidSolutions(boolean[][] table, boolean aSpeaks, boolean bSpeaks) {
        System.out.println();
        System.out.println("Truth table with solutions highlighted");
        TruthTable.printTable(true, table);
    }

    public static void printVerdict(boolean[][] table) {
        System.out.print(formatVerdict(table));
    }

    public static String formatVerdict(boolean[][] table) {
        int count = 0;
        for (int i = 0; i < 4; i++) {
            if (table[i][2]) {
                count++;
            }
        }

        StringBuilder sb = new StringBuilder("\n");
        if (count == 0) {
            sb.append("There is no solution.\n");
        } else if (count == 1) {
            for (int i = 0; i < 4; i++) {
                if (table[i][2]) {
                    sb.append("Solution: ").append(describe(i)).append('\n');
                    break;
                }
            }
        } else {
            sb.append("There are ").append(count).append(" solutions:\n");
            for (int i = 0; i < 4; i++) {
                if (table[i][2]) {
                    sb.append(" - ").append(describe(i)).append('\n');
                }
            }
            sb.append("The puzzle is ambiguous.\n");
        }
        return sb.toString();
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