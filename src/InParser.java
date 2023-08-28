import javax.management.InstanceNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class InParser implements InputParser {

    Controller controller;
    Model model;
    ErrorSetter erSet;
    RestaurantRepository repo;

    public InParser(Controller ctrl, Model model, ErrorSetter erSet){
        this.controller = ctrl;
        this.model = model;
        this.repo = model;
        this.erSet = erSet;
    }

    /**
     * Metodo che legge e salva i config dalla GUI
     */
    public boolean parseConfig(String inputCapacity, String inputWorkload, String inputPercent, String todayString){
        try {
            int capacity = Integer.parseInt(inputCapacity); //converto i parametri numerici
            int workload = Integer.parseInt(inputWorkload);
            int percent = Integer.parseInt(inputPercent);
            if (controller.checkValidityOfSetting(capacity, workload)) {
                if (!controller.checkDate(todayString)) //check per correttezza della data
                {
                    erSet.errorSetter(Controller.INVALID_DATE);
                    return false;
                } else {
                    DateOur today = controller.inputToDate(todayString);
                    if (capacity <= 0 || workload <= 0) //check parametri numerici
                    {
                        erSet.errorSetter(Controller.MIN_ZERO);
                        return false;
                    }
                    else if (percent > 10 || percent < 0) {
                        erSet.errorSetter(Controller.WRONG_SURPLUS);
                        return false;
                    }
                    else {
                        //salvo tutti i dati e aggiorno la GUI
                        model.setCapacity(capacity);
                        model.setWorkPersonLoad(workload);
                        model.setToday(today);
                        model.setIncrement(percent);
                        controller.calculateWarehouse(false);
                        return true;
                    }
                }
            } else {
                erSet.errorSetter(Controller.TOO_LOW_CONFIG);
                return false;
            }
        } catch (NumberFormatException e) {
            erSet.errorSetter(Controller.INVALID_FORMAT);
            return false;
        }
    }

    public boolean parseFoods(String input){
        try {
            Map<String, Double> foods = createExtraFoodsMap(input, true);
            repo.addFoods(foods);
            return true;
        }  catch (RuntimeException e) {
            erSet.errorSetter(Controller.WRONG_UNIT);
            return false;
        } catch (Exception e) {
            erSet.errorSetter(Controller.INVALID_FORMAT);
            return false;
        }
    }

    public boolean parseDrinks(String input){
        try {
            Map<String, Double> drinks = createExtraFoodsMap(input, false);
            repo.addDrinks(drinks);
            return true;
        }  catch (RuntimeException e) {
            erSet.errorSetter(Controller.WRONG_UNIT);
            return false;
        } catch (Exception e) {
            erSet.errorSetter(Controller.INVALID_FORMAT);
            return false;
        }
    }

    public  Map<String, Double> createExtraFoodsMap(String input, boolean isFoods) throws Exception{
        if (input.trim().isEmpty())
            throw new Exception("");
        Map<String, Double> extraFoods = new HashMap<>();
        for (String line : input.split("\n")) {
            if (!line.contains(":")) //controllo il formato della stringa
                throw new Exception("");

            String[] inputSplit = line.split(":");

            if (inputSplit[0].isEmpty())
                throw new Exception(""); //nome non valido
            if (inputSplit.length < 2)
                throw new Exception("");

            double quantity = Double.parseDouble(inputSplit[1]);

            if(isFoods) {
                if (inputSplit[2].toLowerCase().contains("l"))
                    throw new RuntimeException("");
                quantity = controller.checkUnitExtraFoods(inputSplit[2], quantity); //conversione dell'unità
            }else {
                if (inputSplit[2].toLowerCase().contains("g"))
                    throw new RuntimeException("");
                quantity = controller.checkUnit(inputSplit[2], quantity); //conversione dell'unità
            }
            if (quantity <= 0) //quantità non valida
                erSet.errorSetter(Controller.MIN_ZERO);
            else
                extraFoods.put(inputSplit[0].toLowerCase(), quantity);
        }
        return extraFoods;
    }

    public boolean parseRecipe(String inputName, String inputIngredients, String inputPortions, String inputWorkload){
        try {
            Set<Ingredient> ingredientQuantitySet = new HashSet<>();
            String[] lines = inputIngredients.split("\n");

            if (inputName.trim().isEmpty()) //controllo validità del nome
                throw new RuntimeException("");

            boolean err = false;
            double quantity;
            for (String line : lines) {
                if (!line.contains(":")) //controllo validità della stringa
                    throw new RuntimeException("");

                String[] words = line.split(":");

                if (words[0].trim().isEmpty()) //controllo validità del nome
                    throw new RuntimeException("");

                if (words.length < 3) //controllo lunghezza stringa
                    throw new RuntimeException("");

                quantity = Double.parseDouble(words[1]);
                quantity = controller.checkUnit(words[2], quantity);
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
                if (!ingredientQuantitySet.add(new Ingredient(words[0].toLowerCase(), unit, quantity))) {
                    erSet.errorSetter(Controller.DOUBLE_INGREDIENT);
                    throw new Exception("");
                }
            }

            int portions = Integer.parseInt(inputPortions);
            double workLoad = Double.parseDouble(inputWorkload);
            if (workLoad >= model.getWorkPersonLoad()) //se il workload è impossibilmente alto da soddisfare
            {
                erSet.errorSetter(Controller.WORKLOAD_TOO_HIGH);
                return false;
            }
            else {
                if (portions <= 0 || workLoad <= 0 || err) {
                    erSet.errorSetter(Controller.MIN_ZERO);
                    return false;
                }
                else {
                    if (!repo.isDuplicate(inputName)) {
                        if (repo.add(new Recipe(inputName, ingredientQuantitySet, portions, workLoad)))
                            return true;
                         else {
                            erSet.errorSetter(Controller.EXISTING_NAME);
                            return false;
                        }
                    } else {
                        erSet.errorSetter(Controller.EXISTING_NAME);
                        return false;
                    }
                }
            }
        } catch (RuntimeException e) {
            erSet.errorSetter(Controller.INVALID_FORMAT);
            return false;
        } catch (Exception e) {
            erSet.errorSetter(Controller.DOUBLE_INGREDIENT);
            return false;
        }
    }

    public boolean parseDish(String inputName, String inputRecipe, String inputStartDate, String inputEndDate, boolean perm, boolean seasonal){
        try
        {
            if (inputName.trim().isEmpty()) //controllo validità del nome
                throw new NumberFormatException();

            if (!perm) // se non è permanente controllo le date
            {
                if (!controller.checkDate(inputStartDate) || !controller.checkDate(inputEndDate)) {
                    erSet.errorSetter(Controller.INVALID_DATE);
                    return false;
                }
            } else
            {
                inputStartDate = "01/01/1444";
                inputEndDate = "31/12/1444";
            }
            try {
                Recipe recipe = repo.findRecipe(inputRecipe);
                Dish temp = new Dish(inputName, recipe, inputStartDate, inputEndDate, seasonal, perm);
                if (!repo.isDuplicate(inputName)) {
                    if (repo.add(temp)) {
                        return true;
                    } else erSet.errorSetter(Controller.EXISTING_NAME);
                } else erSet.errorSetter(Controller.EXISTING_NAME);
            } catch (InstanceNotFoundException e) {
                erSet.errorSetter(Controller.NO_RECIPE);
            }
        } catch (Exception e) {
            erSet.errorSetter(Controller.INVALID_FORMAT);
        }
        return false;
    }

    public boolean parseMenu(String inputName, String inputs, String inputStartDate, String inputEndDate, boolean permanent, boolean seasonal){
        try {
            if (inputName.trim().isEmpty()) //controllo validità del nome
                throw new NumberFormatException();

            String[] inputList = inputs.split("\n");

            Set<Dish> dishesForMenu = new HashSet<>();

            if (!permanent) //se non è permanente controllo la validità delle date
            {
                if (!controller.checkDate(inputStartDate) || !controller.checkDate(inputEndDate)) {
                    erSet.errorSetter(Controller.INVALID_DATE);
                    return false;
                }
            } else {
                inputStartDate = "01/01/1444";
                inputEndDate = "31/12/1444";
            }
            boolean dishNotFound = false;
            for (String s : inputList) {
                if (!s.trim().isEmpty()) {
                    try {
                        dishesForMenu.add(repo.findDish(s));
                    } catch (InstanceNotFoundException e) {
                        dishNotFound = true;
                    }
                }
            }
            if (dishNotFound) //se almeno un piatto prima non è stato trovato da errore
            {
                erSet.errorSetter(Controller.NO_DISH);
                return false;
            }
            else {
                ThematicMenu temp = new ThematicMenu(inputName, inputStartDate, inputEndDate, dishesForMenu, seasonal, permanent); //creo il menu
                if (temp.getWorkThematicMenuLoad() <= ((double) model.getWorkPersonLoad() * 4 / 3)) //controllo se il workLoad è giusto
                {
                    if (!repo.isDuplicate(temp.getName())) //if (valid)
                    {
                        if (repo.add(temp)) //aggiorno la GUI
                        {
                            return true;
                        } else erSet.errorSetter(Controller.EXISTING_NAME);
                    } else erSet.errorSetter(Controller.EXISTING_NAME);
                } else erSet.errorSetter(Controller.THICC_MENU);
            }
        } catch (RuntimeException e) {
            erSet.errorSetter(Controller.INVALID_DATE);
        } catch (Exception e) {
            erSet.errorSetter(Controller.INSUFFICENT_DISH);
        }
        return false;
    }

    public boolean parseBooking(String name, String dateString, String numberString, String orderString){
        try {
            int number = Integer.parseInt(numberString);
            DateOur date = controller.inputToDate(dateString);
            if (date != null && date.getDate().after(model.getToday().getDate())) //controllo che la data abbia un senso
            {
                int workload = 0;
                HashMap<Dish, Integer> order = controller.inputToOrder(orderString, date, number);
                if (!order.isEmpty()) {
                    if (number > 0) //il numero deve essere maggiore di 0
                    {
                        for (Map.Entry<Dish, Integer> dish : order.entrySet())
                            workload += dish.getKey().getRecipe().getWorkLoadPortion() * dish.getValue(); //calcolo il workload di questo giorno
                        return controller.manageBooking(name, date, number, workload, order); //se la prenotazione viene salvata ritorno true
                    } else erSet.errorSetter(Controller.MIN_ZERO);
                }
            } else erSet.errorSetter(Controller.NOT_TODAY);
        } catch (NumberFormatException e) {
            erSet.errorSetter(Controller.INVALID_FORMAT);
        }
        return false;
    }

    public boolean parseUser(String name, String password, String confPassword, boolean manager, boolean employee, boolean storageWorker){
        if (manager || employee || storageWorker) //deve avere almeno un ruolo, altrimenti non può accedere a nulla
        {
            if (!name.trim().isEmpty() && !password.trim().isEmpty()) //controllo la loro validità
            {
                if (password.equals(confPassword)) //se le password coincidono
                {
                    User user = new User(name, password, manager, employee, storageWorker); //salvo l'utente
                    user.hashAndSaltPassword(); //hasho la password
                    if (repo.add(user)) {
                        return true;
                    } else erSet.errorSetter(Controller.INVALID_USERNAME);
                } else erSet.errorSetter(Controller.INVALID_PASSWORD);
            } else erSet.errorSetter(Controller.INVALID_FORMAT);
        } else erSet.errorSetter(Controller.NOT_ENOUGHT_ROLES);
        return false;
    }
}