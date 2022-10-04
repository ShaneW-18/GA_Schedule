package Objects;

import java.util.ArrayList;
import java.util.List;

public class Schedule {
    List<ScheduleRow> schedule = new ArrayList<>();
    int fitness = 0;

    public boolean addClass(ScheduleRow row){
        if(checkForDuplicate(row) && checkDirty(row)){
            schedule.add(row);
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
    @Override
    public String toString(){
        return schedule.toString() + "\n__________\n";
    }

}
