package app;

import java.util.Scanner;


public class Main {
     static Scanner input = new Scanner(System.in);
     static boolean speakerA = false;
       static  boolean speakerB = false;
       static  String statementA = "" ;
      static   String statementB = "";

      static  boolean[][] truthTable = new boolean[4][4];

    public static void main(String[] args) {
        
    }

    void getValidInput(){
        String response = "";

        do {
            System.out.println("Who is speaking? ");
            System.out.println("A: for A is speaking \t B: for B is speaking\t C for both are speaking");

        } while (!response.equals("A") && !response.equals("B") && !response.equals("C"));
    }



     public String askForStatement(String speaker) {
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


    
}
