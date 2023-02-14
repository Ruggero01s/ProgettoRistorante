import javax.swing.*;
import java.awt.*;
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
		sui.cfgResBaseOut.setText("Capacità: "+ model.getCapacity() + "\n" + "IndividualWorkload: " + model.getWorkPersonLoad() + "\n" + "Restaurant Worlkload: "+ model.getWorkResturantLoad());
		sui.cfgBaseInputCap.setText(Integer.toString(model.getCapacity()));
		sui.cfgBaseInputIndWork.setText(Integer.toString(model.getWorkPersonLoad()));
	}

	public void clearInfo(String name)
	{
		switch (name){
			case "config.xml":
				model.setCapacity(0);
				model.setWorkPersonLoad(0);
				Writer.writeConfigBase(model.getCapacity(), model.getWorkPersonLoad());
				break;
			case "drinks.xml":
				model.getDrinksMap().clear();
				Writer.writeDrinks(model.getDrinksMap());
				break;
			case "extraFoods.xml":
				model.getExtraFoodsMap().clear();
				Writer.writeExtraFoods(model.getExtraFoodsMap());
				break;
			case "dishes.xml":
				model.getDishesSet().clear();
				Writer.writeDishes(model.getDishesSet());
				updateRecipeStringList();
				updateDishStringList();
				break;
			case "thematicMenu.xml":
				model.getThematicMenusSet().clear();
				Writer.writeThematicMenu(model.getThematicMenusSet());
				updateMenuOut();
				break;
		}
	}

	public void writeAll()
	{
		if(!model.getDrinksMap().isEmpty())
			Writer.writeDrinks(model.getDrinksMap());
		if(!model.getExtraFoodsMap().isEmpty())
			Writer.writeExtraFoods(model.getExtraFoodsMap());
		Writer.writeConfigBase(model.getCapacity(), model.getWorkPersonLoad());
		if(!model.getDishesSet().isEmpty())
			Writer.writeDishes(model.getDishesSet());
		if(!model.getThematicMenusSet().isEmpty())
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
	
	public void saveRecipe()
	{
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

	public void saveMenu() throws ParseException
	{
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
			model.getThematicMenusSet().add(new ThematicMenu(inputName, inputStartDate, inputEndDate, dishesForMenu));
			updateMenuOut();
		}
	}

	public static boolean checkDate (String s)
	{
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

	public static ArrayList<Dish> stringListToDishList(ArrayList<String> list )
	{
		ArrayList<Dish> dishes = new ArrayList<>();
		for (String s: list) {
			for (Dish d: model.getDishesSet()) {
				if (d.getName().equals(s))
					dishes.add(d);
			}
		}
		return dishes;
	}

	public void updateRecipeStringList()
	{
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
	public void updateDishStringList()
	{
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

	public void updateDrinkList()
	{
		String out="";
		for (Map.Entry<String, Double> drink : model.getDrinksMap().entrySet())
		{
			out = out + drink.getKey() + ":" + drink.getValue().toString() + "\n";
		}
		sui.setDrinkList(out);
		sui.cfgResDrinksOut.setText(out);
	}
	public void updateFoodList()
	{
		String out="";
		for (Map.Entry<String, Double> food : model.getExtraFoodsMap().entrySet())
		{
			out = out + food.getKey() + ":" + food.getValue().toString() + "\n";
		}
		sui.setFoodsList(out);
		sui.cfgResFoodsOut.setText(out);
	}
}
