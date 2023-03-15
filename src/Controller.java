import javax.swing.*;
import java.util.*;

public class Controller
{
	Model model = new Model();
	SimpleUI sui;
	Reader reader = new Reader(this);
	
	/**
	 * metodo di inizializzazione
	 */
	public void init()
	{
		sui = new SimpleUI(this);
		loadModel();
		sui.init();
		generateGroceryList();
		generateAfterMeal();
	}
	
	/**
	 * Inizializzazione del model tramite i reader
	 */
	private void loadModel()
	{
		// Chiamo tutti i reader per leggere i dati salvati
		model.setUsers(reader.readPeople());
		reader.readConfig(model);
		model.setDrinksMap(reader.readDrinks());
		model.setExtraFoodsMap(reader.readExtraFoods());
		model.setRecipesSet(reader.readRecipes());
		model.setDishesSet(reader.readDishes());
		model.setThematicMenusSet(reader.readThematicMenu());
		model.setBookingMap(reader.readBooking());
		model.setRegistro(reader.readRegister());
		//aggiorno le varie stringhe per la GUI
		updateRecipeStringList();
		updateDishStringList();
		updateDrinkList();
		updateFoodList();
		updateMenuOut();
		updateMenuBoxes();
		menu();
		updateConfig();
	}
	
	/**
	 * Metodo che aggiorna la pagina dei config
	 */
	private void updateConfig()
	{
		sui.cfgResBaseOut.setText("Capacità: " + model.getCapacity() + "\n" + "IndividualWorkload: " +
				model.getWorkPersonLoad() + "\n" + "Restaurant Worlkload: " + model.getWorkResturantLoad() + "\n"
				+ "Data odierna: " + model.getToday().getStringDate() + "Surplus %: " + model.getIncrement());
		sui.cfgBaseInputCap.setText(Integer.toString(model.getCapacity()));
		sui.cfgBaseInputIndWork.setText(Integer.toString(model.getWorkPersonLoad()));
		sui.cfgBaseInputDate.setText(model.getToday().getStringDate());
		sui.cfgBaseInputSurplus.setText(Integer.toString(model.getIncrement()));
	}
	
	/**
	 * converte la data di oggi in una stringa
	 * @return data di oggi convertita a stringa
	 */
	public String getTodayString()
	{
		return model.getToday().getStringDate();
	}
	
	/**
	 * metodo che svuota la memoria
	 * @param name campo che va inizializzato
	 */
	public void clearInfo(String name)
	{
		switch (name)
		{
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
			case "config.xml":
				model.setCapacity(0);
				model.setWorkPersonLoad(0);
				model.setIncrement(5);
				model.setToday(new DateOur("01", "01", "1444"));
				
				Writer.writeConfigBase(model.getCapacity(), model.getWorkPersonLoad(), model.getToday(), model.getIncrement());
				sui.cfgResBaseOut.setText("""
						Capacità: 0
						IndividualWorkload: 0
						Restaurant Workload: 0
						Data odierna: 01/01/1444
						Surplus %: 5""");
				
				sui.cfgBaseInputCap.setText(Integer.toString(0));
				sui.cfgBaseInputIndWork.setText(Integer.toString(0));
				clearInfo("bookings");
			case "recipes.xml":
				model.getRecipesSet().clear();
				Writer.writeRecipes(model.getRecipesSet());
				updateRecipeStringList();    //se cancello le ricette devo cancellare anche i piatti, i menu ed i bookings
			case "dishes.xml":
				model.getDishesSet().clear();
				Writer.writeDishes(model.getDishesSet());
				updateDishStringList();
				menu(); //todo spero sia giusto
			case "thematicMenus.xml":
				model.getThematicMenusSet().clear();
				Writer.writeThematicMenu(model.getThematicMenusSet());
				updateMenuOut();
				updateMenuBoxes();
			case "bookings":
				model.getBookingMap().clear();
				writeBookings();
				break;
			//todo clear del magazzino
		}
	}
	
	/**
	 * Salvataggio tramite writer di tutti i dati del manager
	 */
	public void writeManager()
	{
		Writer.writeDrinks(model.getDrinksMap());
		Writer.writeExtraFoods(model.getExtraFoodsMap());
		Writer.writeConfigBase(model.getCapacity(), model.getWorkPersonLoad(), model.getToday(), model.getIncrement());
		Writer.writeRecipes(model.getRecipesSet());
		Writer.writeDishes(model.getDishesSet());
		Writer.writeThematicMenu(model.getThematicMenusSet());
	}
	
	/**
	 * Metodo che legge e salva i config dalla GUI
	 */
	public void saveConfig()
	{
		try
		{
			String inputCapacity = sui.cfgBaseInputCap.getText();
			String inputWorkload = sui.cfgBaseInputIndWork.getText();
			String inputPercent = sui.cfgBaseInputSurplus.getText();
			String todayString = sui.cfgBaseInputDate.getText().trim();
			int capacity = Integer.parseInt(inputCapacity); //converto i parametri numerici
			int workload = Integer.parseInt(inputWorkload);
			int percent = Integer.parseInt(inputPercent);
			
			if (!checkDate(todayString)) //check per correttezza della data
				sui.errorSetter("invalidDate");
			else
			{
				DateOur today = inputToDate(todayString);
				if (capacity <= 0 || workload <= 0) //check parametri numerici
					sui.errorSetter("minZero");
				else if (percent > 10)
					sui.errorSetter("surplusTooGreat");
				else
				{
					//salvo tutti i dati ed aggiorno la GUI
					model.setCapacity(capacity);
					model.setWorkPersonLoad(workload);
					model.setToday(today);
					model.setIncrement(percent);
					sui.cfgResBaseOut.setText("Capacità: " + model.getCapacity() + "\n" + "IndividualWorkload: " +
							model.getWorkPersonLoad() + "\n" + "Restaurant Worlkload: " + model.getWorkResturantLoad() +
							"\n" + "Data odierna: " + model.getToday().getStringDate() + "Surplus %: " + model.getIncrement());
				}
			}
		}
		catch (NumberFormatException e)
		{
			sui.errorSetter("NumberFormatException");
		}
	}
	
	/**
	 * Metodo che legge e salva i drinks
	 */
	public void saveDrinks()
	{
		try
		{
			String input = sui.cfgDrinksInput.getText();
			if (!input.contains(":")) //controllo il formato della stringa
				throw new RuntimeException("");
			
			String[] inputSplit = input.split(":");
			
			if (inputSplit[0].isBlank())
				throw new RuntimeException(""); //nome non valido
			if (inputSplit.length < 2)
				throw new RuntimeException("");
			
			double quantity = Double.parseDouble(inputSplit[1]);
			
			if (quantity <= 0) //quantità non valida
				sui.errorSetter("minZero");
			else
			{
				model.getDrinksMap().put(inputSplit[0], quantity);
				sui.cfgDrinksInput.setText("");
				updateDrinkList();
			}
		}
		catch (RuntimeException e)
		{
			sui.errorSetter("NumberFormatException");
		}
	}
	
	/**
	 * Metodo che legge e salva i foods aggiuntivi
	 */
	public void saveFoods()
	{
		try
		{
			String input = sui.cfgDrinksInput.getText();
			
			if (!input.contains(":")) //controllo il formato della stringa
				throw new RuntimeException("");
			
			String[] inputSplit = input.split(":");
			
			if (inputSplit.length < 2) //controllo il formato della stringa
				throw new RuntimeException("");
			if (inputSplit[0].isBlank()) //controllo la validità del nome
				throw new RuntimeException("");
			double quantity = Double.parseDouble(inputSplit[1]);
			
			if (quantity <= 0) //controllo che la quantità sia > 0
				sui.errorSetter("minZero");
			else
			{
				model.getExtraFoodsMap().put(inputSplit[0], quantity);
				sui.cfgFoodsInput.setText("");
				updateFoodList();
			}
		}
		catch (RuntimeException e)
		{
			sui.errorSetter("NumberFormatException");
		}
	}
	
	/**
	 * Metodo che legge e salva le ricette
	 */
	public void saveRecipe()
	{
		try
		{
			String inputName = sui.cfgRecipeNameInput.getText();
			String inputIngredients = sui.cfgRecipeIngredientsInput.getText();
			String inputPortions = sui.cfgRecipePortionsInput.getText();
			String inputWorkload = sui.cfgRecipeWorkLoadInput.getText();
			
			Set<Ingredient> ingredientQuantitySet = new HashSet<>();
			String[] lines = inputIngredients.split("\n");
			
			if (inputName.isBlank()) //controllo validità del nome
				throw new RuntimeException("");
			
			boolean err = false;
			for (String line : lines)
			{
				if (!line.contains(":")) //controllo validità della stringa
					throw new RuntimeException("");
				
				String[] words = line.split(":");
				
				if (words[0].isBlank()) //controllo validità del nome
					throw new RuntimeException("");
				
				if (words.length < 3) //controllo lunghezza stringa
					throw new RuntimeException("");
				
				double quantity = Double.parseDouble(words[1]);
				quantity = checkUnit(words[2], quantity);
				String unit;
				
				if (quantity <= 0) //controllo che la quantità sia > 0
				{
					err = true;
					break;
				}
				
				if (words[2].toLowerCase().contains("g"))
					unit = "g";
				else
					unit = "L";
				ingredientQuantitySet.add(new Ingredient(words[0], unit, quantity)); //converto tutti in g o L
			}
			
			int portions = Integer.parseInt(inputPortions);
			double workLoad = Double.parseDouble(inputWorkload);
			if (workLoad >= model.getWorkPersonLoad())
				sui.errorSetter("workloadTooHigh");
			
			if (portions <= 0 || workLoad <= 0 || err)
				sui.errorSetter("minZero");
			else
			{
				if (model.getRecipesSet().add(new Recipe(inputName, ingredientQuantitySet, portions, workLoad)))
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
	
	/**
	 *Converto e checko la validità delle unità
	 * @param unit unita d'ingresso
	 * @param quantity quantità da convertire
	 * @return quantità convertita
	 */
	private double checkUnit(String unit, Double quantity)
	{
		if (!(unit.equalsIgnoreCase("g") || unit.equalsIgnoreCase("l"))) //converto solo se l'unità non è grammi o litri
		{
			switch (unit.toLowerCase())
			{
				case "kg", "kl" -> quantity *= 1000.0;
				case "hg", "hl" -> quantity *= 100.0;
				case "dag", "dal" -> quantity *= 10.0;
				case "dg", "dl" -> quantity /= 10.0;
				case "cg", "cl" -> quantity /= 100.0;
				case "mg", "ml" -> quantity /= 1000.0;
				default -> throw new RuntimeException(""); //in caso di unità non riconosciuta lancio un errore
			}
		}
		return quantity;
	}
	
	/**
	 * Leggo e salvo i piatti
	 */
	public void saveDish()
	{
		try
		{
			String inputName = sui.cfgDishNameInput.getText();
			
			if (inputName.isBlank()) //controllo validità del nome
				throw new RuntimeException("");
			
			String inputRecipe = Objects.requireNonNull(sui.cfgDishComboBox.getSelectedItem()).toString().split("-")[0].trim();
			
			String inputStartDate = sui.cfgDishSDateInput.getText();
			String inputEndDate = sui.cfgDishEDateInput.getText();
			boolean perm = sui.cfgDishPermanentRadio.isSelected();
			boolean seasonal = sui.cfgDishSeasonalRadio.isSelected();
			
			if (!perm) // se non è permanente controllo le date
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
			boolean found = false;
			for (Recipe r : model.getRecipesSet())
			{
				if (r.getId().equals(inputRecipe))
				{
					if (model.getDishesSet().add(new Dish(inputName, r, inputStartDate, inputEndDate, seasonal, perm))) //associo il piatto alla ricetta
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
			menu(); //aggiorno il menu
		}
		catch (RuntimeException e)
		{
			sui.errorSetter("NumberFormatException");
		}
	}
	
	/**
	 * Leggo e salvo i menu tematici
	 */
	public void saveMenu()
	{
		try
		{
			String inputName = sui.cfgMenuNameInput.getText();
			String inputs = sui.cfgMenuDishesInput.getText();
			
			if (inputName.isBlank()) //controllo validità del nome
				throw new RuntimeException("");
			
			String[] inputList = inputs.split("\n");
			
			String inputStartDate = sui.cfgMenuSDateInput.getText();
			String inputEndDate = sui.cfgMenuEDateInput.getText();
			
			boolean permanent = sui.cfgMenuPermanentRadio.isSelected();
			boolean seasonal = sui.cfgMenuSeasonalRadio.isSelected();
			
			ArrayList<Dish> dishesForMenu = new ArrayList<>();
			
			if (!permanent) //se non è permanente controllo la validità delle date
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
			boolean found = false;
			for (String s : inputList)
			{
				found = false;
				if (!s.isBlank())
				{
					for (Dish d : model.getDishesSet())
					{
						if (d.getName().equals(s))
						{
							dishesForMenu.add(d); //aggiungo i piatti al menu
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
			if (!found) //todo cosa succede se il secondo dish non è valido
				sui.errorSetter("noDish");
			else
			{
				ThematicMenu temp = new ThematicMenu(inputName, inputStartDate, inputEndDate, dishesForMenu, seasonal, permanent); //creo il menu
				if (temp.getWorkThematicMenuLoad() <= ((double) model.getWorkPersonLoad() * 4 / 3)) //controllo se il workLoad è giusto
				{
					boolean valid = true;
					for (String s : sui.dishString)
					{
						if (s.equals(temp.getName())) //controllo che non esistano un piatto ed un meno con lo stesso nome
						{
							valid = false;
							break;
						}
					}
					if (valid)
					{
						if (model.getThematicMenusSet().add(temp)) //aggiorno la GUI
						{
							updateMenuOut();
							updateMenuBoxes();
						}
						else
							sui.errorSetter("existingName");
					}
					else
						sui.errorSetter("sameNameAsDish");
				}
				else
					sui.errorSetter("thiccMenu");
			}
		}
		catch (RuntimeException e)
		{
			sui.errorSetter("NumberFormatException");
		}
	}
	
	/**
	 * Controllo se una data è valida
	 * @param s data da controllare
	 * @return true se valida, false altrimenti
	 */
	public static boolean checkDate(String s)
	{
		if (s.isBlank()) //se è vuota non è valida
			return false;
		if (!s.contains("/")) //se non contiene / non è valida
			return false;
		String[] pezzi = s.split("/");
		if (pezzi.length < 3) //se non ha 3 elementi non è valida
			return false;
		if (Integer.parseInt(pezzi[2]) <= 0) //se anno minore di 0 errore
			return false;
		return switch (Integer.parseInt(pezzi[1])) //controllo mesi e giorni
		{
			case 1, 3, 5, 7, 8, 10, 12 -> (Integer.parseInt(pezzi[0]) <= 31 || Integer.parseInt(pezzi[0]) > 0);
			case 2 -> (Integer.parseInt(pezzi[0]) <= 29 || Integer.parseInt(pezzi[0]) > 0);
			case 4, 6, 9, 11 -> (Integer.parseInt(pezzi[0]) <= 30 || Integer.parseInt(pezzi[0]) > 0);
			default ->  false;
		};
	}
	
	public ArrayList<Dish> stringListToDishList(ArrayList<String> list)
	{
		ArrayList<Dish> dishes = new ArrayList<>();
		for (String s : list)
		{
			for (Dish d : model.getDishesSet())
			{
				if (d.getName().equals(s))
					dishes.add(d);
			}
		}
		return dishes;
	}
	
	public void updateMenuOut()
	{
		StringBuilder out = new StringBuilder();
		for (ThematicMenu m : model.getThematicMenusSet())
		{
			out.append(m.getName()).append(" - [").append(m.getStartPeriod().getStringDate()).append(" || ").append(m.getStartPeriod().getStringDate()).append("] - w.").append(m.getWorkThematicMenuLoad()).append(" \n");
		}
		sui.cfgResMenuOut.setText(out.toString().trim());
		sui.setMenuList(out.toString().trim());
	}
	
	public Recipe stringToRecipe(String id)
	{
		for (Recipe r : model.getRecipesSet())
		{
			if (r.getId().equals(id))
				return r;
		}
		return null; //non dovrebbe succedere
	}
	
	public void updateRecipeStringList()
	{
		
		if (model.getRecipesSet().isEmpty() || (model.getRecipesSet() == null))
		{
			String[] noRecipe = {"Non ci sono ricette inserite"};
			DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(noRecipe);
			sui.cfgDishComboBox.setModel(model);
			sui.cfgResRecipesOut.setText(noRecipe[0]);
			sui.setRecipeList(noRecipe[0]);
		}
		else
		{
			String[] recipes = new String[model.getRecipesSet().size()];
			int i = 0;
			for (Recipe recipe : model.getRecipesSet())
			{
				recipes[i] = (recipe.getId() + " - " + "[" + recipe.getIngredientsList() + "] - p." + recipe.getPortions() + " - w." + recipe.getWorkLoadPortion());
				i++;
			}
			DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(recipes);
			sui.cfgDishComboBox.setModel(model);
			
			StringBuilder compactedArray = new StringBuilder();
			for (String s : recipes)
			{
				compactedArray.append(s).append("\n");
			}
			sui.cfgResRecipesOut.setText(compactedArray.toString().trim());
			sui.setRecipeList(compactedArray.toString().trim());
		}
	}
	
	//TODO fare i metodi con parametri che vengono da sui
	public void updateDishStringList()
	{
		String[] dishes = new String[model.getDishesSet().size()];
		int i = 0;
		for (Dish d : model.getDishesSet())
		{
			dishes[i] = (d.getName() + " - [" + d.getStartPeriod().getStringDate() + " || " + d.getEndPeriod().getStringDate() + "] - " + "(" + d.getRecipe().getId() + ")");
			i++;
		}
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(dishes);
		sui.cfgMenuComboBox.setModel(model);
		
		StringBuilder compactedArray = new StringBuilder();
		for (String s : dishes)
		{
			compactedArray.append(s).append("\n");
		}
		sui.cfgResDishesOut.setText(compactedArray.toString().trim());
		sui.setDishList(compactedArray.toString().trim());
	}
	
	public void updateDrinkList()
	{
		StringBuilder out = new StringBuilder();
		for (Map.Entry<String, Double> drink : model.getDrinksMap().entrySet())
		{
			out.append(drink.getKey()).append(":").append(drink.getValue().toString()).append("\n");
		}
		sui.setDrinkList(out.toString().trim());
		sui.cfgResDrinksOut.setText(out.toString().trim());
	}
	
	public void updateFoodList()
	{
		StringBuilder out = new StringBuilder();
		for (Map.Entry<String, Double> food : model.getExtraFoodsMap().entrySet())
		{
			out.append(food.getKey()).append(":").append(food.getValue().toString()).append("\n");
		}
		sui.setFoodsList(out.toString().trim());
		sui.cfgResFoodsOut.setText(out.toString().trim());
	}
	
	public void updateMenuBoxes()
	{
		String[] out = makeMenuList();
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(out);
		sui.empNewBookMenuBox.setModel(model);
		sui.cfgResDatiMenuBox.setModel(model);
	}
	
	public void writeMenuComp(String menuName)
	{
		StringBuilder out = new StringBuilder();
		for (ThematicMenu menu : model.getThematicMenusSet())
		{
			if (menu.getName().equals(menuName))
			{
				out.append(menuName).append(" w.").append(menu.getWorkThematicMenuLoad()).append("\n");
				for (Dish d : menu.getDishes())
				{
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
		for (ThematicMenu m : model.getThematicMenusSet())
		{
			out[i] = m.getName();
			i++;
		}
		return out;
	}
	
	
	public DateOur inputToDate(String input)
	{
		String[] bookDates;
		try
		{
			bookDates = input.split("/");
			return new DateOur(bookDates[0], bookDates[1], bookDates[2]);
		}
		catch (Exception e)
		{
			sui.errorSetter("invalidDate");
		}
		return null;
	}
	
	public HashMap<Dish, Integer> inputToOrder(String in, DateOur date, int number)
	{
		String[] lines = in.split("\n");
		HashMap<Dish, Integer> order = new HashMap<>();
		boolean found;
		int count = 0;
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
							count += num;
							for (Dish dish : model.getDishesSet())
							{
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
							if (dish.isValid(date))
							{
								found = true;
								count += num;
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
			if (count < number)
			{
				order.clear();
				sui.errorSetter("orderForTooLittle");
			}
			return order;
		}
		catch (NumberFormatException e)
		{
			sui.errorSetter("noQuantity");
			return new HashMap<>();
		}
	}
	
	public void seeBookings(DateOur data)
	{
		if (model.getBookingMap().containsKey(data))
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
				work.append(b.getWorkload()).append("\n");
				capacity += b.getNumber();
				workload += b.getWorkload();
			}
			sui.empSeeBookNameAreaOut.setText(name.toString().trim());
			sui.empSeeBookNumAreaOut.setText(number.toString().trim());
			sui.empSeeBookWorkloadAreaOut.setText(work.toString().trim());
			sui.empSeeBookCapacityTotalOut.setText(Integer.toString(model.getCapacity() - capacity));
			sui.empSeeBookWorkloadTotalOut.setText(Double.toString(model.getWorkResturantLoad() - workload));
		}
		else
			sui.errorSetter("noBookings");
	}
	
	public void saveBooking()
	{
		try
		{
			String name = sui.empNewBookNameInput.getText();
			DateOur date = inputToDate(sui.empNewBookDateInput.getText());
			if (date != null && date.getDate().after(model.getToday().getDate()))
			{
				int number = Integer.parseInt(sui.empNewBookNumInput.getText()), workload = 0;
				HashMap<Dish, Integer> order = inputToOrder(sui.empNewBookOrderInput.getText(), date, number);
				if (!order.isEmpty())
				{
					if (number > 0)
					{
						for (Map.Entry<Dish, Integer> dish : order.entrySet())
							workload += dish.getKey().getRecipe().getWorkLoadPortion() * dish.getValue();
						if (manageBooking(name, date, number, workload, order))
						{
							sui.empNewBookNameInput.setText(Model.CLEAR);
							sui.empNewBookDateInput.setText(Model.CLEAR);
							sui.empNewBookNumInput.setText(Model.CLEAR);
							sui.empNewBookOrderInput.setText(Model.CLEAR);
						}
					}
					else
						sui.errorSetter("minZero");
				}
			}
			else sui.errorSetter("notToday");
		}
		catch (NumberFormatException e)
		{
			sui.errorSetter("NumberFormatException");
		}
	}
	
	/**
	 * Adds a new booking to the restaurant's booking system for the specified date, with the provided information.
	 * @param name     The name of the person making the booking.
	 * @param date     The date of the booking (must be of type DateOur).
	 * @param number   The number of guests in the booking.
	 * @param workload The expected workload for the restaurant staff for the booking.
	 * @param order    A HashMap of the dishes and the quantities of each that the guests will order.
	 * @return true if the booking was successfully added, false otherwise (for example, if the restaurant is already at capacity or the workload is too high).
	 */
	public boolean manageBooking(String name, DateOur date, int number, int workload, HashMap<Dish, Integer> order)
	{
		// Create a new ArrayList to hold the bookings for the specified date
		ArrayList<Booking> day = new ArrayList<>();
		// Set the initial capacity and workload based on the inputs
		int capacity = number;
		int work = workload;
		
		// Retrieve the bookings for the specified date, if they exist
		// If there is capacity and workload available, create a new Booking object and add it to the ArrayList for the date
		// Update the booking map with the ArrayList for the specified date
		// If there is no capacity or workload available, set an error message and return false
		if (model.getBookingMap().containsKey(date))
		{
			day = new ArrayList<>(model.getBookingMap().get(date));
			// If there are existing bookings for the date, calculate the total capacity and workload based on all bookings for the date
			for (Booking b : day)
			{
				capacity += b.getNumber();
				work += b.getWorkload();
			}
		}
		// Check if the restaurant has capacity and workload available to add the new booking
		if (capacity <= model.getCapacity() && work <= model.getWorkResturantLoad())
		{
			// If there is capacity and workload available, create a new Booking object and add it to the ArrayList for the date
			day.add(new Booking(name, number, workload, order));
			// Add the ArrayList to the booking map with the specified date
			model.getBookingMap().put(date, day);
			return true;
		}
		else
		{
			// If there is no capacity or workload available, set an error message and return false
			sui.errorSetter("fullRestaurant");
			return false;
		}
	}
	
	public HashMap<Dish, Integer> dishToMap(HashMap<String, Integer> map)
	{
		HashMap<Dish, Integer> out = new HashMap<>();
		for (Map.Entry<String, Integer> s : map.entrySet())
		{
			for (Dish dish : model.getDishesSet())
			{
				if (dish.getName().equals(s.getKey()))
				{
					out.put(dish, s.getValue());
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
	
	private void generateGroceryList()  //todo sta cosa va snellita
	{
		Set<Ingredient> grocerySet = new HashSet<>();
		HashMap<String, Double> surplusMap = new HashMap<>();
		
		double quantity, surplus = 0.0;
		int multi;
		Recipe recipe;
		
		if (model.getBookingMap().containsKey(model.getToday()))
		{
			HashMap<Dish, Integer> allDish = new HashMap<>();
			ArrayList<Booking> book = new ArrayList<>(model.getBookingMap().get(model.getToday()));
			Set<Ingredient> foods = new HashSet<>();
			Set<Ingredient> drinks = new HashSet<>();
			for (Booking b : book)
			{
				for (Map.Entry<String, Double> entry : model.getDrinksMap().entrySet())
				{
					Ingredient i = new Ingredient(entry.getKey(), "L", Math.ceil(entry.getValue() * b.getNumber()));
					if (drinks.contains(i))
					{
						for (Ingredient drink : drinks)
						{
							if (drink.equals(i))
							{
								drink.setQuantity(drink.getQuantity() + i.getQuantity());
								break;
							}
						}
					}
					else
					{
						drinks.add(i);
					}
				}
				for (Map.Entry<String, Double> entry : model.getExtraFoodsMap().entrySet())
				{
					Ingredient i = new Ingredient(entry.getKey(), "Hg", Math.ceil(entry.getValue() * b.getNumber()));
					if (foods.contains(i))
					{
						for (Ingredient food : foods)
						{
							if (food.equals(i))
							{
								food.setQuantity(food.getQuantity() + i.getQuantity());
								break;
							}
						}
					}
					else
					{
						foods.add(i);
					}
				}
				for (Map.Entry<Dish, Integer> entry : b.getOrder().entrySet())
				{
					Dish dish = entry.getKey();
					if (allDish.containsKey(dish))
						allDish.put(dish, entry.getValue() + allDish.get(dish));
					else
						allDish.put(dish, entry.getValue());
				}
			}
			
			grocerySet.addAll(foods);
			grocerySet.addAll(drinks);
			
			for (Map.Entry<Dish, Integer> entry : allDish.entrySet())
			{
				recipe = entry.getKey().getRecipe();
				
				for (Ingredient ingredient : recipe.getIngredients())
				{
					multi = entry.getValue() / recipe.getPortions();
					int resto = entry.getValue() % recipe.getPortions();
					
					if (resto != 0)
					{
						multi++;
						surplus = ingredient.getQuantity() / recipe.getPortions() * resto;
					}
					
					quantity = ingredient.getQuantity() * multi;
					
					if (grocerySet.contains(ingredient))
					{
						double delta = quantity;
						quantity += ingredient.getQuantity();
						delta = quantity - delta;
						if (delta < surplusMap.get(ingredient.getName()))
						{
							surplusMap.put(ingredient.getName(), (surplusMap.get(ingredient.getName()) - delta));
						}
						else
						{
							grocerySet.add(new Ingredient(ingredient.getName(), ingredient.getUnit(), quantity));
							surplusMap.put(ingredient.getName(), surplusMap.get(ingredient.getName()) + surplus);
						}
					}
					else
					{
						grocerySet.add(new Ingredient(ingredient.getName(), ingredient.getUnit(), quantity));
						surplusMap.put(ingredient.getName(), surplus);
					}
				}
			}
			
			for (Ingredient i : grocerySet)//incremento del X% ogni ingrediente
			{
				i.setQuantity(i.getQuantity() + i.getQuantity() * model.getIncrement() / 100.0);
			}
			
			grocerySet = new HashSet<>(compareWithRegister(grocerySet));
			
			
			Set<Ingredient> drink = new HashSet<>();
			Set<Ingredient> food = new HashSet<>();
			
			
			drink = new HashSet<>(compareWithRegister(drink));
			food = new HashSet<>(compareWithRegister(food));
			
			addToRegister(grocerySet);
			addToRegister(drink);
			addToRegister(food);
			
			sui.wareListOut.setText(groceriesToString(grocerySet, drink, food));
			sui.wareListMagOut.setText(setToString(model.getRegistro()));
		}
		else
		{
			sui.wareListOut.setText("Non essendoci prenotazioni per oggi la lista della spesa è vuota");
		}
		sui.wareListMagOut.setText(setToString(model.getRegistro()));
	}
	
	private Set<Ingredient> compareWithRegister(Set<Ingredient> set)
	{
		for (Ingredient reg : model.getRegistro())
		{
			for (Ingredient ingredient : set) //mannaggia al set che non ha un .get
			{
				if (reg.equals(ingredient))
				{
					double q = ingredient.getQuantity() - reg.getQuantity();
					if (q <= 0)
						set.remove(ingredient);
					else
						ingredient.setQuantity(q);
					break;
				}
			}
		}
		return set;
	}
	
	private void addToRegister(Set<Ingredient> set)
	{
		for (Ingredient ingredient : set)
		{
			if (model.getRegistro().contains(ingredient))
			{
				for (Ingredient reg : model.getRegistro())
				{
					if (reg.equals(ingredient))
					{
						model.getRegistro().add(new Ingredient(ingredient.getName(), ingredient.getUnit(), (ingredient.getQuantity()) + reg.getQuantity()));
						break;
					}
				}
			}
			else
				model.getRegistro().add(ingredient);
		}
	}
	
	private String groceriesToString(Set<Ingredient> ingredients, Set<Ingredient> drinks, Set<Ingredient> foods)
	{
		if (ingredients.isEmpty() && drinks.isEmpty() && foods.isEmpty())
			return "La lista della spesa è vuota perché tutti gli ingredienti necessari sono già in magazzino";
		
		String out;
		out = setToString(ingredients) + "\n";
		out += setToString(foods) + "\n";
		out += setToString(drinks);
		
		return out;
	}
	
	public String setToString(Set<Ingredient> set)
	{
		StringBuilder out = new StringBuilder();
		for (Ingredient entry : set)
		{
			out.append(entry.getName()).append(":").append(entry.getQuantity()).append(":").append(entry.getUnit()).append("\n");
		}
		return out.toString().trim();
	}
	
	public void warehouseChanges()
	{
		
		String text = sui.wareReturnListIn.getText().trim();
		if (text.isEmpty())
			sui.errorSetter("NumberFormatException");
		
		Set<Ingredient> ingredients = new HashSet<>();
		
		for (String s : text.split("\n"))
		{
			if (!s.contains(":"))
				sui.errorSetter("NumberFormatException");
			
			String[] t = s.split(":");
			
			if (t.length < 3 || t[0].isEmpty() || t[2].isEmpty())
				sui.errorSetter("NumberFormatException");
			
			String name = t[0], unit = t[2];
			double quantity = Double.parseDouble(t[1]);
			quantity = checkUnit(unit, quantity);
			
			if (unit.toLowerCase().contains("g"))
				unit = "g";
			else
				unit = "L";
			
			
			if (!ingredients.add(new Ingredient(name, unit, quantity)))
				sui.errorSetter("NumberFormatException");
		}
		try
		{
			updateAfterMeal(ingredients);
			sui.wareReturnListIn.setText("");
		}
		catch (RuntimeException e)
		{
			sui.errorSetter(e.getMessage());
		}
	}
	
	
	private void updateAfterMeal(Set<Ingredient> ingredients) throws RuntimeException
	{
		Set<Ingredient> register = new HashSet<>(model.getRegistroAfterMeal());
		for (Ingredient deltaIngredient : ingredients)
		{
			if (!model.getRegistro().contains(deltaIngredient))
				throw new RuntimeException("noIngredient");
			for (Ingredient regIngr : model.getRegistro())
			{
				if (regIngr.equals(deltaIngredient))
				{
					if (register.contains(deltaIngredient))
					{
						for (Ingredient ingredient : register)
						{
							if (ingredient.equals(deltaIngredient))
							{
								double newIngr = ingredient.getQuantity() + deltaIngredient.getQuantity();
								if (newIngr < 0)
									register.remove(ingredient);
								else
								{
									if (newIngr > regIngr.getQuantity())
										throw new RuntimeException("invalidQuantity");
									ingredient.setQuantity(newIngr);
									register.add(ingredient);
								}
								break;
							}
						}
					}
					else
					{
						if (deltaIngredient.getQuantity() >= 0 && deltaIngredient.getQuantity() <= regIngr.getQuantity())
							register.add(deltaIngredient);
						else
							throw new RuntimeException("invalidQuantity");
					}
				}
			}
		}
		model.setRegistroAfterMeal(register);
		sui.wareReturnListOut.setText(setToString(model.getRegistroAfterMeal()));
	}
	
	public void generateAfterMeal()
	{
		Set<Ingredient> registroNow = new HashSet<>(model.getRegistro());
		
		if (!model.getBookingMap().containsKey(model.getToday()))
		{
			sui.wareReturnListOut.setText(setToString(model.getRegistro()));
			return;
		}
		
		ArrayList<Booking> book = new ArrayList<>(model.getBookingMap().get(model.getToday()));
		for (Booking b : book)
			for (Map.Entry<Dish, Integer> entry : b.getOrder().entrySet())
				registroNow.removeAll(entry.getKey().getRecipe().getIngredients());
		
		for (Map.Entry<String, Double> drink : model.getDrinksMap().entrySet())
			registroNow.removeIf(ingredient -> ingredient.getName().equals(drink.getKey()));
		for (Map.Entry<String, Double> food : model.getExtraFoodsMap().entrySet())
			registroNow.removeIf(ingredient -> ingredient.getName().equals(food.getKey()));
		
		model.setRegistroAfterMeal(registroNow);
	}
	
	public void nextDay()
	{
		model.getToday().getDate().add(Calendar.DATE, 1);
		updateDishStringList();
		updateMenuOut();
		updateMenuBoxes();
		sui.cfgResBaseOut.setText("Capacità: " + model.getCapacity() + "\n" + "IndividualWorkload: " +
				model.getWorkPersonLoad() + "\n" + "Restaurant Worlkload: " + model.getWorkResturantLoad() + "\n"
				+ "Data odierna: " + model.getToday().getStringDate() + "Surplus %: " + model.getIncrement());
		sui.cfgBaseInputDate.setText(model.getToday().getStringDate());
		sui.wareListText.setText("Lista aggiornata al " + getTodayString());
		sui.wareListMagText.setText("Magazzino aggiornato al " + getTodayString());
		sui.wareReturnListOut.setText("");
		model.setRegistro(model.getRegistroAfterMeal());
		menu();
		oldBooking();
		generateGroceryList();
		generateAfterMeal();
	}
	
	private void oldBooking()
	{
		for (Map.Entry<DateOur, ArrayList<Booking>> entry : model.getBookingMap().entrySet())
		{
			if (entry.getKey().getDate().before(model.getToday()))
				model.getBookingMap().remove(entry.getKey());
		}
	}
	
	private void menu()
	{
		StringBuilder menus = new StringBuilder();
		for (Dish dish : model.getDishesSet())
			if (dish.isValid(model.getToday()))
				menus.append(dish.getName()).append("\n");
		if (menus.length() == 0)
			menus = new StringBuilder("Non ci sono piatti disponibili per la data ordierna");
		sui.cfgResMenuCartaOut.setText(menus.toString());
	}
	
	public void saveUser()
	{
		String name = sui.passSaveUserText.getText().trim();
		String password = Arrays.toString(sui.passSavePasswordField.getPassword()).trim();
        boolean manager = sui.passManCheck.isSelected(), employee = sui.passEmpCheck.isSelected(), storageWorker = sui.passWareCheck.isSelected();
        if(manager || employee || storageWorker)
        {
            if (!name.isBlank() && !password.isBlank()) {
                boolean doppler = false;
                if (model.getUsers().size() == 0) {
                    for (User user : model.getUsers()) {
                        if (user.getName().equals(name)) {
                            sui.errorSetter("invalidUsername");
                            doppler = true;
                            break;
                        }
                    }
                }
                if (!doppler) {
                    if (password.equals(Arrays.toString(sui.passSavePassword2Field.getPassword()).trim())) {
                        User user = new User(name, password, manager, employee, storageWorker);
                        user.hashAndSaltPassword();
                        model.getUsers().add(user);
                        Writer.writePeople(model.getUsers());
                        sui.passSaveUserText.setText(Model.CLEAR);
                        sui.passSavePasswordField.setText(Model.CLEAR);
                        sui.passSavePassword2Field.setText(Model.CLEAR);
                    } else
                        sui.errorSetter("passwordFailed");
                }
            } else
                sui.errorSetter("NumberFormatException");
        } else
            sui.errorSetter("notEnoughRoles");
	}
	
	public void login()
	{
		String name = sui.passLoginUserText.getText().trim();
		String password = Arrays.toString(sui.passLoginPasswordField.getPassword()).trim();
		if (!name.isEmpty() && !password.isEmpty())
		{
			boolean found = false;
			for (User user : model.getUsers())
			{
				if (user.getName().equals(name))
				{
					found = true;
					if (user.checkPassword(password))
					{
						sui.login();
						model.setTheUser(user);
						sui.passLoginPasswordField.setText(Model.CLEAR);
						sui.passLoginUserText.setText(Model.CLEAR);
					}
					else
						sui.errorSetter("passwordFailed");
					break;
				}
			}
			if (!found)
				sui.errorSetter("invalidUsername");
		}
		else
			sui.errorSetter("NumberFormatException");
	}
	
	/**
	 * Controlla se l'utente ha i permessi per accedere ad un certo ruolo
	 * @param role ruolo che devo controllare
	 * @return true se l'utente ha i permessi, false altrimenti
	 */
	public boolean checkPermission(String role)
	{
		return switch (role)
		{
			case "manager" -> model.getTheUser().isManager();
			case "employee" -> model.getTheUser().isEmployee();
			case "warehouse worker" -> model.getTheUser().isStorageWorker();
			default -> false;
		};
	}
}
