import java.util.*;

public class Controller
{
	private Model model = new Model();
	private SimpleUI sui;
	private Reader reader = new Reader(this);
	
	public Model getModel()
	{
		return model;
	}
	
	/**
	 * metodo di inizializzazione
	 */
	public void init()
	{
		sui = new SimpleUI(this);
		loadModel();
		gui.init(model.getToday().getStringDate());
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
		menuCartaToday();
		updateConfig();
	}
	
	/**
	 * Metodo che aggiorna la pagina dei config
	 */
	private void updateConfig()
	{
		String[] configState = new String[5];
		configState[0] = "Capacità: " + model.getCapacity() + "\n" + "IndividualWorkload: " +
				model.getWorkPersonLoad() + "\n" + "Restaurant Worlkload: " + model.getWorkResturantLoad() + "\n"
				+ "Data odierna: " + model.getToday().getStringDate() + "Surplus %: " + model.getIncrement();
		configState[1] = Integer.toString(model.getCapacity());
		configState[2] = Integer.toString(model.getWorkPersonLoad());
		configState[3] = model.getToday().getStringDate();
		configState[4] = Integer.toString(model.getIncrement());

		gui.updateConfig(List.of(configState));
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
				gui.cfgResBaseOut.setText("""
						Capacità: 0
						IndividualWorkload: 0
						Restaurant Workload: 0
						Data odierna: 01/01/1444
						Surplus %: 5""");
				
				gui.cfgBaseInputCap.setText(Integer.toString(0));
				gui.cfgBaseInputIndWork.setText(Integer.toString(0));
				clearInfo("bookings");
			case "recipes.xml":
				model.getRecipesSet().clear();
				Writer.writeRecipes(model.getRecipesSet());
				updateRecipeStringList();    //se cancello le ricette devo cancellare anche i piatti, i menu ed i bookings
			case "dishes.xml":
				model.getDishesSet().clear();
				Writer.writeDishes(model.getDishesSet());
				updateDishStringList();
				menuCartaToday(); //todo spero sia giusto
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
	public void saveConfig(String inputCapacity, String inputWorkload,String inputPercent,String todayString)
	{
		try
		{
			int capacity = Integer.parseInt(inputCapacity); //converto i parametri numerici
			int workload = Integer.parseInt(inputWorkload);
			int percent = Integer.parseInt(inputPercent);
			
			if (!checkDate(todayString)) //check per correttezza della data
			{
				erSet.errorSetter(SimpleUI.INVALID_DATE);
			}
			else
			{
				DateOur today = inputToDate(todayString);
				if (capacity <= 0 || workload <= 0) //check parametri numerici
					erSet.errorSetter(SimpleUI.MIN_ZERO);
				else if (percent > 10)
					erSet.errorSetter(SimpleUI.SURPLUS_TOO_GREAT);
				else
				{
					//salvo tutti i dati ed aggiorno la GUI
					model.setCapacity(capacity);
					model.setWorkPersonLoad(workload);
					model.setToday(today);
					model.setIncrement(percent);
					updateConfig();
				}
			}
		}
		catch (NumberFormatException e)
		{
			erSet.errorSetter(SimpleUI.INVALID_FORMAT);
		}
	}
	
	/**
	 * Metodo che legge e salva i drinks
	 */
	public void saveDrinks(String input)
	{
		try
		{
			if (!input.contains(":")) //controllo il formato della stringa
				throw new RuntimeException("");
			
			String[] inputSplit = input.split(":");
			
			if (inputSplit[0].isBlank())
				throw new RuntimeException(""); //nome non valido
			if (inputSplit.length < 2)
				throw new RuntimeException("");
			
			double quantity = Double.parseDouble(inputSplit[1]);
			
			if (quantity <= 0) //quantità non valida
				erSet.errorSetter(SimpleUI.MIN_ZERO);
			else
			{
				model.getDrinksMap().put(inputSplit[0], quantity);
				updateDrinkList();
			}
		}
		catch (RuntimeException e)
		{
			erSet.errorSetter(SimpleUI.INVALID_FORMAT);
		}
	}
	
	/**
	 * Metodo che legge e salva i foods aggiuntivi
	 */
	public void saveFoods(String input)
	{
		try
		{
			if (!input.contains(":")) //controllo il formato della stringa
				throw new RuntimeException("");
			
			String[] inputSplit = input.split(":");
			
			if (inputSplit.length < 2) //controllo il formato della stringa
				throw new RuntimeException("");
			if (inputSplit[0].isBlank()) //controllo la validità del nome
				throw new RuntimeException("");
			double quantity = Double.parseDouble(inputSplit[1]);
			
			if (quantity <= 0) //controllo che la quantità sia > 0
				erSet.errorSetter(SimpleUI.MIN_ZERO);
			else
			{
				model.getExtraFoodsMap().put(inputSplit[0], quantity);
				updateFoodList();
			}
		}
		catch (RuntimeException e)
		{
			erSet.errorSetter(SimpleUI.INVALID_FORMAT);
		}
	}
	
	/**
	 * Metodo che legge e salva le ricette
	 */
	public void saveRecipe(String inputName, String inputIngredients, String inputPortions,	String inputWorkload)
	{
		try
		{
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
				erSet.errorSetter(SimpleUI.WORKLOAD_TOO_HIGHT);
			
			if (portions <= 0 || workLoad <= 0 || err)
				erSet.errorSetter(SimpleUI.MIN_ZERO);
			else
			{
				if (model.getRecipesSet().add(new Recipe(inputName, ingredientQuantitySet, portions, workLoad)))
				{
					updateRecipeStringList();
				}
				else
					erSet.errorSetter(SimpleUI.EXISTING_NAME);
			}
		}
		catch (NumberFormatException e)
		{
			erSet.errorSetter(SimpleUI.INVALID_FORMAT);
		}
	}
	
	/**
	 * Converto e checko la validità delle unità
	 * @param unit     unita d'ingresso
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
	public void saveDish(String inputName, String inputRecipe, String inputStartDate, String inputEndDate, boolean perm)
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
					erSet.errorSetter(SimpleUI.INVALID_DATE);
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
					else //nel caso un menu esiste già con lo stesso nome (add torna false se non riesce ad aggiungerlo alla lista)
						erSet.errorSetter(SimpleUI.EXISTING_NAME);
				}
			}
			if (!found)
				erSet.errorSetter(SimpleUI.NO_RECIPE);
			menuCartaToday(); //aggiorno il menu
		}
		catch (RuntimeException e)
		{
			erSet.errorSetter(SimpleUI.INVALID_FORMAT);
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
					sui.errorSetter(SimpleUI.INVALID_DATE);
					return;
				}
			}
			else
			{
				inputStartDate = "01/01/1444";
				inputEndDate = "31/12/1444";
			}
			boolean found, dishNotFound=false;
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
				if (!found) // se non trova il piatto
					dishNotFound=true;
			}
			if (dishNotFound) //se almeno un piatto prima non è stato trovato da errore
				sui.errorSetter(SimpleUI.NO_DISH);
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
							sui.errorSetter(SimpleUI.EXISTING_NAME);
					}
					else
						sui.errorSetter(SimpleUI.NAME_SAME_AS_DISH);
				}
				else
					sui.errorSetter(SimpleUI.THICC_MENU);
			}
		}
		catch (RuntimeException e)
		{
			sui.errorSetter(SimpleUI.INSUFFICENT_DISH);
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
					default -> false;
				};
	}
	
	/**
	 * Metodo che riceve in ingresso una stringa
	 * contenente piatti e la trasforma in un array list
	 * @param list stringa di piatti
	 * @return Array list di piatti
	 */
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
	
	/**
	 * Metodo che aggiorna la GUI scrivendo in output
	 * tutti i menu tematici con i relativi dati
	 */
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
	
	/**
	 * Metodo che trasforma una stringa in una ricetta
	 * @param id ricetta in forma di stringa
	 * @return ricetta
	 */
	public Recipe stringToRecipe(String id)
	{
		for (Recipe r : model.getRecipesSet())
		{
			if (r.getId().equals(id))
				return r;
		}
		sui.errorSetter(9999); //errore generico
		return null; //non dovrebbe succedere
	}
	
	/**
	 * Metodo che serve per aggiornare le ricette nella GUI
	 */
	public void updateRecipeStringList()
	{
		String[] recipes = new String[model.getRecipesSet().size()]; //todo controllare se recipeSet esiste anche se vuoto
		if (model.getRecipesSet().isEmpty() || (model.getRecipesSet() == null)) //se le ricette sono vuote
		{
			gui.updateRecipes(recipes);
		}
		else //se c'è almeno una ricetta
		{
			int i = 0;
			for (Recipe recipe : model.getRecipesSet()) //trasformo le ricette in stringa
			{
				recipes[i] = (recipe.getId() + " - " + "[" + recipe.getIngredientsList() + "] - p." + recipe.getPortions() + " - w." + recipe.getWorkLoadPortion());
				i++;
			}
			gui.updateRecipes(recipes);
		}
	}
	
	/**
	 * Metodo che serve per aggiornare i piatti nella GUI
	 */
	public void updateDishStringList()
	{
		String[] dishes = new String[model.getDishesSet().size()];
		int i = 0;
		for (Dish d : model.getDishesSet()) //trasformo i dish in stringa
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
	
	/**
	 * Metodo che serve per aggiornare i drinks nella GUI
	 */
	public void updateDrinkList()
	{
		StringBuilder out = new StringBuilder();
		for (Map.Entry<String, Double> drink : model.getDrinksMap().entrySet()) //trasformo la map di drinks in stringa
		{
			out.append(drink.getKey()).append(":").append(drink.getValue().toString()).append("\n");
		}
		gui.updateDrinks(out.toString().trim());
	}
	
	/**
	 * Metodo che serve per aggiornare gli extra foods nella GUI
	 */
	public void updateFoodList()
	{
		StringBuilder out = new StringBuilder();
		for (Map.Entry<String, Double> food : model.getExtraFoodsMap().entrySet()) //trasformo la map di extra foods in stringa
		{
			out.append(food.getKey()).append(":").append(food.getValue().toString()).append("\n");
		}
		gui.updateFoods(out.toString().trim());
	}

	/**
	 * metodo per aggiornare le liste di menu nelle comboBox
	 */
	public void updateMenuBoxes()
	{
		String[] out = makeMenuList();
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(out);
		sui.empNewBookMenuBox.setModel(model);
		sui.cfgResDatiMenuBox.setModel(model);
	}

	/**
	 * crea una stringa con i dati di un certo menu con nome menuName
	 * @param menuName menu da trasformare in stringa
	 */
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
		gui.cfgResDatiMenuOut.setText(out.toString());
	}

	/**
	 * @return una stringa con un nome per ogni menu per riga
	 */
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
	
	/**
	 * Metodo che prende una string e la trasforma in data
	 * @param input stringa da convertire
	 * @return data costruita
	 */
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
			sui.errorSetter(SimpleUI.INVALID_DATE);
			return null;
		}
	}

	/**
	 * Metodo che prende dati dalla finestra di creazione ordine e ne estrae un ordine, analizza una stringa spezzandola in righe e cerca il nome del piatto/menu nei vari set esistenti
	 * @param in Stringa in entrata
	 * @param date Data per oggi, per controllare che il piatto trovato sia disponibile
	 * @param number numero del piatto/menu ordinato
	 * @return un HashMap che contiene per ogni Dish nell'ordine la quantità ordinata
	 */
	public HashMap<Dish, Integer> inputToOrder(String in, DateOur date, int number)
	{
		if(in.isBlank()) throw new RuntimeException();
		String[] lines = in.split("\n");
		HashMap<Dish, Integer> order = new HashMap<>();
		boolean found;
		int count = 0;
		try
		{
			for (String line : lines)  //per ogni linea
			{
				found = false;
				String[] parts = line.split(":");
				String name = parts[0];
				Integer num = Integer.parseInt(parts[1]);
				if (num <= 0) //errore se il num associato è <=0
				{
					order.clear();
					sui.errorSetter(SimpleUI.MIN_ZERO);
					return order;
				}
				for (ThematicMenu menu : model.getThematicMenusSet()) //cerca se il nome scritto è tra i menu tematici
				{
					if (name.equals(menu.getName()))
					{
						if (menu.isValid(date)) //controllo della data di disponibilità
						{
							found = true;
							count += num;
							for (Dish dish : menu.getDishes()) // "spacchetta" il menu nei piatti che lo compongono e li aggiunge all'ordine
							{
								if (order.containsKey(dish)) //sommando il numero se già presenti
									order.put(dish, order.get(dish) + num);
								else
									order.put(dish, num);
							}
							break;
						}
						else
						{
							order.clear();
							sui.errorSetter(SimpleUI.OUT_OF_DATE);
							return order;
						}
					}
				}
				if (!found) //se il nome non è tra i menu cerca i piatti singoli e fa lo stesso procedimento
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
								erSet.errorSetter(SimpleUI.INVALID_DATE);
								return order;
							}
						}
					}
				}
				if (!found) //se ancora non ha trovato una corrispondenza da errore
				{
					order.clear();
					erSet.errorSetter(SimpleUI.NOT_FOUND);
					return order;
				}
			}
			if (count < number) //controlla che ci sia almeno un piatto per persona
			{
				order.clear();
				erSet.errorSetter(SimpleUI.ORDER_FOR_TOO_LITTLE);
			}
			return order;
		}
		catch (NumberFormatException e) //catch l'errore del parseInt iniziale
		{
			erSet.errorSetter(SimpleUI.NO_QUANTITY);
			return new HashMap<>();
		}
		catch (RuntimeException e) //catch l'errore del parseInt iniziale
		{
			erSet.errorSetter(SimpleUI.EMPTY_INPUT);
			return new HashMap<>();
		}
	}
	
	/**
	 * Metodo che mostra in GUI tutte le prenotazioni di un giorno
	 * @param data giorno da controllare
	 */
	public void seeBookings(DateOur data)
	{
		if (model.getBookingMap().containsKey(data))
		{
			ArrayList<Booking> dayBookings = new ArrayList<>(model.getBookingMap().get(data)); //prenotazioni della data
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
			//elenco di stringhe da stampare in GUI
			gui.empSeeBookNameAreaOut.setText(name.toString().trim());
			gui.empSeeBookNumAreaOut.setText(number.toString().trim());
			gui.empSeeBookWorkloadAreaOut.setText(work.toString().trim());
			//capacity e workload rimanenti per quel giorno
			gui.empSeeBookCapacityTotalOut.setText(Integer.toString(model.getCapacity() - capacity));
			gui.empSeeBookWorkloadTotalOut.setText(Double.toString(model.getWorkResturantLoad() - workload));
		}
		else
			erSet.errorSetter(SimpleUI.NO_BOOKINGS); //non ci sono prenotazioni per quel giorno
	}
	
	/**
	 * Metodo che crea una prenotazione dai dati in GUI
	 */
	public void saveBooking()
	{
		try
		{
			String name = gui.empNewBookNameInput.getText();
			DateOur date = inputToDate(gui.empNewBookDateInput.getText());
			if (date != null && date.getDate().after(model.getToday().getDate())) //controllo che la data abbia un senso
			{
				int number = Integer.parseInt(gui.empNewBookNumInput.getText()), workload = 0;
				HashMap<Dish, Integer> order = inputToOrder(gui.empNewBookOrderInput.getText(), date, number);
				if (!order.isEmpty()) //todo mettiamo un errore se sta cosa è falsa?
				{
					if (number > 0)//il numero deve essere maggiore di 0
					{
						for (Map.Entry<Dish, Integer> dish : order.entrySet())
							workload += dish.getKey().getRecipe().getWorkLoadPortion() * dish.getValue(); //calcolo il workload di questo giorno
						if (manageBooking(name, date, number, workload, order)) //se la prenotazione viene salvata resetto tutto
						{
							gui.empNewBookNameInput.setText(Model.CLEAR);
							gui.empNewBookDateInput.setText(Model.CLEAR);
							gui.empNewBookNumInput.setText(Model.CLEAR);
							gui.empNewBookOrderInput.setText(Model.CLEAR);
						}
					}
					else
						erSet.errorSetter(SimpleUI.MIN_ZERO);
				}
			}
			else
				erSet.errorSetter(SimpleUI.NOT_TODAY);
		}
		catch (NumberFormatException e)
		{
			erSet.errorSetter(SimpleUI.INVALID_FORMAT);
		}
	}
	
	/**
	 * Aggiunge una prenotazione alle prenotazioni del ristorante
	 * @param name     nome della prenotazione
	 * @param date     data della prenotazione
	 * @param number   numero di persone
	 * @param workload workload della prenotazione
	 * @param order    hashmap di tutti i piatti ordinati
	 * @return true se la prenotazione è stata salvata, false altrimenti
	 */
	public boolean manageBooking(String name, DateOur date, int number, int workload, HashMap<Dish, Integer> order)
	{
		ArrayList<Booking> day = new ArrayList<>();
		int capacity = number;
		int work = workload;

		if (model.getBookingMap().containsKey(date))
		{
			//se ci sono altre prenotazioni per quel giorno
			//calcolo la capacità ed il workload occupati
			day = new ArrayList<>(model.getBookingMap().get(date));
			for (Booking b : day)
			{
				capacity += b.getNumber();
				work += b.getWorkload();
			}
		}
		//controllo se c`è posto nel ristorante
		if (capacity <= model.getCapacity() && work <= model.getWorkResturantLoad())
		{
			day.add(new Booking(name, number, workload, order));
			model.getBookingMap().put(date, day);
			return true;
		}
		else
		{
			erSet.errorSetter(SimpleUI.FULL_RESTURANT);
			return false;
		}
	}

	/**
	 * Metodo che trasforma una mappa di string ed integer in una
	 * mappa di dish ed integer
	 * @param map mappa di string ed integer
	 * @return mappa di dish ed integer
	 */
	public HashMap<Dish, Integer> dishToMap(HashMap<String, Integer> map)
	{
		HashMap<Dish, Integer> out = new HashMap<>();
		for (Map.Entry<String, Integer> s : map.entrySet())
		{
			for (Dish dish : model.getDishesSet())
			{
				if (dish.getName().equals(s.getKey()))
				{
					out.put(dish, s.getValue()); //quando la stringa coincide con un piatto aggiorno la mappa
					break;
				}
			}
		}
		return out;
	}
	
	/**
	 * Metodo che chiama il writer per le prenotazioni
	 */
	public void writeBookings()
	{
		Writer.writeBookings(model.getBookingMap());
	}
	
	/**
	 * Metodo che chiama il writer per il magazzino
	 */
	public void writeRegister()
	{
		Writer.writeRegister(model.getRegistro());
	}

	/**
	 * Metodo che genera la lista della spesa in base ai piatti da servire oggi
	 * ed in base al magazzino attuale
	 */
	private void generateGroceryList()  //todo sta cosa va snellita
	{
		Set<Ingredient> grocerySet = new HashSet<>();
		HashMap<String, Double> surplusMap = new HashMap<>();
		
		double quantity, surplus = 0.0;
		int multi;
		Recipe recipe;
		String groceryList,register;
		
		if (model.getBookingMap().containsKey(model.getToday()))
		{
			HashMap<Dish, Integer> allDish = new HashMap<>();
			ArrayList<Booking> book = new ArrayList<>(model.getBookingMap().get(model.getToday()));
			Set<Ingredient> foods = new HashSet<>();
			Set<Ingredient> drinks = new HashSet<>();
			for (Booking b : book)
			{
				for (Map.Entry<String, Double> entry : model.getDrinksMap().entrySet()) //aggiungo i drink alla lista della spesa
				{
					Ingredient i = new Ingredient(entry.getKey(), "L", Math.ceil(entry.getValue() * b.getNumber()));
					if (drinks.contains(i))
					{
						for (Ingredient drink : drinks)
						{
							if (drink.equals(i))
							{
								drink.setQuantity(drink.getQuantity() + i.getQuantity()); //aggiungo i drinks in caso esistano già nel set
								break;
							}
						}
					}
					else
						drinks.add(i); //aggiungo i drinks in caso non ci siano già
				}
				for (Map.Entry<String, Double> entry : model.getExtraFoodsMap().entrySet()) //aggiungo i foods alla lista della spesa
				{
					Ingredient i = new Ingredient(entry.getKey(), "Hg", Math.ceil(entry.getValue() * b.getNumber()));
					if (foods.contains(i))
					{
						for (Ingredient food : foods)
						{
							if (food.equals(i))
							{
								food.setQuantity(food.getQuantity() + i.getQuantity()); //aggiungo i drinks in caso esistano già nel set
								break;
							}
						}
					}
					else
						foods.add(i); //aggiungo i foods in caso non ci siano già
				}
				for (Map.Entry<Dish, Integer> entry : b.getOrder().entrySet()) //creo una mappa con tutti i piatti ordinati per oggi
				{
					Dish dish = entry.getKey();
					if (allDish.containsKey(dish))
						allDish.put(dish, entry.getValue() + allDish.get(dish));
					else
						allDish.put(dish, entry.getValue());
				}
			}

			//addo alla lista della spesa i foods e drinks calcolati prima
			grocerySet.addAll(foods);
			grocerySet.addAll(drinks);
			
			for (Map.Entry<Dish, Integer> entry : allDish.entrySet())
			{
				recipe = entry.getKey().getRecipe(); //scorro le ricette di ogni piatto
				
				for (Ingredient ingredient : recipe.getIngredients())
				{
					multi = entry.getValue() / recipe.getPortions(); // quante ricette devo cucinare per soddisfare la richiesta
					int resto = entry.getValue() % recipe.getPortions(); //quantità di cibo per arrotondare alla ricetta successiva
					
					if (resto != 0) //se ci sono avanzi
					{
						multi++;
						surplus = ingredient.getQuantity() / recipe.getPortions() * resto; //salvo il cibo avanzato
					}
					
					quantity = ingredient.getQuantity() * multi; //quantità di cibo da comprare
					
					if (grocerySet.contains(ingredient)) //se nella lista della spesa c'è già questo ingrediente
					{
						double delta = quantity;
						quantity += ingredient.getQuantity(); //todo testare e cambiare e commentare
						delta = quantity - delta;
						if (delta < surplusMap.get(ingredient.getName()))
							surplusMap.put(ingredient.getName(), (surplusMap.get(ingredient.getName()) - delta));
						else
						{
							grocerySet.add(new Ingredient(ingredient.getName(), ingredient.getUnit(), quantity));
							surplusMap.put(ingredient.getName(), surplusMap.get(ingredient.getName()) + surplus);
						}
					}
					else
					{
						grocerySet.add(new Ingredient(ingredient.getName(), ingredient.getUnit(), quantity)); //aggiungo ingrediente ed avanzi
						surplusMap.put(ingredient.getName(), surplus);
					}
				}
			}
			
			for (Ingredient i : grocerySet)//incremento del X% ogni ingrediente
			{
				i.setQuantity(i.getQuantity() + i.getQuantity() * model.getIncrement() / 100.0);
			}
			
			grocerySet = new HashSet<>(compareWithRegister(grocerySet)); //comparo la lista della spesa con ciò che ho in magazzino
			
			
			Set<Ingredient> drink = new HashSet<>();
			Set<Ingredient> food = new HashSet<>();
			
			
			drink = new HashSet<>(compareWithRegister(drink)); //controllo la lista dei drinks con il magazzino
			food = new HashSet<>(compareWithRegister(food)); //controllo la lista dei foods con il magazzino

			//aggiorno il magazzino
			addToRegister(grocerySet);
			addToRegister(drink);
			addToRegister(food);
			
			groceryList =groceriesToString(grocerySet, drink, food);
		}
		else
		{
			groceryList ="Non essendoci prenotazioni per oggi la lista della spesa è vuota"; //in caso non ci siano prenotazioni per oggi la lista della spesa è vuoto
		}
		register =setToString(model.getRegistro());
		gui.updateWare(groceryList,register);
	}

	/**
	 * Metodo che compara ed aggiorna la lista della spesa in base al magazzino
	 * @param set lista della spesa
	 * @return lista della spesa aggiornata
	 */
	private Set<Ingredient> compareWithRegister(Set<Ingredient> set)
	{
		for (Ingredient reg : model.getRegistro())
		{
			for (Ingredient ingredient : set)
			{
				if (reg.equals(ingredient))
				{
					double q = ingredient.getQuantity() - reg.getQuantity();
					if (q <= 0) //se in magazzino avevo una quantità più grande di quella che dovevo comprare non la compro
						set.remove(ingredient);
					else //se in magazzino avevo già degli avanzi diminuisco la quantità da comprare
						ingredient.setQuantity(q);
					break;
				}
			}
		}
		return set;
	}
	
	/**
	 * Metodo che prende in ingresso un set d'ingredienti e lo aggiunge al magazzino
	 * @param set ingredienti da aggiungere
	 */
	private void addToRegister(Set<Ingredient> set)
	{
		for (Ingredient ingredient : set)
		{
			if (model.getRegistro().contains(ingredient))
			{
				for (Ingredient reg : model.getRegistro())
				{
					if (reg.equals(ingredient)) //in caso l'ingrediente ci sia già nel magazzino sommo quello che devo aggiungere a auello che c'era già
					{
						model.getRegistro().add(new Ingredient(ingredient.getName(), ingredient.getUnit(), (ingredient.getQuantity()) + reg.getQuantity()));
						break;
					}
				}
			}
			else
				model.getRegistro().add(ingredient); //se non c'è già nel magazzino lo aggiungo e basta
		}
	}

	/**
	 * Metodo che trasforma i set della lista della spesa in stringa
	 * @param ingredients lista della spesa generale
	 * @param drinks lista dei drinks
	 * @param foods lista dei foods
	 * @return stringa di output
	 */
	private String groceriesToString(Set<Ingredient> ingredients, Set<Ingredient> drinks, Set<Ingredient> foods)
	{
		if (ingredients.isEmpty() && drinks.isEmpty() && foods.isEmpty()) // in caso sia tutto vuoto
			return "La lista della spesa è vuota perché tutti gli ingredienti necessari sono già in magazzino";
		
		String out;
		out = setToString(ingredients) + "\n";
		out += setToString(foods) + "\n";
		out += setToString(drinks);
		
		return out;
	}
	
	/**
	 * Metodo che converte set d'ingredienti in stringhe
	 * @param set set da convertire
	 * @return stringa del set
	 */
	public String setToString(Set<Ingredient> set)
	{
		StringBuilder out = new StringBuilder();
		for (Ingredient entry : set)
		{
			out.append(entry.getName()).append(":").append(entry.getQuantity()).append(":").append(entry.getUnit()).append("\n");
		}
		return out.toString().trim();
	}
	
	/**
	 * Metodo che dalla GUI crea una lista d'ingredienti da modificare nel magazzino
	 * @param text ingredienti da cambiare
	 * @return true se è andato a buon fine, false altrimenti
	 */
	public boolean warehouseChanges(String text)
	{
		if (text.isBlank()) //controllo che il testo non sia valido
			erSet.errorSetter(SimpleUI.INVALID_FORMAT);
		
		Set<Ingredient> ingredients = new HashSet<>();
		
		for (String s : text.split("\n"))
		{
			if (!s.contains(":")) //controllo il formato della riga
				erSet.errorSetter(SimpleUI.INVALID_FORMAT);
			
			String[] t = s.split(":");
			
			if (t.length < 3 || t[0].isBlank() || t[2].isBlank()) //controllo il formato della stringa splittata
				erSet.errorSetter(SimpleUI.INVALID_FORMAT);
			
			String name = t[0], unit = t[2];
			double quantity = Double.parseDouble(t[1]);
			quantity = checkUnit(unit, quantity); //converto la quantità e controllo la validità dell'unità di misura
			
			if (unit.toLowerCase().contains("g")) //ho convertito tutto in grammi e litri
				unit = "g";
			else
				unit = "L";
			
			if (!ingredients.add(new Ingredient(name, unit, quantity))) //aggiungo l'ingrediente alla lista
				erSet.errorSetter(SimpleUI.INVALID_FORMAT);
		}
		try
		{
			updateAfterMeal(ingredients);
			return true;
		}
		catch (RuntimeException e)
		{
			erSet.errorSetter(Integer.parseInt(e.getMessage()));
		}
		return false;
	}
	
	/**
	 *Metodo che modifica il magazzino dopo il pasto
	 * @param ingredients ingredienti da togliere e/o da aggiungere
	 * @throws RuntimeException usata per gestire i problemi di formato
	 */
	private void updateAfterMeal(Set<Ingredient> ingredients) throws RuntimeException
	{
		Set<Ingredient> register = new HashSet<>(model.getRegistroAfterMeal());
		for (Ingredient deltaIngredient : ingredients)
		{
			if (!model.getRegistro().contains(deltaIngredient)) //se provo ad aggiungere un ingrediente che prima non c'era ho un errore
				throw new RuntimeException(Integer.toString(SimpleUI.NO_INGREDIENT));
			for (Ingredient regIngr : model.getRegistro())
			{
				if (regIngr.equals(deltaIngredient))
				{
					if (register.contains(deltaIngredient))  //modifico il registro post pasto
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
										throw new RuntimeException(Integer.toString(SimpleUI.INVALID_QUANTITY)); //se la quantità che torna dalla cucina è maggiore di quella che è uscita c'è un errore
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
							throw new RuntimeException(Integer.toString(SimpleUI.INVALID_QUANTITY));
					}
				}
			}
		}
		model.setRegistroAfterMeal(register);
		gui.updateWareReturnList(setToString(model.getRegistroAfterMeal()));
	}
	
	/**
	 * Calcolo il magazzino alla fine di un pasto
	 */
	private void generateAfterMeal()
	{
		Set<Ingredient> registroNow = new HashSet<>(model.getRegistro());
		
		if (!model.getBookingMap().containsKey(model.getToday())) //se oggi non c'erano prenotazioni il magazzino non è cambiato
			gui.updateWareReturnList(setToString(model.getRegistro()));
		else
		{
			ArrayList<Booking> book = new ArrayList<>(model.getBookingMap().get(model.getToday()));
			for (Booking b : book)
				for (Map.Entry<Dish, Integer> entry : b.getOrder().entrySet())
					registroNow.removeAll(entry.getKey().getRecipe().getIngredients()); //tolgo dal magazzino tutti gli ingredienti usati per i piatti di oggi
			
			//rimuovo dal magazzino i drink e gli extra foods
			for (Map.Entry<String, Double> drink : model.getDrinksMap().entrySet())
				registroNow.removeIf(ingredient -> ingredient.getName().equals(drink.getKey()));
			for (Map.Entry<String, Double> food : model.getExtraFoodsMap().entrySet())
				registroNow.removeIf(ingredient -> ingredient.getName().equals(food.getKey()));
			
			model.setRegistroAfterMeal(registroNow);
		}
	}
	
	/**
	 * Metodo che si occupa delle operazioni necessarie per il cambio di giorno
	 */
	public void nextDay()
	{
		model.getToday().getDate().add(Calendar.DATE, 1); //cambio la data
		//update GUI
		updateDishStringList();
		updateMenuOut();
		updateMenuBoxes();
		List<String> data = new ArrayList<>();
		data.add(String.valueOf(model.getCapacity()));
		data.add(String.valueOf(model.getWorkPersonLoad()));
		data.add(String.valueOf(model.getWorkResturantLoad()));
		data.add(model.getToday().getStringDate());
		data.add(String.valueOf(model.getIncrement()));
		gui.nextDay(data,model.getToday().getStringDate());
		menuCartaToday();
		
		oldBooking(); //cancello le vecchie prenotazioni
		//aggiorno e calcolo magazzino e lista della spesa
		model.setRegistro(model.getRegistroAfterMeal());
		generateGroceryList();
		generateAfterMeal();
	}
	
	/**
	 * Metodo che rimuove tutte le prenotazioni scadute
	 */
	private void oldBooking()
	{
		for (Map.Entry<DateOur, ArrayList<Booking>> entry : model.getBookingMap().entrySet())
		{
			if (entry.getKey().getDate().before(model.getToday())) //rimuovo le prenotazioni che hanno una data precedente ad oggi
				model.getBookingMap().remove(entry.getKey());
		}
	}
	
	/**
	 * Metodo che mostra in GUI
	 * tutti i piatti validi oggi
	 */
	private void menuCartaToday()
	{
		StringBuilder menus = new StringBuilder();
		for (Dish dish : model.getDishesSet()) //genero la lista di piatti
			if (dish.isValid(model.getToday()))
				menus.append(dish.getName()).append("\n");
		if (menus.length() == 0)
			menus = new StringBuilder("Non ci sono piatti disponibili per la data ordierna"); //se non ci sono piatti validi
		sui.cfgResMenuCartaOut.setText(menus.toString());
	}
	
	/**
	 * Metodo che salva un nuovo utente tramite i dati della GUI
	 * @param name nome dell'utente
	 * @param password password
	 * @param confPassword conferma della password
	 * @param manager true se ha accesso alla tab manager, false altrimenti
	 * @param employee true se ha accesso alla tab employee, false altrimenti
	 * @param storageWorker true se ha accesso alla tab storageWorker, false altrimenti
	 * @return true se il salvataggio è avvenuto, false altrimenti
	 */
	public boolean saveUser(String name, String password, String confPassword, boolean manager, boolean employee, boolean storageWorker)
	{
		if (manager || employee || storageWorker) //deve avere almeno un ruolo, altrimenti non può accedere a nulla
		{
			if (!name.isBlank() && !password.isBlank()) //controllo la loro validità
			{
				boolean doppler = false;
				if (model.getUsers().size() != 0) //se c`è almeno un altro utente
				{
					for (User user : model.getUsers())
					{
						if (user.getName().equals(name)) //controllo che non esista un altro utente con lo stesso nome
						{
							erSet.errorSetter(SimpleUI.INVALID_USERNAME);
							doppler = true;
							break;
						}
					}
				}
				if (!doppler)
				{
					if (password.equals(confPassword)) //se le password coincidono
					{
						User user = new User(name, password, manager, employee, storageWorker); //salvo l'utente
						user.hashAndSaltPassword(); //hasho la password
						model.getUsers().add(user);
						Writer.writePeople(model.getUsers()); //salvo la lista aggiornata
						return true;
					}
					else
						erSet.errorSetter(SimpleUI.INVALID_PASSWORD);
				}
			}
			else
				erSet.errorSetter(SimpleUI.INVALID_FORMAT);
		}
		else
			erSet.errorSetter(SimpleUI.NOT_ENOUGHT_ROLES);
		return false;
	}

	/**
	 * Metodo che gestisce il login di un utente tramite utente e password
	 * @param name nome utente
	 * @param password password inserita
	 * @return true se il login è andato a buon fine, false altrimenti
	 */
	public boolean login(String name, String password)
	{
		if (!name.isBlank() && !password.isBlank()) //controllo che abbiano un valore
		{
			boolean found = false;
			for (User user : model.getUsers())
			{
				if (user.getName().equals(name)) //cerco l'utente tra l'elenco
				{
					found = true;
					if (user.checkPassword(password)) //se la password coincide
					{
						gui.login(); //cambio stato alla GUI
						model.setTheUser(user); //memorizzo l'utente corrente
						return true;
					}
					else
						erSet.errorSetter(SimpleUI.INVALID_PASSWORD);
					break;
				}
			}
			if (!found)
				erSet.errorSetter(SimpleUI.INVALID_USERNAME);
		}
		else
			erSet.errorSetter(SimpleUI.INVALID_FORMAT);
		return false;
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
