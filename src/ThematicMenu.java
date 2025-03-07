import java.util.Calendar;
import java.util.Set;

public class ThematicMenu implements ConvertToString
{
	private final String name; //nome del menu tematico
	private final DateOur startPeriod; //inizio periodo di validità
	private final DateOur endPeriod; //fine periodo di validità
	private final Set <Dish> dishes; //elenco dei piatti contenuti nel menu
	private final boolean seasonal; //true se stagionale
	private final boolean permanent; //true se permanente
	private double workThematicMenuLoad; //workLoad del menu
	
	public ThematicMenu(String name, String startPeriod, String endPeriod, Set<Dish> dishes, boolean seasonal, boolean permanent) throws  RuntimeException
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
		
		if(!permanent)
			if(this.startPeriod.equals(this.endPeriod)) //se le date sono uguali lancio un errore
				throw new RuntimeException();
		
		if(!seasonal && !permanent)
			if(this.startPeriod.getDate().after(this.endPeriod.getDate())) //se le date sono al contrario lancio un errore
				throw new RuntimeException();

		if(seasonal && permanent)
			throw new RuntimeException("Errore: seasonal && permanent = true");
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
	
	public Set<Dish> getDishes()
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

		return this.name.equalsIgnoreCase(menu.getName());
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
	 * @return true se il menu è valido nella data, false altrimenti
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
	
	/**
	 * Converto i menu in stringhe
	 * @return Stringa di output
	 */
	public String convertToString()
	{
		String s;
		if(permanent)  s = this.name+" - [PERM] - (";
		else if(seasonal) {
			String startDate = startPeriod.getDate().get(Calendar.DAY_OF_MONTH) + "/" + (startPeriod.getDate().get(Calendar.MONTH)+1);
			String endDate = endPeriod.getDate().get(Calendar.DAY_OF_MONTH) +"/"+ (endPeriod.getDate().get(Calendar.MONTH)+1);
			s = this.name+" - ["+startDate+" || "+endDate+"] - [SEAS] - (";
		}else s = this.name+" - ["+this.startPeriod.getStringDate()+" || "+this.endPeriod.getStringDate()+"] - (";
		StringBuilder out = new StringBuilder(s);
		for (Dish dish:this.dishes)
			out.append(dish.getName()).append(", ");
		return out.append(")").toString();
	}
}
