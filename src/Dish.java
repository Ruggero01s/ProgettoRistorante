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
        this.name = name;

        String[] periodDateStart = startPeriod.split("/");
        this.startPeriod = new DateOur(periodDateStart[0], periodDateStart[1], periodDateStart[2]);

        String[] periodDateEnd = endPeriod.split("/");
        this.endPeriod = new DateOur(periodDateEnd[0], periodDateEnd[1],periodDateEnd[2]);
        this.recipe = recipe;
        this.seasonal = seasonal;
        this.permanent = permanent;
    
        if(!permanent)
            if(this.startPeriod.equals(this.endPeriod)) //se le date sono uguali lancio un errore
                throw new RuntimeException();
        
        if(!seasonal && !permanent)
            if(this.startPeriod.getDate().after(this.endPeriod.getDate())) //se le date sono al contrario lancio un errore
                throw new RuntimeException();

        if(seasonal && permanent)
            throw new RuntimeException();
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

        return this.name.equalsIgnoreCase(dish.getName());
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
    
    /**
     * Converto i piatti in stringhe
     * @return Stringa di output
     */
    public String convertToString()
    {
        if(permanent) return this.name+" - [PERM] - ("+this.recipe.getId()+")";
        else if(seasonal) {
            String startDate = startPeriod.getDate().get(Calendar.DAY_OF_MONTH) +"/"+ (startPeriod.getDate().get(Calendar.MONTH)+1);
            String endDate = endPeriod.getDate().get(Calendar.DAY_OF_MONTH) +"/"+ (endPeriod.getDate().get(Calendar.MONTH)+1);
            return this.name+" - ["+startDate+" || "+endDate+"] - [SEAS] - ("+this.recipe.getId()+")";
        }else return this.name+" - ["+this.startPeriod.getStringDate()+" || "+this.endPeriod.getStringDate()+"] - ("+this.recipe.getId()+")";
    }
}
