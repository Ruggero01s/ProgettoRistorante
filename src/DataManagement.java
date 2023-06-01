import java.util.Set;

public interface DataManagement
{
	
	void clearInfo();
	
	boolean writeManager();
	
	void writeMenuComp(String selectedItem);

	boolean writeRegister();

	DateOur inputToDate(String s);
	
	void seeBookings(DateOur date);

	boolean writeBookings();
	
	void nextDay();
	
	String setToString(Set<Ingredient> registroAfterMeal);
	
	boolean warehouseChanges(String trim);
	
	boolean clearBookings(DateOur date);
	
	void clearBookings();

	boolean checkDate(String date);
}
