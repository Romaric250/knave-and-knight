Knight and Knaves Puzzle Solver (Java)

## Objective

Automate knight-and-knave puzzles: knights always speak truly, knaves always lie.

You meet **A** and **B**. One or both may speak. Input is symbolic; the program builds a truth table, then finds which worlds are real solutions.

## Notation (input)

| Symbol | Meaning        |
|--------|----------------|
| A      | A is a knight  |
| B      | B is a knight  |
| !      | NOT            |
| n      | AND            |
| v      | OR             |

Example: `AnB` means “A and B are both knights”. `!An!B` means neither is a knight.

Speaker choice: **A** (only A), **B** (only B), or **C** (both).

## Truth table vs solution

The program always prints a **truth table** with four rows (all combinations of whether A and B are knights).

Columns **A stmt** / **B stmt** are the **logical value of what was said** in that world (true or false as a proposition). That is not the same as “is this person telling the truth?” yet.

`Evaluation.evaluateSolution` marks a row:

- **One speaker:** knight/knave must match the stmt in that row. Plain `AnB` with stmt **T** → both knights; stmt **F** → no solution. `!An!B` with stmt **F** → speaker knave, other knight. Other both-person formulas with `!` (e.g. `!AnB`) with stmt **T** → speaker knight, other knave (and the mirror for stmt **F** when it applies). Single-person formulas use speaker knight on T and speaker knave + other knight on F.
- **Both speak:** both must match their stmt, with one knight and one knave, and one stmt T and one stmt F.

The truth table always has four rows; many rows are not solutions.

## Program flow

1. **Input** — who speaks and the statement(s)
2. **TruthTable** — build and print the four-row table
3. **Evaluation** — mark each row valid or not (`truthTable[i][2]`)
4. **Verdict** — highlight valid rows and print none / one / many solutions

## Run

```bash
cd src
javac app/*.java
java app.Main
```

## Layout

- `Main.java` — runs the steps in order
- `Input.java` — tasks 1–2 (setup input)
- `TruthTable.java` — task 3 (table + statement evaluation)
- `Evaluation.java` — task 4 (knight/knave check per row)
- `Verdict.java` — tasks 5–6 (highlight + English verdict)