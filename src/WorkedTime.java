public class WorkedTime {
    
    private int startHour, startMin, endHour, endMin;

    public WorkedTime(int startHour, int startMin, int endHour, int endMin) {
        this.startHour = startHour;
        this.startMin = startMin;
        this.endHour = endHour;
        this.endMin = endMin;
    }


    public int getStartHour() {
        return startHour;
    }    

    public int getStartMin() {
        return startMin;
    }

    public int getEndHour() {
        return endHour;
    }

    public int getEndMin() {
        return endMin;
    }
}
