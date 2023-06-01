import javax.management.InstanceNotFoundException;
import java.util.*;

public class Model implements RestaurantRepository
{
	public static final double INCREASE20 = 1.20;
	private int capacity, workPersonLoad, increment;
	private double workloadRestaurant;
	private DateOur today;
	private Map<String, Double> drinksMap = new HashMap<>();
	private Map<String, Double> extraFoodsMap = new HashMap<>();
	private Set<Dish> dishesSet = new HashSet<>();
	private Set<Recipe> recipesSet = new HashSet<>();
	private Set<ThematicMenu> thematicMenusSet = new HashSet<>();
	private Map<DateOur, List<Booking>> bookingMap = new HashMap<>();
	private Set<Ingredient> registroAfterMeal = new HashSet<>();
	private Set<Ingredient> registroBeforeMeal = new HashSet<>();
	private Set<User> users = new HashSet<>();
	private User theUser;

	public Set<ConvertToString> getDishesSetConverted () {
		return new HashSet<>(this.dishesSet);
	}

	public Set<ConvertToString> getRecipeSetConverted () {
		return new HashSet<>(this.recipesSet);
	}

	public Set<ConvertToString> getThematicMenuSetConverted () {
		return new HashSet<>(this.thematicMenusSet);
	}

	public User getTheUser () {
		return theUser;
	}

	public void setTheUser (User theUser) {
		this.theUser = theUser;
	}

	public Set<User> getUsers () {
		return users;
	}

	public void setUsers (Set<User> users) {
		this.users = users;
	}

	public Set<Ingredient> getRegistroAfterMeal () {
		return registroAfterMeal;
	}

	public void setRegistroAfterMeal (Set<Ingredient> registroAfterMeal) {
		this.registroAfterMeal = registroAfterMeal;
	}

	public Set<Ingredient> getRegistroBeforeMeal () {
		return registroBeforeMeal;
	}

	public void setRegistroBeforeMeal (Set<Ingredient> registroBeforeMeal) {
		this.registroBeforeMeal = registroBeforeMeal;
	}

	public int getWorkPersonLoad () {
		return workPersonLoad;
	}

	public void setWorkPersonLoad (int workPersonLoad) {
		this.workPersonLoad = workPersonLoad;
		updateWorkResturantLoad();
	}

	public DateOur getToday () {
		return today;
	}

	public void setToday (DateOur today) {
		this.today = today;
	}

	public Map<DateOur, List<Booking>> getBookingMap () {
		return bookingMap;
	}

	public void setBookingMap (Map<DateOur, List<Booking>> bookingMap) {
		this.bookingMap = bookingMap;
	}

	public void updateWorkResturantLoad () {
		this.workloadRestaurant = this.capacity * this.workPersonLoad * INCREASE20;
	}

	public double getWorkResturantLoad () {
		return workloadRestaurant;
	}

	public int getCapacity () {
		return capacity;
	}

	public void setCapacity (int capacity) {
		this.capacity = capacity;
		updateWorkResturantLoad();
	}

	public Set<Dish> getDishesSet () {
		return dishesSet;
	}

	public void setDishesSet (Set<Dish> dishesSet) {
		this.dishesSet = dishesSet;
	}

	public Set<Recipe> getRecipesSet () {
		return recipesSet;
	}

	public void setRecipesSet (Set<Recipe> recipesSet) {
		this.recipesSet = recipesSet;
	}

	public Set<ThematicMenu> getThematicMenusSet () {
		return thematicMenusSet;
	}

	public void setThematicMenusSet (Set<ThematicMenu> thematicMenusSet) {
		this.thematicMenusSet = thematicMenusSet;
	}

	public Map<String, Double> getDrinksMap () {
		return drinksMap;
	}

	public void setDrinksMap (Map<String, Double> drinksMap) {
		this.drinksMap = drinksMap;
	}

	public Map<String, Double> getExtraFoodsMap () {
		return extraFoodsMap;
	}

	public void setExtraFoodsMap (Map<String, Double> extraFoodsMap) {
		this.extraFoodsMap = extraFoodsMap;
	}

	public int getIncrement () {
		return increment;
	}

	public void setIncrement (int increment) {
		this.increment = increment;
	}

	public Dish findDish(String name) throws InstanceNotFoundException{
		for (Dish d: dishesSet) {
			if (d.getName().equalsIgnoreCase(name))
				return d;
		}
		throw new InstanceNotFoundException();
	}

	public Recipe findRecipe(String name) throws InstanceNotFoundException{
		for (Recipe r: recipesSet) {
			if (r.getId().equalsIgnoreCase(name))
				return r;
		}
		throw new InstanceNotFoundException();
	}

	public ThematicMenu findMenu(String name) throws InstanceNotFoundException {
		for (ThematicMenu t: thematicMenusSet) {
			if (t.getName().equalsIgnoreCase(name))
				return t;
		}
		throw new InstanceNotFoundException();
	}

	//TODO multithread?
	public boolean isDuplicate(String name) {
		for (Recipe r: recipesSet) {
			if (r.getId().equalsIgnoreCase(name))
				return true;
		}
		for (Dish d: dishesSet) {
			if (d.getName().equalsIgnoreCase(name))
				return true;
		}
		for (ThematicMenu t: thematicMenusSet) {
			if (t.getName().equalsIgnoreCase(name))
				return true;
		}
		return false;
	}


}
