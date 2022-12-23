import java.util.ArrayList;

public class ThematicMenu
{
	private String name;
	private Date startPeriod,endPeriod;
	private ArrayList <Dish> dishes = new ArrayList<>();
	private double workThematicMenuLoad;
	
	public ThematicMenu(String name, Date startPeriod, Date endPeriod, ArrayList<Dish> dishes)
	{
		this.name = name;
		this.startPeriod = startPeriod;
		this.endPeriod = endPeriod;
		this.dishes = dishes;
		calcWorkThematicMenuLoad(); //calcola in automatico il workThematicMenuLoad
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public Date getStartPeriod()
	{
		return startPeriod;
	}
	
	public void setStartPeriod(Date startPeriod)
	{
		this.startPeriod = startPeriod;
	}
	
	public Date getEndPeriod()
	{
		return endPeriod;
	}
	
	public void setEndPeriod(Date endPeriod)
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
	
	
	public void calcWorkThematicMenuLoad ()
	{
		this.workThematicMenuLoad=0;
		for (Dish dish: dishes)
			this.workThematicMenuLoad+=dish.getRecipe().getWorkLoadPortion();
	}
	
}
