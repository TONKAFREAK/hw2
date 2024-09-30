public class Employee {
    
    private String id;
    private String name;
    private String address;
    private double[] hourlyRate;
    
    public Employee(String id, String name, String address, double beforeMidnight, double midnight, double afterMidnight ) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.hourlyRate = new double[2];
        this.hourlyRate[0] = beforeMidnight;
        this.hourlyRate[1] = midnight;
        this.hourlyRate[2] = afterMidnight;
        
    }

    public String getId() {
        return id;
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
}
