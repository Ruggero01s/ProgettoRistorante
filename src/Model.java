import javax.management.InstanceNotFoundException;
import java.util.*;

public class Model implements RestaurantRepository
{
	public static final double INCREASE20 = 1.20;
	private int capacity, workPersonLoad, increment;
	private double workloadRestaurant;
	private DateOur today;
	private Map<String, Double> drinksMap = new HashMap<>();
	private Map<String, Double> extraFoodsMap = new HashMap<>();
	private Set<Dish> dishesSet = new HashSet<>();
	private Set<Recipe> recipesSet = new HashSet<>();
	private Set<ThematicMenu> thematicMenusSet = new HashSet<>();
	private Map<DateOur, List<Booking>> bookingMap = new HashMap<>();
	private Set<Ingredient> registroAfterMeal = new HashSet<>();
	private Set<Ingredient> registroBeforeMeal = new HashSet<>();
	private Set<User> users = new HashSet<>();
	private User theUser;

	public Set<ConvertToString> getDishesSetConverted () {
		return new HashSet<>(this.dishesSet);
	}

	public Set<ConvertToString> getRecipeSetConverted () {
		return new HashSet<>(this.recipesSet);
	}

	public Set<ConvertToString> getThematicMenuSetConverted () {
		return new HashSet<>(this.thematicMenusSet);
	}

	public User getCurrentUser() {
		return theUser;
	}

	public void setCurrentUser(User theUser) {
		this.theUser = theUser;
	}

	public Set<User> getUsers () {
		return users;
	}

	public void setUsers (Set<User> users) {
		this.users = users;
	}

	public Set<Ingredient> getRegistroAfterMeal () {
		return registroAfterMeal;
	}

	public void setRegistroAfterMeal (Set<Ingredient> registroAfterMeal) {
		this.registroAfterMeal = registroAfterMeal;
	}

	public Set<Ingredient> getRegistroBeforeMeal () {
		return registroBeforeMeal;
	}

	public void setRegistroBeforeMeal (Set<Ingredient> registroBeforeMeal) {
		this.registroBeforeMeal = registroBeforeMeal;
	}

	public int getWorkPersonLoad () {
		return workPersonLoad;
	}

	public void setWorkPersonLoad (int workPersonLoad) {
		this.workPersonLoad = workPersonLoad;
		updateWorkRestaurantLoad();
	}

	public DateOur getToday () {
		return today;
	}

	public void setToday (DateOur today) {
		this.today = today;
	}

	public Map<DateOur, List<Booking>> getBookingMap () {
		return bookingMap;
	}

	public void setBookingMap (Map<DateOur, List<Booking>> bookingMap) {
		this.bookingMap = bookingMap;
	}

	public void updateWorkRestaurantLoad() {
		this.workloadRestaurant = this.capacity * this.workPersonLoad * INCREASE20;
	}

	public double getWorkRestaurantLoad() {
		return workloadRestaurant;
	}

	public int getCapacity () {
		return capacity;
	}

	public void setCapacity (int capacity) {
		this.capacity = capacity;
		updateWorkRestaurantLoad();
	}

	public Set<Dish> getDishesSet () {
		return dishesSet;
	}

	public void setDishesSet (Set<Dish> dishesSet) {
		this.dishesSet = dishesSet;
	}

	public Set<Recipe> getRecipesSet () {
		return recipesSet;
	}

	public void setRecipesSet (Set<Recipe> recipesSet) {
		this.recipesSet = recipesSet;
	}

	public Set<ThematicMenu> getThematicMenusSet () {
		return thematicMenusSet;
	}

	public void setThematicMenusSet (Set<ThematicMenu> thematicMenusSet) {
		this.thematicMenusSet = thematicMenusSet;
	}

	public Map<String, Double> getDrinksMap () {
		return drinksMap;
	}

	public void setDrinksMap (Map<String, Double> drinksMap) {
		this.drinksMap = drinksMap;
	}

	public Map<String, Double> getExtraFoodsMap () {
		return extraFoodsMap;
	}

	public void setExtraFoodsMap (Map<String, Double> extraFoodsMap) {
		this.extraFoodsMap = extraFoodsMap;
	}

	public int getIncrement () {
		return increment;
	}

	public void setIncrement (int increment) {
		this.increment = increment;
	}

	/**
	 * Metodo che presa una stringa cerca il piatto
	 * @param name stringa da cercare
	 * @return il piatto
	 * @throws InstanceNotFoundException se non trova l'oggetto associato
	 */
	public Dish findDish(String name) throws InstanceNotFoundException{
		for (Dish d: dishesSet) {
			if (d.getName().equalsIgnoreCase(name))
				return d;
		}
		throw new InstanceNotFoundException();
	}
	/**
	 * Metodo che presa una stringa cerca la ricetta
	 * @param name stringa da cercare
	 * @return la ricetta
	 * @throws InstanceNotFoundException se non trova l'oggetto associato
	 */
	public Recipe findRecipe(String name) throws InstanceNotFoundException{
		for (Recipe r: recipesSet) {
			if (r.getId().equalsIgnoreCase(name))
				return r;
		}
		throw new InstanceNotFoundException();
	}
	/**
	 * Metodo che presa una stringa cerca l'utente
	 * @param name stringa da cercare
	 * @return l'utente
	 * @throws InstanceNotFoundException se non trova l'oggetto associato
	 */
	public User findUser(String name) throws InstanceNotFoundException{
		for (User u: users) {
			if (u.getName().equalsIgnoreCase(name))
				return u;
		}
		throw new InstanceNotFoundException();
	}
	/**
	 * Metodo che presa una stringa cerca il menu
	 * @param name stringa da cercare
	 * @return il menu
	 * @throws InstanceNotFoundException se non trova l'oggetto associato
	 */
	public ThematicMenu findMenu(String name) throws InstanceNotFoundException {
		for (ThematicMenu t: thematicMenusSet) {
			if (t.getName().equalsIgnoreCase(name))
				return t;
		}
		throw new InstanceNotFoundException();
	}

	public boolean isDuplicate(String name) {
		for (Recipe r: recipesSet) {
			if (r.getId().equalsIgnoreCase(name))
				return true;
		}
		for (Dish d: dishesSet) {
			if (d.getName().equalsIgnoreCase(name))
				return true;
		}
		for (ThematicMenu t: thematicMenusSet) {
			if (t.getName().equalsIgnoreCase(name))
				return true;
		}
		return false;
	}

	/**
	 * Aggiunge un piatto al set
	 * @param dish piatto da aggiungere
	 * @return true se viene aggiunto, false altrimenti
	 */
	public boolean add(Dish dish){
		return dishesSet.add(dish);
	}
	/**
	 * Aggiunge una ricetta al set
	 * @param recipe ricetta da aggiungere
	 * @return true se viene aggiunto, false altrimenti
	 */
	public boolean add(Recipe recipe)
	{
		return recipesSet.add(recipe);
	}
	/**
	 * Aggiunge un menu al set
	 * @param menu menu da aggiungere
	 * @return true se viene aggiunto, false altrimenti
	 */
	public boolean add(ThematicMenu menu){
		return thematicMenusSet.add(menu);
	}
	/**
	 * Aggiunge un user al set
	 * @param user user da aggiungere
	 * @return true se viene aggiunto, false altrimenti
	 */
	public boolean add(User user){
		return users.add(user);
	}

	public void addFoods(Map<String, Double> foods)
	{
		extraFoodsMap.putAll(foods);
	}

	public void addDrinks(Map<String, Double> drinks)
	{
		drinksMap.putAll(drinks);
	}

	public void addBookings(DateOur date, List<Booking> bookings)
	{
		bookingMap.put(date,bookings);
	}

	/**
	 * Prese una stringa cerca l'oggetto con lo stesso nome, se lo trova lo elimina dal corrispettivo set
	 * @param name stringa da cercare
	 * @return true se l'eliminazione va a buon fine, false altrimenti
	 * @throws InstanceNotFoundException in caso non trovi l'oggetto associato
	 */
	public boolean remove(String name) throws InstanceNotFoundException { //questo metodo esiste in caso si implementi l'opzione di eliminare singoli oggetti e non solo resettare il programma
		try {
			Recipe recipe = findRecipe(name);
			return recipesSet.remove(recipe);
		} catch (InstanceNotFoundException e1) {
			try {
				Dish dish = findDish(name);
				return dishesSet.remove(dish);
			} catch (InstanceNotFoundException e2) {
				ThematicMenu menu = findMenu(name);
				return thematicMenusSet.remove(menu);
			}
		}
	}

	/**
	 * Metodo che svuota la memoria
	 */
	public void clear(){
		drinksMap.clear();
		extraFoodsMap.clear();
		capacity=0;
		workPersonLoad=0;
		increment=5;
		today=new DateOur("11", "11", "1444");
		recipesSet.clear();
		dishesSet.clear();
		thematicMenusSet.clear();
		bookingMap.clear();
		registroBeforeMeal.clear();
		users.clear();
	}

	/**
	 * Metodo per aggiornare la data
	 */
	public void nextDay()
	{
		today.getDate().add(Calendar.DATE, 1); //cambio la data
	}

	/**
	 * Metodo che elimina le prenotazioni passate
	 */
	public void deleteOldBookings(){
		HashMap<DateOur, List<Booking>> temp = new HashMap<>(bookingMap); //per evitare concurrent access
		for (Map.Entry<DateOur, List<Booking>> entry : bookingMap.entrySet()) {
			if (entry.getKey().getDate().before(today.getDate())) //rimuovo le prenotazioni che hanno una data precedente ad oggi
				temp.remove(entry.getKey());
		}
		bookingMap = temp;
	}

	/**
	 * Rimuove tutte le prenotazioni di uno specifico giorno
	 * @param date data da svuotare
	 * @return true se l'eliminazione avviene correttamente, false altrimenti
	 */
	public boolean clearDayBookings (DateOur date)
	{
		if (!bookingMap.containsKey(date)) return true;
		if(!date.equals(today))
		{
			Object oldVal = bookingMap.remove(date);
			return oldVal != null;
		} else return false;
	}

	/**
	 * rimuove tutte le prenotazioni tranne quella di oggi
	 * */
	public void clearBookings(){
		bookingMap.keySet().removeIf(k -> !(k.equals(today)));
	}

}
