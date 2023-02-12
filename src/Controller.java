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
		model.drinksMap=Reader.readDrinks();
		model.extraFoodsMap = Reader.readExtraFoods();
		model.dishesSet = Reader.readDishes();
		for (Dish d: model.dishesSet)
		{
			model.recipesSet.add(d.getRecipe());
		}
		model.thematicMenusSet = Reader.readThematicMenu();

	}

	public void writeAll()
	{
		Writer.writeDrinks(model.drinksMap);
		Writer.writeExtraFoods(model.extraFoodsMap);
		Writer.writeConfigBase(model.capacity, model.workPersonLoad, model.workResturantLoad);
		Writer.writeDishes(model.dishesSet);
	}
	
	public void saveConfig()
	{
		try
		{
			String inputCapacity = gui.cfgInputArea1.getText();
			String inputWorkload = gui.cfgInputArea2.getText();

			int capacity = Integer.parseInt(inputCapacity);
			int workload=Integer.parseInt(inputWorkload);

			if(capacity<= 0 || workload<= 0)
				gui.errorSetter("minZero");
			else
			{
				model.setCapacity(capacity);
				model.setWorkPersonLoad(workload);
				gui.resetInputAreas();
			}
		}
		catch (NumberFormatException e)//todo gestire
		{
			System.out.println("errore, formato non valido");
			e.printStackTrace();
		}
		
	}
	
	public void saveDrinks()
	{
		try
		{
			String inputName = gui.cfgInputArea1.getText();
			String inputQuantityPerson = gui.cfgInputArea2.getText();
			
			model.drinksMap.put(inputName, Double.parseDouble(inputQuantityPerson));
			gui.resetInputAreas();
		}
		catch (NumberFormatException e)//todo gestire
		{
			System.out.println("errore, formato non valido");
			e.printStackTrace();
		}
	}
	
	public void saveFoods(){
		try
		{
			String inputName = gui.cfgInputArea1.getText();
			String inputQuantityPerson = gui.cfgInputArea2.getText();
			
			model.extraFoodsMap.put(inputName, Double.parseDouble(inputQuantityPerson));
			gui.resetInputAreas();
		}
		catch (NumberFormatException e)//todo gestire
		{
			System.out.println("errore, formato non valido");
			e.printStackTrace();
		}
	}
	
	public void printDrinks()
	{
		System.out.println(model.drinksMap);
	}
	
	public void printExtraFoods()
	{
		System.out.println(model.extraFoodsMap);
	}
	
	public void printRecipes()
	{
		System.out.println(model.recipesSet);
	}
	
	public void printDishes()
	{
		System.out.println(model.dishesSet);
	}
	
	public void saveRecipe()
	{
		try
		{
			String inputName = gui.cfgInputArea1.getText();
			String inputIngredients = gui.cfgInputArea3.getText();
			String inputPortions = gui.cfgInputArea2.getText();
			String inputWorkload = gui.cfgInputArea4.getText();
			
			HashMap<String, Double> ingredientQuantityMap = new HashMap<>();
			String[] lines = inputIngredients.split("\n");

			for (String line : lines)
			{
				String[] words = line.split(":");
				ingredientQuantityMap.put(words[0], Double.parseDouble(words[1]));
			}
			model.recipesSet.add(new Recipe(inputName, ingredientQuantityMap, Integer.parseInt(inputPortions), Double.parseDouble(inputWorkload)));
			gui.resetInputAreas();
		}
		catch (NumberFormatException e)//todo gestire
		{
			System.out.println("errore, formato non valido");
			e.printStackTrace();
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
			if(checkDate(inputStartDate) || checkDate(inputEndDate)) {
				gui.errorSetter("invalidDate");
				return;
			}
				boolean found = false;
				for (Recipe r : model.recipesSet) {
					found = false;
					if (r.getId().equals(inputIngredients)) {
						model.dishesSet.add(new Dish(inputName, r, inputStartDate, inputEndDate));
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
			System.out.println("errore, saveDish");
			e.printStackTrace();
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

		if (gui.cfgDishPermanentRadio.isSelected())
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
			for (Dish d : model.dishesSet)
			{
				if (d.getName().equals(s))
				{
					dishesForMenu.add(d);
					found=true;
					break;
				}
			}
		}
		if(!found)
			gui.errorSetter("noDish");
		else
		{
				model.thematicMenusSet.add(new ThematicMenu(inputName, inputStartDate, inputEndDate, dishesForMenu));
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

	public static ArrayList<Dish> stringListToDishList(ArrayList<String> lista){
		ArrayList<Dish> dishes = new ArrayList<>();
		for (String s: lista) {
			for (Dish d: model.dishesSet) {
				if (d.getName().equals(s))
					dishes.add(d);
			}
		}
		return dishes;
	}
}
