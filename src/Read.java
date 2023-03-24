import java.util.*;

public interface Read
{
	Set<User> readPeople();
	
	ModelAttributes readConfig();
	
	Map<String, Double> readDrinks();
	
	Map<String, Double> readExtraFoods();
	
	Set<Recipe> readRecipes();
	
	Set<Dish> readDishes();
	
	Set<ThematicMenu> readThematicMenu();
	
	Map<DateOur, List<Booking>> readBooking();
	
	Set<Ingredient> readRegister();
}
