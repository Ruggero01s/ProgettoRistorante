import java.util.HashMap;

public class Booking
{
    private String name;
    private int number,workload;
    private HashMap<Dish,Integer> order = new HashMap<>();


    public Booking(String name, int number, int workload, HashMap<Dish, Integer> order) {
        this.name = name;
        this.number = number;
        this.workload = workload;
        this.order = order;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getWorkload() {
        return workload;
    }

    public void setWorkload(int workload) {
        this.workload = workload;
    }

    public HashMap<Dish, Integer> getOrder() {
        return order;
    }

    public void setOrder(HashMap<Dish, Integer> order) {
        this.order = order;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
