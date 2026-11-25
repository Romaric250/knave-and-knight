package app;

import java.util.ArrayList;
import java.util.List;

public class TruthTable {

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
        System.out.println("Truth table");
        printTable(false, null);
    }

    static void printTable(boolean showMatch, boolean[][] source) {
        System.out.print(formatTable(showMatch, source));
    }

    static String formatTable(boolean showMatch, boolean[][] source) {
        boolean[][] data = source != null ? source : table;
        StringBuilder sb = new StringBuilder();
        sb.append(buildHeader(showMatch)).append('\n');
        for (int i = 0; i < 4; i++) {
            boolean valid = showMatch && data[i][2];
            sb.append(buildRow(i, showMatch, valid, data)).append('\n');
        }
        return sb.toString();
    }

    static String buildHeader(boolean showMatch) {
        StringBuilder line = new StringBuilder("    ");
        line.append(pad("A", 4)).append(pad("B", 4));
        if (Input.speakerA) {
            for (String part : stmtParts(Input.statementA)) {
                line.append(pad(part, 8));
            }
            line.append(pad(stmtColumnHeader(Input.statementA), 8));
        }
        if (Input.speakerB) {
            for (String part : stmtParts(Input.statementB)) {
                line.append(pad(part, 8));
            }
            line.append(pad(stmtColumnHeader(Input.statementB), 8));
        }
        if (showMatch) {
            line.append(pad("Matches?", 9));
        }
        return line.toString();
    }

    static String buildRow(int row, boolean showMatch, boolean valid, boolean[][] data) {
        boolean aKnight = row >= 2;
        boolean bKnight = row % 2 == 1;
        StringBuilder line = new StringBuilder();
        if (showMatch && valid) {
            line.append(">>> ");
        } else {
            line.append("    ");
        }
        line.append(pad(tf(aKnight), 4)).append(pad(tf(bKnight), 4));
        if (Input.speakerA) {
            for (String part : stmtParts(Input.statementA)) {
                line.append(pad(tf(evalNot(part, aKnight, bKnight)), 8));
            }
            line.append(pad(tf(data[row][0]), 8));
        }
        if (Input.speakerB) {
            for (String part : stmtParts(Input.statementB)) {
                line.append(pad(tf(evalNot(part, aKnight, bKnight)), 8));
            }
            line.append(pad(tf(data[row][1]), 8));
        }
        if (showMatch) {
            line.append(pad(valid ? "YES" : "no", 9));
            if (valid) {
                line.append("  <== SOLUTION");
            }
        }
        return line.toString();
    }

    static String stmtColumnHeader(String raw) {
        if (raw == null || raw.isEmpty()) {
            return "";
        }
        return raw;
    }

    static List<String> stmtParts(String raw) {
        List<String> parts = new ArrayList<>();
        if (raw == null || raw.isEmpty()) {
            return parts;
        }
        String[] atoms = raw.split("(?:n|v|IFF)", -1);
        if (atoms.length > 1) {
            for (String atom : atoms) {
                parts.add(atom);
            }
        }
        return parts;
    }

    static String pad(String text, int width) {
        if (text.length() >= width) {
            return text.substring(0, width);
        }
        return String.format("%-" + width + "s", text);
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
            if (!evalImp(andPart, aKnight, bKnight)) {
                return false;
            }
        }
        return true;
    }

    // IFF is implication
    static boolean evalImp(String part, boolean aKnight, boolean bKnight) {
        int idx = part.indexOf("IFF");
        if (idx < 0) {
            return evalNot(part, aKnight, bKnight);
        }
        String left = part.substring(0, idx);
        String right = part.substring(idx + 3);
        if (left.isEmpty() || right.isEmpty()) {
            return false;
        }
        return !evalImp(left, aKnight, bKnight) || evalImp(right, aKnight, bKnight);
    }

    static boolean evalNot(String value, boolean aKnight, boolean bKnight) {
        if (value.startsWith("!")) {
            return !evalNot(value.substring(1), aKnight, bKnight);
        }
        if (value.equals("A")) {
            return aKnight;
        }
        if (value.equals("B")) {
            return bKnight;
        }
        return false;
    }

    static String tf(boolean value) {
        return value ? "T" : "F";
    }
}