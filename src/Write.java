import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public interface Write
{
    void writePeople(ArrayList<User> people);
    
    void writeConfigBase(int capacity, int workPersonLoad, DateOur today, int increment);
    
    void writeDrinks(HashMap<String, Double> drinks);
    
    void writeExtraFoods(HashMap<String, Double> extraFoodsMap);
    
    void writeRecipes(Set<Recipe> recipesSet);
    
    void writeDishes(Set<Dish> dishesSet);
    
    void writeThematicMenu(Set<ThematicMenu> thematicMenusSet);
    
    void writeBookings(HashMap<DateOur, ArrayList<Booking>> bookingMap);
    
    void writeRegister(Set<Ingredient> registro);
}
