package us.andrewdickinson.ghhs.stickpicker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Andrew on 9/25/2015.
 */
public class TeacherPanel extends JPanel {
    JFrame parentFrame;
    Teacher teacher;

    JPanel advancedPanel;

    JButton pickButton;
    JButton importNewButton;
    JButton removeClassButton;
    JButton resetNamesButton;
    JButton showAdvancedButton;

    JComboBox hourBox;
    ActionListener hourboxListener;
    JTextArea name_list;

    public TeacherPanel(Teacher teacher){
        this.teacher = teacher;
        parentFrame = (JFrame) this.getParent();

        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new FlowLayout());
        mainPanel.setPreferredSize(new Dimension(300, 350));

        if (teacher.getClassHours().length == 0){
            displayImportDialog(parentFrame);
        }

        //Nothing changed, the dialog must have been cancelled
        if (teacher.getClassHours().length == 0){
            System.exit(0);
        }

        ButtonListener bl = new ButtonListener();
        hourboxListener = new ClassSelectedListener();

        JLabel selectClassLabel = new JLabel("Select class:");
        mainPanel.add(selectClassLabel);

        hourBox = new JComboBox(this.teacher.getClassHours());
        hourBox.addActionListener(hourboxListener);
        mainPanel.add(hourBox);

        pickButton = new JButton("Pick Student");
        pickButton.addActionListener(bl);

        JLabel unpickedStudentsLabel = new JLabel("Unpicked Students (This round):");
        mainPanel.add(unpickedStudentsLabel);

        name_list = new JTextArea();
        name_list.setEditable(false);
        JScrollPane unpickedStudentsDisplay = new JScrollPane(name_list);
        unpickedStudentsDisplay.setPreferredSize(new Dimension(250, 200));
        mainPanel.add(unpickedStudentsDisplay);

        resetNamesButton = new JButton("Add all names back to the list");
        resetNamesButton.addActionListener(bl);
        mainPanel.add(pickButton);
        mainPanel.add(resetNamesButton);

        add(mainPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        advancedPanel = new JPanel(new BorderLayout());
        JPanel subPanel = new JPanel(new FlowLayout());

        importNewButton = new JButton("Import New Class");
        importNewButton.addActionListener(bl);
        subPanel.add(importNewButton);

        removeClassButton = new JButton("Remove Class");
        removeClassButton.setForeground(Color.RED);
        removeClassButton.addActionListener(bl);
        subPanel.add(removeClassButton);

        advancedPanel.add(subPanel, BorderLayout.CENTER);

        JLabel signatureLabel = new JLabel("An Andrew Dickinson Production");
        signatureLabel.setFont(new Font("Arial", Font.ITALIC, 10));
        signatureLabel.setHorizontalAlignment(SwingConstants.CENTER);
        advancedPanel.add(signatureLabel, BorderLayout.SOUTH);

        showAdvancedButton = new JButton("Show Advanced");
        showAdvancedButton.addActionListener(bl);
        bottomPanel.add(showAdvancedButton, BorderLayout.NORTH);

        advancedPanel.setVisible(false);
        bottomPanel.add(advancedPanel, BorderLayout.CENTER);

        add(bottomPanel, BorderLayout.SOUTH);

        hourBox.setSelectedIndex(0);
    }

    private Classroom getSelectedClass(){
        String hourText = (String) hourBox.getSelectedItem();
        return teacher.getClassroom(Integer.parseInt(hourText));
    }

    private String pickStudentBasedOnHour(){
        Classroom selectedClass = getSelectedClass();
        return selectedClass.pickStudent().toString();
    }

    private void updateUnpickedDisplay(){
        Classroom selectedClass = getSelectedClass();
        String text = selectedClass
                .getUnSelectedNamesStringWithNewLineSeperators();
        name_list.setText(text);
    }

    private void updateUnpickedDisplay(String error){
        name_list.setText(error);
    }

    private void processPickedButton(){
        String student_name = pickStudentBasedOnHour();
        updateUnpickedDisplay();

        Object[] buttons = {"Pick Another Student", "Close"};

        int result = JOptionPane.showOptionDialog(null,
                student_name, "Student Picked:",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE, null, buttons,
                buttons[0]);

        if (result == 0){
            processPickedButton();
        }
    }

    public void displayImportDialog(JFrame frame){
        JPanel importPanel = new ImportPanel(teacher);
        JDialog dialog = new JDialog(frame, "Import Dialog", true);
        dialog.setResizable(false);
        dialog.getContentPane().add(importPanel);
        dialog.setSize(300, 375);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }

    private void updateHourBox(){
        hourBox.removeActionListener(hourboxListener);
        hourBox.removeAllItems();
        for (String hour : teacher.getClassHours()){
            hourBox.addItem(hour);
        }
        hourBox.addActionListener(hourboxListener);
        hourBox.setSelectedIndex(0);
    }

    private class ButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == pickButton) {
                processPickedButton();
            } else if (e.getSource() == importNewButton){
                displayImportDialog(parentFrame);
                updateHourBox();
            } else if (e.getSource() == resetNamesButton){
                getSelectedClass().setupUnpickedList();
                updateUnpickedDisplay();
            } else if (e.getSource() == removeClassButton){
                if (teacher.getClassHours().length > 1){
                    Classroom classroom = getSelectedClass();
                    teacher.removeClassroom(classroom.getHour());
                    updateHourBox();
                } else {
                    //We're out of classes, immediately prompt for a new one
                    Classroom classroom = getSelectedClass();
                    teacher.removeClassroom(classroom.getHour());
                    JOptionPane.showMessageDialog(null,
                            "You must have at least one class. Select one please.",
                            "Out of classes",
                            JOptionPane.ERROR_MESSAGE);
                    displayImportDialog(parentFrame);
                    try {
                        updateHourBox();
                    } catch (IllegalArgumentException ex){
                        //The user canceled selecting a file
                        updateUnpickedDisplay("Please import a class");
                    }
                }
            } else if (e.getSource() == showAdvancedButton){
                if (advancedPanel.isVisible()){
                    advancedPanel.setVisible(false);
                    showAdvancedButton.setText("Show Advanced");
                } else {
                    advancedPanel.setVisible(true);
                    showAdvancedButton.setText("Hide Advanced");
                }
            }
        }
    }

    private class ClassSelectedListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            updateUnpickedDisplay();
        }
    }
}
