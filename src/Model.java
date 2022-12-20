import java.util.HashMap;

public class Model
{

    static final double INCREASE20 = 1.20;

    int capacity,workPersonLoad;
     double workResturantLoad;
     HashMap<String, Double> drinks = new HashMap<>();
     HashMap<String, Double> extraFoods = new HashMap<>();

    public int getWorkPersonLoad() {
        return workPersonLoad;
    }

    public void setWorkPersonLoad(int workPersonLoad) {
        this.workPersonLoad = workPersonLoad;
        updateWorkResturantLoad();
    }

    public void updateWorkResturantLoad(){
        this.workResturantLoad=this.capacity*this.workPersonLoad*INCREASE20;
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

    public HashMap<String, Double> getDrinks() {
        return drinks;
    }

    public void setDrinks(HashMap<String, Double> drinks) {
        this.drinks = drinks;
    }

    public HashMap<String, Double> getExtraFoods() {
        return extraFoods;
    }

    public void setExtraFoods(HashMap<String, Double> extraFoods) {
        this.extraFoods = extraFoods;
    }
}
