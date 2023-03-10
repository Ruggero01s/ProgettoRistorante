public class Ingredient
{
	private final String name; //nome dell'ingrediente
	private final String unit; //unità di misura dell'ingrediente
	private double quantity; //quantità dell'ingrediente
	
	public Ingredient(String name, String unit, double quantity)
	{
		this.name = name;
		this.unit = unit;
		this.quantity = quantity;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getUnit()
	{
		return unit;
	}
	
	public double getQuantity()
	{
		return quantity;
	}
	
	public void setQuantity(double quantity)
	{
		this.quantity = quantity;
	}
	
	@Override
	public int hashCode()
	{
		int result = 11;
		result = 31 * result + name.hashCode();
		return result;
	}
	
	/**
	 * override dell'equals
	 * @param obj oggetto da controllare
	 * @return true se i nomi sono uguali e sono entrambi dello stesso tipo, false altrimenti
	 */
	public boolean equals(Object obj)
	{
		if (obj == this)
			return true;
		if (!(obj instanceof Ingredient))
			return false;
		
		Ingredient ingredient = (Ingredient) obj;
		return this.name.equals(ingredient.getName());
	}
}
