import java.util.HashMap;

public class Booking
{
    private final String name; //nome della prenotazione
    private final int number; //coperti della prenotazione
    private final int workload; //workload totale
    private  HashMap<Dish,Integer> order; //insieme dei piatti ordinati con la relativa quantità


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
    
}
