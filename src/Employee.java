
/*
 * Stores Employee's private information such as:
 * Int : id 
 * String : name
 * String : address
 * double[] : hourly rate ( list of hourly rates )
 * double : total pay ( total money earned )
 * 
 * has getters and setters, + toString
*/
public class Employee {
    
    private String id;
    private String name;
    private String address;
    private double[] hourlyRate;
    private double totalPay;
    
    public Employee(String id, String name, String address, double beforeMidnight, double midnight, double afterMidnight ) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.hourlyRate = new double[3];
        this.hourlyRate[0] = beforeMidnight;
        this.hourlyRate[1] = midnight;
        this.hourlyRate[2] = afterMidnight;
        
    }

    public String getId() {
        return id;
    }

    public double getTotalPay() {
        return totalPay;
    }
    
    public void setTotalPay(double totalPay) {
        this.totalPay = totalPay;
    }
    
    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public double getHourlyRate(int index) {
        return hourlyRate[index];
    }

    @Override
    public String toString() {
        return "\nEmployee ID: " + id + ",\nName: " + name + ",\nAddress: " + address + 
               ",\nRates: [\nBefore 9PM: $" + hourlyRate[0] + 
               ",\n9PM to Midnight: $" + hourlyRate[1] + 
               ",\nAfter Midnight: $" + hourlyRate[2] + "\n]";
    }
}
