import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
        WorkedTime workedTimes = null;
        String id = "";
        int daysWorked = 0;

        while (payReader.hasNext()) {

            


            
        }

        payReader.close();
        return payrolls;

    }

    public static void main(String[] args) throws Exception {
        
        List<Employee> employees = readEmployees(new File("personnel_data.txt"));
        List<Payroll> payrolls = readPayroll(new File("payroll_data.txt"));
        System.out.println(employees);
        //System.out.println(payrolls);
    }
}
