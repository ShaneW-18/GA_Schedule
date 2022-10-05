import Objects.Schedule;
import Objects.ScheduleRow;
import Static.Global;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;

public class GA {

    List<Schedule> currentGeneration = new ArrayList<>();
    List<Schedule> pastGeneration = new ArrayList<>();
    PriorityQueue<Schedule> parentSelection = new PriorityQueue<>();
    Schedule parent1,  parent2;

    final int MAX_GENERATION;
    final int POPULATION_SIZE;
    final double CROSSOVER_CHANCE;
    final double MUTATION_CHANCE;

    public GA(int MAX_GENERATION, int POPULATION_SIZE, double CROSSOVER_CHANCE, double MUTATION_CHANCE) {
        this.MAX_GENERATION = MAX_GENERATION;
        this.POPULATION_SIZE = POPULATION_SIZE;
        this.CROSSOVER_CHANCE = CROSSOVER_CHANCE;
        this.MUTATION_CHANCE = MUTATION_CHANCE;
        randomPopulation();
    }

    public void createNewGeneration(){
        pastGeneration.addAll(currentGeneration);
        tournament();
        if(Math.random() <= MUTATION_CHANCE) {
            crossover();
        }

    }

    private void crossover(){
        Random rand = new Random();

        int crossoverPoint = rand.nextInt(MAX_GENERATION);

        List<ScheduleRow> firstHalf1 = parent1.getScheduleRows().subList(0, crossoverPoint);
        List<ScheduleRow> secondHalf1 = parent1.getScheduleRows().subList(crossoverPoint, parent1.getScheduleRows().size() -1);
        List<ScheduleRow> firstHalf2 = parent2.getScheduleRows().subList(0, crossoverPoint);
        List<ScheduleRow> secondHalf2 = parent2.getScheduleRows().subList(crossoverPoint, parent2.getScheduleRows().size() -1);
        System.out.println();
    }
    private void mutation(){
       for(int i = 0; i < parent1.getScheduleRows().size(); i++){

       }
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
    private void randomPopulation(){
        Random rand = new Random();
        while(currentGeneration.size() != MAX_GENERATION) {
            int index = 1;
            Schedule schedule = new Schedule();
            while (index != Global.COURSES.size() + 1) {
                var time = Global.TIME_PERIOD_LIST.get(rand.nextInt(Global.TIME_PERIOD_LIST.size()));
                var room = Global.ROOM_LIST.get(rand.nextInt(Global.ROOM_LIST.size()));
                var check = schedule.addClass(new ScheduleRow(time, room, Global.COURSES.get(index)));
                if (check)
                    index++;
            }
            schedule.calculateFitness();
            currentGeneration.add(schedule);
        }
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

}
