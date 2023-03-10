public class Dish
{
    private final String name; //nome del piatto
    private final DateOur startPeriod; //inizio periodo di validità del piatto
    private final DateOur endPeriod; //fine periodo di validità del piatto
    private final Recipe recipe; //ricetta associata al piatto
    private final boolean seasonal;// se un piatto è stagionale o no
    private final boolean permanent; // se un piatto è permanente o no

    public Dish(String name, Recipe recipe, String startPeriod, String endPeriod, boolean seasonal, boolean permanent)
    {
        this.name = name;

        String[] periodDateStart = startPeriod.split("/");
        this.startPeriod = new DateOur(periodDateStart[0], periodDateStart[1], periodDateStart[2]);

        String[] periodDateEnd = endPeriod.split("/");
        this.endPeriod = new DateOur(periodDateEnd[0], periodDateEnd[1],periodDateEnd[2]);
        this.recipe = recipe;
        this.seasonal = seasonal;
        this.permanent = permanent;
    }

    public String getName() {
        return name;
    }
    
    public boolean isSeasonal() {
        return seasonal;
    }
    
    public boolean isPermanent() {
        return permanent;
    }
    
    public DateOur getStartPeriod() {
        return startPeriod;
    }
    
    public DateOur getEndPeriod() {
        return endPeriod;
    }
    
    public Recipe getRecipe() {
        return recipe;
    }
    
    /**
     * override dell'equals
     * @param obj oggetto da controllare
     * @return true se i nomi sono uguali e sono entrambi dello stesso tipo, false altrimenti
     */
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (!(obj instanceof Dish))
            return false;

        Dish dish = (Dish) obj;

        return this.name.equals(dish.getName());
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + name.hashCode();
        return result;
    }
    
    /**
     * Controllo la validità di un piatto
     * @param date data da controllare
     * @return true se il piatto è valido nella date, false altrimenti
     */
    public boolean isValid(DateOur date)
    {
        if(this.permanent) //se è permanente è sempre valido
            return true;
        else
        {
            if (this.seasonal)
                return date.bet(this.startPeriod,this.endPeriod);
            else
                return date.between(this.startPeriod, this.endPeriod);
        }
    }
}
