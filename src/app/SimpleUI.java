package app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class SimpleUI {

    static final Color BG = new Color(18, 22, 40);
    static final Color PANEL = new Color(28, 34, 58);
    static final Color FIELD = new Color(22, 28, 48);
    static final Color TEXT = new Color(220, 228, 245);
    static final Color MUTED = new Color(140, 155, 190);
    static final Color GOLD = new Color(255, 200, 90);
    static final Color CYAN = new Color(100, 220, 255);
    static final Color TERMINAL = new Color(120, 255, 180);

    public static void open() {
        SwingUtilities.invokeLater(SimpleUI::build);
    }

    static void build() {
        JFrame frame = new JFrame("Knave & Knight — Arcade");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel root = new JPanel(new BorderLayout(0, 12));
        root.setBackground(BG);
        root.setBorder(BorderFactory.createEmptyBorder(14, 16, 14, 16));

        JPanel form = new JPanel(new GridLayout(4, 2, 10, 10));
        form.setBackground(PANEL);
        form.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(GOLD, 2),
                BorderFactory.createEmptyBorder(12, 12, 12, 12)));

        JComboBox<String> who = new JComboBox<>(new String[] { "A", "B", "C" });
        JTextField stmtA = new JTextField();
        JTextField stmtB = new JTextField();
        styleCombo(who);
        styleField(stmtA);
        styleField(stmtB);

        form.add(label("Who speaks"));
        form.add(who);
        form.add(label("Villager A says"));
        form.add(stmtA);
        form.add(label("Villager B says"));
        form.add(stmtB);
        form.add(label("Syntax"));
        JLabel hint = label("A  B  !  n  v  IFF");
        hint.setForeground(CYAN);
        form.add(hint);

        JPanel top = new JPanel(new BorderLayout(0, 10));
        top.setBackground(BG);
        top.add(titlePanel(), BorderLayout.NORTH);
        top.add(form, BorderLayout.CENTER);

        JTextArea output = new JTextArea(16, 58);
        output.setEditable(false);
        output.setBackground(new Color(10, 14, 26));
        output.setForeground(TERMINAL);
        output.setCaretColor(TERMINAL);
        output.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));
        output.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));

        JScrollPane scroll = new JScrollPane(output);
        scroll.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(CYAN, 1),
                " Truth table ",
                0,
                0,
                new Font(Font.SANS_SERIF, Font.BOLD, 12),
                CYAN));
        scroll.getViewport().setBackground(output.getBackground());

        JButton solve = new JButton("SOLVE");
        solve.setBackground(GOLD);
        solve.setForeground(new Color(35, 28, 8));
        solve.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
        solve.setFocusPainted(false);
        solve.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 230, 140), 2),
                BorderFactory.createEmptyBorder(10, 24, 10, 24)));
        solve.addActionListener(e -> runSolve(who, stmtA, stmtB, output));

        JPanel bottom = new JPanel(new BorderLayout(0, 10));
        bottom.setBackground(BG);
        bottom.add(solve, BorderLayout.NORTH);
        bottom.add(scroll, BorderLayout.CENTER);

        root.add(top, BorderLayout.NORTH);
        root.add(bottom, BorderLayout.CENTER);
        frame.setContentPane(root);

        frame.setMinimumSize(new Dimension(620, 520));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    static JPanel titlePanel() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(BG);
        JLabel title = new JLabel("KNAVE  &  KNIGHT", SwingConstants.CENTER);
        title.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 26));
        title.setForeground(GOLD);
        JLabel sub = new JLabel("Knights speak truth  ·  Knaves always lie", SwingConstants.CENTER);
        sub.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
        sub.setForeground(MUTED);
        sub.setBorder(BorderFactory.createEmptyBorder(4, 0, 0, 0));
        p.add(title, BorderLayout.NORTH);
        p.add(sub, BorderLayout.CENTER);
        return p;
    }

    static JLabel label(String text) {
        JLabel l = new JLabel(text);
        l.setForeground(TEXT);
        l.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 13));
        return l;
    }

    static void styleField(JTextField f) {
        f.setBackground(FIELD);
        f.setForeground(TEXT);
        f.setCaretColor(CYAN);
        f.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));
        f.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(70, 90, 130)),
                BorderFactory.createEmptyBorder(6, 8, 6, 8)));
    }

    static void styleCombo(JComboBox<?> c) {
        c.setBackground(FIELD);
        c.setForeground(TEXT);
        c.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 13));
    }

    static void runSolve(JComboBox<String> who, JTextField stmtA, JTextField stmtB, JTextArea output) {
        String mode = (String) who.getSelectedItem();
        String a = stmtA.getText();
        String b = stmtB.getText();
        if ((mode.equals("A") || mode.equals("C")) && !Input.isReadable(a.replace(" ", ""))) {
            output.setText(">>> Invalid statement for A.");
            return;
        }
        if ((mode.equals("B") || mode.equals("C")) && !Input.isReadable(b.replace(" ", ""))) {
            output.setText(">>> Invalid statement for B.");
            return;
        }
        Input.setPuzzle(mode, a, b);
        TruthTable.buildTruthTable();
        TruthTable.markValidRows();
        String text = "=== TRUTH TABLE ===\n" + TruthTable.formatTable(false, null);
        text += "\n=== SOLUTIONS HIGHLIGHTED ===\n" + TruthTable.formatTable(true, TruthTable.table);
        text += Verdict.formatVerdict(TruthTable.table);
        output.setText(text);
        output.setCaretPosition(0);
    }
}