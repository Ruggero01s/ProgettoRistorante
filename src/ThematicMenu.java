import java.util.Calendar;
import java.util.Set;

public class ThematicMenu extends ConsumerItem
{
	private final Set <Dish> dishes; //elenco dei piatti contenuti nel menu
	private double workThematicMenuLoad; //workLoad del menu
	
	public ThematicMenu(String name, String startPeriod, String endPeriod, Set<Dish> dishes, boolean seasonal, boolean permanent) throws  RuntimeException
	{
		super(name,startPeriod,endPeriod,seasonal,permanent);
		this.dishes = dishes;
		calcWorkThematicMenuLoad(); //calcola in automatico il workThematicMenuLoad
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
