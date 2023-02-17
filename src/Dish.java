import java.text.ParseException;

public class Dish
{
    private String name;
    private DateOur startPeriod, endPeriod;
    private Recipe recipe;
    private boolean seasonal,permanent;

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

    public void setSeasonal(boolean seasonal) {
        this.seasonal = seasonal;
    }

    public boolean isPermanent() {
        return permanent;
    }

    public void setPermanent(boolean permanent) {
        this.permanent = permanent;
    }

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
