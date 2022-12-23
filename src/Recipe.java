import java.util.HashMap;

public class Recipe //todo override equals
{
    private String id;
    private HashMap<String, Double> ingredients = new HashMap<>();
    private int portions;
    private double workLoadPortion;

    public Recipe(String id, HashMap<String, Double> ingredients, int portions, double workLoadPortion) {
        this.id = id;
        this.ingredients = ingredients;
        this.portions = portions;
        this.workLoadPortion = workLoadPortion;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public HashMap<String, Double> getIngredients() {
        return ingredients;
    }

    public void setIngredients(HashMap<String, Double> ingredients) {
        this.ingredients = ingredients;
    }

    public int getPortions() {
        return portions;
    }

    public void setPortions(int portions) {
        this.portions = portions;
    }

    public double getWorkLoadPortion() {
        return workLoadPortion;
    }

    public void setWorkLoadPortion(double workLoadPortion) {
        this.workLoadPortion = workLoadPortion;
    }
}