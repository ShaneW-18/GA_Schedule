import Objects.Room;
import Objects.Schedule;
import Objects.ScheduleRow;
import Objects.TimePeriod;
import Static.Global;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class GA {

    List<Schedule> currentGeneration = new ArrayList<>();
    List<Schedule> pastGeneration = new ArrayList<>();
    PriorityQueue<Schedule> parentSelection = new PriorityQueue<>();
    Schedule parent1,  parent2;
    int max, min;
    double avg;
    int generation = 0;
    final int MAX_GENERATION;
    final int POPULATION_SIZE;
    final double CROSSOVER_CHANCE;
    final double MUTATION_CHANCE;

    public GA(int MAX_GENERATION, int POPULATION_SIZE, double CROSSOVER_CHANCE, double MUTATION_CHANCE) {
        this.MAX_GENERATION = MAX_GENERATION;
        this.POPULATION_SIZE = POPULATION_SIZE;
        this.CROSSOVER_CHANCE = CROSSOVER_CHANCE;
        this.MUTATION_CHANCE = MUTATION_CHANCE;
        while(true) {
            if (randomPopulation())
                break;
        }
    }

    public void createNewGeneration(){
        for(int i = 0; i<MAX_GENERATION -1; i++) {
            Benchmarks(false);
            pastGeneration.clear();
            pastGeneration.addAll(currentGeneration);
            currentGeneration.clear();
            while (currentGeneration.size() != POPULATION_SIZE) {
                tournament();
                if(Math.random() <= CROSSOVER_CHANCE) {
                    var parents = crossover();
                     parent1 = parents[0];
                     parent2 = parents[1];

                }
                if (Math.random() <= MUTATION_CHANCE) {
                    mutation(parent1);
                }
                if (Math.random() <= MUTATION_CHANCE) {
                    mutation(parent2);
                }

                parent1.calculateFitness();
                parent2.calculateFitness();
                currentGeneration.add(parent1);
                currentGeneration.add(parent2);
            }
        }
        Benchmarks(true);

    }

    private Schedule[] crossover(){
        Random rand = new Random();
        var child1 = new Schedule();
        var child2 = new Schedule();

        var badDataCheck = false;
        var crossoverPoint = ThreadLocalRandom.current().nextInt(parent1.getScheduleRows().size() - 2) + 1;
            List<ScheduleRow> firstHalf1 = new ArrayList<>(parent1.getScheduleRows().subList(0, crossoverPoint));
            List<ScheduleRow> secondHalf1 = new ArrayList<>(parent1.getScheduleRows().subList(crossoverPoint, parent1.getScheduleRows().size()));
            List<ScheduleRow> firstHalf2 = new ArrayList<>(parent2.getScheduleRows().subList(0, crossoverPoint));
            List<ScheduleRow> secondHalf2 = new ArrayList<>(parent2.getScheduleRows().subList(crossoverPoint, parent2.getScheduleRows().size()));

            for (var f : firstHalf1) {
                if (!child1.addClass(f, false, 0)) {
                    badDataCheck = true;
                    break;
                }
            }

            for (var f : secondHalf2) {
                if (badDataCheck || !child1.addClass(f, false, 0)) {
                    badDataCheck = true;
                    break;
                }
            }
            for (var f : firstHalf2) {
                if (badDataCheck||!child2.addClass(f, false, 0)) {
                    badDataCheck = true;
                    break;
                }
            }
            for (var f : secondHalf1) {
                if (badDataCheck||!child2.addClass(f, false, 0)) {
                    badDataCheck = true;
                    break;
                }
            }

        if(badDataCheck || child1.equals(child2)){
            return new Schedule[]{parent1, parent2};
        }
        return new Schedule[]{child1, child2};
    }

    private void tournament(){

            Random rand = new Random();
            var tournament1 = pastGeneration.get(rand.nextInt(0, pastGeneration.size() - 1));
            var tournament2 = pastGeneration.get(rand.nextInt(0, pastGeneration.size() - 1));

            parent1 = tournament1.getFitness() > tournament2.getFitness() ? tournament1 : tournament2;

            tournament1 = pastGeneration.get(rand.nextInt(pastGeneration.size()));
            tournament2 = pastGeneration.get(rand.nextInt(pastGeneration.size()));

            parent2 = tournament1.getFitness() > tournament2.getFitness() ? tournament1 : tournament2;

    }
    private boolean randomPopulation(){
        Random rand = new Random();
        while(currentGeneration.size() != POPULATION_SIZE) {
            int index = 1;
            var badPop = 0;
            Schedule schedule = new Schedule();
            while (index != Global.COURSES.size() + 1) {
                var timeRand =rand.nextInt(Global.TIME_PERIOD_LIST.size());
                var roomRand = rand.nextInt(Global.ROOM_LIST.size());
                var time = Global.TIME_PERIOD_LIST.get(timeRand);
                var room = Global.ROOM_LIST.get(roomRand);
                var check = schedule.addClass(new ScheduleRow(time, room, Global.COURSES.get(index)), false, 0);
                badPop++;
                if(badPop > 500)
                    return false;
                if (check) {
                    index++;
                }
            }
            schedule.calculateFitness();
            currentGeneration.add(schedule);
        }
        return true;
    }
    private Schedule randomSchedule(){
        Random rand = new Random();
        int index = 1;
        var badPop = 0;
        var badPopCheck = false;
        Schedule schedule = new Schedule();
        while (index != Global.COURSES.size() + 1) {
            var timeRand =rand.nextInt(Global.TIME_PERIOD_LIST.size());
            var roomRand = rand.nextInt(Global.ROOM_LIST.size());
            var time = Global.TIME_PERIOD_LIST.get(timeRand);
            var room = Global.ROOM_LIST.get(roomRand);
            var check = schedule.addClass(new ScheduleRow(time, room, Global.COURSES.get(index)),false, 0);
            badPop++;
            if(badPop > 500) {
                badPopCheck = true;
                break;
            }
            if (check) {
                index++;
            }
        }
        if(badPopCheck)
            schedule = randomSchedule();
        return schedule;

    }
    private void mutation(Schedule schedule) {
        Random rand = new Random();
        var index = rand.nextInt(schedule.getScheduleRows().size());

        Room room = new Room(schedule.getScheduleRows().get(index).getRoom().getRoomID(), schedule.getScheduleRows().get(index).getRoom().getSize(), schedule.getScheduleRows().get(index).getRoom().getMultimedia());
        ScheduleRow newScheduleRow = new ScheduleRow(schedule.getScheduleRows().get(index).getTimePeriod(), schedule.getScheduleRows().get(index).getRoom(), schedule.getScheduleRows().get(index).getCourse());
        List<Room> temp = new ArrayList<Room>(Global.ROOM_LIST);

        Collections.shuffle(temp);

        for (var r : temp) {
            if (checkRoom(Global.ROOMS.get(r.getRoomID()), schedule.getScheduleRows().get(index), schedule)) {
                room = r;
                break;
            }
        }
        newScheduleRow.setRoom(room);
        for (var t : Global.TIME_PERIOD_LIST) {
            newScheduleRow.setTimePeriod(t);
            var check = schedule.addClass(newScheduleRow,false, 0);
            if (check) {
                schedule.removeClass(schedule.getScheduleRows().get(index));
                schedule.getScheduleRows().remove(index);
                schedule.calculateFitness();
                schedule.setScheduleRows(sort(schedule.getScheduleRows()));
                break;
            }
        }

    }
    private  List<ScheduleRow> sort(List<ScheduleRow> list){
        List<ScheduleRow> sortedList = new ArrayList<>();
        for (var s: list) {
            if(s.getProfessor().getName().equals("Peter"))
                sortedList.add(s);
        }
        for (var s: list) {
            if(s.getProfessor().getName().equals("Brian"))
                sortedList.add(s);
        }
        for (var s: list) {
            if(s.getProfessor().getName().equals("Bilitski"))
                sortedList.add(s);
        }
        for (var s: list) {
            if(s.getProfessor().getName().equals("Ohl"))
                sortedList.add(s);
        }       for (var s: list) {
            if(s.getProfessor().getName().equals("Sandro"))
                sortedList.add(s);
        }
        for (var s: list) {
            if(s.getProfessor().getName().equals("Mr.xxx"))
                sortedList.add(s);
        }

        for (var s: list) {
            if(s.getProfessor().getName().equals("Frederick"))
                sortedList.add(s);
        }

        for (var s: list) {
            if(s.getProfessor().getName().equals("Meg"))
                sortedList.add(s);
        }

        for (var s: list) {
            if(s.getProfessor().getName().equals("Stewie"))
                sortedList.add(s);
        }
        for (var s: list) {
            if(s.getProfessor().getName().equals("Glen"))
                sortedList.add(s);
        }
        return sortedList;



    }
    private Boolean checkRoom(Room room, ScheduleRow row, Schedule schedule){
                if(Objects.equals(room.getRoomID(), row.getRoom().getRoomID()))
                    return false;
                var count = 0;
                for(var s : schedule.getScheduleRows()){
                    if(Objects.equals(s.getRoom().getRoomID(), room.getRoomID()))
                        count++;
                    if(count == 6)
                        return false;
                }
                if(row.getCourse().getSize() >= room.getSize() && (row.getCourse().getMultimedia() != room.getMultimedia() || !room.getMultimedia()))
                    return false;

        return true;
    }




    public List<Schedule> getCurrentGeneration() {
        return currentGeneration;
    }

    public void setCurrentGeneration(List<Schedule> currentGeneration) {
        this.currentGeneration = currentGeneration;
    }

    public List<Schedule> getPastGeneration() {
        return pastGeneration;
    }

    public void setPastGeneration(List<Schedule> pastGeneration) {
        this.pastGeneration = pastGeneration;
    }

    @Override
    public String toString(){
        return currentGeneration.toString();
    }
    private void Benchmarks(boolean done){
        generation++;
        max = -10000;
        Schedule maxSchedule= null;
        min = 1000;
        for (var s : currentGeneration) {
            if(s.getFitness() > max) {
                max = s.getFitness();
                maxSchedule = s;
            }
            if(s.getFitness() < min)
                min = s.getFitness();
            avg += s.getFitness();
        }
        avg = avg / currentGeneration.size();
        System.out.println("Generation: " +generation+" | max: " + max + " min: " + min + " avg: " + avg);
        if(done){
            System.out.println("----------------------------------------------------------------------------------------------");
            System.out.printf("%5s %10s %15s %15s %6s %5s %10s %10s %10s", "CRN", "Course", "Professor", "Room", "Needs", "Has", "Time", "Size", "Capacity");
            System.out.println("\n----------------------------------------------------------------------------------------------");
            for (ScheduleRow r : maxSchedule.getScheduleRows()) {
                System.out.printf("%5d %10s %15s %15s %6s %5s %10s %10d %10d",
                        r.getCourse().getCrn(), r.getCourse().getCoursename().toUpperCase(), r.getProfessor().getName(), r.getRoom().getRoomID(),
                        r.getCourse().getMultimedia() ? "X" : "", r.getRoom().getMultimedia() ? "X" : "", r.getTimePeriod().getStartTime(), r.getCourse().getSize(), r.getRoom().getSize());
                System.out.println();
            }
        }
    }


}
