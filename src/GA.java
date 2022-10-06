import Objects.Course;
import Objects.Room;
import Objects.Schedule;
import Objects.ScheduleRow;
import Objects.TimePeriod;
import Static.Global;

import java.util.*;

public class GA {

    List<Schedule> currentGeneration = new ArrayList<>();
    List<Schedule> pastGeneration = new ArrayList<>();
    PriorityQueue<Schedule> parentSelection = new PriorityQueue<>();
    Schedule parent1,  parent2;
    int max, min;
    double avg;

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
        for(int i = 0; i<MAX_GENERATION; i++) {
            Benchmarks();
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
                    parent1 = mutation(parent1);
                    parent2 = mutation(parent2);
                }
                parent1.calculateFitness();
                parent2.calculateFitness();
                currentGeneration.add(parent1);
                currentGeneration.add(parent2);
            }
        }

    }

    private Schedule[] crossover(){
        Random rand = new Random();
        Collections.shuffle(parent1.getScheduleRows());
        Collections.shuffle(parent2.getScheduleRows());
        var child1 = new Schedule();
        var child2 = new Schedule();


        var badDataCheck = false;
        var index = 1;
        do {
            badDataCheck = false;
            int crossoverPoint = rand.nextInt( 1, parent1.getScheduleRows().size() -1);
            List<ScheduleRow> firstHalf1 = new ArrayList<>(parent1.getScheduleRows().subList(0, crossoverPoint));
            List<ScheduleRow> secondHalf1 = new ArrayList<>(parent1.getScheduleRows().subList(crossoverPoint, parent1.getScheduleRows().size()));
            List<ScheduleRow> firstHalf2 = new ArrayList<>(parent2.getScheduleRows().subList(0, crossoverPoint));
            List<ScheduleRow> secondHalf2 = new ArrayList<>(parent2.getScheduleRows().subList(crossoverPoint, parent2.getScheduleRows().size()));

            for (var f : firstHalf1) {
                if (!child1.addClass(f))
                    badDataCheck = true;
            }
            for (var f : secondHalf2) {
                if (!child1.addClass(f))
                    badDataCheck = true;
            }
            for (var f : firstHalf2) {
                if (!child2.addClass(f))
                    badDataCheck = true;
            }
            for (var f : secondHalf1) {
                if (!child2.addClass(f))
                    badDataCheck = true;
            }

        }while(badDataCheck && index++ != 24);
        if(badDataCheck){
            return new Schedule[]{parent1, parent2};
        }
        return new Schedule[]{child1, child2};
    }

    private void crossoverCheck(){

    }
    private void tournament(){
        Random rand = new Random();
        var tournament1 = pastGeneration.get(rand.nextInt(0, pastGeneration.size() - 1));
        var tournament2 = pastGeneration.get(rand.nextInt(0, pastGeneration.size() - 1));
        while(tournament2.equals(tournament1)){
            tournament2 = pastGeneration.get(rand.nextInt(pastGeneration.size()));
        }
        parent1 = tournament1.getFitness() > tournament2.getFitness() ? tournament1 : tournament2;

        tournament1 = pastGeneration.get(rand.nextInt(pastGeneration.size()));
        tournament2 = pastGeneration.get(rand.nextInt(pastGeneration.size()));
        while(tournament2.equals(tournament1)){
            tournament2 = pastGeneration.get(rand.nextInt(pastGeneration.size()));
        }
        parent2 = tournament1.getFitness() > tournament2.getFitness() ? tournament1 : tournament2;
    }
    private boolean randomPopulation(){
        Random rand = new Random();
        while(currentGeneration.size() != MAX_GENERATION) {
            int index = 1;
            var badPop = 0;
            Schedule schedule = new Schedule();
            while (index != Global.COURSES.size() + 1) {
                var timeRand =rand.nextInt(Global.TIME_PERIOD_LIST.size());
                var roomRand = rand.nextInt(Global.ROOM_LIST.size());
                var time = Global.TIME_PERIOD_LIST.get(timeRand);
                var room = Global.ROOM_LIST.get(roomRand);
                var check = schedule.addClass(new ScheduleRow(time, room, Global.COURSES.get(index)));
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
    private Schedule mutation(Schedule schedule) {
        Random rand = new Random();
        var index = rand.nextInt(schedule.getScheduleRows().size());
        //System.out.println("crossover point: "  +index );
        TimePeriod time = schedule.getScheduleRows().get(index).getTimePeriod();;
        Room room = schedule.getScheduleRows().get(index).getRoom();;
        ScheduleRow newScheduleRow = schedule.getScheduleRows().get(index);
        List<Room> temp = new ArrayList<Room>(Global.ROOM_LIST);
       // System.out.println("origninal: "  + newScheduleRow);
        Collections.shuffle(temp);
        for (var r : temp) {
            if (checkRoom(Global.ROOMS.get("Biddle123"), schedule.getScheduleRows().get(index), schedule)) {
                room = r;
                break;
            }
        }
        for (var t : Global.TIME_PERIOD_LIST) {
            if (checkTime(t, newScheduleRow, schedule, room)) {
                time = t;
                break;
            }
        }

        newScheduleRow.setRoom(room);
        newScheduleRow.setTimePeriod(time);
        //System.out.println("after mutation : "  + newScheduleRow);
        schedule.getScheduleRows().set(index, newScheduleRow);
        return schedule;

    }
    private Boolean checkRoom(Room room, ScheduleRow row, Schedule schedule){
                if(Objects.equals(room.getRoomID(), row.getRoom().getRoomID()))
                    return false;
//                if(Objects.equals(room.getRoomID(), r.getRoom().getRoomID()) && row.getTimePeriod().getTimePeriodID() == r.getTimePeriod().getTimePeriodID())
//                    return false;
                if(row.getCourse().getSize() >= room.getSize() && (row.getCourse().getMultimedia() != room.getMultimedia() || !room.getMultimedia()))
                    return false;

        return true;
    }

    private Boolean checkTime(TimePeriod time, ScheduleRow row, Schedule schedule, Room room){
        for(ScheduleRow r : schedule.getScheduleRows()){
            if(!r.equals(row)){
                if(time.getTimePeriodID() == row.getTimePeriod().getTimePeriodID())
                    return false;
                if(room.getRoomID().equals(r.getRoom().getRoomID()) && time.getTimePeriodID() == r.getTimePeriod().getTimePeriodID())
                    return false;
                if(row.getCourse().getProfessor().getProfessorID() == r.getCourse().getProfessor().getProfessorID() && r.getTimePeriod().getTimePeriodID() == time.getTimePeriodID())
                    return false;
            }
        }
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
    private void Benchmarks(){
        max = -10000;
        min = 1000;
        for (var s : currentGeneration) {
            if(s.getFitness() > max)
                max = s.getFitness();
            if(s.getFitness() < min)
                min = s.getFitness();
            avg += s.getFitness();
        }
        avg = avg / currentGeneration.size();
        System.out.println("max: " + max + " min: " + min + " avg: " + avg);
    }


}
