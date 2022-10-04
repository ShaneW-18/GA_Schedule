package Objects;

public class Course {
    int crn;   // unique id for identifying a course offering
    String coursename;  // description of course
    Professor professor;  // from Professors Structure
    int size;    // max number of people enrolled
    Boolean multimedia;  // multimedia needed

    ////  place holders for room-time-professor matchings /////
//    int tpid;  // suggested time course is offered
//    int  rid;   // suggested room
//    int fitness;


    public Course(int crn, String courseName, Professor professor, int size, Boolean multimedia) {
        this.crn = crn;
        this.coursename = courseName;
        this.professor = professor;
        this.size = size;
        this.multimedia = multimedia;
    }

    public int getCrn() {
        return crn;
    }

    public void setCrn(int crn) {
        this.crn = crn;
    }

    public String getCoursename() {
        return coursename;
    }

    public void setCoursename(String coursename) {
        this.coursename = coursename;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Boolean getMultimedia() {
        return multimedia;
    }

    public void setMultimedia(Boolean multimedia) {
        this.multimedia = multimedia;
    }
    @Override
    public String toString(){
        return coursename;
    }
}
