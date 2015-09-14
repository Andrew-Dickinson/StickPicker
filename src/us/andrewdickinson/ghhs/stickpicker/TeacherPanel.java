package us.andrewdickinson.ghhs.stickpicker;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.IOException;
import java.lang.instrument.IllegalClassFormatException;

/**
 * Created by Andrew on 9/13/2015.
 */
public class TeacherPanel extends JPanel{

    public TeacherPanel() {

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
}
