import java.util.Set;

public interface DataManagement
{
	
	void clearInfo();
	
	void writeManager();
	
	void writeMenuComp(String selectedItem);
	
	void writeRegister();
	
	DateOur inputToDate(String s);
	
	void seeBookings(DateOur date);
	
	void writeBookings();
	
	void nextDay();
	
	String setToString(Set<Ingredient> registroAfterMeal);
	
	boolean warehouseChanges(String trim);
	
	boolean clearBookings(DateOur date);
	
	void clearBookings();
	boolean checkDate(String date);
}
