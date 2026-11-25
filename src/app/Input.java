package app;

import java.util.Scanner;

public class Input {

    static Scanner input = new Scanner(System.in);

    static boolean speakerA = false;
    static boolean speakerB = false;
    static String statementA = "";
    static String statementB = "";

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
            System.out.print("Enter " + speaker + "'s statement (use A, B, !, n, v, IFF): ");
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

        String[] pieces = text.split("(?:n|v|IFF)", -1);
        for (String piece : pieces) {
            if (!piece.matches("!?[AB]")) {
                return false;
            }
        }
        return true;
    }
}