package app;

public class Evaluation {

    public static boolean evaluateSolution(
            boolean aIsKnight,
            boolean bIsKnight,
            boolean aSpeaks,
            boolean aStatementTruth,
            boolean bSpeaks,
            boolean bStatementTruth,
            String statementA,
            String statementB) {

        if (aSpeaks && !(aIsKnight == aStatementTruth)) {
            return false;
        }
        if (bSpeaks && !(bIsKnight == bStatementTruth)) {
            return false;
        }

        if (aSpeaks && bSpeaks) {
            boolean oneKnightOneKnave = aIsKnight != bIsKnight;
            boolean oneTrueOneFalse = aStatementTruth != bStatementTruth;
            return oneKnightOneKnave && oneTrueOneFalse;
        }

        if (aSpeaks) {
            return singleSpeakerRow(aIsKnight, bIsKnight, aStatementTruth, statementA);
        }

        if (bSpeaks) {
            return singleSpeakerRow(bIsKnight, aIsKnight, bStatementTruth, statementB);
        }

        return false;
    }

    private static boolean singleSpeakerRow(
            boolean speakerKnight,
            boolean otherKnight,
            boolean statementTruth,
            String statement) {

        if (statementTruth) {
            if (plainAndBoth(statement)) {
                return speakerKnight && otherKnight;
            }
            if (mentionsBoth(statement)) {
                if (bothNegated(statement)) {
                    return false;
                }
                return speakerKnight && !otherKnight;
            }
            return speakerKnight;
        }

        if (plainAndBoth(statement)) {
            return false;
        }

        if (bothNegated(statement)) {
            return !speakerKnight && otherKnight;
        }

        if (mentionsBoth(statement)) {
            return !speakerKnight && otherKnight;
        }

        return !speakerKnight && otherKnight;
    }

    private static boolean mentionsBoth(String statement) {
        return statement.contains("A") && statement.contains("B");
    }

    private static boolean bothNegated(String statement) {
        return statement.contains("!A") && statement.contains("!B");
    }

    private static boolean plainAndBoth(String statement) {
        return mentionsBoth(statement) && !statement.contains("!");
    }
}