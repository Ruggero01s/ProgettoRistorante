public interface InputParser {

    boolean parseConfig(String inputCapacity, String inputWorkload, String inputPercent, String todayString);
    boolean parseFoods(String input);
    boolean parseDrinks(String input);
    boolean parseRecipe(String inputName, String inputIngredients, String inputPortions, String inputWorkload);
    boolean parseDish(String inputName, String inputRecipe, String inputStartDate, String inputEndDate, boolean perm, boolean seasonal);
    boolean parseMenu(String inputName, String inputs, String inputStartDate, String inputEndDate, boolean permanent, boolean seasonal);
    boolean parseBooking(String name, String dateString, String number, String orderString);
    boolean parseUser(String name, String password, String confPassword, boolean manager, boolean employee, boolean storageWorker);
}
