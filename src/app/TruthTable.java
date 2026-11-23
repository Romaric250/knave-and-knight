package app;

public class TruthTable {

    // Columns: A says, B says, valid, unused.
    static boolean[][] table = new boolean[4][4];

    static void buildTruthTable() {
        for (int i = 0; i < 4; i++) {
            boolean aKnight = i >= 2;
            boolean bKnight = i % 2 == 1;
            if (Input.speakerA) {
                table[i][0] = evaluateStatement(Input.statementA, aKnight, bKnight);
            }
            if (Input.speakerB) {
                table[i][1] = evaluateStatement(Input.statementB, aKnight, bKnight);
            }
            table[i][2] = false;
            table[i][3] = false;
        }
    }

    static void printTruthTable() {
        System.out.println();
        System.out.println("Truth table (statement value in each world; not yet a solution)");
        System.out.printf("    %-4s %-4s %-8s %-8s%n", "A", "B", "A stmt", "B stmt");
        for (int i = 0; i < 4; i++) {
            String saysA = Input.speakerA ? tf(table[i][0]) : "-";
            String saysB = Input.speakerB ? tf(table[i][1]) : "-";
            System.out.printf("    %-4s %-4s %-8s %-8s%n",
                    tf(i >= 2),
                    tf(i % 2 == 1),
                    saysA,
                    saysB);
        }
    }

    static void markValidRows() {
        for (int i = 0; i < 4; i++) {
            boolean aKnight = i >= 2;
            boolean bKnight = i % 2 == 1;
            table[i][2] = Evaluation.evaluateSolution(
                    aKnight,
                    bKnight,
                    Input.speakerA,
                    table[i][0],
                    Input.speakerB,
                    table[i][1],
                    Input.statementA,
                    Input.statementB);
        }
    }

    static boolean evaluateStatement(String text, boolean aKnight, boolean bKnight) {
        String[] orParts = text.split("v", -1);
        for (String orPart : orParts) {
            if (evalAnd(orPart, aKnight, bKnight)) {
                return true;
            }
        }
        return false;
    }

    static boolean evalAnd(String part, boolean aKnight, boolean bKnight) {
        String[] andParts = part.split("n", -1);
        for (String andPart : andParts) {
            if (!evalAtom(andPart, aKnight, bKnight)) {
                return false;
            }
        }
        return true;
    }

    static boolean evalAtom(String atom, boolean aKnight, boolean bKnight) {
        if (atom.startsWith("!")) {
            return !evalAtom(atom.substring(1), aKnight, bKnight);
        }
        if (atom.equals("A")) {
            return aKnight;
        }
        if (atom.equals("B")) {
            return bKnight;
        }
        return false;
    }

    static String tf(boolean value) {
        return value ? "T" : "F";
    }
}