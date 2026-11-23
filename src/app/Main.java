package app;

import java.util.Scanner;

public class Main {

    static Scanner input = new Scanner(System.in);

    static boolean speakerA = false;
    static boolean speakerB = false;
    static String statementA = "";
    static String statementB = "";

    // Row is one of four knight/knave worlds. Columns: A says, B says, valid, unused.
    static boolean[][] truthTable = new boolean[4][4];

    public static void main(String[] args) {
        System.out.println("Knight and Knave Puzzle Solver");
        getValidInput();
        buildTruthTable();
        printTruthTable();
        markValidRows();
        Verdict.highlightValidSolutions(truthTable, speakerA, speakerB);
        Verdict.printVerdict(truthTable);
    }

    static void getValidInput() {
        String response;
        do {
            System.out.println("Who is speaking?");
            System.out.println("A: A only\tB: B only\tC: both");
            response = input.nextLine().trim().toUpperCase();
        } while (!response.equals("A") && !response.equals("B") && !response.equals("C"));

        speakerA = response.equals("A") || response.equals("C");
        speakerB = response.equals("B") || response.equals("C");

        if (speakerA) {
            statementA = askForStatement("A");
        }
        if (speakerB) {
            statementB = askForStatement("B");
        }
    }

    static String askForStatement(String speaker) {
        String text;
        do {
            System.out.print("Enter " + speaker + "'s statement (use A, B, !, n, v): ");
            text = input.nextLine().replace(" ", "");
            if (!isReadable(text)) {
                System.out.println("Could not understand that. Example: !An!B");
            }
        } while (!isReadable(text));
        return text;
    }

    static boolean isReadable(String text) {
        if (text.isEmpty()) {
            return false;
        }

        String[] pieces = text.split("[nv]", -1);
        for (String piece : pieces) {
            if (!piece.matches("!?[AB]")) {
                return false;
            }
        }
        return true;
    }

    static void buildTruthTable() {
        for (int i = 0; i < 4; i++) {
            boolean aKnight = i >= 2;
            boolean bKnight = i % 2 == 1;
            if (speakerA) {
                truthTable[i][0] = evaluateStatement(statementA, aKnight, bKnight);
            }
            if (speakerB) {
                truthTable[i][1] = evaluateStatement(statementB, aKnight, bKnight);
            }
            truthTable[i][2] = false;
            truthTable[i][3] = false;
        }
    }

    static void printTruthTable() {
        System.out.println();
        System.out.println("Truth table");
        System.out.printf("    %-4s %-4s %-8s %-8s%n", "A", "B", "A says", "B says");
        for (int i = 0; i < 4; i++) {
            String saysA = speakerA ? tf(truthTable[i][0]) : "-";
            String saysB = speakerB ? tf(truthTable[i][1]) : "-";
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
            truthTable[i][2] = Evaluation.evaluateSolution(
                    aKnight,
                    bKnight,
                    speakerA,
                    truthTable[i][0],
                    speakerB,
                    truthTable[i][1]);
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