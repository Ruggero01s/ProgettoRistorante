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
	
	public Set<Dish> getDishes()
	{
		return dishes;
	}
	
	public double getWorkThematicMenuLoad()
	{
		return workThematicMenuLoad;
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
	 * Converto i menu in stringhe
	 * @return Stringa di output
	 */
	public String convertToString()
	{
		String s;
		if(this.isPermanent())  s = this.getName() +" - [PERM] - (";
		else if(this.isSeasonal()) {
			String startDate = this.getStartPeriod().getDate().get(Calendar.DAY_OF_MONTH) + "/" + (this.getStartPeriod().getDate().get(Calendar.MONTH)+1);
			String endDate = this.getEndPeriod().getDate().get(Calendar.DAY_OF_MONTH) +"/"+ (this.getEndPeriod().getDate().get(Calendar.MONTH)+1);
			s = this.getName() +" - ["+startDate+" || "+endDate+"] - [SEAS] - (";
		}else s = this.getName() +" - ["+ this.getStartPeriod().getStringDate()+" || "+ this.getEndPeriod().getStringDate()+"] - (";
		StringBuilder out = new StringBuilder(s);
		for (Dish dish:this.dishes)
			out.append(dish.getName()).append(", ");
		return out.append(")").toString();
	}
}
