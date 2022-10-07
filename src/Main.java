import FileUtils.ReadFile;
import Objects.Schedule;
import Objects.ScheduleRow;
import Static.Global;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System. in);
        ReadFile read = new ReadFile();
        read.ReadProfessor();
        read.ReadRooms();
        read.ReadTime();
        read.ReadCourse();

        GA ga = new GA(300, 50, .75, .05);
        ga.createNewGeneration();


    }
}