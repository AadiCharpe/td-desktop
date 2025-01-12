import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Box;
import javax.swing.JCheckBox;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import java.awt.event.WindowAdapter;

import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("ToDo App");
        frame.setSize(500, 400);

        ArrayList<JCheckBox> checkBoxes = new ArrayList<>();
        Box checklist = Box.createVerticalBox();
        try {
            BufferedReader in = new BufferedReader(new FileReader("todo.txt"));
            String line;
            while((line = in.readLine()) != null) {
                JCheckBox checkBox = new JCheckBox(line);
                checkBoxes.add(checkBox);
                checklist.add(checkBox);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        JButton add = new JButton("Add ToDo");
        add.addActionListener(e -> {
            String s = JOptionPane.showInputDialog("Enter Name For ToDo:");
            JCheckBox checkBox = new JCheckBox(s);
            checkBoxes.add(checkBox);
            checklist.add(checkBox);
            frame.revalidate();
        });
        JButton remove = new JButton("Remove Completed ToDo");
        remove.addActionListener(e -> {
            for(int i = 0; i < checkBoxes.size(); i++) {
                JCheckBox box = checkBoxes.get(i);
                if (box.isSelected()) {
                    checkBoxes.remove(box);
                    checklist.remove(box);
                    checklist.repaint();
                    frame.revalidate();
                }
            }
        });
        JPanel panel = new JPanel();
        panel.add(add);
        panel.add(remove);
        frame.add(new JScrollPane(checklist));
        frame.add(panel, "South");
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                try {
                    PrintWriter out = new PrintWriter("todo.txt");
                    for(JCheckBox box : checkBoxes)
                        if(!box.isSelected())
                            out.println(box.getText());
                    out.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        frame.setVisible(true);
    }
}