package Objects;

import java.util.Objects;

public class ScheduleRow implements Comparable<ScheduleRow>{
    private int fitness;
    private TimePeriod timePeriod;
    private Room room;
    private Professor professor;
    private Course course;


    public ScheduleRow(TimePeriod timePeriod, Room room, Course course){
        this.timePeriod = timePeriod;
        this.room = room;
        this.course= course;
        this.professor = this.course.getProfessor();
        calcFitness();
    }

    public int getFitness() {
        return fitness;
    }

    public void setFitness(int fitness) {
        this.fitness = fitness;
    }

    public TimePeriod getTimePeriod() {
        return timePeriod;
    }

    public void setTimePeriod(TimePeriod timePeriod) {
        this.timePeriod = timePeriod;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    private void calcFitness(){

    }

    @Override
    public int compareTo(ScheduleRow scheduleRow) {
        return Integer.compare(this.fitness, scheduleRow.fitness);
    }
    @Override
    public boolean equals(Object o) {
        var row = (ScheduleRow)o;
        if(row.getRoom().equals((this.getRoom())) && row.getTimePeriod().equals(this.getTimePeriod()))
            return false;
        if(row.getProfessor().equals(this.getProfessor()) && row.getTimePeriod().equals(this.getTimePeriod()))
            return false;
        return true;
    }
    @Override
    public String toString(){
        return course + " with " + professor +  ": " + timePeriod + " in " +room + "\n";
    }
}



