import java.util.*;

public class Model {

    static final double INCREASE20 = 1.20;

    private int capacity=0, workPersonLoad=0;
    private double workloadRestaurant;
    private HashMap<String, Double> drinksMap = new HashMap<>();
    private HashMap<String, Double> extraFoodsMap = new HashMap<>();
    private Set<Dish> dishesSet = new HashSet<>();
    private Set<Recipe> recipesSet= new HashSet<>();
    private Set<ThematicMenu> thematicMenusSet= new HashSet<>();


    public int getWorkPersonLoad() {
        return workPersonLoad;
    }

    public void setWorkPersonLoad(int workPersonLoad) {
        this.workPersonLoad = workPersonLoad;
        updateWorkResturantLoad();
    }

    public void setWorkloadRestaurant(double workloadRestaurant) {
        this.workloadRestaurant = workloadRestaurant;
    }

    public HashMap<Date, ArrayList<Booking>> getBookingMap() {
        return bookingMap;
    }

    public void setBookingMap(HashMap<Date, ArrayList<Booking>> bookingMap) {
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
}
