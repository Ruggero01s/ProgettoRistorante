import java.text.ParseException;

public class Dish    //todo override equals
{
    private String name;
    private DateOur startPeriod, endPeriod;
    private Recipe recipe;
    private boolean seasonal;

    public Dish(String name, Recipe recipe/*, boolean permanent*/, String startPeriod, String endPeriod) throws ParseException {
        this.name = name;

        String[] periodDateStart = startPeriod.split("/");
        this.startPeriod = new DateOur(periodDateStart[0], periodDateStart[1]);

        String[] periodDateEnd = endPeriod.split("/");
        this.endPeriod = new DateOur(periodDateEnd[0], periodDateEnd[1]);
        this.recipe = recipe;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

   /* public boolean isPermanent() {
        return permanent;
    }

    public void setPermanent(boolean permanent) {
        this.permanent = permanent;
    }*/

    public DateOur getStartPeriod() {
        return startPeriod;
    }

    public void setStartPeriod(DateOur startPeriod) {
        this.startPeriod = startPeriod;
    }

    public DateOur getEndPeriod() {
        return endPeriod;
    }

    public void setEndPeriod(DateOur endPeriod) {
        this.endPeriod = endPeriod;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
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
        return date.between(this.startPeriod,this.endPeriod);
    }

}
