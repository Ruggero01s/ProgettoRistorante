import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public interface Read
{
	ArrayList<User> readPeople();
	
	void readConfig(Model model);
	
	HashMap<String, Double> readDrinks();
	
	HashMap<String, Double> readExtraFoods();
	
	Set<Recipe> readRecipes();
	
	Set<Dish> readDishes();
	
	Set<ThematicMenu> readThematicMenu();
	
	HashMap<DateOur, ArrayList<Booking>> readBooking();
	
	Set<Ingredient> readRegister();
}
