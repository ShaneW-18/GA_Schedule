package Objects;

import Static.Global;

import java.util.*;

public class Schedule {
    List<ScheduleRow> scheduleRows = new ArrayList<>();
    Map<Professor, PriorityQueue<ScheduleRow>> professorMap = new HashMap<>();
    int fitness = 0;

    public boolean addClass(ScheduleRow row){
        if(checkForDuplicate(row) && checkDirty(row)){
            scheduleRows.add(row);
            if(professorMap.containsKey(row.getProfessor()))
                professorMap.get(row.getProfessor()).add(row);
            else {
                PriorityQueue<ScheduleRow> list = new PriorityQueue<ScheduleRow>(new scheduleRowComparator());
                list.add(row);
                professorMap.put(row.getProfessor(), list);
            }
            return true;
        }
        return false;

    }
    private boolean checkForDuplicate(ScheduleRow row){
        for (ScheduleRow s : scheduleRows) {
            if(row.getRoom().equals((s.getRoom())) && row.getTimePeriod().equals(s.getTimePeriod()))
                return false;
            if(row.getProfessor().equals(s.getProfessor()) && row.getTimePeriod().equals(s.getTimePeriod()))
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
            List<ScheduleRow> professorSchedule = new ArrayList<>();
            while (!professorMap.get(p).isEmpty()){
                professorSchedule.add(professorMap.get(p).poll());
            }
            int classesInRow = 0;
            int lastTime = professorSchedule.get(0).getTimePeriod().TimePeriodID;
            for (var l : professorSchedule) {
                if(l.getTimePeriod().getTimePeriodID() - lastTime >= 3)
                    fitness -= 15;
                if(l.getTimePeriod().getTimePeriodID() == lastTime + 1)
                    classesInRow++;
                fitness -= l.getRoom().size - l.getCourse().getSize();
                lastTime = l.getTimePeriod().getTimePeriodID();
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

    public List<ScheduleRow> getScheduleRows() {
        return scheduleRows;
    }

    public void setScheduleRows(List<ScheduleRow> scheduleRows) {
        this.scheduleRows = scheduleRows;
    }

    public Map<Professor, PriorityQueue<ScheduleRow>> getProfessorMap() {
        return professorMap;
    }

    public void setProfessorMap(Map<Professor, PriorityQueue<ScheduleRow>> professorMap) {
        this.professorMap = professorMap;
    }

    public int getFitness() {
        return fitness;
    }

    public void setFitness(int fitness) {
        this.fitness = fitness;
    }

    @Override
    public String toString(){
        return scheduleRows.toString() + "\n__________\n";
    }

}
