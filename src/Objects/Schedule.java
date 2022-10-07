package Objects;

import Static.Global;

import java.util.*;

public class Schedule {
    List<ScheduleRow> scheduleRows = new ArrayList<>();
    Map<Professor, PriorityQueue<ScheduleRow>> professorMap = new HashMap<>();
    int fitness = 0;

    public boolean addClass(ScheduleRow row, boolean set, int i){
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
    public void removeClass(ScheduleRow row){
        List<ScheduleRow> professorSchedule = new ArrayList<>();
        while (!professorMap.get(row.getProfessor()).isEmpty()) {
            professorSchedule.add(professorMap.get(row.getProfessor()).poll());
        }
        for(var i = 0; i<professorSchedule.size(); i++){
            if(Objects.equals(professorSchedule.get(i).getRoom().getRoomID(), row.getRoom().RoomID) && professorSchedule.get(i).getTimePeriod().getTimePeriodID() == row.getTimePeriod().getTimePeriodID()) {
                professorSchedule.remove(i);
                break;
            }
        }
        for(var s : professorSchedule){
            professorMap.get(row.getProfessor()).add(s);
        }



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
        fitness = 0;

        for (Professor p: Global.PROFESSORS_LIST) {
            List<ScheduleRow> professorSchedule = new ArrayList<>();
            try {
                while (!professorMap.get(p).isEmpty()) {
                    professorSchedule.add(professorMap.get(p).poll());
                }
            }
            catch (Exception e){
                System.out.println();
            }

            for(var s : professorSchedule){
                professorMap.get(p).add(s);
            }

            int classesInRow = 0;
            int lastTime = 0;
            try {

                 lastTime = professorSchedule.get(0).getTimePeriod().TimePeriodID;
            }
            catch (Exception e){
                System.out.println();
            }
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
