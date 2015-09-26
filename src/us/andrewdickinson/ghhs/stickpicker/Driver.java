package us.andrewdickinson.ghhs.stickpicker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.prefs.Preferences;

/**
 * Created by Andrew on 9/13/2015.
 */
public class Driver {
    public static void main(String[] args){
        final JFrame frame = new JFrame("Stick Picker");

        final Teacher teacher = new Teacher();
        teacher.load();

        TeacherPanel teacherPanel = new TeacherPanel(teacher);
        frame.getContentPane().add(teacherPanel);
        frame.setSize(300, 400);
        frame.setResizable(false);
        frame.setVisible(true);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width / 2 - frame.getSize().width / 2,
                dim.height / 2 - frame.getSize().height / 2);

        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event) {
                teacher.save();
                frame.dispose();
                System.exit(0);
            }
        });
    }




}
