
package FileUtils;

import Objects.Course;
import Objects.Professor;
import Objects.Room;
import Static.Global;
import Objects.TimePeriod;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class ReadFile {

    public void ReadProfessor() {
        File file = new File("src/Input/Professors");
        BufferedReader br = null;
        try {
            br = new BufferedReader(new java.io.FileReader(file));
            String st;
            while ((st = br.readLine()) != null) {
                String[] line = st.split("\\s+");
                Professor professor = new Professor(Integer.parseInt(line[0]), line[1]);
                Global.PROFESSORS.put(professor.getName(), professor);
            }
        }
        catch(IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void ReadTime() {
        File file = new File("src/Input/Time");
        BufferedReader br = null;
        try {
            br = new BufferedReader(new java.io.FileReader(file));
            String st;
            while ((st = br.readLine()) != null) {
                String[] line = st.split("\\s+");
                TimePeriod timePeriod = new TimePeriod(Integer.parseInt(line[0]), line[1].toCharArray(),Integer.parseInt(line[2]));
                Global.TIME_PERIOD.put(timePeriod.getTimePeriodID(), timePeriod);
                Global.TIME_PERIOD_LIST.add(timePeriod);
            }
        }
        catch(IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void ReadRooms(){
        File file = new File("src/Input/Rooms");
        BufferedReader br = null;
        try {
            br = new BufferedReader(new java.io.FileReader(file));
            String st;
            while ((st = br.readLine()) != null) {
                String[] line = st.split("\\s+");
                boolean media = Objects.equals(line[2], "X");
                Room room = new Room(line[0], Integer.parseInt(line[1]),media);
                Global.ROOMS.put(room.getRoomID(), room);
                Global.ROOM_LIST.add(room);
            }
        }
        catch(IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void ReadCourse() {
        File file = new File("src/Input/Courses");
        BufferedReader br = null;
        try {
            br = new BufferedReader(new java.io.FileReader(file));
            String st;
            while ((st = br.readLine()) != null) {
                String[] line = st.split("\\s+");
                boolean media = Objects.equals(line[4], "x");
                Course course = new Course(Integer.parseInt(line[0]), line[1], Global.PROFESSORS.get(line[2]), Integer.parseInt(line[3]), media);
                Global.COURSES.put(course.getCrn(), course);
            }
        }
    catch(IOException e) {
        throw new RuntimeException(e);
    }
    }
}
