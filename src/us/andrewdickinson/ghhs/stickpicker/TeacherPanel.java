package us.andrewdickinson.ghhs.stickpicker;

import javafx.scene.text.Text;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.lang.instrument.IllegalClassFormatException;

/**
 * Created by Andrew on 9/13/2015.
 */
public class TeacherPanel extends JPanel{
    private JButton importButton;
    private JScrollPane incomingClassDisplay;

    private Classroom incomingClass;

    public TeacherPanel() {
        ButtonListener bl = new ButtonListener();

        JLabel studentText = new JLabel("Students");

        incomingClassDisplay = new JScrollPane();
        incomingClassDisplay.setSize(200, 200);
        incomingClassDisplay.add(studentText);

        importButton = new JButton("Import");
        importButton.addActionListener(bl);

        add(importButton);
        add(incomingClassDisplay);
    }

    public static Classroom getClassroomFromFile(){
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter("CSV Files", "csv"));
        int status = chooser.showOpenDialog(null);


        if (status == JFileChooser.APPROVE_OPTION){
            String fileName = chooser.getSelectedFile().getAbsolutePath();
            try {
                Classroom class1 = new Classroom(1, fileName);
                JOptionPane.showMessageDialog(null,
                        "Students Found:\n" +
                                class1.getNamesStringWithNewLineSeperators());
                return class1;
            } catch (IOException e) {
                e.printStackTrace();
            } catch (IllegalClassFormatException e){
                e.printStackTrace();
            }
        }

        return null;
    }

    public void updatePanel(){

    }

    private class ButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == importButton){
                incomingClass = getClassroomFromFile();
            }
        }
    }
}
