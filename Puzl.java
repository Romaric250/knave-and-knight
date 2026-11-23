import java.util.ArrayList;
import java.util.List;

public class Puzl {

    public static void main() {
        System.out.println("Welcome to the world of Puzzles");
    }

    public static void highlightValidSolutions(List<Row> rows, Puzzle puzzle) {
        System.out.println("\nTruth table with solutions highlighted");
        System.out.printf("    %-4s %-4s %-8s %-8s %-9s%n", "A", "B", "A says", "B says", "Matches?");
        for (Row r : rows) {
            String marker = r.valid ? ">>> " : "    ";
            String note = r.valid ? "  <== SOLUTION" : "";
            System.out.printf("%s%-4s %-4s %-8s %-8s %-9s%s%n", marker,
                tf(r.a), tf(r.b), tf(r.saysA), tf(r.saysB), r.valid ? "YES" : "no", note);
            }
        
        }


    public static void printVerdict(List<Row> rows) {
        List<Row> solutions = new ArrayList<>();
        for (Row r : rows) if (r.valid) solutions.add(r);

        System.out.println();
        if (solutions.isEmpty()) {
            System.out.println("There is no solution.");
        } else if (solutions.size() == 1) {
            System.out.println("Solution: " + describe(solutions.get(0)));
        } else {
            System.out.println("There are " + solutions.size() + " solutions:");
            for (Row r : solutions) System.out.println(" - " + describe(r));
            System.out.println("The puzzle is ambiguous.");
        }
}

    private static String describe(Row r) {
        return "A is a " + (r.a ? "knight" : "knave")
            + " and B is a " + (r.b ? "knight" : "knave") + ".";
    }



}