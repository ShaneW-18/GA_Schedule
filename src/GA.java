import Objects.Schedule;
import Objects.ScheduleRow;
import Static.Global;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GA {

    List<Schedule> currentGeneration = new ArrayList<>();
    List<Schedule> pastGeneration = new ArrayList<>();

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
}
