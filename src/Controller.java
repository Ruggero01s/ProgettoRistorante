import javax.swing.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Controller
{
	static Model model = new Model();
	SimpleUI sui = new SimpleUI(this);
	
	public void init()
	{
		sui.init();
		loadModel();
	}

	private void loadModel()
	{
		Reader.readConfig(model);
		model.setDrinksMap(Reader.readDrinks());
		model.setExtraFoodsMap(Reader.readExtraFoods());
		model.setRecipesSet(Reader.readRecipes());
		model.setDishesSet(Reader.readDishes());
		for (Dish d: model.getDishesSet())
		{
			model.getRecipesSet().add(d.getRecipe());
		}
		model.setThematicMenusSet(Reader.readThematicMenu());
		updateRecipeStringList();
		updateDishStringList();
		updateDrinkList();
		updateFoodList();
		updateMenuOut();
		updateEmpNewBookMenuBox();
		sui.cfgResBaseOut.setText("Capacità: "+ model.getCapacity() + "\n" + "IndividualWorkload: " + model.getWorkPersonLoad() + "\n" + "Restaurant Worlkload: "+ model.getWorkResturantLoad());
		sui.cfgBaseInputCap.setText(Integer.toString(model.getCapacity()));
		sui.cfgBaseInputIndWork.setText(Integer.toString(model.getWorkPersonLoad()));
	}

	public void clearInfo(String name)
	{
		switch (name)
		{
			case "config.xml":
				model.setCapacity(0);
				model.setWorkPersonLoad(0);
				Writer.writeConfigBase(model.getCapacity(), model.getWorkPersonLoad());
				sui.cfgResBaseOut.setText("""
						Capacità: 0
						IndividualWorkload: 0
						Restaurant Worlkload: 0""");
				sui.cfgBaseInputCap.setText(Integer.toString(0));
				sui.cfgBaseInputIndWork.setText(Integer.toString(0));
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
				updateEmpNewBookMenuBox();
				break;
		}
	}

	public void writeAll()
	{
		//if(!model.getDrinksMap().isEmpty())
			Writer.writeDrinks(model.getDrinksMap());
		//if(!model.getExtraFoodsMap().isEmpty())
			Writer.writeExtraFoods(model.getExtraFoodsMap());
		Writer.writeConfigBase(model.getCapacity(), model.getWorkPersonLoad());
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

			int capacity = Integer.parseInt(inputCapacity);
			int workload= Integer.parseInt(inputWorkload);

			if(capacity<= 0 || workload<= 0)
				sui.errorSetter("minZero");
			else
			{
				model.setCapacity(capacity);
				model.setWorkPersonLoad(workload);
				sui.cfgResBaseOut.setText("Capacità: "+ model.getCapacity() + "\n" + "IndividualWorkload: " +
						model.getWorkPersonLoad() + "\n" + "Restaurant Worlkload: "+ model.getWorkResturantLoad());
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
			String[] input = sui.cfgDrinksInput.getText().split(":");
			double quantity = Double.parseDouble(input[1]);

			if(quantity<= 0)
				sui.errorSetter("minZero");
			else
			{
				model.getDrinksMap().put(input[0], quantity);
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
			String[] input = sui.cfgFoodsInput.getText().split(":");
			double quantity = Double.parseDouble(input[1]);
			if(quantity<= 0)
				sui.errorSetter("minZero");
			else
			{
				model.getExtraFoodsMap().put(input[0], quantity);
				sui.cfgFoodsInput.setText("");
				updateFoodList();
			}
		}
		catch (NumberFormatException e)
		{
			sui.errorSetter("NumberFormatException");
		}
	}
	
	public void printDrinks()
	{
		System.out.println(model.getDrinksMap());
	}
	
	public void printExtraFoods()
	{
		System.out.println(model.getExtraFoodsMap());
	}
	
	public void printRecipes()
	{
		System.out.println(model.getRecipesSet());
	}
	
	public void printDishes()
	{
		System.out.println(model.getDishesSet());
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

			for (String line : lines)
			{
				String[] words = line.split(":");
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
				model.getRecipesSet().add(new Recipe(inputName, ingredientQuantityMap, portions, workLoad));
				sui.cfgRecipeNameInput.setText("");
				sui.cfgRecipeIngredientsInput.setText("");
				sui.cfgRecipePortionsInput.setText("");
				sui.cfgRecipeWorkLoadInput.setText("");
				updateRecipeStringList();
			}
		}
		catch (NumberFormatException e)
		{
			sui.errorSetter("NumberFormatException");
		}
	}
    
    public void saveDish() {
		try
		{
			String inputName = sui.cfgDishNameInput.getText();
			String inputIngredients = (String) sui.cfgDishComboBox.getSelectedItem();
			
			String inputStartDate = sui.cfgDishSDateInput.getText();
			String inputEndDate = sui.cfgDishEDateInput.getText();

			if (sui.cfgDishPermanentRadio.isSelected())
			{
				inputStartDate = "01/01";
				inputEndDate = "31/12";
			}
			if(!checkDate(inputStartDate) || !checkDate(inputEndDate)) {
				sui.errorSetter("invalidDate");
				return;
			}
				boolean found = false;
				for (Recipe r : model.getRecipesSet()) {
					found = false;
					if (r.getId().equals(inputIngredients)) {
						model.getDishesSet().add(new Dish(inputName, r, inputStartDate, inputEndDate));
						updateDishStringList();
						sui.cfgDishNameInput.setText("");
						sui.cfgDishSDateInput.setText("");
						sui.cfgDishEDateInput.setText("");
						found = true;
						break;
					}
				}
				if (!found)
					sui.errorSetter("noRecipe");
		}catch (ParseException e)
		{
			sui.errorSetter("invalidDate");
		}
	}

	public void saveMenu() throws ParseException {
		String inputName = sui.cfgMenuNameInput.getText();
		String inputs = sui.cfgMenuDishesInput.getText();

		String[] inputList = inputs.split("\n");

		String inputStartDate = sui.cfgMenuSDateInput.getText();
		String inputEndDate = sui.cfgMenuEDateInput.getText();

		ArrayList<Dish> dishesForMenu = new ArrayList<>();

		if (sui.cfgMenuPermanentRadio.isSelected())
		{
			inputStartDate = "01/01";
			inputEndDate = "31/12";
		}
		if(!checkDate(inputStartDate) || !checkDate(inputEndDate)) {
			sui.errorSetter("invalidDate");
			return;
		}

		boolean found=false;
		for (String s: inputList)
		{
			found = false;
			if(!s.equals("")) {
				for (Dish d : model.getDishesSet()) {
					if (d.getName().equals(s)) {
						dishesForMenu.add(d);
						found = true;
						sui.cfgMenuNameInput.setText("");
						sui.cfgMenuDishesInput.setText("");
						sui.cfgMenuSDateInput.setText("");
						sui.cfgMenuEDateInput.setText("");
						break;
					}
				}
			}
		}
		if(!found)
			sui.errorSetter("noDish");
		else
		{
			ThematicMenu temp = new ThematicMenu(inputName, inputStartDate, inputEndDate, dishesForMenu);
			if(temp.getWorkThematicMenuLoad()<=((double)model.getWorkPersonLoad()*4/3)) {
				boolean valid = true;
				for (String s : sui.dishString) {
					if (s.equals(temp.getName())) {
						valid = false;
						break;
					}
				}
				if (valid) {
					model.getThematicMenusSet().add(temp);
					updateMenuOut();
					updateEmpNewBookMenuBox();
				}
				else sui.errorSetter("sameNameAsDish");
			}else sui.errorSetter("thiccMenu");
		}
	}

	public static boolean checkDate (String s) {
		if(s.equals(""))
			return false;
		if(!s.contains("/"))
			return false;
		String [] pezzi = s.split("/");
		switch (Integer.parseInt(pezzi[1]))
		{
			case 1,3,5,7,8,10,12:
				if(Integer.parseInt(pezzi[0]) <=31 || Integer.parseInt(pezzi[0]) >0)
					return true;
				break;
			case 2:
				if(Integer.parseInt(pezzi[0]) <=29 || Integer.parseInt(pezzi[0]) >0)
					return true;
				break;
			case 4,6,9,11:
				if(Integer.parseInt(pezzi[0]) <=30 || Integer.parseInt(pezzi[0]) >0)
					return true;
				break;
			default:
				return false;
		}
		return false;
	}

	public static ArrayList<Dish> stringListToDishList(ArrayList<String> list ) {
		ArrayList<Dish> dishes = new ArrayList<>();
		for (String s: list) {
			for (Dish d: model.getDishesSet()) {
				if (d.getName().equals(s))
					dishes.add(d);
			}
		}
		return dishes;
	}

	public void updateMenuOut () {
		String out="";
		for (ThematicMenu m: model.getThematicMenusSet()) {
			out=out+m.getName()+"\n";
		}
		sui.cfgResMenuOut.setText(out);
		sui.setMenuList(out);
	}

	public static Recipe stringToRecipe(String id) {
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
			recipes[i]=(r.getId());
			i++;
		}
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>( recipes );
		sui.cfgDishComboBox.setModel(model);

		String compactedArray="";
		for (String s: recipes) {
			compactedArray=(compactedArray + s + "\n");
		}
		sui.cfgResRecipesOut.setText(compactedArray);
		sui.setRecipeList(compactedArray);
	}
//TODO fare i metodi con parametri che vengono da sui
	public void updateDishStringList()	{
		String[] dishes = new String[model.getDishesSet().size()];
		int i=0;
		for (Dish d: model.getDishesSet())
		{
			dishes[i]=(d.getName());
			i++;
		}
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>( dishes );
		sui.cfgMenuComboBox.setModel(model);

		String compactedArray="";
		for (String s: dishes) {
			compactedArray=(compactedArray + s + "\n");
		}
		sui.cfgResDishesOut.setText(compactedArray);
		sui.setDishList(compactedArray);
	}

	public void updateDrinkList(){
		String out="";
		for (Map.Entry<String, Double> drink : model.getDrinksMap().entrySet())
		{
			out = out + drink.getKey() + ":" + drink.getValue().toString() + "\n";
		}
		sui.setDrinkList(out);
		sui.cfgResDrinksOut.setText(out);
	}

	public void updateFoodList(){
		String out="";
		for (Map.Entry<String, Double> food : model.getExtraFoodsMap().entrySet())
		{
			out = out + food.getKey() + ":" + food.getValue().toString() + "\n";
		}
		sui.setFoodsList(out);
		sui.cfgResFoodsOut.setText(out);
	}

	public void updateEmpNewBookMenuBox(){
		String[] out = new String[model.getThematicMenusSet().size()];
		int i=0;
		for (ThematicMenu m: model.getThematicMenusSet()) {
			out[i]=m.getName();
			i++;
		}
		sui.empNewBookMenuBox.setModel(new DefaultComboBoxModel(out));
	}

	public Date inputToDate(String input) throws ParseException {
		String[] bookDates;
		try {
			bookDates = input.split("/");
			DateOur data = new DateOur(bookDates[0],bookDates[1]);
			return data;
		}
		catch (ParseException e) {
			sui.errorSetter("invalidDate");
		}
		return null;
	}
	public void seeBookings(Date data){
		ArrayList<Booking> dayBookings = new ArrayList<>(model.getBookingMap().get(data));
		String out="";
		for (Booking b: dayBookings) {
			out = (out + "\n" + b.getName() +"\t" + "n. " + b.getNumber() + "\t" + b.getWorkload());
		}
		sui.empSeeBookAreaOut.setText(out);
	}

	public void saveBooking ()
	{
		String name=sui.empNewBookNameInput.getText();
		DateOur date = inputToDate(sui.empNewBookDateInput.getText());
		int number = Integer.parseInt(sui.empNewBookNumInput.getText()),workload=0;
		HashMap<Dish, Integer> order=inputToOrder(sui.empNewBookOrderInput.getText());
		if (!order.isEmpty())
		{
			if (number > 0) {
				for (Map.Entry<Dish, Integer> dish : order.entrySet())
					workload += dish.getKey().getRecipe().getWorkLoadPortion() * dish.getValue();
				if (manageBooking(name, date, number, workload, order))
				{
					sui.empNewBookNameInput.setText("");
					sui.empNewBookDateInput.setText("");
					sui.empNewBookNumInput.setText("");
					sui.empNewBookOrderInput.setText("");
				}
			} else
				sui.errorSetter("minZero");
		}
	}

	public boolean manageBooking(String name, DateOur date, int number, int workload, HashMap<Dish,Integer> order)
	{
		ArrayList <Booking> day= new ArrayList<>();
		if (!model.getBookingMap().containsKey(date))
		{
			day.add(new Booking(name, number, workload, order));
			model.getBookingMap().put(date,new ArrayList<Booking>(Arrays.asList(new Booking(name, number, workload, order))));
			return true;
		}
		else
		{
			int capacity=number,work=workload;
			day = model.getBookingMap().get(date);
			for (Booking b : day)
			{
				capacity+=b.getNumber();
				work+=b.getWorkload();
			}
			if(capacity<=model.getCapacity() && work<= model.getWorkResturantLoad())
			{
				day.add(new Booking(name, number, workload, order));
				model.getBookingMap().put(date, day);
			}
			else
				sui.errorSetter("fullRestaurant");
		}
	}

	public static HashMap<Dish,Integer> dishToMap (HashMap<String,Integer> map)	{
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

}
