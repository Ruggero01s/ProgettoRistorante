import java.util.HashMap;
import java.util.Map;

public class  Recipe
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

    public boolean equals(Object r) {
        if (r == this) return true;
        if (!(r instanceof Recipe)) {
            return false;
        }

        Recipe recipe = (Recipe) r;

        return this.id.equals((recipe.id));
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + id.hashCode();
        return result;
    }

    public String getIngredientsList(){
        StringBuilder out= new StringBuilder();
        for (Map.Entry<String, Double> entry : ingredients.entrySet())
        {
            out.append(entry.getKey()).append(":").append(entry.getValue()).append(", ");
        }
        return out.toString();
    }
}