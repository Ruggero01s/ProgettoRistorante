import java.util.ArrayList;

public class ThematicMenu
{
	private final String name; //nome del menu tematico
	private final DateOur startPeriod; //inizio periodo di validità
	private final DateOur endPeriod; //fine periodo di validità
	private  ArrayList <Dish> dishes; //elenco dei piatti contenuti nel menu
	private final boolean seasonal; //true se stagionale
	private final boolean permanent; //true se permanente
	private double workThematicMenuLoad; //workLoad del menu
	
	public ThematicMenu(String name, String startPeriod, String endPeriod, ArrayList<Dish> dishes, boolean seasonal, boolean permanent)
	{
		this.name = name;
		String[] startPezzi = startPeriod.split("/");
		String[] endPezzi = endPeriod.split("/");
		this.startPeriod = new DateOur(startPezzi[0],startPezzi[1],startPezzi[2]);
		this.endPeriod = new DateOur(endPezzi[0],endPezzi[1],endPezzi[2]);
		this.dishes = dishes;
		calcWorkThematicMenuLoad(); //calcola in automatico il workThematicMenuLoad
		this.seasonal=seasonal;
		this.permanent=permanent;
	}
	
	public String getName()
	{
		return name;
	}
	
	public DateOur getStartPeriod()
	{
		return startPeriod;
	}
	
	public DateOur getEndPeriod()
	{
		return endPeriod;
	}
	
	public ArrayList<Dish> getDishes()
	{
		return dishes;
	}
	
	public double getWorkThematicMenuLoad()
	{
		return workThematicMenuLoad;
	}
	
	public boolean isSeasonal() {
		return seasonal;
	}
	
	public boolean isPermanent() {
		return permanent;
	}
	
	/**
	 * Calcola in automatico il workload del menu
	 */
	private void calcWorkThematicMenuLoad ()
	{
		this.workThematicMenuLoad=0;
		for (Dish dish: dishes)
			this.workThematicMenuLoad+=dish.getRecipe().getWorkLoadPortion();
	}
	
	/**
	 * override dell'equals
	 * @param obj oggetto da confrontare
	 * @return true se i nomi sono uguali e sono entrambi dello stesso tipo, false altrimenti
	 */
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (!(obj instanceof ThematicMenu))
			return false;

		ThematicMenu menu = (ThematicMenu) obj;

		return this.name.equals(menu.getName());
	}

	@Override
	public int hashCode() {
		int result = 17;
		result = 31 * result + name.hashCode();
		return result;
	}
	/**
	 * Controllo la validità di un menu
	 * @param date data da controllare
	 * @return true se il menu è valido nella date, false altrimenti
	 */
	public boolean isValid(DateOur date)
	{
		if(this.permanent) //i piatti permanenti sono sempre validi
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
