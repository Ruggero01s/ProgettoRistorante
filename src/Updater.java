import java.util.List;
import java.util.Map;
import java.util.Set;

public interface Updater
{
    void updateConfig(int capacity, double workRestaurantLoad, int increment, int workPersonLoad, DateOur today );
    void updateDrinkList(Map<String, Double> drinksMap);
    void updateFoodList(Map<String, Double> foodsMap);
    void updateBookedDates(Map<DateOur, List<Booking>> bookingMap);
    void updateMenuCarta(Set<Dish> dishSet, DateOur today);
    void updateRecipes(Set<Recipe> recipeSet);
    void updateDishes(Set<Dish> dishesSet);
    void updateMenus(Set<ThematicMenu> thematicMenuSetC);
    void updateWarehouse(Set<Ingredient> grocerySet, Set<Ingredient> registroBeforeMeal, Set<Ingredient> registroAfterMeal);
    void updateWareReturnList(Set<Ingredient> ingredientSet);
}
