import java.util.HashMap;

public class Controller {
    Model model = new Model();
    GUI gui = new GUI(this);

    public void init() {
        gui.init();
    }

    public void writeAll() {
        Writer.writeDrinks(model.drinksMap);
        Writer.writeExtraFoods(model.extraFoodsMap);
        Writer.writeConfigBase(model.capacity, model.workPersonLoad, model.workResturantLoad);
        Writer.writeDishes(model.dishesSet);
    }

    public void saveConfig() {
        try {
            String inputCapacity = gui.cfgInputArea1.getText();
            String inputWorkload = gui.cfgInputArea2.getText();

            model.setCapacity(Integer.parseInt(inputCapacity));
            model.setWorkPersonLoad(Integer.parseInt(inputWorkload));
            gui.resetInputAreas();
        } catch (NumberFormatException e)//todo gestire
        {
            System.out.println("errore, formato non valido");
            e.printStackTrace();
        }

    }

    public void saveDrinks() {
        try {
            String inputName = gui.cfgInputArea1.getText();
            String inputQuantityPerson = gui.cfgInputArea2.getText();

            model.drinksMap.put(inputName, Double.parseDouble(inputQuantityPerson));
            gui.resetInputAreas();
        } catch (NumberFormatException e)//todo gestire
        {
            System.out.println("errore, formato non valido");
            e.printStackTrace();
        }
    }

    public void saveFoods() {
        try {
            String inputName = gui.cfgInputArea1.getText();
            String inputQuantityPerson = gui.cfgInputArea2.getText();

            model.extraFoodsMap.put(inputName, Double.parseDouble(inputQuantityPerson));
            gui.resetInputAreas();
        } catch (NumberFormatException e)//todo gestire
        {
            System.out.println("errore, formato non valido");
            e.printStackTrace();
        }
    }

    public void printDrinks() {
        System.out.println(model.drinksMap);
    }

    public void printExtraFoods() {
        System.out.println(model.extraFoodsMap);
    }

    public void printRecipes() {
        System.out.println(model.recipesSet);
    }

    public void printDishes() {
        System.out.println(model.dishesSet);
    }

    public void saveRecipe() {
        try {
            String inputName = gui.cfgInputArea1.getText();
            String inputIngredients = gui.cfgInputArea3.getText();
            String inputPortions = gui.cfgInputArea2.getText();
            String inputWorkload = gui.cfgInputArea4.getText();

            HashMap<String, Double> ingredientQuantityMap = new HashMap<>();
            String text = inputIngredients;
            String[] lines = text.split("\n");
            for (String line : lines) {
                String[] words = line.split(":");
                ingredientQuantityMap.put(words[0], Double.parseDouble(words[1]));
            }
            model.recipesSet.add(new Recipe(inputName, ingredientQuantityMap, Integer.parseInt(inputPortions), Double.parseDouble(inputWorkload)));
            gui.resetInputAreas();
        } catch (NumberFormatException e)//todo gestire
        {
            System.out.println("errore, formato non valido");
            e.printStackTrace();
        }
    }

    public void saveDish() {
        try {
            String inputName = gui.cfgInputArea1.getText();
            String inputIngredients = gui.cfgInputArea2.getText();

            String inputStartDate = gui.cfgInputArea3.getText();
            String inputEndDate = gui.cfgInputArea4.getText();

            if (gui.cfgDishPermanentRadio.isSelected()) {
                inputStartDate = "1/1";
                inputEndDate = "31/12";
            }
            for (Recipe r : model.recipesSet) {
                if (r.id.equals(inputIngredients)) {
                    model.dishesSet.add(new Dish(inputName, r, inputStartDate, inputEndDate));
                    break;
                }
            gui.resetInputAreas();
            }
    } catch(Exception e)
    {
        System.out.println("errore, saveDish");
        e.printStackTrace();
    }
}
}
