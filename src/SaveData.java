public interface SaveData
{
	void saveConfig(String inputCapacity, String inputWorkload, String inputPercent, String todayString);
	void saveFoods(String input);
	void saveDrinks(String input);
	void saveRecipe(String inputName, String inputIngredients, String inputPortions, String inputWorkload);
	void saveDish(String inputName, String inputRecipe, String inputStartDate, String inputEndDate, boolean perm, boolean seasonal);
	void saveMenu(String inputName, String inputs, String inputStartDate, String inputEndDate, boolean permanent, boolean seasonal);
	boolean saveBooking(String name, String dateString, int number, String orderString);
}
