package us.andrewdickinson.ghhs.stickpicker;

import com.opencsv.CSVReader;

import java.io.FileReader;
import java.io.IOException;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrew on 9/13/2015.
 */
public class Classroom {
    private ArrayList<Student> student_list = new ArrayList<Student>();
    private int class_hour;
    private ArrayList<Student> unpicked_list = new ArrayList<Student>();

    public Classroom(int class_hour) {
        this.class_hour = class_hour;
    }

    public Classroom(int class_hour, String csvFileName)
            throws IOException, IllegalClassFormatException{
        this.class_hour = class_hour;
        importFromCSV(csvFileName);
    }

    public void setupUnpickedList(){
        unpicked_list = new ArrayList<Student>(student_list);
    }

    public Student pickStudent(){
        if (unpicked_list.size() == 0){
            setupUnpickedList();
        }

        int index = (int) (Math.random() * (unpicked_list.size() + 1));
        return unpicked_list.remove(index);
    }

    public int getHour() {
        return class_hour;
    }

    /**
     * Adds a student with the given name to the classroom
     * @param firstName The student's first name
     * @param lastName The student's last name
     * @return If the student is new to the classroom
     */
    public boolean addStudent(String firstName, String lastName) {
        Student new_student = new Student(firstName, lastName);
        return addStudent(new_student);

    }

    /**
     * Adds a student to the classroom
     * @param new_student The student to add
     * @return If the student is new to the classroom
     */
    public boolean addStudent(Student new_student) {
        return student_list.add(new_student);
    }

    /**
     * Removes a student with the given name from the classroom
     * @param firstName The student's first name
     * @param lastName The student's last name
     * @return Whether or not they existed beforehand
     */
    public boolean removeStudent(String firstName, String lastName) {
        Student old_student = new Student(firstName, lastName);
        return removeStudent(old_student);

    }

    /**
     * Removes the student from the classroom if they exist
     * @param old_student The student to remove
     * @return Whether or not they existed beforehand
     */
    public boolean removeStudent(Student old_student){
        if (student_list.remove(old_student)){
            if (unpicked_list.contains(old_student))
                unpicked_list.remove(old_student);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Classroom classroom = (Classroom) o;
        return (class_hour != classroom.class_hour);
    }

    public int compareTo(Classroom other){
        return new Integer(this.getHour()).compareTo(other.getHour());
    }

    /**
     * Imports a set of students from a csv file
     * @param fileName The path of the file to import
     */
    public void importFromCSV(String fileName) throws IOException,
                                            IllegalClassFormatException{
        int firstNameIndex = -1;
        int lastNameIndex = -1;

        CSVReader reader = new CSVReader(new FileReader(fileName));
        List<String[]> csvData = reader.readAll();
        for (int i = 0; i < csvData.get(0).length; i++) {
            if (csvData.get(0)[i].toLowerCase().contains("first")) {
                firstNameIndex = i;
                break;
            }
        }
        for (int i = 0; i < csvData.get(0).length; i++) {
            if (i != firstNameIndex
                   && csvData.get(0)[i].toLowerCase().contains("last")){
                lastNameIndex = i;
                break;
            }
        }

        if (firstNameIndex == -1 && lastNameIndex == -1){
            throw new IllegalClassFormatException();
        }

        //Skip the header row
        for (int i = 1; i < csvData.size(); i++){
            String firstName = "";
            String lastName = "";
            if (firstNameIndex != -1) {
                firstName = csvData.get(i)[firstNameIndex];
            }
            if (lastNameIndex != -1){
                lastName = csvData.get(i)[lastNameIndex];
            }

            addStudent(firstName, lastName);
        }

    }

    @Override
    public String toString() {
        return student_list.toString();
    }

    public ArrayList<String> getNames(){
        ArrayList<String> names = new ArrayList<String>();
        for (Student student : student_list){
            names.add(student.toString());
        }

        return names;
    }

    public String getNamesStringWithNewLineSeperators(){
        ArrayList<String> names = getNames();
        String string = "";
        for (String name : names){
            string += name + "\n";
        }

        return string;
    }
}
