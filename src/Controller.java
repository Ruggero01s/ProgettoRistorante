import javax.management.InstanceNotFoundException;
import java.util.*;

public class Controller implements SaveData, DataManager, WriterManager, DateManager
{
    private Model model;
    private RestaurantRepository repo;
    private GUI gui;
    private ErrorSetter erSet;
    private Login loginner;
    private InputParser inputParser;
    private Updater updater;
    private Read reader;
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
    public static final int CANNOT_DELETE = 28;
    public static final int ERROR_IN_WRITING = 29;

    /**
     * Metodo d'inizializzazione
     */
    public void init() {
        model = new Model();
        repo = model;
        gui = new SimpleUI(this, this, this, this, this);
        reader = new Reader(repo);
        erSet = (ErrorSetter) gui;
        loginner = new Loginner(repo, erSet);
        inputParser = new InParser(this, model, erSet);
        updater = new UpdateUI(gui);
        loadModel();
        gui.init(model.getToday().getStringDate());
        calculateWarehouse(true);

        /*
        TestBlackBox saveDishTester = new TestBlackBox(this); //TODO TEST
        saveDishTester.testingNames();
        saveDishTester.testingDates();
        saveDishTester.testingRecipes();*/
    }

    /**
     * Metodo che calcola e salva tutti i dati del magazziniere, ovvero:
     * lista della spesa, magazzino prima del pasto e magazzino dopo il pasto
     *
     * @param load true se il magazzino è gia generato e non deve essere calcolato, false altrimenti
     */
    public void calculateWarehouse(boolean load) {
        Set<Ingredient> consumedSet = new HashSet<>(generateConsumedList());
        Set<Ingredient> grocerySet = generateGroceryList(consumedSet);
        if (!load)
            generateRegistroBeforeMeal(grocerySet);
        generateRegistroAfterMeal(consumedSet);
        updater.updateWarehouse(grocerySet, model.getRegistroBeforeMeal(), model.getRegistroAfterMeal());

    }

    /**
     * Inizializzazione del model tramite i reader
     */
    private void loadModel() {
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
        updater.updateRecipes(model.getRecipesSet());
        updater.updateDrinkList(model.getDrinksMap());
        updater.updateDishes((model.getDishesSet()));
        updater.updateFoodList(model.getExtraFoodsMap());
        updater.updateMenus(model.getThematicMenusSet());

        updater.updateMenuCarta(model.getDishesSet(), model.getToday());
        updater.updateBookedDates(model.getBookingMap());
        updater.updateConfig(model.getCapacity(), model.getWorkRestaurantLoad(), model.getIncrement(), model.getWorkPersonLoad(), model.getToday());
        oldBooking(); //elimino le vecchie prenotazioni
    }


    /**
     * Metodo che svuota la memoria
     */
    public void clearInfo() {
        model.clear();
        updater.updateConfig(0, 0, 5, 0, new DateOur("11","11","1444"));
        updater.updateRecipes(model.getRecipesSet());
        updater.updateDishes(model.getDishesSet());
        updater.updateMenus(model.getThematicMenusSet());
        updater.updateDrinkList(model.getDrinksMap());
        updater.updateFoodList(model.getExtraFoodsMap());
        updater.updateMenuCarta(model.getDishesSet(),model.getToday());
        updater.updateBookedDates(model.getBookingMap());
        updater.updateWarehouse(new HashSet<>(), new HashSet<>(), new HashSet<>());
        gui.logout();

        boolean ok;
        ok = writer.writePeople(model.getUsers());
        ok = ok && writeManager();
        ok = ok && writeBookings();
        ok = ok && writeRegister();
        if (ok)
            gui.confirmClear();
        else
            erSet.errorSetter(ERROR_IN_WRITING);
    }

    /**
     * Cancella le prenotazioni in uno specifico giorno
     *
     * @param input giorno in cui annullare le prenotazioni
     * @return true se la prenotazione è stata cancellata o se non è mai esistita
     */
    public boolean clearBookings(DateOur input) {
        if (repo.clearDayBookings(input)) {
            if (writeBookings()) gui.confirmClear();
            else erSet.errorSetter(ERROR_IN_WRITING);
            updater.updateBookedDates(model.getBookingMap());
            return true;
        } else return false;
    }

    /**
     * Cancella tutte le prenotazioni tranne quelle in data odierna
     */
    public void clearBookings() {
        repo.clearBookings();
        if (writeBookings()) gui.confirmSave();
        else erSet.errorSetter(ERROR_IN_WRITING);
    }

    /**
     * Salvataggio tramite writer di tutti i dati del manager
     */
    public boolean writeManager() {
        boolean ok;
        ok = writer.writeDrinks(model.getDrinksMap());
        ok = ok && writer.writeExtraFoods(model.getExtraFoodsMap());
        ok = ok && writer.writeConfigBase(model.getCapacity(), model.getWorkPersonLoad(), model.getToday(), model.getIncrement());
        ok = ok && writer.writeRecipes(model.getRecipesSet());
        ok = ok && writer.writeDishes(model.getDishesSet());
        return ok && writer.writeThematicMenu(model.getThematicMenusSet());
    }

    /**
     * Metodo che legge e salva i config dalla GUI
     */
    public boolean saveConfig(String inputCapacity, String inputWorkload, String inputPercent, String todayString) {
        if (inputParser.parseConfig(inputCapacity, inputWorkload, inputPercent, todayString)){
            updater.updateConfig(model.getCapacity(), model.getWorkRestaurantLoad(), model.getIncrement(), model.getWorkPersonLoad(),model.getToday());
            return true;
        } else return false;
    }

    /**
     * Metodo che controllo se i nuovi parametri inseriti mantengono valide le prenotazioni già effettuate
     *
     * @param capacity capacità modificata
     * @param workload workload modificato
     * @return true se i parametri mantengono l'integrità delle prenotazioni, false altrimenti
     */
    public boolean checkValidityOfSetting(int capacity, int workload) {
        int usedCapacity, usedWorkload;
        for (Map.Entry<DateOur, List<Booking>> books : model.getBookingMap().entrySet()) //scorro tutte le prenotazioni
        {
            usedCapacity = 0;
            usedWorkload = 0;
            for (Booking booking : books.getValue()) //scorro tutte le prenotazioni di un giorno
            {
                usedWorkload += booking.getWorkload();
                usedCapacity += booking.getNumber();
            }
            if (capacity < usedCapacity || workload * capacity * Model.INCREASE20 < usedWorkload) //controllo la validità dei parametri per ogni giorno prenotato
                return false;
        }
        return true;
    }

    /**
     * Metodo che legge e salva i drinks
     */
    public boolean saveDrinks(String input) {
       if (inputParser.parseDrinks(input)) {
           updater.updateDrinkList(model.getDrinksMap());
           return true;
       } else return false;
    }

    /**
     * Metodo che legge e salva i foods aggiuntivi
     */
    public boolean saveFoods(String input) {
        if(inputParser.parseFoods(input)){
            updater.updateFoodList(model.getExtraFoodsMap());
            return true;
        } else return false;
    }

    /**
     * Metodo che legge e salva le ricette
     */
    public boolean saveRecipe(String inputName, String inputIngredients, String inputPortions, String inputWorkload) {
        if(inputParser.parseRecipe(inputName, inputIngredients, inputPortions, inputWorkload)) {
            updater.updateRecipes(model.getRecipesSet());
            return true;
        } else return false;
    }

    /**
     * Converto e checko la validità delle unità
     *
     * @param unit     unita d'ingresso
     * @param quantity quantità da convertire
     * @return quantità convertita
     */
    public double checkUnit(String unit, Double quantity) {
        if (!(unit.equalsIgnoreCase("g") || unit.equalsIgnoreCase("l"))) //converto solo se l'unità non è grammi o litri
        {
            switch (unit.toLowerCase()) {
                case "kl":
                case "kg":
                    quantity *= 1000.0;
                    break;
                case "hg":
                case "hl":
                    quantity *= 100.0;
                    break;
                case "dag":
                case "dal":
                    quantity *= 10.0;
                    break;
                case "dg":
                case "dl":
                    quantity /= 10.0;
                    break;
                case "cg":
                case "cl":
                    quantity /= 100.0;
                    break;
                case "mg":
                case "ml":
                    quantity /= 1000.0;
                    break;
                default:
                    throw new RuntimeException(""); //in caso di unità non riconosciuta lancio un errore
            }
        }
        return quantity;
    }

    /**
     * Converto e checko la validità delle unità in caso di extra foods
     *
     * @param unit     unita d'ingresso
     * @param quantity quantità da convertire
     * @return quantità convertita in Hg
     */
    public double checkUnitExtraFoods(String unit, Double quantity) {
        if (!(unit.equalsIgnoreCase("hg"))) //converto solo se l'unità non è in Hg
        {
            switch (unit.toLowerCase()) {
                case "kg":
                    quantity *= 10.0;
                    break;
                case "dag":
                    quantity /= 10.0;
                    break;
                case "g":
                    quantity /= 100.0;
                    break;
                case "dg":
                    quantity /= 1000.0;
                    break;
                case "cg":
                    quantity /= 10000.0;
                    break;
                case "mg":
                    quantity /= 100000.0;
                    break;
                default:
                    throw new RuntimeException(""); //in caso di unità non riconosciuta lancio un errore
            }
        }
        return quantity;
    }

    /**
     * Leggo e salvo i piatti
     */
    public boolean saveDish(String inputName, String inputRecipe, String inputStartDate, String inputEndDate, boolean perm, boolean seasonal) {
        if (inputParser.parseDish(inputName, inputRecipe, inputStartDate, inputEndDate, perm, seasonal)){
            updater.updateDishes(model.getDishesSet());
            updater.updateMenuCarta(model.getDishesSet(),model.getToday());//aggiorno il menu
            return true;
        } else return false;
    }

    /**
     * Leggo e salvo i menu tematici
     */
    public boolean saveMenu(String inputName, String inputs, String inputStartDate, String inputEndDate, boolean permanent, boolean seasonal) {
       if (inputParser.parseMenu(inputName, inputs, inputStartDate, inputEndDate, permanent, seasonal))
       {
           updater.updateMenus(model.getThematicMenusSet());
           return true;
       } else return false;
    }


    /**
     * Controllo se una data è valida
     *
     * @param date data da controllare
     * @return true se valida, false altrimenti
     */
    public boolean checkDate(String date) {
        if (date.trim().isEmpty()) //se è vuota non è valida
            return false;
        if (!date.contains("/")) //se non contiene / non è valida
            return false;
        String[] pezzi = date.split("/");
        if (pezzi.length < 3) //se non ha 3 elementi non è valida
            return false;
        if (Integer.parseInt(pezzi[2]) <= 0) //se anno minore di 0 errore
            return false;
        switch (Integer.parseInt(pezzi[1])) //controllo mesi e giorni
        {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                return (Integer.parseInt(pezzi[0]) <= 31 || Integer.parseInt(pezzi[0]) > 0);
            case 2:
                return (Integer.parseInt(pezzi[0]) <= 29 || Integer.parseInt(pezzi[0]) > 0);
            case 4:
            case 6:
            case 9:
            case 11:
                return (Integer.parseInt(pezzi[0]) <= 30 || Integer.parseInt(pezzi[0]) > 0);
            default:
                return false;
        }
    }



    /**
     * Crea una stringa con i dati di un certo menu con nome menuName
     *
     * @param menuName menu da trasformare in stringa
     */
    public void printMenuComp(String menuName) {
        StringBuilder out = new StringBuilder();
        try {
            ThematicMenu menu = repo.findMenu(menuName);
            out.append(menuName).append(" w.").append(menu.getWorkThematicMenuLoad()).append("\n");
            for (Dish d : menu.getDishes()) {
                out.append("    ").append(d.getName()).append("\n");
            }
            gui.selectedMenu(out.toString());
        } catch (InstanceNotFoundException e) {
            erSet.errorSetter(-1);
        }

    }

    /**
     * Salvo il magazzino
     *
     * @return true se il salvataggio è riuscito, false altrimenti
     */
    public boolean writeRegister() {
        return writer.writeRegister(model.getRegistroBeforeMeal());
    }

    /**
     * Metodo che prende una string e la trasforma in data
     *
     * @param input stringa da convertire
     * @return data costruita
     */
    public DateOur inputToDate(String input) {
        String[] bookDates;
        try {
            bookDates = input.split("/");
            return new DateOur(bookDates[0], bookDates[1], bookDates[2]);
        } catch (Exception e) {
            erSet.errorSetter(INVALID_DATE);
            return null;
        }
    }

    /**
     * Metodo che prende dati dalla finestra di creazione ordine e ne estrae un ordine, analizza una stringa spezzandola in righe e cerca il nome del piatto/menu nei vari set esistenti
     *
     * @param in     Stringa in entrata
     * @param date   Data per oggi, per controllare che il piatto trovato sia disponibile
     * @param number numero del piatto/menu ordinato
     * @return un HashMap che contiene per ogni Dish nell'ordine la quantità ordinata
     */
    public HashMap<Dish, Integer> inputToOrder(String in, DateOur date, int number) {
        if (in.trim().isEmpty()) throw new RuntimeException();
        String[] lines = in.split("\n");
        HashMap<Dish, Integer> order = new HashMap<>();
        int count = 0;
        try {
            for (String line : lines)  //per ogni linea
            {
                String[] parts = line.split(":");
                String name = parts[0];
                Integer num = Integer.parseInt(parts[1]);
                if (num <= 0) //errore se il num associato è <=0
                {
                    order.clear();
                    erSet.errorSetter(MIN_ZERO);
                    return order;
                }
                try {
                    ThematicMenu menu = repo.findMenu(name);
                    if (menu.isValid(date)) //controllo della data di disponibilità
                    {
                        count += num;
                        for (Dish dish : menu.getDishes()) // "spacchetta" il menu nei piatti che lo compongono e li aggiunge all'ordine
                        {
                            if (order.containsKey(dish)) //sommando il numero se già presenti
                                order.put(dish, order.get(dish) + num);
                            else
                                order.put(dish, num);
                        }
                    } else {
                        order.clear();
                        erSet.errorSetter(OUT_OF_DATE);
                        return order;
                    }
                } catch (InstanceNotFoundException e) {
                    try {
                        Dish dish = repo.findDish(name);
                        if (dish.isValid(date)) {
                            count += num;
                            if (order.containsKey(dish))
                                order.put(dish, order.get(dish) + num);
                            else
                                order.put(dish, num);
                        } else {
                            order.clear();
                            erSet.errorSetter(INVALID_DATE);
                            return order;
                        }
                    } catch (InstanceNotFoundException e2) {
                        order.clear();
                        erSet.errorSetter(NOT_FOUND);
                        return order;
                    }
                }
            }
            if (count < number) //controlla che ci sia almeno un piatto per persona
            {
                order.clear();
                erSet.errorSetter(ORDER_FOR_TOO_LITTLE);
            }
            return order;
        } catch (NumberFormatException e) //catch l'errore del parseInt iniziale
        {
            erSet.errorSetter(NO_QUANTITY);
            return new HashMap<>();
        } catch (RuntimeException e) //catch l'errore del parseInt iniziale
        {
            erSet.errorSetter(EMPTY_INPUT);
            return new HashMap<>();
        }
    }

    /**
     * Metodo che mostra in GUI tutte le prenotazioni di un giorno
     *
     * @param data giorno da controllare
     */
    public void seeBookings(DateOur data) {
        if (model.getBookingMap().containsKey(data)) {
            ArrayList<Booking> dayBookings = new ArrayList<>(model.getBookingMap().get(data)); //prenotazioni della data
            StringBuilder name = new StringBuilder();
            StringBuilder number = new StringBuilder();
            StringBuilder work = new StringBuilder();
            int capacity = 0, workload = 0;

            for (Booking b : dayBookings) {
                name.append(b.getName()).append("\n");
                number.append(b.getNumber()).append("\n");
                work.append(b.getWorkload()).append("\n");
                capacity += b.getNumber();
                workload += b.getWorkload();
            }
            gui.updateBooking(name.toString().trim(), number.toString().trim(), work.toString().trim(), Integer.toString(model.getCapacity() - capacity), Double.toString(model.getWorkRestaurantLoad() - workload));
        } else
            erSet.errorSetter(NO_BOOKINGS); //non ci sono prenotazioni per quel giorno
    }

    /**
     * Salvo le prenotazioni
     *
     * @return true se il salvataggio è riuscito, false altrimenti
     */
    public boolean writeBookings() {
        return writer.writeBookings(model.getBookingMap());
    }

    /**
     * Metodo che crea una prenotazione dai dati in GUI
     *
     * @param name         nome della prenotazione
     * @param dateString   data sotto forma di stringa
     * @param numberString numero di coperti
     * @param orderString  ordini sotto forma di stringa
     * @return true se la prenotazione è stata salvata, false altrimenti
     */
    public boolean saveBooking(String name, String dateString, String numberString, String orderString) {
       return inputParser.parseBooking(name, dateString, numberString, orderString);
    }

    /**
     * Aggiunge una prenotazione alle prenotazioni del ristorante
     *
     * @param name     nome della prenotazione
     * @param date     data della prenotazione
     * @param number   numero di persone
     * @param workload workload della prenotazione
     * @param order    hashmap di tutti i piatti ordinati
     * @return true se la prenotazione è stata salvata, false altrimenti
     */
    public boolean manageBooking(String name, DateOur date, int number, int workload, HashMap<Dish, Integer> order) {
        ArrayList<Booking> bookings = new ArrayList<>();
        int capacity = number;
        int work = workload;

        if (model.getBookingMap().containsKey(date)) {
            //se ci sono altre prenotazioni per quel giorno
            //calcolo la capacità ed il workload occupati
            bookings = new ArrayList<>(model.getBookingMap().get(date));
            for (Booking b : bookings) {
                capacity += b.getNumber();
                work += b.getWorkload();
            }
        }
        //controllo se c`è posto nel ristorante
        if (capacity <= model.getCapacity() && work <= model.getWorkRestaurantLoad()) {
            bookings.add(new Booking(name, number, workload, order));
            repo.addBookings(date, bookings);
            updater.updateBookedDates(model.getBookingMap());
            return true;
        } else {
            erSet.errorSetter(FULL_RESTURANT);
            return false;
        }
    }

    /**
     * Metodo che dalla GUI crea una lista d'ingredienti da modificare nel magazzino
     *
     * @param text ingredienti da cambiare
     * @return true se è andato a buon fine, false altrimenti
     */
    public boolean warehouseChanges(String text) {
        if (text.trim().isEmpty()) //controllo che il testo sia valido
        {
            erSet.errorSetter(INVALID_FORMAT);
            return false;
        }

        Set<Ingredient> ingredients = new HashSet<>();

        for (String s : text.split("\n")) {
            if (!s.contains(":")) //controllo il formato della riga
            {
                erSet.errorSetter(INVALID_FORMAT);
                return false;
            }

            String[] t = s.split(":");

            if (t.length < 3 || t[0].trim().isEmpty() || t[2].trim().isEmpty()) //controllo il formato della stringa splittata
            {
                erSet.errorSetter(INVALID_FORMAT);
                return false;
            }

            String name = t[0], unit = t[2];
            double quantity = Double.parseDouble(t[1]);
            if (model.getExtraFoodsMap().containsKey(name)) {
                quantity = checkUnitExtraFoods(unit, quantity);
                unit = "Hg";
            } else {
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
        try {
            updateAfterMeal(ingredients);
            return true;
        } catch (RuntimeException e) {
            erSet.errorSetter(Integer.parseInt(e.getMessage()));
        }
        return false;
    }

    /**
     * Metodo che modifica il magazzino dopo il pasto
     *
     * @param ingredients ingredienti da togliere e/o da aggiungere
     * @throws RuntimeException usata per gestire i problemi di formato
     */
    private void updateAfterMeal(Set<Ingredient> ingredients) throws RuntimeException {
        Set<Ingredient> register = new HashSet<>(model.getRegistroAfterMeal());
        for (Ingredient deltaIngredient : ingredients) {
            if (!model.getRegistroBeforeMeal().contains(deltaIngredient)) //se provo ad aggiungere un ingrediente che prima non c'era ho un errore
                throw new RuntimeException(Integer.toString(NO_INGREDIENT));
            for (Ingredient regIngr : model.getRegistroBeforeMeal()) {
                if (regIngr.equals(deltaIngredient)) {
                    if (register.contains(deltaIngredient))  //modifico il registro post pasto
                    {
                        for (Ingredient ingredient : register) {
                            if (ingredient.equals(deltaIngredient)) {
                                if (!ingredient.getUnit().equals(deltaIngredient.getUnit()))
                                    throw new RuntimeException(Integer.toString(WRONG_UNIT));
                                double newIngr = ingredient.getQuantity() + deltaIngredient.getQuantity();

                                if (newIngr < 0)
                                    register.remove(ingredient);
                                else {
                                    if (newIngr > regIngr.getQuantity())
                                        throw new RuntimeException(Integer.toString(INVALID_QUANTITY)); //se la quantità che torna dalla cucina è maggiore di quella che è uscita c'è un errore
                                    ingredient.setQuantity(newIngr);
                                    register.add(ingredient);
                                }
                                break;
                            }
                        }
                    } else {
                        if (deltaIngredient.getQuantity() >= 0 && deltaIngredient.getQuantity() <= regIngr.getQuantity())
                            if (deltaIngredient.getUnit().equals(regIngr.getUnit()))
                                register.add(deltaIngredient);
                            else throw new RuntimeException(Integer.toString(WRONG_UNIT));
                        else
                            throw new RuntimeException(Integer.toString(INVALID_QUANTITY));
                    }
                }
            }
        }
        model.setRegistroAfterMeal(register);
        updater.updateWareReturnList(model.getRegistroAfterMeal());
    }

    /**
     * Metodo che si occupa delle operazioni necessarie per il cambio di giorno
     */
    public void nextDay() {
        model.nextDay();

        //update GUI
        updater.updateDishes(model.getDishesSet());
        updater.updateMenus(model.getThematicMenusSet());
        gui.nextDay(model.getToday().getStringDate());
        updater.updateConfig(model.getCapacity(), model.getWorkRestaurantLoad(), model.getIncrement(), model.getWorkPersonLoad(), model.getToday());
        updater.updateMenuCarta(model.getDishesSet(),model.getToday());

        oldBooking(); //cancello le vecchie prenotazioni

        //aggiorno e calcolo magazzino e lista della spesa
        model.setRegistroBeforeMeal(model.getRegistroAfterMeal());
        calculateWarehouse(false);
    }

    /**
     * Metodo che rimuove tutte le prenotazioni scadute
     */
    private void oldBooking() {
        repo.deleteOldBookings();
        updater.updateBookedDates(model.getBookingMap());
    }

    /**
     * Metodo che genera una lista d'ingredienti consumati nel giorno di oggi
     *
     * @return set d'ingredienti consumati
     */
    private Set<Ingredient> generateConsumedList() {
        Set<Ingredient> consumedSet = new HashSet<>();
        Set<Ingredient> drinks = new HashSet<>();
        Set<Ingredient> foods = new HashSet<>();
        if (model.getBookingMap().containsKey(model.getToday())) {
            Map<Dish, Integer> allDish = new HashMap<>();
            for (Booking booking : model.getBookingMap().get(model.getToday())) {
                foods = new HashSet<>(generateConsumedFoods(foods, booking));
                drinks = new HashSet<>(generateConsumedDrinks(drinks, booking));
                for (Map.Entry<Dish, Integer> dish : booking.getOrder().entrySet()) //calcolo quanti piatti di ogni tipo devo cucinare oggi
                {
                    if (allDish.containsKey(dish.getKey()))
                        allDish.put(dish.getKey(), dish.getValue() + allDish.get(dish.getKey()));
                    else
                        allDish.put(dish.getKey(), dish.getValue());
                }
            }
            for (Map.Entry<Dish, Integer> dish : allDish.entrySet()) {
                Recipe recipe = dish.getKey().getRecipe();
                for (Ingredient ingredient : recipe.getIngredients()) {
                    double multi = (dish.getValue() / (double) recipe.getPortions()); //calcolo la proporzione d'ingredienti da usare
                    consumedSet = new HashSet<>(addIngredient(consumedSet, ingredient, multi));
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
     * Genera il set di ingredienti per gli extraFoods
     * @param booking prenotazioni di quel giorno
     * @return foods modificati
     */
    private Set<Ingredient> generateConsumedFoods(Set<Ingredient> foods, Booking booking) {
        for (Map.Entry<String, Double> entry : model.getExtraFoodsMap().entrySet()) //aggiungo i foods alla lista della spesa
        {
            Ingredient i = new Ingredient(entry.getKey(), "Hg", Math.ceil(entry.getValue() * booking.getNumber()));
            foods = new HashSet<>(addIngredient(foods, i, 1));
        }
        return foods;
    }

    /**
     * Aggiunge gli ingredienti alla lista da consumare, sommando eventualmente le quantità
     * @param ingredientSet ingredienti da consumare
     * @param i ingrediente da inserire
     * @return set con l'ingrediente aggiunto
     */
    private Set<Ingredient> addIngredient(Set<Ingredient> ingredientSet, Ingredient i, double multi) {
        if (ingredientSet.contains(i)) {
            for (Ingredient ingredient : ingredientSet) {
                if (ingredient.equals(i)) {
                    ingredient.setQuantity(ingredient.getQuantity() + multi * i.getQuantity()); //sommo gli ingredient in caso esistano già nel set
                    break;
                }
            }
        } else {
            i.setQuantity(multi * i.getQuantity());
            ingredientSet.add(i); //aggiungo gli ingredient in caso non ci siano già
        }
        return ingredientSet;
    }

    /**
     * Genera il set di ingredienti per i drinks
     * @param booking prenotazioni di quel giorno
     * @return drinks modificati
     */
    private Set<Ingredient> generateConsumedDrinks(Set<Ingredient> drinks, Booking booking) {
        for (Map.Entry<String, Double> entry : model.getDrinksMap().entrySet()) //aggiungo i drink alla lista della spesa
        {
            Ingredient i = new Ingredient(entry.getKey(), "L", Math.ceil(entry.getValue() * booking.getNumber()));
            drinks = new HashSet<>(addIngredient(drinks, i, 1));
        }
        return drinks;
    }

    /**
     * Metodo che genera la lista della spesa in base agli ingredienti
     * consumati oggi e quelli disponibili in magazzino
     *
     * @param consumedSet ingredienti consumati oggi
     * @return lista della spesa
     */
    private Set<Ingredient> generateGroceryList(Set<Ingredient> consumedSet) {
        Set<Ingredient> registroBeforeMeal = new HashSet<>(model.getRegistroBeforeMeal());
        Set<Ingredient> consumedCopy = new HashSet<>();

        for (Ingredient consIngr : consumedSet)//faccio una copia in modo da non modificare l'originale
            consumedCopy.add(new Ingredient(consIngr.getName(), consIngr.getUnit(), consIngr.getQuantity()));

        Set<Ingredient> groceryList = new HashSet<>();
        for (Ingredient consumedIngr : consumedCopy) {
            if (registroBeforeMeal.contains(consumedIngr)) //se ho già un ingrediente in magazzino
            {
                for (Ingredient regIngr : registroBeforeMeal) {
                    if (regIngr.equals(consumedIngr)) {
                        double q = consumedIngr.getQuantity() - regIngr.getQuantity(); //calcolo quanto devo effettivamente comprare
                        if (q >= 0) {
                            consumedIngr.setQuantity(q);
                            groceryList.add(consumedIngr);
                        }
                        break;
                    }
                }
            } else
                groceryList.add(consumedIngr); //se non ho un ingrediente in magazzino devo comprare l'intera quantità che consumo
        }
        return groceryList;
    }

    /**
     * Metodo che calcola il magazzino prima di un pasto
     * in base al magazzino del giorno precedente e alla lista della spesa
     *
     * @param groceryList lista della spesa
     */
    private void generateRegistroBeforeMeal(Set<Ingredient> groceryList) {
        Set<Ingredient> registroBeforeMeal = new HashSet<>();
        for (Ingredient regIngr : model.getRegistroBeforeMeal()) //faccio una copia per non modificare l'originale
            registroBeforeMeal.add(new Ingredient(regIngr.getName(), regIngr.getUnit(), regIngr.getQuantity()));

        for (Ingredient grocery : groceryList) {
            if (registroBeforeMeal.contains(grocery)) {
                for (Ingredient regIngr : registroBeforeMeal) {
                    if (regIngr.equals(grocery)) {
                        //se un ingrediente è nella lista e nel magazzino lo addo al magazzino considerando anche la quantità residua nello stesso
                        regIngr.setQuantity(grocery.getQuantity() + regIngr.getQuantity());
                        break;
                    }
                }
            } else //se un ingrediente è nella lista ma non nel magazzino lo addo al magazzino
                registroBeforeMeal.add(grocery);
        }
        model.setRegistroBeforeMeal(registroBeforeMeal);
    }

    /**
     * Metodo che genera il magazzino dopo il pasto
     * calcolandolo dal magazzino prima del pasto
     *
     * @param consumedList lista d'ingredienti consumati nel pasto
     */
    private void generateRegistroAfterMeal(Set<Ingredient> consumedList) {
        Set<Ingredient> registroBeforeMeal = new HashSet<>(model.getRegistroBeforeMeal());
        Set<Ingredient> registroAfterMeal = new HashSet<>();

        for (Ingredient beforeIngr : registroBeforeMeal) {
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
     *
     * @param name          nome dell'utente
     * @param password      password
     * @param confPassword  conferma della password
     * @param manager       true se ha accesso alla tab manager, false altrimenti
     * @param employee      true se ha accesso alla tab employee, false altrimenti
     * @param storageWorker true se ha accesso alla tab storageWorker, false altrimenti
     * @return true se il salvataggio è avvenuto, false altrimenti
     */
    public boolean saveUser(String name, String password, String confPassword, boolean manager, boolean employee, boolean storageWorker) {
        if (inputParser.parseUser(name, password, confPassword, manager, employee, storageWorker)) {
            if (writer.writePeople(model.getUsers())) {
                gui.confirmSave();
                return true;
            }// salvo la nuova lista
            else {
                erSet.errorSetter(Controller.ERROR_IN_WRITING);
            }
        }
        return false;
    }

    /**
     * metodo che chiama il loginner per il check dei permessi
     * @param role ruolo da controllare
     * @return true se l'utente ha permessi, false altrimenti
     */
    public boolean checkPermission(String role) {
        return loginner.checkPermission(role, model.getCurrentUser());
    }

    /**
     * metodo che chiama il loginner per effettuare il login
     * @param name nome utente
     * @param password password
     * @return true se il login è valido, false altrimenti
     */
    public boolean login(String name, String password) {
        User user = loginner.login(name, password);
        if (user != null)
        {
            model.setCurrentUser(user);
            gui.login(); //cambio stato alla GUI
            return true;
        }
        else return false;
    }
}
