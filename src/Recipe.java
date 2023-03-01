import java.util.HashSet;
import java.util.Set;

public class  Recipe
{
    private final String id;
    private Set<Ingredient> ingredients;
    private final int portions;
    private final double workLoadPortion;

    public Recipe(String id, Set<Ingredient> ingredients, int portions, double workLoadPortion) {
        this.id = id;
        this.ingredients =new HashSet<>(ingredients);
        this.portions = portions;
        this.workLoadPortion = workLoadPortion;
    }

    public String getId() {
        return id;
    }
    
    
    public Set<Ingredient> getIngredients()
    {
        return ingredients;
    }
    
    public int getPortions() {
        return portions;
    }
    
    public double getWorkLoadPortion() {
        return workLoadPortion;
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

    public String getIngredientsList()
    {
        StringBuilder out= new StringBuilder();
        for (Ingredient i: ingredients)
        {
            out.append(i.getName()).append(":").append(i.getQuantity()).append(", ");
        }
        return out.toString();
    }
}