import java.util.*;

public interface Write
{
    void writePeople(Set<User> people);
    
    void writeConfigBase(int capacity, int workPersonLoad, DateOur today, int increment);
    
    void writeDrinks(Map<String, Double> drinks);
    
    void writeExtraFoods(Map<String, Double> extraFoodsMap);
    
    void writeRecipes(Set<Recipe> recipesSet); // non usiamo cose più generali per evitare possibili problemi collaterali nell'integrità dei dati

    void writeDishes(Set<Dish> dishesSet);
    
    void writeThematicMenu(Set<ThematicMenu> thematicMenusSet);
    
    void writeBookings(Map<DateOur, List<Booking>> bookingMap);
    
    void writeRegister(Set<Ingredient> registro);
}
