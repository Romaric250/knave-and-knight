package app;

public class Evaluation {
    public static boolean evaluateSolution(
            boolean aIsKnight,
            boolean bIsKnight,
            boolean aSpeaks,
            boolean aStatementTruth,
            boolean bSpeaks,
            boolean bStatementTruth) {

        boolean aValid = true;

        if (aSpeaks) {
            aValid = (aIsKnight == aStatementTruth);
        }

        boolean bValid = true;

        if (bSpeaks) {
            bValid = (bIsKnight == bStatementTruth);
        }

        return aValid && bValid;
    }
}