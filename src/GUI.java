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
		void nextDay(List <String> data,String today);
		void updateWareReturnList (String out);
		void updateWare(String groceryList, String register);
		void login();
		void updateBooking (String name, String number, String work, String capacity, String workload);
		void selectedMenu(String toString);
}
