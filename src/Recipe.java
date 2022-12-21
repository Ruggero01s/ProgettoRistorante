import java.util.HashMap;

public class Recipe
{
    String id;
    HashMap<String, Double> ingredients = new HashMap<>();
    int portions;
    double workLoadPortion;

    public Recipe(String id, HashMap<String, Double> ingredients, int portions, double workLoadPortion)
    {
        this.id = id;
        this.ingredients = ingredients;
        this.portions = portions;
        this.workLoadPortion = workLoadPortion;
    }
}