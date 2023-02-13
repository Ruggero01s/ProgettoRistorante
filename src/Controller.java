import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;

public class Controller
{
	static Model model = new Model();
	GUI gui = new GUI(this);
	
	public void init()
	{
		gui.init();
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
				break;
			case "thematicMenu.xml":
				model.getThematicMenusSet().clear();
				Writer.writeThematicMenu(model.getThematicMenusSet());
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
			String inputCapacity = gui.cfgInputArea1.getText();
			String inputWorkload = gui.cfgInputArea2.getText();

			int capacity = Integer.parseInt(inputCapacity);
			int workload= Integer.parseInt(inputWorkload);

			if(capacity<= 0 || workload<= 0)
				gui.errorSetter("minZero");
			else
			{
				model.setCapacity(capacity);
				model.setWorkPersonLoad(workload);
				gui.resetInputAreas();
			}
		}
		catch (NumberFormatException e)
		{
			gui.errorSetter("NumberFormatException");
		}
		
	}
	
	public void saveDrinks()
	{
		try
		{
			String inputName = gui.cfgInputArea1.getText();
			String inputQuantityPerson = gui.cfgInputArea2.getText();

			double quantity = Double.parseDouble(inputQuantityPerson);

			if(quantity<= 0)
				gui.errorSetter("minZero");
			else
			{
				model.getDrinksMap().put(inputName, quantity);
				gui.resetInputAreas();
			}
		}
		catch (NumberFormatException e)
		{
			gui.errorSetter("NumberFormatException");
		}
	}
	
	public void saveFoods(){
		try
		{
			String inputName = gui.cfgInputArea1.getText();
			String inputQuantityPerson = gui.cfgInputArea2.getText();

			double quantity =Double.parseDouble(inputQuantityPerson);

			if(quantity<= 0)
				gui.errorSetter("minZero");
			else
			{
				model.getExtraFoodsMap().put(inputName, Double.parseDouble(inputQuantityPerson));
				gui.resetInputAreas();
			}
		}
		catch (NumberFormatException e)
		{
			gui.errorSetter("NumberFormatException");
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
			String inputName = gui.cfgInputArea1.getText();
			String inputIngredients = gui.cfgInputArea3.getText();
			String inputPortions = gui.cfgInputArea2.getText();
			String inputWorkload = gui.cfgInputArea4.getText();
			
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
				gui.errorSetter("minZero");
			else
			{
				model.getRecipesSet().add(new Recipe(inputName, ingredientQuantityMap, portions, workLoad));
				gui.resetInputAreas();
			}
		}
		catch (NumberFormatException e)
		{
			gui.errorSetter("NumberFormatException");
		}
	}
    
    public void saveDish() {
		try
		{
			String inputName = gui.cfgInputArea1.getText();
			String inputIngredients = gui.cfgInputArea2.getText();
			
			String inputStartDate = gui.cfgInputArea3.getText();
			String inputEndDate = gui.cfgInputArea4.getText();

			if (gui.cfgDishPermanentRadio.isSelected())
			{
				inputStartDate = "01/01";
				inputEndDate = "31/12";
			}
			if(!checkDate(inputStartDate) || !checkDate(inputEndDate)) {
				gui.errorSetter("invalidDate");
				return;
			}
				boolean found = false;
				for (Recipe r : model.getRecipesSet()) {
					found = false;
					if (r.getId().equals(inputIngredients)) {
						model.getDishesSet().add(new Dish(inputName, r, inputStartDate, inputEndDate));
						found = true;
						break;
					}
				}
				if (!found)
					gui.errorSetter("noRecipe");
				else
					gui.resetInputAreas();

		}catch (ParseException e)
		{
			gui.errorSetter("invalidDate");
		}
	}

	public void saveMenu() throws ParseException
	{
		String inputName = gui.cfgInputArea1.getText();
		String inputs = gui.cfgInputArea2.getText();

		String[] inputList = inputs.split("\n");

		String inputStartDate = gui.cfgInputArea3.getText();
		String inputEndDate = gui.cfgInputArea4.getText();

		ArrayList<Dish> dishesForMenu = new ArrayList<>();

		if (gui.cfgMenuPermanentRadio.isSelected())
		{
			inputStartDate = "01/01";
			inputEndDate = "31/12";
		}
		if(!checkDate(inputStartDate) || !checkDate(inputEndDate)) {
			gui.errorSetter("invalidDate");
			return;
		}

		boolean found=false;
		for (String s: inputList)
		{
			found = false;
			for (Dish d : model.getDishesSet())
			{
				if (d.getName().equals(s))
				{
					dishesForMenu.add(d);
					found=true;
					break;
				}
			}
		}
		gui.resetInputAreas();
		if(!found)
			gui.errorSetter("noDish");
		else
		{
				model.getThematicMenusSet().add(new ThematicMenu(inputName, inputStartDate, inputEndDate, dishesForMenu));
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
}
