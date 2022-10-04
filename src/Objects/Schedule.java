package Objects;

import Static.Global;
import org.w3c.dom.Node;

import java.util.*;
import java.util.stream.Collectors;

public class Schedule {
    List<ScheduleRow> schedule = new ArrayList<>();
    Map<Professor, PriorityQueue<ScheduleRow>> professorMap = new HashMap<>();
    int fitness = 0;

    public boolean addClass(ScheduleRow row){
        if(checkForDuplicate(row) && checkDirty(row)){
            schedule.add(row);
            if(professorMap.containsKey(row.getProfessor()))
                professorMap.get(row.getProfessor()).add(row);
            else {
                PriorityQueue<ScheduleRow> list = new PriorityQueue<ScheduleRow>(new scheduleComparator());
                list.add(row);
                professorMap.put(row.getProfessor(), list);
            }
            return true;
        }
        return false;

    }
    private boolean checkForDuplicate(ScheduleRow row){
        for (ScheduleRow s : schedule) {
            if(!s.equals(row))
                return false;
        }
        return true;
    }
    private boolean checkDirty(ScheduleRow row){
        if(row.getCourse().size <= row.getRoom().size && (row.getCourse().multimedia == row.getRoom().multimedia || row.getRoom().multimedia))
            return true;
        return false;
    }
    public void calculateFitness(){
        for (Professor p: Global.PROFESSORS_LIST) {
            int classesInRow = 0;
            int lastTime;
            for (var l : professorMap.get(p)) {
                lastTime = l.getTimePeriod().getTimePeriodID();
                if(l.getTimePeriod().getTimePeriodID() - lastTime >= 3)
                    fitness -= 15;
                if(l.getTimePeriod().getTimePeriodID() == lastTime + 1)
                    classesInRow++;
                fitness -= l.getRoom().size - l.getCourse().getSize();
            }
            if(classesInRow >= 3)
                fitness -= (classesInRow * 10) - 20;

            Map<Room, Integer> duplicateCountMap = new HashMap<>();
            professorMap.get(p).forEach(company -> duplicateCountMap.merge(company.getRoom(), 1, Integer::sum));
            int highest = 1;
            for (Map.Entry<Room,Integer> mapElement : duplicateCountMap.entrySet()) {
                if(mapElement.getValue() > highest)
                    highest = mapElement.getValue();
            }
            fitness += (highest * 5) - 5;
        }
    }

    @Override
    public String toString(){
        return schedule.toString() + "\n__________\n";
    }

}
