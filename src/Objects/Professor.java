package Objects;

public class Professor {
    int ProfessorID;   // unique id for identifying professors
    String name; // name of professor

    public Professor(int professorID, String name) {
        ProfessorID = professorID;
        this.name = name;
    }

    public int getProfessorID() {
        return ProfessorID;
    }

    public void setProfessorID(int professorID) {
        ProfessorID = professorID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @Override
    public String toString(){
        return name;
    }
}

