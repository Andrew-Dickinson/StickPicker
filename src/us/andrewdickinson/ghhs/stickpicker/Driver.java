package us.andrewdickinson.ghhs.stickpicker;

import javax.swing.*;

/**
 * Created by Andrew on 9/13/2015.
 */
public class Driver {
    public static void main(String[] args){
        JFrame frame = new JFrame("Stick Picker");
        frame.setDefaultCloseOperation (WindowConstants.EXIT_ON_CLOSE);
        Teacher teacher = new Teacher();
        JPanel teacherPanel = new ImportPanel(teacher);
        frame.getContentPane().add(teacherPanel);
        frame.setSize(300, 400);
        frame.setResizable(false);
        frame.setVisible(true);
    }
}
