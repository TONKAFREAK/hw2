import java.util.ArrayList;
import java.util.List;

/*
 * Stores Payroll's private information such as:
 * String : id ( id of the employye whom the payroll is for )
 * int : daysWorked ( number of days worked by the employee )
 * List<WorkedTime> : workedTimes ( list of worked times object )
 * 
 * has getters and setters, + toString
*/

public class Payroll {
    
    private String id;
    private int daysWorked;
    private List<WorkedTime> workedTimes= new ArrayList<>();

    public Payroll(String id, int daysWorked, List<WorkedTime> workedTimes) {
        this.id = id;
        this.daysWorked = daysWorked;
        this.workedTimes = workedTimes;
    }

    public String getId() {
        return id;
    }

    public int getDaysWorked() {
        return daysWorked;
    }

    public List<WorkedTime> getWorkedTimes() {
        return workedTimes;
    }

    public void addWorkedTime(WorkedTime workedTime) {
        workedTimes.add(workedTime);
    }

    @Override
    public String toString() {
        return "\nPayroll\n{" +
                "\nid='" + id + '\'' +
                ",\ndaysWorked=" + daysWorked +
                ",\nworkedTimes=" + workedTimes +
                "\n}";
    }
}
