package us.andrewdickinson.ghhs.stickpicker;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.lang.instrument.IllegalClassFormatException;
import java.text.NumberFormat;

/**
 * Created by Andrew on 9/13/2015.
 */
public class ImportPanel extends JPanel{
    private JButton fileButton;
    private JButton importButton;
    private JTextArea name_list;
    private JTextField hour_field;

    private Teacher classTeacher;
    private Classroom incomingClass;

    public ImportPanel(Teacher teacher) {
        classTeacher = teacher;
        ButtonListener bl = new ButtonListener();

        JLabel selectAFile = new JLabel("Select a file to import:");
        JLabel studentsFound = new JLabel("Students Found:");

        name_list = new JTextArea();
        name_list.setEditable(false);
        JScrollPane incomingClassDisplay = new JScrollPane(name_list);
        incomingClassDisplay.setPreferredSize(new Dimension(250, 200));

        fileButton = new JButton("Select CSV File");
        fileButton.addActionListener(bl);

        importButton = new JButton("Import");
        importButton.addActionListener(bl);

        JLabel hour_text = new JLabel("Hour:");

        hour_field = new JTextField();
        hour_field.setPreferredSize(new Dimension(30, 20));

        JPanel bottomGrid = new JPanel(new GridLayout(2, 1));
        JPanel hour_panel = new JPanel();
        hour_panel.add(hour_text);
        hour_panel.add(hour_field);
        bottomGrid.add(hour_panel);

        JPanel importButtonPanel = new JPanel();
        importButtonPanel.add(importButton);

        bottomGrid.add(importButtonPanel);

        add(selectAFile);
        add(fileButton);
        add(studentsFound);
        add(incomingClassDisplay);
        add(bottomGrid);
    }

    public static Classroom getClassroomFromFile(){
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter("CSV Files", "csv"));
        int status = chooser.showOpenDialog(null);


        if (status == JFileChooser.APPROVE_OPTION){
            String fileName = chooser.getSelectedFile().getAbsolutePath();
            try {
                return new Classroom(fileName);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (IllegalClassFormatException e){
                e.printStackTrace();
            }
        }

        return null;
    }

    /**
     * Closes the parent dialog. It's a long ways up the chain
     */
    private void closeDialog(){
        this.getParent().getParent()
                .getParent().getParent().setVisible(false);
    }

    private class ButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == fileButton){
                incomingClass = getClassroomFromFile();

                    if (incomingClass != null)
                        name_list.setText(
                                incomingClass.getNamesStringWithNewLineSeperators()
                        );
                     else
                        name_list.setText("Import Error");
            } else if (e.getSource() == importButton) {
                try {
                    int hour = Integer.parseInt(hour_field.getText());
                    if (hour < 0){
                        throw new NumberFormatException();
                    }
                    incomingClass.setClass_hour(hour);
                    classTeacher.addClassroom(incomingClass);
                    JOptionPane.showMessageDialog(null,
                            "Your " + incomingClass.getPrettyHour() +
                               " hour class was imported successfully!",
                            "Import Successful",
                            JOptionPane.PLAIN_MESSAGE);
                    closeDialog();
                } catch (NumberFormatException error) {
                    JOptionPane.showMessageDialog(null,
                            "Please specify a valid hour",
                            "Invalid Hour",
                            JOptionPane.ERROR_MESSAGE);
                } catch (IllegalArgumentException error) {
                    JOptionPane.showMessageDialog(null,
                            "Please enter a new hour or delete the old one in this slot",
                            "Hour Already Exists",
                            JOptionPane.ERROR_MESSAGE);
                } catch (NullPointerException error) {
                    JOptionPane.showMessageDialog(null,
                            "Please select a file",
                            "No file data",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
}
