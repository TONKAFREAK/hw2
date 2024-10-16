import java.util.ArrayList;
import java.util.List;

public class Payroll {
    
    private String id;
    private int daysWorked;
    private List<WorkedTime> workedTimes= new ArrayList<>();

    public Payroll(String id, int daysWorked, WorkedTime workedTimes) {
        this.id = id;
        this.daysWorked = daysWorked;
        this.workedTimes.add(workedTimes);
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
        return "Payroll{" +
                "id='" + id + '\'' +
                ", daysWorked=" + daysWorked +
                ", workedTimes=" + workedTimes +
                '}';
    }
}
