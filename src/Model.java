import java.util.*;

public class Model {

    public static final String CLEAR= "";

    static final double INCREASE20 = 1.20;

    private int capacity, workPersonLoad,increment;
    private double workloadRestaurant;
    private DateOur today;
    private HashMap<String, Double> drinksMap = new HashMap<>();
    private HashMap<String, Double> extraFoodsMap = new HashMap<>();
    private Set<Dish> dishesSet = new HashSet<>();
    private Set<Recipe> recipesSet= new HashSet<>();
    private Set<ThematicMenu> thematicMenusSet= new HashSet<>();
    private HashMap<DateOur,ArrayList<Booking>>  bookingMap = new HashMap<>();
    private Set<Ingredient> grocerySet = new HashSet<>();
    private Set<Ingredient> registro = new HashSet<>();
    
    public Set<Ingredient> getRegistro()
    {
        return registro;
    }
    
    public void setRegistro(Set<Ingredient> registro)
    {
        this.registro = registro;
    }
    
    public Set<Ingredient> getGrocerySet() {
        return grocerySet;
    }

    public void setGroceryMap(Set<Ingredient> grocerySet) {
        this.grocerySet = grocerySet;
    }

    public int getWorkPersonLoad() {
        return workPersonLoad;
    }

    public void setWorkPersonLoad(int workPersonLoad) {
        this.workPersonLoad = workPersonLoad;
        updateWorkResturantLoad();
    }

    public DateOur getToday() {
        return today;
    }

    public void setToday(DateOur today) {
        this.today = today;
    }

    public void setWorkloadRestaurant(double workloadRestaurant) {
        this.workloadRestaurant = workloadRestaurant;
    }

    public HashMap<DateOur, ArrayList<Booking>> getBookingMap() {
        return bookingMap;
    }

    public void setBookingMap(HashMap<DateOur, ArrayList<Booking>> bookingMap) {
        this.bookingMap = bookingMap;
    }

    public void updateWorkResturantLoad() {
        this.workloadRestaurant = this.capacity * this.workPersonLoad * INCREASE20;
    }

    public double getWorkResturantLoad() {
        return workloadRestaurant;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
        updateWorkResturantLoad();
    }

    public Set<Dish> getDishesSet() {
        return dishesSet;
    }

    public void setDishesSet(Set<Dish> dishesSet) {
        this.dishesSet = dishesSet;
    }

    public Set<Recipe> getRecipesSet() {
        return recipesSet;
    }

    public void setRecipesSet(Set<Recipe> recipesSet) {
        this.recipesSet = recipesSet;
    }

    public Set<ThematicMenu> getThematicMenusSet() {
        return thematicMenusSet;
    }

    public void setThematicMenusSet(Set<ThematicMenu> thematicMenusSet) {
        this.thematicMenusSet = thematicMenusSet;
    }

    public HashMap<String, Double> getDrinksMap() {
        return drinksMap;
    }

    public void setDrinksMap(HashMap<String, Double> drinksMap) {
        this.drinksMap = drinksMap;
    }

    public HashMap<String, Double> getExtraFoodsMap() {
        return extraFoodsMap;
    }

    public void setExtraFoodsMap(HashMap<String, Double> extraFoodsMap) {
        this.extraFoodsMap = extraFoodsMap;
    }

    public int getIncrement() {
        return increment;
    }

    public void setIncrement(int increment) {
        this.increment = increment;
    }
}
