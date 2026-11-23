package app;

public class Main {

    public static void main(String[] args) {
        System.out.println("Knight and Knave Puzzle Solver");
        Input.getValidInput();
        TruthTable.buildTruthTable();
        TruthTable.printTruthTable();
        TruthTable.markValidRows();
        Verdict.highlightValidSolutions(TruthTable.table, Input.speakerA, Input.speakerB);
        Verdict.printVerdict(TruthTable.table);
    }
}