import java.text.ParseException;
import java.util.HashMap;

public class Dish    //todo override equals
{
    String name;
    Date startPeriod, endPeriod;
    Recipe recipe;

    public Dish(String name, Recipe recipe/*, boolean permanent*/, String startPeriod, String endPeriod) throws ParseException {
        this.name = name;

        String[] periodDateStart = startPeriod.split("/");
        this.startPeriod = new Date(periodDateStart[0], periodDateStart[1]);

        String[] periodDateEnd = endPeriod.split("/");
        this.endPeriod = new Date(periodDateEnd[0], periodDateEnd[1]);
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

    public Date getStartPeriod() {
        return startPeriod;
    }

    public void setStartPeriod(Date startPeriod) {
        this.startPeriod = startPeriod;
    }

    public Date getEndPeriod() {
        return endPeriod;
    }

    public void setEndPeriod(Date endPeriod) {
        this.endPeriod = endPeriod;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }
}
