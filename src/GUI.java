import java.util.List;

public interface GUI
{
		void init(String today);
		void updateConfig(List<String> configState);
		void updateDrinks(String drinks);
		void updateFoods(String foods);
		void updateRecipes(String[] recipes);
		void updateDishes(String[] dishes);
		void updateMenuCarta(String menuCarta);
		void updateMenus(String menus);
		void updateMenuBoxes(String[] menus);
		void nextDay(String today);
		void updateWareReturnList (String afterMeal);
		void updateWare(String groceryList, String beforeMeal);
		void login();
		void updateBooking (String name, String number, String work, String capacity, String workload);
		void updateBookedDates(String dates);
		void selectedMenu(String toString);
		void logout();
		void confirmSave();
		void confirmClear();
}
