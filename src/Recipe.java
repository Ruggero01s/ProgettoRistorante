import java.util.*;

public class  Recipe
{
    private final String id; //nome della ricetta
    private Set<Ingredient> ingredients; //elenco d'ingredienti
    private final int portions; //numero di porzioni
    private final double workLoadPortion; //workload della ricetta per porzione

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
    
    /**
     * override dell'equals
     * @param obj oggetto da controllare
     * @return true se i nomi sono uguali e sono entrambi dello stesso tipo, false altrimenti
     */
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (!(obj instanceof Recipe))
            return false;

        Recipe recipe = (Recipe) obj;
        return this.id.equals((recipe.id));
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + id.hashCode();
        return result;
    }
    
    /**
     * toString della lista degli ingredienti
     * @return string contenente tutti gli ingredienti
     */
    public String getIngredientsList()
    {
        StringBuilder out= new StringBuilder();
        for (Ingredient i: ingredients)
        {
            out.append(i.getName()).append(":").append(i.getQuantity()).append(", ");
        }
        return out.toString();
    }
    
    public String convertToSting()
    {
        StringBuilder out= new StringBuilder(this.id + " - [");
    
        for (Ingredient ingredient : ingredients) //todo iterare in ordine il set
        {
            out.append(ingredient.convertToString()).append(", ");
        }
        out.append("] - " + "p.").append(this.portions).append(" - ").append("w.").append(this.workLoadPortion);
      
        return out.toString();
    }
}