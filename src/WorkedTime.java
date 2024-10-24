
/*
 * Stores WorkedTime's private information such as:
 * int : startHour ( start hour of the worked time )
 * int : startMin ( start minute of the worked time )
 * int : endHour ( end hour of the worked time )
 * int : endMin ( end minute of the worked time )
 * 
 * has getters and setters , + toString
*/
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

    @Override
    public String toString() {
        return startHour + ":" + startMin + " - " + endHour + ":" + endMin;
    }

}
