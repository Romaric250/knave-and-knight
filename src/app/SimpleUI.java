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
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class SimpleUI {

    static final Color BG = new Color(0x1A1A1A);
    static final Color GOLD = new Color(0xD4AF37);
    static final Color FIELD_BG = new Color(0x242424);
    static final Color GREEN = new Color(0x33CC66);
    static final Color RED = new Color(0xCC4444);
    static final Color ORANGE = new Color(0xE8A030);

    public static void open() {
        SwingUtilities.invokeLater(SimpleUI::build);
    }

    static void build() {
        JFrame frame = new JFrame("Knights and Knaves");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel root = new JPanel(new BorderLayout(0, 0));
        root.setBackground(BG);
        root.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(3, 0, 0, 0, GOLD),
                BorderFactory.createEmptyBorder(12, 20, 16, 20)));

        JLabel header = new JLabel("KNIGHTS AND KNAVES");
        header.setForeground(GOLD);
        header.setFont(new Font(Font.SERIF, Font.BOLD, 22));
        header.setBorder(BorderFactory.createEmptyBorder(0, 0, 14, 0));
        root.add(header, BorderLayout.NORTH);

        JPanel body = new JPanel(new BorderLayout(0, 12));
        body.setBackground(BG);

        JPanel form = new JPanel(new GridLayout(3, 2, 8, 10));
        form.setBackground(BG);

        JComboBox<String> who = new JComboBox<>(new String[] { "A", "B", "BOTH" });
        JTextField stmtA = new JTextField();
        JTextField stmtB = new JTextField();
        stmtB.setEnabled(false);
        styleField(stmtA);
        styleField(stmtB);
        styleCombo(who);

        form.add(goldLabel("Who speaks (A / B / BOTH):"));
        form.add(who);
        form.add(goldLabel("A statement (A, B, !, n, v, I):"));
        form.add(stmtA);
        form.add(goldLabel("B statement:"));
        form.add(stmtB);

        who.addActionListener(e -> {
            String w = (String) who.getSelectedItem();
            boolean a = w.equals("A") || w.equals("BOTH");
            boolean b = w.equals("B") || w.equals("BOTH");
            stmtA.setEnabled(a);
            stmtB.setEnabled(b);
            if (!a) {
                stmtA.setText("");
            }
            if (!b) {
                stmtB.setText("");
            }
        });

        JTextPane output = new JTextPane();
        output.setEditable(false);
        output.setBackground(BG);
        output.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        output.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JScrollPane scroll = new JScrollPane(output);
        scroll.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(GOLD, 1),
                BorderFactory.createLineBorder(GOLD, 1)));
        scroll.getViewport().setBackground(BG);

        JButton solve = new JButton("SOLVE");
        solve.setBackground(GOLD);
        solve.setForeground(Color.BLACK);
        solve.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
        solve.setFocusPainted(false);
        solve.setBorder(BorderFactory.createEmptyBorder(12, 0, 12, 0));
        solve.setMaximumSize(new Dimension(Integer.MAX_VALUE, 48));
        solve.addActionListener(e -> runSolve(who, stmtA, stmtB, output));

        body.add(form, BorderLayout.NORTH);
        body.add(scroll, BorderLayout.CENTER);

        JPanel center = new JPanel(new BorderLayout(0, 8));
        center.setBackground(BG);
        center.add(body, BorderLayout.CENTER);
        center.add(solve, BorderLayout.SOUTH);

        root.add(center, BorderLayout.CENTER);
        frame.setContentPane(root);
        frame.setMinimumSize(new Dimension(560, 520));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    static JLabel goldLabel(String text) {
        JLabel l = new JLabel(text);
        l.setForeground(GOLD);
        l.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        return l;
    }

    static void styleField(JTextField f) {
        f.setBackground(FIELD_BG);
        f.setForeground(GOLD);
        f.setCaretColor(GOLD);
        f.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));
        f.setBorder(BorderFactory.createLineBorder(GOLD, 1));
    }

    static void styleCombo(JComboBox<?> c) {
        c.setBackground(FIELD_BG);
        c.setForeground(GOLD);
        c.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));
    }

    static void runSolve(JComboBox<String> who, JTextField stmtA, JTextField stmtB, JTextPane output) {
        String mode = (String) who.getSelectedItem();
        String a = Input.normalizeStatement(stmtA.getText());
        String b = Input.normalizeStatement(stmtB.getText());
        if ((mode.equals("A") || mode.equals("BOTH")) && !Input.isReadable(a)) {
            showText(output, "Invalid statement for A.", GOLD);
            return;
        }
        if ((mode.equals("B") || mode.equals("BOTH")) && !Input.isReadable(b)) {
            showText(output, "Invalid statement for B.", GOLD);
            return;
        }
        Input.setPuzzle(mode, a, b);
        TruthTable.buildTruthTable();
        TruthTable.markValidRows();
        String text = "TRUTH TABLE\n" + TruthTable.formatTable(false, null);
        text += "\nTRUTH TABLE WITH SOLUTIONS HIGHLIGHTED\n";
        text += TruthTable.formatTable(true, TruthTable.table);
        text += Verdict.formatVerdict(TruthTable.table);
        showColoredOutput(output, text);
    }

    static void showText(JTextPane pane, String text, Color color) {
        StyledDocument doc = pane.getStyledDocument();
        SimpleAttributeSet attr = new SimpleAttributeSet();
        StyleConstants.setForeground(attr, color);
        StyleConstants.setFontFamily(attr, Font.MONOSPACED);
        try {
            doc.remove(0, doc.getLength());
            doc.insertString(0, text, attr);
        } catch (Exception ignored) {
        }
    }

    static void showColoredOutput(JTextPane pane, String text) {
        StyledDocument doc = pane.getStyledDocument();
        try {
            doc.remove(0, doc.getLength());
            for (int i = 0; i < text.length(); i++) {
                char c = text.charAt(i);
                SimpleAttributeSet attr = new SimpleAttributeSet();
                StyleConstants.setFontFamily(attr, Font.MONOSPACED);
                StyleConstants.setFontSize(attr, 12);
                Color color = GOLD;
                if (tableTf(text, i)) {
                    color = c == 'T' ? GREEN : RED;
                } else if (text.regionMatches(i, "YES", 0, 3)) {
                    color = GREEN;
                } else if (text.regionMatches(i, "no", 0, 2) && bordered(text, i, 2)) {
                    color = RED;
                } else if (text.regionMatches(i, "The puzzle is ambiguous.", 0, 26)) {
                    color = ORANGE;
                }
                StyleConstants.setForeground(attr, color);
                doc.insertString(doc.getLength(), String.valueOf(c), attr);
            }
        } catch (Exception ignored) {
        }
        pane.setCaretPosition(0);
    }

    static boolean tableTf(String text, int i) {
        char c = text.charAt(i);
        if (c != 'T' && c != 'F') {
            return false;
        }
        return bordered(text, i, 1);
    }

    static boolean bordered(String text, int i, int len) {
        boolean before = i == 0 || text.charAt(i - 1) == ' ';
        boolean after = i + len >= text.length() || text.charAt(i + len) == ' ';
        return before && after;
    }
}