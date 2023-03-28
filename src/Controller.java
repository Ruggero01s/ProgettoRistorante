import java.util.*;

public class Controller implements SearchRecipe, SearchDish, Login, SaveData, DataManagement
{
	private final Model model = new Model();
	private GUI gui;
	private ErrorSetter erSet;
	private final Read reader = new Reader(this, this); //todo controllare interazioni con write e read
	private final Write writer = new Writer();
	
	//costanti per la gestione degli errori
	
	public static final int MIN_ZERO = 0;
	public static final int INVALID_FORMAT = 1;
	public static final int NO_RECIPE = 2;
	public static final int NO_DISH = 3;
	public static final int INSUFFICENT_DISH = 4;
	public static final int INVALID_DATE = 5;
	public static final int FULL_RESTURANT = 6;
	public static final int THICC_MENU = 7;
	public static final int NAME_SAME_AS_DISH = 8;
	public static final int NOT_FOUND = 9;
	public static final int NO_BOOKINGS = 10;
	public static final int NO_QUANTITY = 11;
	public static final int OUT_OF_DATE = 12;
	public static final int EXISTING_NAME = 13;
	public static final int WRONG_SURPLUS = 14;
	public static final int NO_INGREDIENT = 15;
	public static final int INVALID_QUANTITY = 16;
	public static final int NOT_TODAY = 17;
	public static final int WORKLOAD_TOO_HIGH = 18;
	public static final int ORDER_FOR_TOO_LITTLE = 19;
	public static final int INVALID_PASSWORD = 20;
	public static final int INVALID_USERNAME = 21;
	public static final int NOT_ENOUGHT_ROLES = 22;
	public static final int NO_PERMISSION = 23;
	public static final int EMPTY_INPUT = 24;
	public static final int WRONG_UNIT = 25;
	public static final int TOO_LOW_CONFIG = 26;
	public static final int DOUBLE_INGREDIENT = 27;

	/**
	 * Metodo d'inizializzazione
	 */
	public void init ()
	{
		gui = new SimpleUI(this, this, this);
		erSet = (ErrorSetter) gui;
		loadModel();
		gui.init(model.getToday().getStringDate());
		calculateWarehouse(true);
	}
	
	/**
	 * Metodo che calcola e salva tutti i dati del magazziniere, ovvero:
	 * lista della spesa, magazzino prima del pasto e magazzino dopo il pasto
	 * @param load true se il magazzino è gia generato e non deve essere calcolato, false altrimenti
	 */
	private void calculateWarehouse(boolean load)
	{
		Set<Ingredient> consumedSet = new HashSet<>(generateConsumedList());
		Set<Ingredient> grocerySet = generateGroceryList(consumedSet);
		if(!load)
			generateRegistroBeforeMeal(grocerySet);
		generateRegistroAfterMeal(consumedSet);
		gui.updateWare(setToString(grocerySet), setToString(model.getRegistroBeforeMeal()));
		gui.updateWareReturnList(setToString(model.getRegistroAfterMeal()));
	}
	
	/**
	 * Inizializzazione del model tramite i reader
	 */
	private void loadModel()
	{
		// Chiamo tutti i reader per leggere i dati salvati
		ModelAttributes modelAttributes = reader.readConfig();
		model.setCapacity(modelAttributes.getCapacity());
		model.setWorkPersonLoad(modelAttributes.getWorkloadPerson());
		model.setToday(modelAttributes.getToday());
		model.setIncrement(modelAttributes.getIncrement());
		model.setUsers(reader.readPeople());
		model.setDrinksMap(reader.readDrinks());
		model.setExtraFoodsMap(reader.readExtraFoods());
		model.setRecipesSet(reader.readRecipes());
		model.setDishesSet(reader.readDishes());
		model.setThematicMenusSet(reader.readThematicMenu());
		model.setBookingMap(reader.readBooking());
		model.setRegistroBeforeMeal(reader.readRegister());
		//aggiorno le varie stringhe per la GUI
		gui.updateRecipes(convertToStringVector(model.getRecipeSetConverted()));
		updateDrinkList();
		gui.updateDishes(convertToStringVector(model.getDishesSetConverted()));
		updateFoodList();
		gui.updateMenus(convertToString(model.getThematicMenuSetConverted()));
		gui.updateMenuBoxes(makeMenuList());
		menuCartaToday();
		updateBookedDates();
		updateConfig();
		oldBooking(); //elimino le vecchie prenotazioni
	}
	
	/**
	 * Metodo che aggiorna la pagina dei config
	 */
	private void updateConfig ()
	{
		List<String> configState = new ArrayList<>();
		configState.add("Capacità: " + model.getCapacity() + "\nIndividualWorkload: " +
				model.getWorkPersonLoad() + "\nRestaurant Worlkload: " + model.getWorkResturantLoad() + "\nData odierna: " +
				model.getToday().getStringDate() + "\nSurplus %: " + model.getIncrement());
		configState.add(Integer.toString(model.getCapacity()));
		configState.add(Integer.toString(model.getWorkPersonLoad()));
		configState.add(model.getToday().getStringDate());
		configState.add(Integer.toString(model.getIncrement()));
		
		gui.updateConfig(configState);
	}
	
	/**
	 * Metodo che svuota la memoria
	 */
	public void clearInfo()
	{
		//clear dei drinks
		model.getDrinksMap().clear();
		writer.writeDrinks(model.getDrinksMap());
		updateDrinkList();

		//clear dei  foods
		model.getExtraFoodsMap().clear();
		writer.writeExtraFoods(model.getExtraFoodsMap());
		updateFoodList();

		//clear dei config
		model.setCapacity(0);
		model.setWorkPersonLoad(0);
		model.setIncrement(5);
		model.setToday(new DateOur("01", "01", "1444"));
		writer.writeConfigBase(model.getCapacity(), model.getWorkPersonLoad(), model.getToday(), model.getIncrement());
		String[] data = {"0", "0", "0", "01/01/1444", "5"};
		gui.updateConfig(List.of(data));

		//clear delle recipe
		model.getRecipesSet().clear();
		writer.writeRecipes(model.getRecipesSet());
		gui.updateRecipes(convertToStringVector(model.getRecipeSetConverted()));

		//clear dei dish
		model.getDishesSet().clear();
		writer.writeDishes(model.getDishesSet());
		gui.updateDishes(convertToStringVector(model.getDishesSetConverted()));
		menuCartaToday();

		//clear dei menu
		model.getThematicMenusSet().clear();
		writer.writeThematicMenu(model.getThematicMenusSet());
		gui.updateMenus(convertToString(model.getThematicMenuSetConverted()));
		gui.updateMenuBoxes(makeMenuList());
		
		//clear delle prenotazioni
		model.getBookingMap().clear();
		writeBookings();
		
		//clear del magazzino
		model.getRegistroBeforeMeal().clear();
		writeRegister();
		gui.updateWare("","");

		//clear degli user
		model.getUsers().clear();
		writer.writePeople(model.getUsers());
		gui.logout();
	}

	/**
	 * Cancella le prenotazioni in uno specifico giorno
	 * @param input giorno in cui annullare le prenotazioni
	 * @return true se la
	 */
	public boolean clearBookings(DateOur input)
	{
		Object obj = model.getBookingMap().remove(input);
		writeBookings();
		return !(obj == null);
	}
	
	/**
	 * Cancella tutte le prenotazioni tranne quelle in data odierna
	 */
	public void clearBookings()
	{
		model.getBookingMap().keySet().removeIf(k -> !(k.equals(model.getToday())));
		writeBookings();
	}
	
	/**
	 * Salvataggio tramite writer di tutti i dati del manager
	 */
	public void writeManager()
	{
		writer.writeDrinks(model.getDrinksMap());
		writer.writeExtraFoods(model.getExtraFoodsMap());
		writer.writeConfigBase(model.getCapacity(), model.getWorkPersonLoad(), model.getToday(), model.getIncrement());
		writer.writeRecipes(model.getRecipesSet());
		writer.writeDishes(model.getDishesSet());
		writer.writeThematicMenu(model.getThematicMenusSet());
	}
	
	/**
	 * Metodo che legge e salva i config dalla GUI
	 */
	public void saveConfig(String inputCapacity, String inputWorkload, String inputPercent, String todayString)
	{
		try
		{
			int capacity = Integer.parseInt(inputCapacity); //converto i parametri numerici
			int workload = Integer.parseInt(inputWorkload);
			int percent = Integer.parseInt(inputPercent);
			if(checkValidityOfSetting(capacity,workload))
			{
				if (!checkDate(todayString)) //check per correttezza della data
				{
					erSet.errorSetter(INVALID_DATE);
				}
				else
				{
					DateOur today = inputToDate(todayString);
					if (capacity <= 0 || workload <= 0) //check parametri numerici
						erSet.errorSetter(MIN_ZERO);
					else if (percent > 10 || percent<0)
						erSet.errorSetter(WRONG_SURPLUS);
					else
					{
						//salvo tutti i dati e aggiorno la GUI
						model.setCapacity(capacity);
						model.setWorkPersonLoad(workload);
						model.setToday(today);
						model.setIncrement(percent);
						calculateWarehouse(true);
					}
				}
			}
			else
				erSet.errorSetter(TOO_LOW_CONFIG);
			updateConfig();
		}
		catch (NumberFormatException e)
		{
			erSet.errorSetter(INVALID_FORMAT);
		}
	}
	
	/**
	 * Metodo che controllo se i nuovi parametri inseriti mantengono valide le prenotazioni già effettuate
	 * @param capacity capacità modificata
	 * @param workload workload modificato
	 * @return true se i parametri mantengono l'integrità delle prenotazioni, false altrimenti
	 */
	private boolean checkValidityOfSetting (int capacity, int workload)
	{
		int usedCapacity, usedWorkload;
		for (Map.Entry<DateOur, List<Booking>> books : model.getBookingMap().entrySet()) //scorro tutte le prenotazioni
		{
			usedCapacity =0;
			usedWorkload=0;
			for (Booking booking: books.getValue()) //scorro tutte le prenotazioni di un giorno
			{
				usedWorkload+=booking.getWorkload();
				usedCapacity+=booking.getNumber();
			}
			if(capacity<usedCapacity || workload*capacity*Model.INCREASE20<usedWorkload) //controllo la validità dei parametri per ogni giorno prenotato
				return false;
		}
		return true;
	}
	
	
	/**
	 * Metodo che legge e salva i drinks
	 */
	public void saveDrinks (String input)
	{
		try
		{
			if (!input.contains(":")) //controllo il formato della stringa
				throw new NumberFormatException("");
			
			String[] inputSplit = input.split(":");
			
			if (inputSplit[0].isBlank())
				throw new NumberFormatException(""); //nome non valido
			if (inputSplit.length < 2)
				throw new NumberFormatException("");
			
			double quantity = Double.parseDouble(inputSplit[1]);
			if(inputSplit[2].toLowerCase().contains("g"))
				throw new RuntimeException("");
			quantity = checkUnit(inputSplit[2], quantity); //conversione dell'unità
			if (quantity <= 0) //quantità non valida
				erSet.errorSetter(MIN_ZERO);
			else {
				model.getDrinksMap().put(inputSplit[0].toLowerCase(), quantity);
				updateDrinkList();
			}
		}
		catch (RuntimeException e)
		{
			erSet.errorSetter(WRONG_UNIT);
		}
		catch (Exception e)
		{
			erSet.errorSetter(INVALID_FORMAT);
		}

	}
	
	/**
	 * Metodo che legge e salva i foods aggiuntivi
	 */
	public void saveFoods (String input)
	{
		try
		{
			if (!input.contains(":")) //controllo il formato della stringa
				throw new NumberFormatException("");
			
			String[] inputSplit = input.split(":");
			
			if (inputSplit.length < 2) //controllo il formato della stringa
				throw new NumberFormatException("");
			if (inputSplit[0].isBlank()) //controllo la validità del nome
				throw new NumberFormatException("");
			double quantity = Double.parseDouble(inputSplit[1]);
			quantity = checkUnitExtraFoods(inputSplit[2],quantity);
			if (quantity <= 0) //controllo che la quantità sia > 0
				erSet.errorSetter(MIN_ZERO);
			else
			{
				model.getExtraFoodsMap().put(inputSplit[0].toLowerCase(), quantity);
				updateFoodList();
			}
		}
		catch (RuntimeException e)
		{
			erSet.errorSetter(WRONG_UNIT);
		}
		catch (Exception e)
		{
			erSet.errorSetter(INVALID_FORMAT);
		}
	}
	
	/**
	 * Metodo che legge e salva le ricette
	 */
	public void saveRecipe (String inputName, String inputIngredients, String inputPortions, String inputWorkload)
	{
		try
		{
			Set<Ingredient> ingredientQuantitySet = new HashSet<>();
			String[] lines = inputIngredients.split("\n");
			
			if (inputName.isBlank()) //controllo validità del nome
				throw new RuntimeException("");
			
			boolean err = false;
			double quantity;
			for (String line : lines)
			{
				if (!line.contains(":")) //controllo validità della stringa
					throw new RuntimeException("");
				
				String[] words = line.split(":");
				
				if (words[0].isBlank()) //controllo validità del nome
					throw new RuntimeException("");
				
				if (words.length < 3) //controllo lunghezza stringa
					throw new RuntimeException("");
				
				quantity = Double.parseDouble(words[1]);
				quantity = checkUnit(words[2], quantity);
				String unit;

				if (quantity <= 0) //controllo che la quantità sia > 0
				{
					err = true;
					break;
				}
				//converto tutti in g o L
				if (words[2].toLowerCase().contains("g"))
					unit = "g";
				else
					unit = "L";
				if(!ingredientQuantitySet.add(new Ingredient(words[0].toLowerCase(), unit, quantity)))
				{
					erSet.errorSetter(DOUBLE_INGREDIENT);
					throw new Exception("");
				}
			}
			
			int portions = Integer.parseInt(inputPortions);
			double workLoad = Double.parseDouble(inputWorkload);
			if (workLoad >= model.getWorkPersonLoad()) //se il workload è impossibilmente alto da soddisfare
				erSet.errorSetter(WORKLOAD_TOO_HIGH);
			else {
				if (portions <= 0 || workLoad <= 0 || err)
					erSet.errorSetter(MIN_ZERO);
				else {
					if (model.getRecipesSet().add(new Recipe(inputName, ingredientQuantitySet, portions, workLoad))) {
						gui.updateRecipes(convertToStringVector(model.getRecipeSetConverted()));
					} else
						erSet.errorSetter(EXISTING_NAME);
				}
			}
		}
		catch (RuntimeException e)
		{
			erSet.errorSetter(INVALID_FORMAT);
		}
		catch (Exception e)
		{
			erSet.errorSetter(DOUBLE_INGREDIENT);
		}
	}
	
	/**
	 * Converto e checko la validità delle unità
	 * @param unit     unita d'ingresso
	 * @param quantity quantità da convertire
	 * @return quantità convertita
	 */
	private double checkUnit (String unit, Double quantity)
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
	 * Converto e checko la validità delle unità in caso di extra foods
	 * @param unit     unita d'ingresso
	 * @param quantity quantità da convertire
	 * @return quantità convertita in Hg
	 */
	private double checkUnitExtraFoods (String unit, Double quantity)
	{
		if (!(unit.equalsIgnoreCase("hg"))) //converto solo se l'unità non è in Hg
		{
			switch (unit.toLowerCase())
			{
				case "kg" -> quantity *= 10.0;
				case "dag" -> quantity /= 10.0;
				case "g" -> quantity /= 100.0;
				case "dg" -> quantity /= 1000.0;
				case "cg" -> quantity /= 10000.0;
				case "mg"-> quantity /= 100000.0;
				default -> throw new RuntimeException(""); //in caso di unità non riconosciuta lancio un errore
			}
		}
		return quantity;
	}
	
	/**
	 * Leggo e salvo i piatti
	 */
	public void saveDish (String inputName, String inputRecipe, String inputStartDate, String inputEndDate, boolean perm, boolean seasonal)
	{
		try
		{
			if (inputName.isBlank()) //controllo validità del nome
				throw new NumberFormatException();
			
			if (!perm) // se non è permanente controllo le date
			{
				if (!checkDate(inputStartDate) || !checkDate(inputEndDate))
				{
					erSet.errorSetter(INVALID_DATE);
					return;
				}
			}
			else
			{
				inputStartDate = "01/01/1444";
				inputEndDate = "31/12/1444";
			}
			boolean found = false;
			boolean valid = true;
			for (Recipe r : model.getRecipesSet())
			{
				if (r.getId().equalsIgnoreCase(inputRecipe))
				{
					Dish temp = new Dish(inputName, r, inputStartDate, inputEndDate, seasonal, perm);
					for (ThematicMenu thematicMenu: model.getThematicMenusSet())
					{
						if (thematicMenu.getName().equalsIgnoreCase(temp.getName())) //controllo che non esistano un piatto ed un menu con lo stesso nome
						{
							valid = false;
							break;
						}
					}
					if (valid)
					{
						if (model.getDishesSet().add(temp))
						{
							gui.updateDishes(convertToStringVector(model.getDishesSetConverted())); //aggiorno la GUI
							found = true;
						}
						else
							erSet.errorSetter(EXISTING_NAME);
					}
					else
						erSet.errorSetter(NAME_SAME_AS_DISH);
					break;
				}
			}
			if (!found && valid)
				erSet.errorSetter(NO_RECIPE);
			menuCartaToday(); //aggiorno il menu
		}
		catch (RuntimeException e)
		{
			erSet.errorSetter(INVALID_DATE);
		}catch (Exception e)
		{
			erSet.errorSetter(INVALID_FORMAT);
		}
	}
	
	/**
	 * Leggo e salvo i menu tematici
	 */
	public void saveMenu (String inputName, String inputs, String inputStartDate, String inputEndDate, boolean permanent, boolean seasonal)
	{
		try
		{
			if (inputName.isBlank()) //controllo validità del nome
				throw new NumberFormatException();
			
			String[] inputList = inputs.split("\n");
			
			Set<Dish> dishesForMenu = new HashSet<>();
			
			if (!permanent) //se non è permanente controllo la validità delle date
			{
				if (!checkDate(inputStartDate) || !checkDate(inputEndDate))
				{
					erSet.errorSetter(INVALID_DATE);
					return;
				}
			}
			else
			{
				inputStartDate = "01/01/1444";
				inputEndDate = "31/12/1444";
			}
			boolean found, dishNotFound = false;
			for (String s : inputList)
			{
				found = false;
				if (!s.isBlank())
				{
					for (Dish d : model.getDishesSet())
					{
						if (d.getName().equalsIgnoreCase(s))
						{
							dishesForMenu.add(d); //aggiungo i piatti al menu
							found = true;
							break;
						}
					}
				}
				if (!found) // se non trova il piatto
					dishNotFound = true;
			}
			if (dishNotFound) //se almeno un piatto prima non è stato trovato da errore
				erSet.errorSetter(NO_DISH);
			else
			{
				ThematicMenu temp = new ThematicMenu(inputName, inputStartDate, inputEndDate, dishesForMenu, seasonal, permanent); //creo il menu
				if (temp.getWorkThematicMenuLoad() <= ((double) model.getWorkPersonLoad() * 4 / 3)) //controllo se il workLoad è giusto
				{
					boolean valid = true;
					for (Dish dish : model.getDishesSet())
					{
						if (dish.getName().equalsIgnoreCase(temp.getName())) //controllo che non esistano un piatto ed un menu con lo stesso nome
						{
							valid = false;
							break;
						}
					}
					if (valid)
					{
						if (model.getThematicMenusSet().add(temp)) //aggiorno la GUI
						{
							gui.updateMenus(convertToString(model.getThematicMenuSetConverted()));
							gui.updateMenuBoxes(makeMenuList());
						}
						else
							erSet.errorSetter(EXISTING_NAME);
					}
					else
						erSet.errorSetter(NAME_SAME_AS_DISH);
				}
				else
					erSet.errorSetter(THICC_MENU);
			}
		}
		catch (RuntimeException e)
		{
			erSet.errorSetter(INVALID_DATE);
		}catch (Exception e)
		{
			erSet.errorSetter(INSUFFICENT_DISH);
		}
	}
	
	/**
	 * Controllo se una data è valida
	 * @param s data da controllare
	 * @return true se valida, false altrimenti
	 */
	public static boolean checkDate (String s)
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
	 * @param name stringa di piatti
	 * @return Array list di piatti
	 */
	public Dish searchDish (String name)
	{
		for (Dish dish : model.getDishesSet())
		{
			if (dish.getName().equalsIgnoreCase(name))
				return dish;
		}
		return null;
	}

	/**
	 * Metodo che trasforma una stringa in una ricetta
	 * @param name ricetta in forma di stringa
	 * @return ricetta
	 */
	public Recipe searchRecipe (String name)
	{
		for (Recipe recipe : model.getRecipesSet())
		{
			if (recipe.getId().equalsIgnoreCase(name))
				return recipe;
		}
		return null;
	}
	
	/**
	 * Metodo che converte un oggetto che implementa ConvertToStrings in una stringa
	 * @param convertToStrings collection da convertire
	 * @return stringa che elenca la collection
	 */
	private String convertToString (Collection<ConvertToString> convertToStrings)
	{
		StringBuilder out = new StringBuilder();
		for (ConvertToString convertToString : convertToStrings)
			out.append(convertToString.convertToString()).append("\n");
		return out.toString().trim();
	}
	
	/**
	 * Metodo che converte un oggetto che implementa ConvertToStrings in un vettore di stringhe
	 * @param convertToStrings collection di oggetti che implementano l'interfaccia
	 * @return la collection convertita in vettore di stringhe
	 */
	private String[] convertToStringVector (Collection<ConvertToString> convertToStrings)
	{
		String[] out = new String[convertToStrings.size()];
		int i = 0;
		
		for (ConvertToString convertToString : convertToStrings)
			out[i++] = convertToString.convertToString();
		
		return out;
	}

	/**
	 * Metodo che serve per aggiornare i drinks nella GUI
	 */
	private void updateDrinkList ()
	{
		StringBuilder out = new StringBuilder();
		for (Map.Entry<String, Double> drink : model.getDrinksMap().entrySet()) //trasformo la map di drinks in stringa
			out.append(drink.getKey()).append(":").append(drink.getValue().toString()).append(":L").append("\n");
		
		gui.updateDrinks(out.toString().trim());
	}
	
	/**
	 * Metodo che serve per aggiornare gli extra foods nella GUI
	 */
	private void updateFoodList ()
	{
		StringBuilder out = new StringBuilder();
		for (Map.Entry<String, Double> food : model.getExtraFoodsMap().entrySet()) //trasformo la map di extra foods in stringa
			out.append(food.getKey()).append(":").append(food.getValue().toString()).append(":Hg").append("\n");
		
		gui.updateFoods(out.toString().trim());
	}

	/**
	 * Crea una stringa con i dati di un certo menu con nome menuName
	 * @param menuName menu da trasformare in stringa
	 */
	public void writeMenuComp (String menuName)
	{
		StringBuilder out = new StringBuilder();
		for (ThematicMenu menu : model.getThematicMenusSet())
		{
			if (menu.getName().equalsIgnoreCase(menuName))
			{
				out.append(menuName).append(" w.").append(menu.getWorkThematicMenuLoad()).append("\n");
				for (Dish d : menu.getDishes())
				{
					out.append("    ").append(d.getName()).append("\n");
				}
				break;
			}
		}
		gui.selectedMenu(out.toString());
	}
	
	/**
	 * @return un array con un nome per ogni menu per indice
	 */
	private String[] makeMenuList ()
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
	public DateOur inputToDate (String input)
	{
		String[] bookDates;
		try
		{
			bookDates = input.split("/");
			return new DateOur(bookDates[0], bookDates[1], bookDates[2]);
		}
		catch (Exception e)
		{
			erSet.errorSetter(INVALID_DATE);
			return null;
		}
	}
	
	/**
	 * Metodo che prende dati dalla finestra di creazione ordine e ne estrae un ordine, analizza una stringa spezzandola in righe e cerca il nome del piatto/menu nei vari set esistenti
	 * @param in     Stringa in entrata
	 * @param date   Data per oggi, per controllare che il piatto trovato sia disponibile
	 * @param number numero del piatto/menu ordinato
	 * @return un HashMap che contiene per ogni Dish nell'ordine la quantità ordinata
	 */
	private HashMap<Dish, Integer> inputToOrder (String in, DateOur date, int number)
	{
		if (in.isBlank()) throw new RuntimeException();
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
					erSet.errorSetter(MIN_ZERO);
					return order;
				}
				for (ThematicMenu menu : model.getThematicMenusSet()) //cerca se il nome scritto è tra i menu tematici
				{
					if (name.equalsIgnoreCase(menu.getName()))
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
							erSet.errorSetter(OUT_OF_DATE);
							return order;
						}
					}
				}
				if (!found) //se il nome non è tra i menu cerca i piatti singoli e fa lo stesso procedimento
				{
					for (Dish dish : model.getDishesSet())
					{
						if (name.equalsIgnoreCase(dish.getName()))
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
								erSet.errorSetter(INVALID_DATE);
								return order;
							}
						}
					}
				}
				if (!found) //se ancora non ha trovato una corrispondenza da errore
				{
					order.clear();
					erSet.errorSetter(NOT_FOUND);
					return order;
				}
			}
			if (count < number) //controlla che ci sia almeno un piatto per persona
			{
				order.clear();
				erSet.errorSetter(ORDER_FOR_TOO_LITTLE);
			}
			return order;
		}
		catch (NumberFormatException e) //catch l'errore del parseInt iniziale
		{
			erSet.errorSetter(NO_QUANTITY);
			return new HashMap<>();
		}
		catch (RuntimeException e) //catch l'errore del parseInt iniziale
		{
			erSet.errorSetter(EMPTY_INPUT);
			return new HashMap<>();
		}
	}
	
	/**
	 * Metodo che mostra in GUI tutte le prenotazioni di un giorno
	 * @param data giorno da controllare
	 */
	public void seeBookings (DateOur data)
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
			
			gui.updateBooking(name.toString().trim(), number.toString().trim(), work.toString().trim(), Integer.toString(model.getCapacity() - capacity), Double.toString(model.getWorkResturantLoad() - workload));
		}
		else
			erSet.errorSetter(NO_BOOKINGS); //non ci sono prenotazioni per quel giorno
	}
	
	/**
	 * Metodo che crea una prenotazione dai dati in GUI
	 * @param name        nome della prenotazione
	 * @param dateString  data sotto forma di stringa
	 * @param number      numero di coperti
	 * @param orderString ordini sotto forma di stringa
	 * @return true se la prenotazione è stata salvata, false altrimenti
	 */
	public boolean saveBooking (String name, String dateString, int number, String orderString)
	{
		try
		{
			DateOur date = inputToDate(dateString);
			if (date != null && date.getDate().after(model.getToday().getDate())) //controllo che la data abbia un senso
			{
				int workload = 0;
				HashMap<Dish, Integer> order = inputToOrder(orderString, date, number);
				if (!order.isEmpty())
				{
					if (number > 0)//il numero deve essere maggiore di 0
					{
						for (Map.Entry<Dish, Integer> dish : order.entrySet())
							workload += dish.getKey().getRecipe().getWorkLoadPortion() * dish.getValue(); //calcolo il workload di questo giorno
						
						return manageBooking(name, date, number, workload, order); //se la prenotazione viene salvata ritorno true
					}
					else
						erSet.errorSetter(MIN_ZERO);
				}
			}
			else
				erSet.errorSetter(NOT_TODAY);
		}
		catch (NumberFormatException e)
		{
			erSet.errorSetter(INVALID_FORMAT);
		}
		return false;
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
	private boolean manageBooking (String name, DateOur date, int number, int workload, HashMap<Dish, Integer> order)
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
			updateBookedDates();
			return true;
		}
		else
		{
			erSet.errorSetter(FULL_RESTURANT);
			return false;
		}
	}
	
	/**
	 * Metodo che produce una stringa di date
	 * di tutti i giorni in cui c'è almeno una prenotazione
	 */
	private void updateBookedDates ()
	{
		Set<DateOur> daysList = model.getBookingMap().keySet(); //insieme di date con almeno una prenotazione
		StringBuilder out = new StringBuilder();
		for (DateOur date : daysList) //scorro le date e creo una stringa
		{
			String s = date.getStringDate();
			out.append(s).append("\n");
		}
		gui.updateBookedDates(out.toString().trim());
	}

	/**
	 * Metodo che chiama il writer per le prenotazioni
	 */
	public void writeBookings ()
	{
		writer.writeBookings(model.getBookingMap());
	}
	
	/**
	 * Metodo che chiama il writer per il magazzino
	 */
	public void writeRegister ()
	{
		writer.writeRegister(model.getRegistroBeforeMeal());
	}

	/**
	 * Metodo che converte set d'ingredienti in stringhe
	 * @param set set da convertire
	 * @return stringa del set
	 */
	public String setToString (Set<Ingredient> set)
	{
		StringBuilder out = new StringBuilder();
		for (Ingredient entry : set)
			out.append(entry.convertToString()).append("\n");
		return out.toString().trim();
	}
	
	/**
	 * Metodo che dalla GUI crea una lista d'ingredienti da modificare nel magazzino
	 * @param text ingredienti da cambiare
	 * @return true se è andato a buon fine, false altrimenti
	 */
	public boolean warehouseChanges (String text)
	{
		if (text.isBlank()) //controllo che il testo sia valido
		{
			erSet.errorSetter(INVALID_FORMAT);
			return false;
		}
		
		Set<Ingredient> ingredients = new HashSet<>();
		
		for (String s : text.split("\n"))
		{
			if (!s.contains(":")) //controllo il formato della riga
			{
				erSet.errorSetter(INVALID_FORMAT);
				return false;
			}
			
			String[] t = s.split(":");
			
			if (t.length < 3 || t[0].isBlank() || t[2].isBlank()) //controllo il formato della stringa splittata
			{
				erSet.errorSetter(INVALID_FORMAT);
				return false;
			}
			
			String name = t[0], unit = t[2];
			double quantity = Double.parseDouble(t[1]);
			if(model.getExtraFoodsMap().containsKey(name))
			{
				quantity = checkUnitExtraFoods(unit,quantity);
				unit="Hg";
			}
			else
			{
				quantity = checkUnit(unit, quantity); //converto la quantità e controllo la validità dell'unità di misura
				if (unit.toLowerCase().contains("g")) //ho convertito tutto in grammi e litri
					unit = "g";
				else
					unit = "L";
			}
			
			if (!ingredients.add(new Ingredient(name, unit, quantity))) //aggiungo l'ingrediente alla lista
			{
				erSet.errorSetter(INVALID_FORMAT);
				return false;
			}
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
	 * Metodo che modifica il magazzino dopo il pasto
	 * @param ingredients ingredienti da togliere e/o da aggiungere
	 * @throws RuntimeException usata per gestire i problemi di formato
	 */
	private void updateAfterMeal (Set<Ingredient> ingredients) throws RuntimeException
	{
		Set<Ingredient> register = new HashSet<>(model.getRegistroAfterMeal());
		for (Ingredient deltaIngredient : ingredients)
		{
			if (!model.getRegistroBeforeMeal().contains(deltaIngredient)) //se provo ad aggiungere un ingrediente che prima non c'era ho un errore
				throw new RuntimeException(Integer.toString(NO_INGREDIENT));
			for (Ingredient regIngr : model.getRegistroBeforeMeal())
			{
				if (regIngr.equals(deltaIngredient))
				{
					if (register.contains(deltaIngredient))  //modifico il registro post pasto
					{
						for (Ingredient ingredient : register)
						{
							if (ingredient.equals(deltaIngredient))
							{
								if (!ingredient.getUnit().equals(deltaIngredient.getUnit()))
									throw new RuntimeException(Integer.toString(WRONG_UNIT));
								double newIngr = ingredient.getQuantity() + deltaIngredient.getQuantity();
								
								if (newIngr < 0)
									register.remove(ingredient);
								else
								{
									if (newIngr > regIngr.getQuantity())
										throw new RuntimeException(Integer.toString(INVALID_QUANTITY)); //se la quantità che torna dalla cucina è maggiore di quella che è uscita c'è un errore
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
							throw new RuntimeException(Integer.toString(INVALID_QUANTITY));
					}
				}
			}
		}
		model.setRegistroAfterMeal(register);
		gui.updateWareReturnList(setToString(model.getRegistroAfterMeal()));
	}
	
	/**
	 * Metodo che si occupa delle operazioni necessarie per il cambio di giorno
	 */
	public void nextDay ()
	{
		model.getToday().getDate().add(Calendar.DATE, 1); //cambio la data
		//update GUI
		gui.updateDishes(convertToStringVector(model.getDishesSetConverted()));
		gui.updateMenus(convertToString(model.getThematicMenuSetConverted()));
		gui.updateMenuBoxes(makeMenuList());
		gui.nextDay(model.getToday().getStringDate());
		updateConfig();
		menuCartaToday();
		
		oldBooking(); //cancello le vecchie prenotazioni
		
		//aggiorno e calcolo magazzino e lista della spesa
		model.setRegistroBeforeMeal(model.getRegistroAfterMeal());
		calculateWarehouse(false);
	}
	
	/**
	 * Metodo che rimuove tutte le prenotazioni scadute
	 */
	private void oldBooking ()
	{
		HashMap<DateOur, List<Booking>> temp = new HashMap<>(model.getBookingMap()); //per evitare concurrent access
		for (Map.Entry<DateOur, List<Booking>> entry : model.getBookingMap().entrySet())
		{
			if (entry.getKey().getDate().before(model.getToday().getDate())) //rimuovo le prenotazioni che hanno una data precedente ad oggi
				temp.remove(entry.getKey());
		}
		model.setBookingMap(temp);
		updateBookedDates();
	}
	
	/**
	 * Metodo che mostra in GUI
	 * tutti i piatti validi oggi
	 */
	private void menuCartaToday ()
	{
		StringBuilder menuCarta = new StringBuilder();
		for (Dish dish : model.getDishesSet()) //genero la lista di piatti
			if (dish.isValid(model.getToday()))
				menuCarta.append(dish.getName()).append("\n");
		if (menuCarta.length() == 0)
			menuCarta = new StringBuilder("Non ci sono piatti disponibili per la data ordierna"); //se non ci sono piatti validi
		gui.updateMenuCarta(menuCarta.toString());
	}
	
	/**
	 * Metodo che genera una lista d'ingredienti consumati nel giorno di oggi
	 * @return set d'ingredienti consumati
	 */
	private Set<Ingredient> generateConsumedList ()
	{
		Set<Ingredient> consumedSet = new HashSet<>();
		Set<Ingredient> drinks = new HashSet<>();
		Set<Ingredient> foods = new HashSet<>();
		
		if (model.getBookingMap().containsKey(model.getToday()))
		{
			Map<Dish, Integer> allDish = new HashMap<>();
			for (Booking booking : model.getBookingMap().get(model.getToday()))
			{
				for (Map.Entry<String, Double> entry : model.getDrinksMap().entrySet()) //aggiungo i drink alla lista della spesa
				{
					Ingredient i = new Ingredient(entry.getKey(), "L", Math.ceil(entry.getValue() * booking.getNumber()));
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
					Ingredient i = new Ingredient(entry.getKey(), "Hg", Math.ceil(entry.getValue() * booking.getNumber()));
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
				for (Map.Entry<Dish, Integer> dish : booking.getOrder().entrySet()) //calcolo quanti piatti di ogni tipo devo cucinare oggi
				{
					if (allDish.containsKey(dish.getKey()))
						allDish.put(dish.getKey(), dish.getValue() + allDish.get(dish.getKey()));
					else
						allDish.put(dish.getKey(), dish.getValue());
				}
			}
			for (Map.Entry<Dish, Integer> dish : allDish.entrySet())
			{
				Recipe recipe = dish.getKey().getRecipe();
				
				for (Ingredient ingredient : recipe.getIngredients())
				{
					double multi = (dish.getValue() / (double) recipe.getPortions()); //calcolo la proporzione d'ingredienti da usare
					
					if (consumedSet.contains(ingredient)) //se ci sono già ingredienti uguali consumati sommo il loro valore
					{
						for (Ingredient consumedStock : consumedSet)
						{
							if (consumedStock.equals(ingredient))
							{
								consumedStock.setQuantity(consumedStock.getQuantity() + multi * ingredient.getQuantity());
								break;
							}
						}
					}
					else
						consumedSet.add(new Ingredient(ingredient.getName(), ingredient.getUnit(), multi * ingredient.getQuantity()));
				}
			}
			for (Ingredient consumedStock : consumedSet)
				consumedStock.setQuantity(consumedStock.getQuantity() * (100 + model.getIncrement()) / 100); //calcolo dell'incremento dell'X%
			
			//aggiungo foods and drinks agli ingredienti consumati
			consumedSet.addAll(drinks);
			consumedSet.addAll(foods);
		}
		return consumedSet;
	}

	/**
	 * Metodo che genera la lista della spesa in base agli ingredienti
	 * consumati oggi e quelli disponibili in magazzino
	 * @param consumedSet ingredienti consumati oggi
	 * @return lista della spesa
	 */
	private Set<Ingredient> generateGroceryList (Set<Ingredient> consumedSet)
	{
		Set<Ingredient> registroBeforeMeal = new HashSet<>(model.getRegistroBeforeMeal());
		Set<Ingredient> consumedCopy = new HashSet<>();

		for (Ingredient consIngr : consumedSet)//faccio una copia in modo da non modificare l'originale
			consumedCopy.add(new Ingredient(consIngr.getName(), consIngr.getUnit(), consIngr.getQuantity()));
		
		Set<Ingredient> groceryList = new HashSet<>();
		for (Ingredient consumedIngr : consumedCopy)
		{
			if (registroBeforeMeal.contains(consumedIngr)) //se ho già un ingrediente in magazzino
			{
				for (Ingredient regIngr : registroBeforeMeal)
				{
					if (regIngr.equals(consumedIngr))
					{
						double q = consumedIngr.getQuantity() - regIngr.getQuantity(); //calcolo quanto devo effettivamente comprare
						if (q >= 0)
						{
							consumedIngr.setQuantity(q);
							groceryList.add(consumedIngr);
						}
						break;
					}
				}
			}
			else
				groceryList.add(consumedIngr); //se non ho un ingrediente in magazzino devo comprare l'intera quantità che consumo
		}
		return groceryList;
	}
	
	/**
	 * Metodo che calcola il magazzino prima di un pasto
	 * in base al magazzino del giorno precedente e alla lista della spesa
	 * @param groceryList lista della spesa
	 */
	private void generateRegistroBeforeMeal (Set<Ingredient> groceryList)
	{
		Set<Ingredient> registroBeforeMeal = new HashSet<>();
		for (Ingredient regIngr : model.getRegistroBeforeMeal()) //faccio una copia per non modificare l'originale
			registroBeforeMeal.add(new Ingredient(regIngr.getName(), regIngr.getUnit(), regIngr.getQuantity()));

		for (Ingredient grocery : groceryList)
		{
			if (registroBeforeMeal.contains(grocery))
			{
				for (Ingredient regIngr : registroBeforeMeal)
				{
					if (regIngr.equals(grocery))
					{
						//se un ingrediente è nella lista e nel magazzino lo addo al magazzino considerando anche la quantità residua nello stesso
						regIngr.setQuantity(grocery.getQuantity() + regIngr.getQuantity());
						break;
					}
				}
			}
			else //se un ingrediente è nella lista ma non nel magazzino lo addo al magazzino
				registroBeforeMeal.add(grocery);
		}
		model.setRegistroBeforeMeal(registroBeforeMeal);
	}
	
	/**
	 * Metodo che genera il magazzino dopo il pasto
	 * calcolandolo dal magazzino prima del pasto
	 * @param consumedList lista d'ingredienti consumati nel pasto
	 */
	private void generateRegistroAfterMeal (Set<Ingredient> consumedList)
	{
		Set<Ingredient> registroBeforeMeal = new HashSet<>(model.getRegistroBeforeMeal());
		Set<Ingredient> registroAfterMeal = new HashSet<>();

		for (Ingredient beforeIngr : registroBeforeMeal)
		{
			double q = beforeIngr.getQuantity();
			for (Ingredient consumedIngr : consumedList) {
				if (beforeIngr.equals(consumedIngr)) {
					q -= consumedIngr.getQuantity(); //calcolo quanta quantità mi è rimasta
					break;
				}
			}
			if (q > 0)
				registroAfterMeal.add(new Ingredient(beforeIngr.getName(), beforeIngr.getUnit(), q)); //se è maggiore di 0 la salvo
		}
		model.setRegistroAfterMeal(registroAfterMeal);
	}

	/**
	 * Metodo che salva un nuovo utente tramite i dati della GUI
	 * @param name          nome dell'utente
	 * @param password      password
	 * @param confPassword  conferma della password
	 * @param manager       true se ha accesso alla tab manager, false altrimenti
	 * @param employee      true se ha accesso alla tab employee, false altrimenti
	 * @param storageWorker true se ha accesso alla tab storageWorker, false altrimenti
	 * @return true se il salvataggio è avvenuto, false altrimenti
	 */
	public boolean saveUser (String name, String password, String confPassword, boolean manager, boolean employee, boolean storageWorker)
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
							erSet.errorSetter(INVALID_USERNAME);
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
						writer.writePeople(model.getUsers()); //salvo la lista aggiornata
						return true;
					}
					else
						erSet.errorSetter(INVALID_PASSWORD);
				}
			}
			else
				erSet.errorSetter(INVALID_FORMAT);
		}
		else
			erSet.errorSetter(NOT_ENOUGHT_ROLES);
		return false;
	}
	
	/**
	 * Metodo che gestisce il login di un utente tramite utente e password
	 * @param name     nome utente
	 * @param password password inserita
	 * @return true se il login è andato a buon fine, false altrimenti
	 */
	public boolean login (String name, String password)
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
						erSet.errorSetter(INVALID_PASSWORD);
					break;
				}
			}
			if (!found)
				erSet.errorSetter(INVALID_USERNAME);
		}
		else
			erSet.errorSetter(INVALID_FORMAT);
		return false;
	}
	
	/**
	 * Controlla se l'utente ha i permessi per accedere ad un certo ruolo
	 * @param role ruolo che devo controllare
	 * @return true se l'utente ha i permessi, false altrimenti
	 */
	public boolean checkPermission (String role)
	{
		boolean out = switch (role)
				{
					case "manager" -> model.getTheUser().isManager();
					case "employee" -> model.getTheUser().isEmployee();
					case "warehouse worker" -> model.getTheUser().isStorageWorker();
					default -> false;
				};
		if (!out)
			erSet.errorSetter(NO_PERMISSION); //in caso non abbia i permessi giusto avviso l'utente
		return out;
	}
}
