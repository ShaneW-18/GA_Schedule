package Objects;

import java.util.Arrays;

public class TimePeriod {
    int TimePeriodID;   // unique id for identifying a period
    char[] Days;   // an identifier that specifies the days
    int StartTime;    // start time of course

    public TimePeriod(int timePeriodID, char[] days, int startTime) {
        TimePeriodID = timePeriodID;
        Days = days;
        StartTime = startTime;
    }

    public int getTimePeriodID() {
        return TimePeriodID;
    }

    public void setTimePeriodID(int timePeriodID) {
        TimePeriodID = timePeriodID;
    }

    public int getStartTime() {
        return StartTime;
    }

    public void setStartTime(int startTime) {
        StartTime = startTime;
    }

    public char[] getDays() {
        return Days;
    }

    public void setDays(char[] days) {
        Days = days;
    }
    @Override
    public String toString(){
        return Arrays.toString(Days) + " at " + getStartTime();
    }
}
