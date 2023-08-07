public interface SaveData
{
	boolean saveConfig(String inputCapacity, String inputWorkload, String inputPercent, String todayString);
	boolean saveFoods(String input);
	boolean saveDrinks(String input);
	boolean saveRecipe(String inputName, String inputIngredients, String inputPortions, String inputWorkload);
	boolean saveDish(String inputName, String inputRecipe, String inputStartDate, String inputEndDate, boolean perm, boolean seasonal);
	boolean saveMenu(String inputName, String inputs, String inputStartDate, String inputEndDate, boolean permanent, boolean seasonal);
	boolean saveBooking(String name, String dateString, String number, String orderString);
	boolean saveUser(String name, String password, String confPassword, boolean manager, boolean employee, boolean storageWorker);
}
