package us.andrewdickinson.ghhs.stickpicker;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

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

    public String getGson(){
        Gson g = new Gson();
        return g.toJson(this);
    }

    public static Teacher getFromGson(String gson){
        Gson g = new Gson();
        return g.fromJson(gson, Teacher.class);
    }
}
