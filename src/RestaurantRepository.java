import javax.management.InstanceNotFoundException;
import java.util.List;
import java.util.Map;

public interface RestaurantRepository
{
    Dish findDish(String name)throws InstanceNotFoundException;
    Recipe findRecipe(String name)throws InstanceNotFoundException;
    ThematicMenu findMenu(String name) throws InstanceNotFoundException;
    User findUser(String name) throws InstanceNotFoundException;
    boolean isDuplicate(String name);
    boolean add(Dish dish);
    boolean add(ThematicMenu menu);
    boolean add(Recipe recipe);
    boolean add(User user);
    void addDrinks(Map<String,Double> drinks);
    void addFoods(Map<String,Double> foods);
    void addBookings(DateOur date, List<Booking> bookings );
    void nextDay();
    void deleteOldBookings();
    boolean clearDayBookings (DateOur date);
    void clearBookings();
    boolean remove(String name) throws InstanceNotFoundException;
}
