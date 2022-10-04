package Objects;

import java.util.Comparator;

public class scheduleComparator implements Comparator<ScheduleRow> {
    @Override
    public int compare(ScheduleRow s1, ScheduleRow s2) {
        if(s1.getTimePeriod().TimePeriodID < s2.getTimePeriod().getTimePeriodID())
            return 1;
        else if (s1.getTimePeriod().TimePeriodID > s2.getTimePeriod().getTimePeriodID())
            return -1;
        return 0;
    }
}
