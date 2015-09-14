package us.andrewdickinson.ghhs.stickpicker;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.IOException;
import java.lang.instrument.IllegalClassFormatException;

/**
 * Created by Andrew on 9/13/2015.
 */
public class Driver {
    public static void main(String[] args){
        JFrame frame = new JFrame("Stick Picker");
        frame.setDefaultCloseOperation (WindowConstants.EXIT_ON_CLOSE);
        JPanel teacherPanel = new TeacherPanel();
        frame.getContentPane().add(teacherPanel);
        frame.setSize(400, 400);
        frame.setResizable(false);
        frame.setVisible(true);
    }
}
