import java.util.*;

public class Model {

    static final double INCREASE20 = 1.20;

    int capacity, workPersonLoad;
    double workResturantLoad;
    HashMap<String, Double> drinksMap = new HashMap<>();
    HashMap<String, Double> extraFoodsMap = new HashMap<>();
    HashSet<Dish> dishesSet = new HashSet<>();
    HashSet<Recipe> recipesSet= new HashSet<>();

    public int getWorkPersonLoad() {
        return workPersonLoad;
    }

    public void setWorkPersonLoad(int workPersonLoad) {
        this.workPersonLoad = workPersonLoad;
        updateWorkResturantLoad();
    }

    public void updateWorkResturantLoad() {
        this.workResturantLoad = this.capacity * this.workPersonLoad * INCREASE20;
    }

    public double getWorkResturantLoad() {
        return workResturantLoad;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
        updateWorkResturantLoad();
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
