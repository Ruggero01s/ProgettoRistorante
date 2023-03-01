import java.util.HashMap;

public class Booking
{
    private String name;
    private final int number;
    private int workload;
    private HashMap<Dish,Integer> order;


    public Booking(String name, int number, int workload, HashMap<Dish, Integer> order) {
        this.name = name;
        this.number = number;
        this.workload = workload;
        this.order = order;
    }

    public int getNumber() {
        return number;
    }
    
    public int getWorkload() {
        return workload;
    }
    
    public HashMap<Dish, Integer> getOrder() {
        return order;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
