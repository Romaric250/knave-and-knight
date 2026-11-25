package app;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class SimpleUI {

    public static void open() {
        SwingUtilities.invokeLater(SimpleUI::build);
    }

    static void build() {
        JFrame frame = new JFrame("Knights and Knaves");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout(10, 10));

        JPanel form = new JPanel(new GridLayout(4, 2, 6, 6));
        JComboBox<String> who = new JComboBox<>(new String[] { "A", "B", "C" });
        JTextField stmtA = new JTextField();
        JTextField stmtB = new JTextField();
        form.add(new JLabel("Who speaks (A / B / C):"));
        form.add(who);
        form.add(new JLabel("A statement (A,B,!,n,v,IFF):"));
        form.add(stmtA);
        form.add(new JLabel("B statement:"));
        form.add(stmtB);
        frame.add(form, BorderLayout.NORTH);

        JTextArea output = new JTextArea(18, 60);
        output.setEditable(false);
        output.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        frame.add(new JScrollPane(output), BorderLayout.CENTER);

        JButton solve = new JButton("Solve");
        solve.addActionListener(e -> {
            String mode = (String) who.getSelectedItem();
            String a = stmtA.getText();
            String b = stmtB.getText();
            if ((mode.equals("A") || mode.equals("C")) && !Input.isReadable(a.replace(" ", ""))) {
                output.setText("Bad statement for A.");
                return;
            }
            if ((mode.equals("B") || mode.equals("C")) && !Input.isReadable(b.replace(" ", ""))) {
                output.setText("Bad statement for B.");
                return;
            }
            Input.setPuzzle(mode, a, b);
            TruthTable.buildTruthTable();
            TruthTable.markValidRows();
            String text = "Truth table\n" + TruthTable.formatTable(false, null);
            text += "\nTruth table with solutions highlighted\n" + TruthTable.formatTable(true, TruthTable.table);
            text += Verdict.formatVerdict(TruthTable.table);
            output.setText(text);
        });
        frame.add(solve, BorderLayout.SOUTH);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}