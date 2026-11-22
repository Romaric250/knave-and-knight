public static evaluation{
    public static boolean evaluateSolution(
        boolean aIsKnight,
        boolean bIsKnight,
        boolean aSpeaks,
        boolean aStatementTruth,
        boolean bSpeaks,
        boolean bStatementTruth) {

    // Check Person A
    boolean aValid = true;

    if (aSpeaks) {
        aValid = (aIsKnight == aStatementTruth);
    }

    // Check Person B
    boolean bValid = true;

    if (bSpeaks) {
        bValid = (bIsKnight == bStatementTruth);
    }

    // The row is a solution only if all speakers are consistent
    return aValid && bValid;
}
}
