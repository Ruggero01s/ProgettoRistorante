import java.text.ParseException;
import java.util.ArrayList;

public class ThematicMenu
{
	private String name;
	private DateOur startPeriod,endPeriod;
	private ArrayList <Dish> dishes = new ArrayList<>();
	private boolean seasonal,permanent;
	private double workThematicMenuLoad;
	
	public ThematicMenu(String name, String startPeriod, String endPeriod, ArrayList<Dish> dishes, boolean seasonal, boolean permanent) throws ParseException {
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
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public DateOur getStartPeriod()
	{
		return startPeriod;
	}
	
	public void setStartPeriod(DateOur startPeriod)
	{
		this.startPeriod = startPeriod;
	}
	
	public DateOur getEndPeriod()
	{
		return endPeriod;
	}
	
	public void setEndPeriod(DateOur endPeriod)
	{
		this.endPeriod = endPeriod;
	}
	
	public ArrayList<Dish> getDishes()
	{
		return dishes;
	}
	
	public void setDishes(ArrayList<Dish> dishes)
	{
		this.dishes = dishes;
	}
	
	public double getWorkThematicMenuLoad()
	{
		return workThematicMenuLoad;
	}
	
	public void setWorkThematicMenuLoad(double workThematicMenuLoad)
	{
		this.workThematicMenuLoad = workThematicMenuLoad;
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

	public void calcWorkThematicMenuLoad ()
	{
		this.workThematicMenuLoad=0;
		for (Dish dish: dishes)
			this.workThematicMenuLoad+=dish.getRecipe().getWorkLoadPortion();
	}

	public boolean equals(Object o) {
		if (o == this) return true;
		if (!(o instanceof ThematicMenu))
		{
			return false;
		}

		ThematicMenu menu = (ThematicMenu) o;

		return this.name.equals(menu.getName());
	}

	@Override
	public int hashCode() {
		int result = 17;
		result = 31 * result + name.hashCode();
		return result;
	}

	public boolean isValid(DateOur date)
	{
		if(this.permanent)
			return true;
		else
		{
			if (this.seasonal = false)
				return date.between(this.startPeriod, this.endPeriod);
			else
			{
				return date.bet(this.startPeriod,this.endPeriod);
			}
		}
	}
}
