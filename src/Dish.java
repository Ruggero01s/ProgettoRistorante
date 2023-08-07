import java.util.Calendar;

public class Dish implements ConvertToString
{
    private final String name; //nome del piatto
    private final DateOur startPeriod; //inizio periodo di validità del piatto
    private final DateOur endPeriod; //fine periodo di validità del piatto
    private final Recipe recipe; //ricetta associata al piatto
    private final boolean seasonal;// se un piatto è stagionale o no
    private final boolean permanent; // se un piatto è permanente o no

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
