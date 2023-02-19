import javax.swing.*;
import java.text.ParseException;
import java.util.*;

public class Controller
{
	Model model = new Model();
	SimpleUI sui;
	Reader reader = new Reader(this);
	
	public void init()
	{
		sui = new SimpleUI(this);
		loadModel();
		sui.init();
	}

	private void loadModel()
	{
		reader.readConfig(model);
		model.setDrinksMap(reader.readDrinks());
		model.setExtraFoodsMap(reader.readExtraFoods());
		model.setRecipesSet(reader.readRecipes());
		model.setDishesSet(reader.readDishes());
		for (Dish d: model.getDishesSet())
		{
			model.getRecipesSet().add(d.getRecipe());
		}
		model.setThematicMenusSet(reader.readThematicMenu());
		model.setBookingMap(reader.readBooking());
		model.setRegistro(reader.readRegister());
		updateRecipeStringList();
		updateDishStringList();
		updateDrinkList();
		updateFoodList();
		updateMenuOut();
		updateMenuBoxes();
		sui.cfgResBaseOut.setText("Capacità: " + model.getCapacity() + "\n" + "IndividualWorkload: " +
				model.getWorkPersonLoad() + "\n" + "Restaurant Worlkload: " + model.getWorkResturantLoad()+ "\n"
				+"Data odierna: " + model.getToday().getStringDate()+"Surplus %: " + model.getIncrement());
		sui.cfgBaseInputCap.setText(Integer.toString(model.getCapacity()));
		sui.cfgBaseInputIndWork.setText(Integer.toString(model.getWorkPersonLoad()));
		sui.cfgBaseInputDate.setText(model.getToday().getStringDate());
		sui.cfgBaseInputSurplus.setText(Integer.toString(model.getIncrement()));
		generateGroceryList();
	}

	public String getTodayString(){
		return model.getToday().getStringDate();
	}

	public void clearInfo(String name)
	{
		switch (name)
		{
			case "config.xml":
				model.setCapacity(0);
				model.setWorkPersonLoad(0);
				model.setIncrement(5);
				try {
					model.setToday(new DateOur("01","01","1444"));
				} catch (ParseException e) {
					throw new RuntimeException(e); //mai
				}
				Writer.writeConfigBase(model.getCapacity(), model.getWorkPersonLoad(),model.getToday(), model.getIncrement());
				sui.cfgResBaseOut.setText("""
						Capacità: 0
						IndividualWorkload: 0
						Restaurant Workload: 0
						Data odierna: 01/01/1444
						Surplus %: 5""");
				sui.cfgBaseInputCap.setText(Integer.toString(0));
				sui.cfgBaseInputIndWork.setText(Integer.toString(0));
				clearInfo("bookings");
				break;
			case "drinks.xml":
				model.getDrinksMap().clear();
				Writer.writeDrinks(model.getDrinksMap());
				updateDrinkList();
				break;
			case "extraFoods.xml":
				model.getExtraFoodsMap().clear();
				Writer.writeExtraFoods(model.getExtraFoodsMap());
				updateFoodList();
				break;
			case "recipes.xml":
				model.getRecipesSet().clear();
				Writer.writeRecipes(model.getRecipesSet());
				updateRecipeStringList();	//il break non serve perchè se cancello le ricette devo cancellare anche i menu ed i dish
			case "dishes.xml":
				model.getDishesSet().clear();
				Writer.writeDishes(model.getDishesSet());
				updateDishStringList();
			case "thematicMenus.xml":
				model.getThematicMenusSet().clear();
				Writer.writeThematicMenu(model.getThematicMenusSet());
				updateMenuOut();
				updateMenuBoxes();
			case "bookings":
				model.getBookingMap().clear();
				writeBookings();
				break;
		}
	}

	public void writeManager()
	{
		//if(!model.getDrinksMap().isEmpty())
		Writer.writeDrinks(model.getDrinksMap());
		//if(!model.getExtraFoodsMap().isEmpty())
		Writer.writeExtraFoods(model.getExtraFoodsMap());
		Writer.writeConfigBase(model.getCapacity(), model.getWorkPersonLoad(),model.getToday(), model.getIncrement());
		Writer.writeRecipes(model.getRecipesSet());
		//if(!model.getDishesSet().isEmpty())
		Writer.writeDishes(model.getDishesSet());
		//if(!model.getThematicMenusSet().isEmpty())
		Writer.writeThematicMenu(model.getThematicMenusSet());
	}
	
	public void saveConfig()
	{
		try
		{
			String inputCapacity = sui.cfgBaseInputCap.getText();
			String inputWorkload = sui.cfgBaseInputIndWork.getText();
			String inputPercent = sui.cfgBaseInputSurplus.getText();
			String todayString = sui.cfgBaseInputDate.getText().trim();
			int capacity = Integer.parseInt(inputCapacity);
			int workload= Integer.parseInt(inputWorkload);
			int percent= Integer.parseInt(inputPercent);
			if (!checkDate(todayString))
				sui.errorSetter("invalidDate");
			else {
				DateOur today = inputToDate(todayString);
				if (capacity <= 0 || workload <= 0)
					sui.errorSetter("minZero");
				else if (percent>10){
					sui.errorSetter("surplusTooGreat");
				}else {
					model.setCapacity(capacity);
					model.setWorkPersonLoad(workload);
					model.setToday(today);
					model.setIncrement(percent);
					sui.cfgResBaseOut.setText("Capacità: " + model.getCapacity() + "\n" + "IndividualWorkload: " +
							model.getWorkPersonLoad() + "\n" + "Restaurant Worlkload: " + model.getWorkResturantLoad()+
							"\n" +"Data odierna: " + model.getToday().getStringDate()+"Surplus %: " + model.getIncrement());
				}
			}
		}
		catch (NumberFormatException e)
		{
			sui.errorSetter("NumberFormatException");
		}
		
	}
	
	public void saveDrinks()
	{
		try
		{
			String input = sui.cfgDrinksInput.getText();
			if (!input.contains(":"))
				throw new NumberFormatException ("");

			String[] inputSplit = input.split(":");

			if (inputSplit[0].equals(""))
				throw new NumberFormatException ("");
			if(inputSplit.length<2)
				throw new NumberFormatException ("");

			double quantity = Double.parseDouble(inputSplit[1]);

			if (quantity <= 0)
				sui.errorSetter("minZero");
			else {
				model.getDrinksMap().put(inputSplit[0], quantity);
				sui.cfgDrinksInput.setText("");
				updateDrinkList();
			}
		}
		catch (NumberFormatException e)
		{
			sui.errorSetter("NumberFormatException");
		}
	}
	
	public void saveFoods(){
		try
		{
			String input = sui.cfgDrinksInput.getText();

			if (!input.contains(":"))
				throw new NumberFormatException ("");

			String[] inputSplit = input.split(":");

			if(inputSplit.length<2)
				throw new NumberFormatException ("");
			if (inputSplit[0].equals(""))
				throw new NumberFormatException ("");
			double quantity = Double.parseDouble(inputSplit[1]);

			if(quantity<= 0)
				sui.errorSetter("minZero");
			else
			{
				model.getExtraFoodsMap().put(inputSplit[0], quantity);
				sui.cfgFoodsInput.setText("");
				updateFoodList();
			}
		}
		catch (NumberFormatException e)
		{
			sui.errorSetter("NumberFormatException");
		}
	}
	
	public void saveRecipe() {
		try
		{
			boolean err=false;
			String inputName = sui.cfgRecipeNameInput.getText();
			String inputIngredients = sui.cfgRecipeIngredientsInput.getText();
			String inputPortions = sui.cfgRecipePortionsInput.getText();
			String inputWorkload = sui.cfgRecipeWorkLoadInput.getText();
			
			HashMap<String, Double> ingredientQuantityMap = new HashMap<>();
			String[] lines = inputIngredients.split("\n");

			if (inputName.equals(""))
				throw new NumberFormatException ("");

			for (String line : lines)
			{
				if(!line.contains(":"))
					throw new NumberFormatException ("");

				String[] words = line.split(":");

				if(words[0].equals(""))
					throw new NumberFormatException ("");

				if (words.length<2)
					throw new NumberFormatException ("");

				double quantity=Double.parseDouble(words[1]);

				if(quantity<=0)
				{
					err = true;
					break;
				}
				ingredientQuantityMap.put(words[0],quantity );
			}
			int portions=Integer.parseInt(inputPortions);
			double workLoad = Double.parseDouble(inputWorkload);
			if(portions<=0 || workLoad<=0 || err)
				sui.errorSetter("minZero");
			else
			{
				if(model.getRecipesSet().add(new Recipe(inputName, ingredientQuantityMap, portions, workLoad)))
				{
					sui.cfgRecipeNameInput.setText(Model.CLEAR);
					sui.cfgRecipeIngredientsInput.setText(Model.CLEAR);
					sui.cfgRecipePortionsInput.setText(Model.CLEAR);
					sui.cfgRecipeWorkLoadInput.setText(Model.CLEAR);
					updateRecipeStringList();
				}
				else
					sui.errorSetter("existingName");

			}
		}
		catch (NumberFormatException e)
		{
			sui.errorSetter("NumberFormatException");
		}
	}
    
    public void saveDish()
	{
		try
		{
			String inputName = sui.cfgDishNameInput.getText();

			if(inputName.equals(Model.CLEAR))
				throw new NumberFormatException ("");

			String inputRecipe = ((String) sui.cfgDishComboBox.getSelectedItem()).split("-")[0].trim();
			
			String inputStartDate = sui.cfgDishSDateInput.getText();
			String inputEndDate = sui.cfgDishEDateInput.getText();
			boolean perm = sui.cfgDishPermanentRadio.isSelected();
			boolean seasonal = sui.cfgDishSeasonalRadio.isSelected();
			if(!perm)
			{
				if (!checkDate(inputStartDate) || !checkDate(inputEndDate))
				{
					sui.errorSetter("invalidDate");
					return;
				}
			}
			else
			{
				inputStartDate = "01/01/1444";
				inputEndDate = "31/12/1444";
			}
			boolean found=false;
			for (Recipe r : model.getRecipesSet())
			{
				found = false;
				if (r.getId().equals(inputRecipe))
				{
					if(model.getDishesSet().add(new Dish(inputName, r, inputStartDate, inputEndDate, seasonal, perm)))
					{
						updateDishStringList();
						sui.cfgDishNameInput.setText(Model.CLEAR);
						sui.cfgDishSDateInput.setText(Model.CLEAR);
						sui.cfgDishEDateInput.setText(Model.CLEAR);
						found = true;
						break;
					}
					else
						sui.errorSetter("existingName");
				}
			}
			if (!found)
				sui.errorSetter("noRecipe");
		}catch (Exception e)
		{
			sui.errorSetter("NumberFormatException");
		}
	}

	public void saveMenu()
	{
		try
		{
		String inputName = sui.cfgMenuNameInput.getText();
		String inputs = sui.cfgMenuDishesInput.getText();

		if (inputName.equals(Model.CLEAR))
			throw new NumberFormatException("");

		String[] inputList = inputs.split("\n");

		String inputStartDate = sui.cfgMenuSDateInput.getText();
		String inputEndDate = sui.cfgMenuEDateInput.getText();

		boolean permanent = sui.cfgMenuPermanentRadio.isSelected();
		boolean seasonal = sui.cfgMenuSeasonalRadio.isSelected();

		ArrayList<Dish> dishesForMenu = new ArrayList<>();

		if(!permanent)
		{
			if (!checkDate(inputStartDate) || !checkDate(inputEndDate)) {
				sui.errorSetter("invalidDate");
				return;
			}
		}
		else
		{
			inputStartDate = "01/01/1444";
			inputEndDate = "31/12/1444";
		}
		boolean found=false;
		for (String s: inputList)
		{
			found = false;
			if(!s.equals(""))
			{
				for (Dish d : model.getDishesSet()) {
					if (d.getName().equals(s)) {
						dishesForMenu.add(d);
						found = true;
						sui.cfgMenuNameInput.setText(Model.CLEAR);
						sui.cfgMenuDishesInput.setText(Model.CLEAR);
						sui.cfgMenuSDateInput.setText(Model.CLEAR);
						sui.cfgMenuEDateInput.setText(Model.CLEAR);
						break;
					}
				}
			}
		}
		if(!found)
			sui.errorSetter("noDish");
		else
		{
			ThematicMenu temp = new ThematicMenu(inputName, inputStartDate, inputEndDate, dishesForMenu, seasonal, permanent);
			if(temp.getWorkThematicMenuLoad()<=((double)model.getWorkPersonLoad()*4/3)) {
				boolean valid = true;
				for (String s : sui.dishString) {
					if (s.equals(temp.getName())) {
						valid = false;
						break;
					}
				}
				if (valid)
				{
					if(model.getThematicMenusSet().add(temp)) {
						updateMenuOut();
						updateMenuBoxes();
					}
					else
						sui.errorSetter("existingName");
				}
				else sui.errorSetter("sameNameAsDish");
			}else sui.errorSetter("thiccMenu");
		}
		} catch (Exception e) {
			sui.errorSetter("NumberFormatException");
		}
	}

	public static boolean checkDate (String s) {
		if(s.equals(""))
			return false;
		if(!s.contains("/"))
			return false;
		String [] pezzi = s.split("/");
		if (pezzi.length<3) return false;
		if(Integer.parseInt(pezzi[2])<=0)
			return false;
		switch (Integer.parseInt(pezzi[1]))
		{
			case 1, 3, 5, 7, 8, 10, 12 ->
			{
				if (Integer.parseInt(pezzi[0]) <= 31 || Integer.parseInt(pezzi[0]) > 0)
					return true;
			}
			case 2 ->
			{
				if (Integer.parseInt(pezzi[0]) <= 29 || Integer.parseInt(pezzi[0]) > 0)
					return true;
			}
			case 4, 6, 9, 11 ->
			{
				if (Integer.parseInt(pezzi[0]) <= 30 || Integer.parseInt(pezzi[0]) > 0)
					return true;
			}
			default ->
			{
				return false;
			}
		}
		return false;
	}

	public ArrayList<Dish> stringListToDishList(ArrayList<String> list ) {
		ArrayList<Dish> dishes = new ArrayList<>();
		for (String s: list) {
			for (Dish d: model.getDishesSet()) {
				if (d.getName().equals(s))
					dishes.add(d);
			}
		}
		return dishes;
	}

	public void updateMenuOut() {
		StringBuilder out= new StringBuilder();
		for (ThematicMenu m: model.getThematicMenusSet()) {
			out.append(m.getName()).append(" - [").append(m.getStartPeriod().getStringDate()).append(" || ").append(m.getStartPeriod().getStringDate()).append("] - w.").append(m.getWorkThematicMenuLoad()).append(" \n");
		}
		sui.cfgResMenuOut.setText(out.toString().trim());
		sui.setMenuList(out.toString().trim());
	}

	public Recipe stringToRecipe(String id) {
			for (Recipe r :model.getRecipesSet() )
			{
				if (r.getId().equals(id))
					return r;
			}
		return null; //non dovrebbe succedere
	}

	public void updateRecipeStringList() {
		String[] recipes = new String[model.getRecipesSet().size()];
		int i=0;
		for (Recipe r: model.getRecipesSet())
		{
			recipes[i]=(r.getId() +" - "+ "["+r.getIngredientsList()+"] - p." + r.getPortions() + " - w." + r.getWorkLoadPortion());
			i++;
		}
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>( recipes );
		sui.cfgDishComboBox.setModel(model);

		StringBuilder compactedArray= new StringBuilder();
		for (String s: recipes) {
			compactedArray.append(s).append("\n");
		}
		sui.cfgResRecipesOut.setText(compactedArray.toString().trim());
		sui.setRecipeList(compactedArray.toString().trim());
	}
//TODO fare i metodi con parametri che vengono da sui
	public void updateDishStringList()	{
		String[] dishes = new String[model.getDishesSet().size()];
		int i=0;
		for (Dish d: model.getDishesSet())
		{
			dishes[i]=(d.getName() + " - ["+d.getStartPeriod().getStringDate() + " || "+ d.getEndPeriod().getStringDate() + "] - " + "(" + d.getRecipe().getId() + ")");
			i++;
		}
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>( dishes );
		sui.cfgMenuComboBox.setModel(model);

		StringBuilder compactedArray= new StringBuilder();
		for (String s: dishes) {
			compactedArray.append(s).append("\n");
		}
		sui.cfgResDishesOut.setText(compactedArray.toString().trim());
		sui.setDishList(compactedArray.toString().trim());
	}

	public void updateDrinkList(){
		StringBuilder out= new StringBuilder();
		for (Map.Entry<String, Double> drink : model.getDrinksMap().entrySet())
		{
			out.append(drink.getKey()).append(":").append(drink.getValue().toString()).append("\n");
		}
		sui.setDrinkList(out.toString().trim());
		sui.cfgResDrinksOut.setText(out.toString().trim());
	}

	public void updateFoodList(){
		StringBuilder out= new StringBuilder();
		for (Map.Entry<String, Double> food : model.getExtraFoodsMap().entrySet())
		{
			out.append(food.getKey()).append(":").append(food.getValue().toString()).append("\n");
		}
		sui.setFoodsList(out.toString().trim());
		sui.cfgResFoodsOut.setText(out.toString().trim());
	}

	public void updateMenuBoxes(){
		String[] out = makeMenuList();
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>( out );
		sui.empNewBookMenuBox.setModel(model);
		sui.cfgResDatiMenuBox.setModel(model);
	}

	public void writeMenuComp(String menuName){
		StringBuilder out = new StringBuilder();
		for (ThematicMenu menu: model.getThematicMenusSet()){
			if (menu.getName().equals(menuName))
			{
				out.append(menuName).append(" w.").append(menu.getWorkThematicMenuLoad()).append("\n");
				for (Dish d: menu.getDishes()) {
					out.append("    ").append(d.getName()).append("\n");
				}
				break;
			}
		}
		sui.cfgResDatiMenuOut.setText(out.toString());
	}

	public String[] makeMenuList()
	{
		String[] out = new String[model.getThematicMenusSet().size()];
		int i = 0;
		for (ThematicMenu m : model.getThematicMenusSet()) {
			out[i] = m.getName();
			i++;
		}
		return out;
	}


	public DateOur inputToDate(String input){
		String[] bookDates;
		try
		{
			bookDates = input.split("/");
			return new DateOur(bookDates[0],bookDates[1],bookDates[2]);
		}
		catch (Exception e) {
			sui.errorSetter("invalidDate");
		}
		return null;
	}

	public HashMap<Dish,Integer> inputToOrder(String in, DateOur date) {
		String[] lines = in.split("\n");
		HashMap<Dish,Integer> order = new HashMap<>();
		boolean found;
		try
		{
			for (String line : lines)
			{
				found = false;
				String[] parts = line.split(":");
				String name = parts[0];
				Integer num = Integer.parseInt(parts[1]);
				if (num <= 0) //errore
				{
					order.clear();
					sui.errorSetter("minZero");
					return order;
				}
				for (ThematicMenu menu : model.getThematicMenusSet())
				{
					if (name.equals(menu.getName()))
					{
						if (menu.isValid(date))
						{
							found = true;
							for (Dish dish : model.getDishesSet()) {
									if (order.containsKey(dish))
										order.put(dish, order.get(dish) + num);
									else
										order.put(dish, num);
							}
							break;
						}
						else
						{
							order.clear();
							sui.errorSetter("outOfDate");
							return order;
						}
					}
				}
				if (!found)
				{
					for (Dish dish : model.getDishesSet())
					{
						if (name.equals(dish.getName()))
						{
							if(dish.isValid(date))
							{
								found = true;
								if (order.containsKey(dish))
									order.put(dish, order.get(dish) + num);
								else
									order.put(dish, num);
								break;
							}
							else
							{
								order.clear();
								sui.errorSetter("invalidDate");
								return order;
							}
						}
					}
				}
				if (!found)
				{
					order.clear();
					sui.errorSetter("notFound");
					return order;
				}
			}
			return order;
		}catch (NumberFormatException e)
		{
			sui.errorSetter("noQuantity");
			return new HashMap<>();
		}
	}

	public void seeBookings(DateOur data)
	{
		if(model.getBookingMap().containsKey(data))
		{
			ArrayList<Booking> dayBookings = new ArrayList<>(model.getBookingMap().get(data));
			StringBuilder name = new StringBuilder();
			StringBuilder number = new StringBuilder();
			StringBuilder work = new StringBuilder();
			int capacity = 0, workload = 0;

			for (Booking b : dayBookings)
			{
				name.append(b.getName()).append("\n");
				number.append(b.getNumber()).append("\n");
				work.append(b.getWorkload()).append("\n") ;
				capacity += b.getNumber();
				workload += b.getWorkload();
			}
			sui.empSeeBookNameAreaOut.setText(name.toString().trim());
			sui.empSeeBookNumAreaOut.setText(number.toString().trim());
			sui.empSeeBookWorkloadAreaOut.setText(work.toString().trim());
			sui.empSeeBookCapacityTotalOut.setText(Integer.toString(model.getCapacity()-capacity));
			sui.empSeeBookWorkloadTotalOut.setText(Double.toString(model.getWorkResturantLoad()-workload));
		}
		else
			sui.errorSetter("noBookings");
	}

	public void saveBooking ()
	{
		try
		{
			String name = sui.empNewBookNameInput.getText();
			DateOur date = inputToDate(sui.empNewBookDateInput.getText());
			if (date !=null && date.getDate().after(model.getToday().getDate()))
			{
				int number = Integer.parseInt(sui.empNewBookNumInput.getText()), workload = 0;
				HashMap<Dish, Integer> order = inputToOrder(sui.empNewBookOrderInput.getText(), date);
				if (!order.isEmpty()) {
					if (number > 0) {
						for (Map.Entry<Dish, Integer> dish : order.entrySet())
							workload += dish.getKey().getRecipe().getWorkLoadPortion() * dish.getValue();
						if (manageBooking(name, date, number, workload, order)) {
							sui.empNewBookNameInput.setText(Model.CLEAR);
							sui.empNewBookDateInput.setText(Model.CLEAR);
							sui.empNewBookNumInput.setText(Model.CLEAR);
							sui.empNewBookOrderInput.setText(Model.CLEAR);
						}
					} else
						sui.errorSetter("minZero");
				}
			}
		}catch(NumberFormatException e){
			sui.errorSetter("NumberFormatException");
		}
	}
	
	
	/**
	 * Adds a new booking to the restaurant's booking system for the specified date, with the provided information.
	 *
	 * @param name The name of the person making the booking.
	 * @param date The date of the booking (must be of type DateOur).
	 * @param number The number of guests in the booking.
	 * @param workload The expected workload for the restaurant staff for the booking.
	 * @param order A HashMap of the dishes and the quantities of each that the guests will order.
	 *
	 * @return true if the booking was successfully added, false otherwise (for example, if the restaurant is already at capacity or the workload is too high).
	 */
	public boolean manageBooking(String name, DateOur date, int number, int workload, HashMap<Dish, Integer> order) {
		// Create a new ArrayList to hold the bookings for the specified date
		ArrayList<Booking> day = new ArrayList<>();
		// Set the initial capacity and workload based on the inputs
		int capacity = number;
		int work = workload;
		
		// Retrieve the bookings for the specified date, if they exist
		if (!model.getBookingMap().containsKey(date)) {
			// If there are no existing bookings for the date, check if the restaurant has capacity and workload available to add the new booking
			if (capacity <= model.getCapacity() && work <= model.getWorkResturantLoad()) {
				// If there is capacity and workload available, create a new Booking object and add it to the ArrayList for the date
				day.add(new Booking(name, number, workload, order));
				// Add the ArrayList to the booking map with the specified date
				model.getBookingMap().put(date, day);
				return true;
			} else {
				// If there is no capacity or workload available, set an error message and return false
				sui.errorSetter("fullRestaurant");
				return false;
			}
		} else {
			// If there are existing bookings for the date, calculate the total capacity and workload based on all bookings for the date
			for (Booking b : day) {
				capacity += b.getNumber();
				work += b.getWorkload();
			}
			// Check if the restaurant has capacity and workload available to add the new booking
			if (capacity <= model.getCapacity() && work <= model.getWorkResturantLoad()) {
				// If there is capacity and workload available, create a new Booking object and add it to the ArrayList for the date
				day.add(new Booking(name, number, workload, order));
				// Update the booking map with the ArrayList for the specified date
				model.getBookingMap().put(date, day);
				return true;
			} else {
				// If there is no capacity or workload available, set an error message and return false
				sui.errorSetter("fullRestaurant");
				return false;
			}
		}
	}

	public HashMap<Dish,Integer> dishToMap (HashMap<String,Integer> map)	{
		HashMap<Dish,Integer> out = new HashMap<>();
		for (Map.Entry<String, Integer> s : map.entrySet())
		{
			for(Dish dish: model.getDishesSet())
			{
				if(dish.getName().equals(s.getKey()))
				{
					out.put(dish,s.getValue());
					break;
				}
			}
		}
		return out;
	}

	public void writeBookings()
	{
		Writer.writeBookings(model.getBookingMap());
	}
	
	public void writeRegister()
	{
		Writer.writeRegister(model.getRegistro());
	}
	
	private void generateGroceryList()
	{
		HashMap<String,Double> groceryMap = new HashMap<>();
		HashMap<String,Double> surplusMap = new HashMap<>();
		double quantity=0.0,surplus=0.0;
		int multi=0;
		Recipe recipe;
		int numberOfPeople=0;
		HashMap<Dish, Integer> allDish = new HashMap<>();
		
		if(model.getBookingMap().containsKey(model.getToday()))
		{
			ArrayList <Booking> book = new ArrayList<>(model.getBookingMap().get(model.getToday()));
			for (Booking b: book)
			{
 				numberOfPeople+=b.getNumber();
				for (Map.Entry<Dish, Integer> entry : b.getOrder().entrySet())
				{
					Dish dish = entry.getKey();
					if(allDish.containsKey(dish))
						allDish.put(dish, entry.getValue() + allDish.get(dish));
					else
						allDish.put(dish, entry.getValue());
				}
			}
			for (Map.Entry<Dish, Integer> entry : allDish.entrySet())
			{
				recipe = entry.getKey().getRecipe();

				for (Map.Entry<String, Double> ingredient : recipe.getIngredients().entrySet())
				{
					multi = entry.getValue()/recipe.getPortions();
					int resto = entry.getValue()%recipe.getPortions();

					if(multi == 0)
					{
						multi++;
						surplus=ingredient.getValue()*(recipe.getPortions()-entry.getValue());
					}
					if(resto!=0)
					{
						multi++;
						surplus=ingredient.getValue()/recipe.getPortions()*resto;
					}

					quantity = ingredient.getValue()*multi;

					if (groceryMap.containsKey(ingredient.getKey()))
					{
						double delta = quantity;
						quantity+= groceryMap.get(ingredient.getKey());
						delta = quantity - delta;
						if (delta<surplusMap.get(ingredient.getKey()))
						{
							surplusMap.put(ingredient.getKey(),(surplusMap.get(ingredient.getKey())-delta));
						}
						else
						{
							groceryMap.put(ingredient.getKey(), quantity);
							surplusMap.put(ingredient.getKey(),surplusMap.get(ingredient.getKey())+surplus);
						}
					}
					else
					{
						groceryMap.put(ingredient.getKey(),quantity);
						surplusMap.put(ingredient.getKey(),surplus);
					}
				}
			}

			groceryMap.replaceAll((k, v) -> v+(v * (double)model.getIncrement()/100.0)); //incremento del 5% ogni ingrediente

			groceryMap = new HashMap<>(compareWithRegister(groceryMap));

			HashMap <String,Double> drink = new HashMap<>(model.getDrinksMap());
			HashMap <String,Double> food = new HashMap<>(model.getExtraFoodsMap());

			final int n=numberOfPeople;
			drink.replaceAll((k,v)-> Math.ceil(v*n));
			food.replaceAll((k,v)-> Math.ceil(v*n));

			drink = new HashMap<>(compareWithRegister(drink));
			food = new HashMap<>(compareWithRegister(food));

		//	model.setGroceryMap(groceryMap); //todo vedere se serve

			updateRegister(groceryMap);
			updateRegister(drink);
			updateRegister(food);

			model.setGroceryMap(groceryMap);
			
			sui.wareListOut.setText(groceriesToString(groceryMap, drink, food));
			sui.wareListMagOut.setText(registerToString());
		}
		else
			sui.wareListOut.setText("Non essendoci prenotazioni per oggi la lista della spesa è vuota");
	}


	private HashMap<String,Double> compareWithRegister(HashMap<String,Double> map)
	{
		for (Map.Entry<String, Double> mag: model.getRegistro().entrySet())
		{
			if(map.containsKey(mag.getKey()))
			{
				double q=map.get(mag.getKey())-mag.getValue();
				if(q<=0)
					map.remove(mag.getKey());
				else
					map.put(mag.getKey(), q);
			}
		}
		return map;
	}


	private void updateRegister(HashMap<String,Double> map)
	{
		for (Map.Entry<String, Double> ingredient : map.entrySet())
		{
			if(model.getRegistro().containsKey(ingredient.getKey()))
				model.getRegistro().put(ingredient.getKey(),ingredient.getValue()+model.getRegistro().get(ingredient.getKey()));
			else
				model.getRegistro().put(ingredient.getKey(),ingredient.getValue());
		}
	}

	private String registerToString(){
		String out ="";
		for (Map.Entry<String, Double> entry : model.getRegistro().entrySet())
		{
			String k = entry.getKey();
			if (model.getExtraFoodsMap().containsKey(k)){
				out += k + ":" + entry.getValue() + " hg\n";
			}
			else if (model.getDrinksMap().containsKey(k)){
				out += k + ":" + entry.getValue() + " L\n";
			}else out += k + ":" + entry.getValue() + " g\n";
			
		}
		return out.trim();
	}
	
	
	private String groceriesToString(HashMap<String, Double> ingredients, HashMap<String, Double> drinks, HashMap<String, Double> foods){
		String out = "";
		out += groceryIngredientsMapToString(ingredients) + "\n";
		out += groceryFoodsMapToString(foods) + "\n";
		out += groceryDrinksMapToString(drinks);
		return out;
	}


	private String groceryDrinksMapToString(HashMap<String, Double> drinks)
	{
		String out ="";
		for (Map.Entry<String, Double> entry : drinks.entrySet())
		{
			out= out+entry.getKey()+" : "+entry.getValue()+" L" +"\n";
		}
		return out.trim();
	}
	private String groceryFoodsMapToString(HashMap<String, Double> foods)
	{
		String out ="";
		for (Map.Entry<String, Double> entry : foods.entrySet())
		{
			out= out+entry.getKey()+" : "+entry.getValue()+" hg" +"\n";
		}
		return out.trim();
	}


	private String groceryIngredientsMapToString(HashMap<String, Double> ingredients)
	{
		String out ="";
		for (Map.Entry<String, Double> entry : model.getGroceryMap().entrySet())
		{
			out= out+entry.getKey()+" : "+entry.getValue()+" g" +"\n";
		}
		return out.trim();

	}
}
