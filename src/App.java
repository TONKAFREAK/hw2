import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Date;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.time.Duration;

public class App {

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
    
    public static void calculatePayroll(List<Payroll> payrolls, List<Employee> employees) {
    
        LocalTime ninePM = LocalTime.of(21, 0);   // 9:00 PM
        LocalTime midnight = LocalTime.MIDNIGHT;  // 12:00 AM
        double totalPay = 0;
    
        for (Payroll payroll : payrolls) {
            for (Employee employee : employees) {
                if (employee.getId().equals(payroll.getId())) {
                    System.out.println(employee.getId() );
                    for (WorkedTime workedTime : payroll.getWorkedTimes()) {

                        LocalTime startTime = LocalTime.of( 12 +workedTime.getStartHour(), workedTime.getStartMin());
                        LocalTime endTime;
                        if (workedTime.getEndHour() == 12) {
                            endTime = LocalTime.of( 0, workedTime.getEndMin());
                        } else if (workedTime.getEndHour() > workedTime.getStartHour() ){
                            endTime = LocalTime.of( 12 +workedTime.getEndHour(), workedTime.getEndMin());
                        }else {
                            endTime = LocalTime.of( workedTime.getEndHour(), workedTime.getEndMin());
                        }
                        
                        //System.out.println(startTime + " - " + endTime);

                        if (employee.getId().equals("0001") || employee.getId().equals("0003")) {

                            if (startTime.isBefore(ninePM)) {

                                double hoursWorked = (double) Duration.between(startTime, ninePM).toMinutes() / 60;
                                System.out.println("BEFORE 9: "+hoursWorked+" Hourly pay: "+employee.getHourlyRate(0));
                                totalPay += employee.getHourlyRate(0) * hoursWorked;
                                startTime = LocalTime.of(21, 0);
                                
                            }
    
                            if (startTime.equals(ninePM) && endTime.getHour() > midnight.getHour()) {
                                LocalTime endTime2 = endTime.getHour() < startTime.getHour() ? LocalTime.of(0, 0) : endTime;
                                double hoursWorked = (double) Duration.between(startTime, endTime2).toMinutes() / 60;
                                System.out.println("AFTER 9 AND BEFORE 12: "+hoursWorked+" Hourly pay: "+employee.getHourlyRate(1));
                                totalPay += employee.getHourlyRate(1) * hoursWorked;
                                startTime = midnight;
                            }
    
                            if (endTime.getHour() < startTime.getHour()) {
                                LocalTime endTime2 = endTime.getHour() < startTime.getHour() ? LocalTime.of(12, 0).plusHours(1) : endTime;
                                double hoursWorked = (double) Duration.between(startTime, endTime2).toMinutes() / 60;
                                System.out.println("AFTER 12: "+hoursWorked+" Hourly pay: "+employee.getHourlyRate(2));
                                totalPay += employee.getHourlyRate(2) * hoursWorked;
                                startTime = endTime;
                            }
                            
                        } 
    
                    }

                    employee.setTotalPay(totalPay);
                    
                    if (employee.getId().equals("0001") || employee.getId().equals("0003")) {
                        System.out.println("Total pay for employee " + employee.getName() + " (" + employee.getId() + "): $" + totalPay);
                    }

                    totalPay = 0;
                    
                }
            }
        }
    }
    


    public static void main(String[] args) throws Exception {
        
        List<Employee> employees = readEmployees(new File("personnel_data.txt"));
        List<Payroll> payrolls = readPayroll(new File("payroll_data.txt"));
        //System.out.println(employees);
        //System.out.println(payrolls);

        calculatePayroll(payrolls, employees);
    }
}
