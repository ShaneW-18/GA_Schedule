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
//
//        System.out.print("Enter population size: ");
//        var populationSize = scanner.nextInt();
//        System.out.print("Enter Maximum Generations: ");
//        var maxGenerations = scanner.nextInt();
//        System.out.println("Enter Crossover Chance in decimal: ");
//        var crossoverChance = scanner.nextDouble();
//        System.out.println("Enter Mutation Chance in decimal: ");
//        var mutationChance = scanner.nextDouble();

        GA ga = new GA(5, 5, 5, 5);
        System.out.println(ga);
        ga.createNewGeneration();
//        Schedule s = new Schedule();
//        ScheduleRow r = new ScheduleRow(Global.TIME_PERIOD.get(2), Global.ROOMS.get("Biddle123"), Global.COURSES.get(3));
//        ScheduleRow r1 = new ScheduleRow(Global.TIME_PERIOD.get(2), Global.ROOMS.get("BL138"), Global.COURSES.get(5));
//        ScheduleRow r2 = new ScheduleRow(Global.TIME_PERIOD.get(3), Global.ROOMS.get("Biddle205"), Global.COURSES.get(8));
//        s.addClass(r1);
//        s.addClass(r2);
//        s.addClass(r1);
       // System.out.println();

    }
}