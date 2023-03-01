import java.text.ParseException;

public class Dish
{
    private String name;
    private final DateOur startPeriod;
    private final DateOur endPeriod;
    private Recipe recipe;
    private final boolean seasonal;
    private final boolean permanent;

    public Dish(String name, Recipe recipe, String startPeriod, String endPeriod, boolean seasonal, boolean permanent) throws ParseException {
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

    public void setName(String name) {
        this.name = name;
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
    
    public boolean equals(Object d) {
        if (d == this) return true;
        if (!(d instanceof Dish)) {
            return false;
        }

        Dish dish = (Dish) d;

        return this.name.equals(dish.getName());
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + name.hashCode();
        return result;
    }

    public boolean isValid(DateOur date)
    {
        if(this.permanent)
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
