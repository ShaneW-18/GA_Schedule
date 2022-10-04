package Static;

import Objects.Course;
import Objects.Professor;
import Objects.Room;
import Objects.TimePeriod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Global {

    public static HashMap<String, Room> ROOMS = new HashMap<>();
    public static List<Room> ROOM_LIST = new ArrayList<>();
    public static HashMap<Integer, TimePeriod> TIME_PERIOD = new HashMap<>();
    public static List<TimePeriod> TIME_PERIOD_LIST = new ArrayList<>();

    public static HashMap<String, Professor> PROFESSORS = new HashMap<>();
    public static HashMap<Integer, Course> COURSES = new HashMap<>();

    public static List<Course> POPULATION = new ArrayList<>();
}
