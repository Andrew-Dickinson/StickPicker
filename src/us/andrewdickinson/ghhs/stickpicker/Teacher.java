package us.andrewdickinson.ghhs.stickpicker;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.xml.internal.ws.org.objectweb.asm.ClassAdapter;

import java.util.ArrayList;
import java.util.prefs.Preferences;

/**
 * Created by Andrew on 9/13/2015.
 */
public class Teacher {
    private ArrayList<Classroom> classrooms = new ArrayList<Classroom>();

    /**
     * Adds a classroom to the teacher
     * @param classroom The classroom to add
     */
    public void addClassroom(Classroom classroom){
        if (classroom != null) {
            if (classrooms.contains(classroom)) {
                throw new IllegalArgumentException("A class already exists for that hour");
            } else {
                classrooms.add(classroom);
            }
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Removes a classroom from the teacher
     * @param hour The hour of the classroom to remove
     * @return Whether or not the hour existed beforehand
     */
    public boolean removeClassroom(int hour){
        for (Classroom classroom : classrooms){
            if (classroom.getHour() == hour){
                return classrooms.remove(classroom);
            }
        }
        return false;
    }

    /**
     * Gets a classroom the teacher has
     * @param hour The hour of the classroom to get
     * @return The requested classroom
     * @throws java.lang.IllegalArgumentException If teacher doesn't
     *      have a class during hour
     */
    public Classroom getClassroom(int hour){
        for (Classroom classroom : classrooms){
            if (classroom.getHour() == hour){
                return classroom;
            }
        }

        throw new IllegalArgumentException();
    }

    /**
     * Gets all of the classrooms for the teacher
     * @return An arraylist of the classrooms
     */
    public ArrayList<Classroom> getClassrooms(){
        return classrooms;
    }

    public String getGson(){
        Gson g = new Gson();
        return g.toJson(this);
    }

    /**
     * Gets an array of the class hours that this teacher has
     * @return An array of class hours
     */
    public String[] getClassHours(){
        String[] hours = new String[classrooms.size()];
        for (int i = 0; i < classrooms.size(); i++){
            hours[i] = Integer.toString(classrooms.get(i).getHour());
        }
        return hours;
    }

    public static Teacher getFromGson(String gson){
        Gson g = new Gson();
        return g.fromJson(gson, Teacher.class);
    }

    public void save(){
        final Preferences prefs = Preferences.userRoot()
                .node(this.getClass().getName());
        prefs.put("DATA", getGson());
    }

    public void load(){
        final Preferences prefs = Preferences.userRoot()
                .node(this.getClass().getName());
        String gson = prefs.get("DATA", "");
        if (!gson.equals("")){
            Teacher t = getFromGson(gson);
            classrooms = t.getClassrooms();
        }
    }
}
