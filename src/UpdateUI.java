import java.util.*;

public class UpdateUI implements Updater
{
    private GUI gui;

    public UpdateUI (GUI gui){
        this.gui = gui;
    }

    /**
     * Metodo che aggiorna la pagina dei config
     */
    public void updateConfig(int capacity, double workRestaurantLoad, int increment, int workPersonLoad, DateOur today ) {
        List<String> configState = new ArrayList<>();
        configState.add("Capacità: " + capacity + "\nIndividualWorkload: " +
                workPersonLoad + "\nRestaurant Worlkload: " + workRestaurantLoad + "\nData odierna: " +
                today.getStringDate() + "\nSurplus %: " + increment);
        configState.add(Integer.toString(capacity));
        configState.add(Integer.toString(workPersonLoad));
        configState.add(today.getStringDate());
        configState.add(Integer.toString(increment));

        gui.updateConfig(configState);
        gui.updateToday(today.getStringDate());
    }

    /**
     * Metodo che serve per aggiornare i drinks nella GUI
     */
    public void updateDrinkList(Map<String, Double> drinksMap) {
        StringBuilder out = new StringBuilder();
        for (Map.Entry<String, Double> drink : drinksMap.entrySet()) //trasformo la map di drinks in stringa
            out.append(drink.getKey()).append(":").append(drink.getValue().toString()).append(":L").append("\n");

        gui.updateDrinks(out.toString().trim());
    }

    /**
     * Metodo che serve per aggiornare gli extra foods nella GUI
     */
    public void updateFoodList(Map<String, Double> foodsMap) {
        StringBuilder out = new StringBuilder();
        for (Map.Entry<String, Double> food : foodsMap.entrySet()) //trasformo la map di extra foods in stringa
            out.append(food.getKey()).append(":").append(food.getValue().toString()).append(":Hg").append("\n");

        gui.updateFoods(out.toString().trim());
    }

    /**
     * Metodo che produce una stringa di date
     * di tutti i giorni in cui c'è almeno una prenotazione
     */
    public void updateBookedDates(Map<DateOur, List<Booking>> bookingMap) {
        Set<DateOur> daysList = bookingMap.keySet(); //insieme di date con almeno una prenotazione
        StringBuilder out = new StringBuilder();
        for (DateOur date : daysList) //scorro le date e creo una stringa
        {
            String s = date.getStringDate();
            out.append(s).append("\n");
        }
        gui.updateBookedDates(out.toString().trim());
    }

    /**
     * Metodo che mostra in GUI
     * tutti i piatti validi oggi
     */
    public void updateMenuCarta(Set<Dish> dishSet, DateOur today) {
        StringBuilder menuCarta = new StringBuilder();
        for (Dish dish : dishSet) //genero la lista di piatti
            if (dish.isValid(today))
                menuCarta.append(dish.getName()).append("\n");
        if (menuCarta.length() == 0)
            menuCarta = new StringBuilder("Non ci sono piatti disponibili per la data ordierna"); //se non ci sono piatti validi
        gui.updateMenuCarta(menuCarta.toString());
    }

    /**
     * chiama il metodo che aggiorna i campi dove vengono mostrate le ricette nelle gui
     * @param recipeSet
     */
    public void updateRecipes(Set<Recipe> recipeSet){
        gui.updateRecipes(convertToStringVector(setConverter(recipeSet)));
    }

    /**
     * chiama il metodo che aggiorna i campi dove vengono mostrati i piatti nelle gui
     * @param dishesSet
     */
    public void updateDishes(Set<Dish> dishesSet){
        gui.updateDishes(convertToStringVector(setConverter(dishesSet)));
    }

    /**
     * chiama il metodo che aggiorna i campi dove vengono mostrate i menù nelle gui
     * @param menusSet
     */
    public void updateMenus(Set<ThematicMenu> menusSet){
        gui.updateMenus(convertToStringAppend(setConverter(menusSet)));
        gui.updateMenuBoxes(makeMenuList(menusSet)); //aggiorna le se selection box pasando un vettore con i nomi dei menù
    }

    /**
     * chiama i metodi di aggiornamento per i campi del magazzino
     * @param grocerySet
     * @param registroBefore
     * @param registroAfter
     */
    public void updateWarehouse(Set<Ingredient> grocerySet, Set<Ingredient> registroBefore, Set<Ingredient> registroAfter){
        gui.updateWare(convertToStringAppend(setConverter(grocerySet)), convertToStringAppend(setConverter(registroBefore)));
        gui.updateWareReturnList(convertToStringAppend(setConverter(registroAfter)));
    }

    /**
     * chiama il metodo che aggiorna i campi dove si mostrano le rimanenze del magazzino
     * @param ingredientSet
     */
    public void updateWareReturnList (Set<Ingredient> ingredientSet)
    {
        gui.updateWareReturnList(convertToStringAppend(setConverter(ingredientSet)));
    }

    /**
     * no comment //todo
     * @param set)
     */
    private Set<ConvertToString> setConverter(Set<?> set)
    {
        return new HashSet<>((Collection<? extends ConvertToString>) set);
    }

    /**
     * @return un array con un nome per ogni menu per indice
     */
    private String[] makeMenuList(Set<ThematicMenu> menusSet) {
        String[] out = new String[menusSet.size()];
        int i = 0;
        for (ThematicMenu m : menusSet) {
            out[i] = m.getName();
            i++;
        }
        return out;
    }

    /**
     * Metodo che converte un oggetto che implementa ConvertToStrings in una stringa
     *
     * @param convertToStrings collection da convertire
     * @return stringa che elenca la collection
     */
    private String convertToStringAppend(Collection<ConvertToString> convertToStrings) {
        StringBuilder out = new StringBuilder();
        for (ConvertToString convertToString : convertToStrings)
            out.append(convertToString.convertToString()).append("\n");
        return out.toString().trim();
    }

    /**
     * Metodo che converte un oggetto che implementa ConvertToStrings in un vettore di stringhe
     *
     * @param convertToStrings collection di oggetti che implementano l'interfaccia
     * @return la collection convertita in vettore di stringhe
     */
    private String[] convertToStringVector(Collection<ConvertToString> convertToStrings) {
        String[] out = new String[convertToStrings.size()];
        int i = 0;

        for (ConvertToString convertToString : convertToStrings)
            out[i++] = convertToString.convertToString();

        return out;
    }
}
