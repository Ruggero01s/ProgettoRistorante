import java.util.Calendar;

public class Dish extends ConsumerItem
{
    private final Recipe recipe; //ricetta associata al piatto

    public Dish(String name, Recipe recipe, String startPeriod, String endPeriod, boolean seasonal, boolean permanent) throws RuntimeException
    {
        super(name, startPeriod, endPeriod, seasonal, permanent);
        this.recipe = recipe;
    }

    public Recipe getRecipe() {
        return recipe;
    }
    
    /**
     * Converto i piatti in stringhe
     * @return Stringa di output
     */
    public String convertToString()
    {
        if(this.isPermanent()) return this.getName() +" - [PERM] - ("+this.recipe.getId()+")";
        else if(this.isSeasonal()) {
            String startDate = this.getStartPeriod().getDate().get(Calendar.DAY_OF_MONTH) +"/"+ (this.getStartPeriod().getDate().get(Calendar.MONTH)+1);
            String endDate = this.getEndPeriod().getDate().get(Calendar.DAY_OF_MONTH) +"/"+ (this.getEndPeriod().getDate().get(Calendar.MONTH)+1);
            return this.getName() +" - ["+startDate+" || "+endDate+"] - [SEAS] - ("+this.recipe.getId()+")";
        }else return this.getName() +" - ["+this.getStartPeriod().getStringDate()+" || "+this.getEndPeriod().getStringDate()+"] - ("+this.recipe.getId()+")";
    }
}
