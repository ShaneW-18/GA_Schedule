package Objects;

import java.util.Comparator;

public class ScheduleComparator implements Comparator<Schedule> {
    @Override
    public int compare(Schedule s1, Schedule s2) {
        if(s1.fitness < s2.fitness)
            return 1;
        else if (s1.fitness > s2.fitness)
            return -1;
        return 0;
    }
}
