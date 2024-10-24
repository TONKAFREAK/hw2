import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {


    /*
     * Input: File
     * Output: List<Employee>
     * 
     * reads the file and creates a list of employees and returns it
    */
    public static List<Employee> readEmployees(File file) {

        try {

            Scanner empReader = new Scanner(file);

            List<Employee> employees = new ArrayList<>();

            int y = 0;

            String id = "", name = "", address = "", address2 = "";
            double beforeMidnight = 0, midnight = 0, afterMidnight = 0;
        
            while (empReader.hasNext()) {

                String line = empReader.nextLine().trim();

                if (y == 0) {

                    id = line;
                    //System.out.println("id "+ line);

                } else if (y == 1) {

                    name = line;
                    //System.out.println("name "+ line);
                }

                else if (y == 2) {

                    address = line;
                    //System.out.println("address "+ line);
                } else if (y == 3) {

                    address2 = address + " " + line;
                    //System.out.println("address2 "+ line);
                } else if (y == 4) {

                    String[] rates = line.trim().split("\\s+");
                    beforeMidnight = Double.parseDouble(rates[0]);
                    midnight = Double.parseDouble(rates[1]);
                    afterMidnight = Double.parseDouble(rates[2]);

                    employees.add(new Employee(id, name, address2, beforeMidnight, midnight, afterMidnight));
                    
                } else if (y == 5) {

                    y = -1;
                }

                y++;
                
            }

            empReader.close();
            return employees;
 
        } catch (FileNotFoundException e) {
            e.printStackTrace();

            return null;
        }
    }


    /*
     * Input: File
     * Output: List<Payroll>
     * 
     * reads the file and creates a list of payrolls and returns it
    */
    public static List<Payroll> readPayroll (File file) throws Exception {
        
        Scanner payReader = new Scanner(file);

        List<Payroll> payrolls = new ArrayList<>(); 
        List<WorkedTime> workedTimes = new ArrayList<>();
        String id = "";
        int daysWorked = 0;

        while (payReader.hasNext()) {

            String line = payReader.nextLine().trim();
            if (line.length() == 4) {
                id = line;
                //System.out.println(id);

                daysWorked = Integer.parseInt(payReader.nextLine().trim());
                //System.out.println(daysWorked);

                for (int i = 0; i < daysWorked; i++) {

                    String[] hours = payReader.nextLine().trim().split("\\s+");
                    String[] startHour = hours[0].split(":");
                    String[] endHour = hours[1].split(":");
                    workedTimes.add(new WorkedTime(Integer.parseInt(startHour[0]), Integer.parseInt(startHour[1]), Integer.parseInt(endHour[0]), Integer.parseInt(endHour[1])));
                    //System.out.println(workedTimes);

                    
                }

                payrolls.add(new Payroll(id, daysWorked, workedTimes));

                workedTimes = new ArrayList<>();
            }
        }

        payReader.close();
        return payrolls;

    }
    
    /*
     * Input: List<Payroll>, List<Employee>
     * Output: void
     * 
     * calculates the total pay for each employee for all the days worked.
    */
    public static void calculatePayroll(List<Payroll> payrolls, List<Employee> employees) {
        final int PAY_PERIOD1_START = 0;     // 6:00 PM
        final int PAY_PERIOD1_END = 180;     // 9:00 PM
        final int PAY_PERIOD2_START = 180;   // 9:00 PM
        final int PAY_PERIOD2_END = 360;     // 12:00 AM
        final int PAY_PERIOD3_START = 360;   // 12:00 AM
        final int PAY_PERIOD3_END = 720;     // 6:00 AM

        for (Employee employee : employees) {
            double totalPay = 0.0;
            Payroll payroll = null;
            for (Payroll p : payrolls) {
                if (p.getId().equals(employee.getId())) {
                    payroll = p;
                    break;
                }
            }
            if (payroll == null) {
                continue;
            }

            for (WorkedTime workedTime : payroll.getWorkedTimes()) {
                int startMinutes = getMinutesSince6PM(workedTime.getStartHour(), workedTime.getStartMin());
                int endMinutes = getMinutesSince6PM(workedTime.getEndHour(), workedTime.getEndMin());

                if (endMinutes <= startMinutes) {
                    endMinutes += 720;
                }

                int overlapStart = Math.max(startMinutes, PAY_PERIOD1_START);
                int overlapEnd = Math.min(endMinutes, PAY_PERIOD1_END);
                if (overlapEnd > overlapStart) {
                    double hours = (overlapEnd - overlapStart) / 60.0;
                    totalPay += hours * employee.getHourlyRate(0);
                }

                overlapStart = Math.max(startMinutes, PAY_PERIOD2_START);
                overlapEnd = Math.min(endMinutes, PAY_PERIOD2_END);
                if (overlapEnd > overlapStart) {
                    double hours = (overlapEnd - overlapStart) / 60.0;
                    totalPay += hours * employee.getHourlyRate(1);
                }

                overlapStart = Math.max(startMinutes, PAY_PERIOD3_START);
                overlapEnd = Math.min(endMinutes, PAY_PERIOD3_END);
                if (overlapEnd > overlapStart) {
                    double hours = (overlapEnd - overlapStart) / 60.0;
                    totalPay += hours * employee.getHourlyRate(2);
                }
            }

            employee.setTotalPay(totalPay);
        }
    }

    // -------------------- HELPER METHODS --------------------

    /*
     * Input: int, int
     * Output: int
     * 
     * returns the number of minutes since 6:00 PM
    */
    public static int getMinutesSince6PM(int hour, int minute) {
        int hour24;

        if (hour >= 6 && hour <= 11) {
            hour24 = hour + 12; 
        } else if (hour == 12) {
            hour24 = 0; 
        } else if (hour >= 1 && hour <= 5) {
            hour24 = hour; 
        } else {
            
            System.err.println("Invalid hour: " + hour);
            return -1;
        }

        if (hour24 < 18) {
            hour24 += 24;
        }

        int totalMinutes = (hour24 - 18) * 60 + minute;
        return totalMinutes;
    }

    /*
     * Input: List<Employee>
     * Output: void
     * 
     * sorts the employees by last name, usses bubble sort algorithm.
    */
    public static void sortAndPrintEmployees( List<Employee> employees ) {

        int n = employees.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                String firstName1 = employees.get(j).getName().split(",")[1].trim();
                String firstName2 = employees.get(j + 1).getName().split(",")[1].trim();

                if (firstName2.compareTo(firstName1) < 0) {
                    Employee temp = employees.get(j);
                    employees.set(j, employees.get(j + 1));
                    employees.set(j + 1, temp);
                }
            }
        }

        System.out.println("\n:");

        for (Employee employee : employees) {
            if (employee.getTotalPay() > 0) {
                System.out.printf("Employee: %s | Pay: $%.2f\n", employee.getName(), employee.getTotalPay());
            }
        }

    }
    
    public static void main(String[] args) throws Exception {
        
        List<Employee> employees = readEmployees(new File("personnel_data.txt"));
        List<Payroll> payrolls = readPayroll(new File("payroll_data.txt"));
        //System.out.println(employees);
        //System.out.println(payrolls);

        calculatePayroll(payrolls, employees);

        sortAndPrintEmployees(employees);

    }
}
