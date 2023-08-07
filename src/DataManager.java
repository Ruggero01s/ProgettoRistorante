public interface DataManager {
	void clearInfo();
	void printMenuComp(String selectedItem);
	void seeBookings(DateOur date);
	boolean warehouseChanges(String trim);
	boolean clearBookings(DateOur date);
	void clearBookings();
	void nextDay();
}
