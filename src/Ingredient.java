import java.io.DataInput;

public class Ingredient
{
	private String name,unit;
	private double quantity;
	
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
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getUnit()
	{
		return unit;
	}
	
	public void setUnit(String unit)
	{
		this.unit = unit;
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
	public int hashCode() {
		int result = 11;
		result = 31 * result + name.hashCode();
		return result;
	}
	
	public boolean equals(Object i) {
		if (i == this) return true;
		if (!(i instanceof Ingredient)) {
			return false;
		}
		Ingredient ingredient = (Ingredient) i;
		
		return this.name.equals(ingredient.getName());
	}
}
