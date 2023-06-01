import javax.management.InstanceNotFoundException;

public interface RestaurantRepository {
    public Dish findDish(String name)throws InstanceNotFoundException;
    public Recipe findRecipe(String name)throws InstanceNotFoundException;
    public ThematicMenu findMenu(String name) throws InstanceNotFoundException;
    public boolean isDuplicate(String name);
}
