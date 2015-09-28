package us.andrewdickinson.ghhs.stickpicker;

/**
 * Created by Andrew on 9/13/2015.
 */
public class Student implements Comparable<Student>{
    private String firstName;
    private String lastName;

    public Student(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Student student = (Student) o;
        if (!firstName.equals(student.firstName)) return false;
        if (!lastName.equals(student.lastName)) return false;

        return true;
    }

    @Override
    public int compareTo(Student other){
        if (this.equals(other)) return 0;

        if (lastName.equals(other.lastName)){
            return firstName.compareTo(other.firstName);
        }

        return lastName.compareTo(other.lastName);
    }
}
