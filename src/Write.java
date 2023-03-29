import java.util.*;

public interface Write
{
    boolean writePeople(Set<User> people);

    boolean writeConfigBase(int capacity, int workPersonLoad, DateOur today, int increment);

    boolean writeDrinks(Map<String, Double> drinks);

    boolean writeExtraFoods(Map<String, Double> extraFoodsMap);

    boolean writeRecipes(Set<Recipe> recipesSet); // non usiamo cose più generali per evitare possibili problemi collaterali nell'integrità dei dati

    boolean writeDishes(Set<Dish> dishesSet);

    boolean writeThematicMenu(Set<ThematicMenu> thematicMenusSet);

    boolean writeBookings(Map<DateOur, List<Booking>> bookingMap);

    boolean writeRegister(Set<Ingredient> registro);
}
