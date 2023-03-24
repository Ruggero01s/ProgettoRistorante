import java.util.Map;

public class Booking
{
    private final String name; //nome della prenotazione
    private final int number; //coperti della prenotazione
    private final int workload; //workload totale
    private Map<Dish,Integer> order; //insieme dei piatti ordinati con la relativa quantità


    public Booking(String name, int number, int workload, Map<Dish, Integer> order) {
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
    
    public Map<Dish, Integer> getOrder() {
        return order;
    }
    
    public String getName() {
        return name;
    }
    
}
