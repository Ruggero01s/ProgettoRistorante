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
    }

    public void saveConfig() {
        try {
            model.setCapacity(Integer.parseInt(gui.cfgInputArea1.getText()));
            model.setWorkPersonLoad(Integer.parseInt(gui.cfgInputArea2.getText()));
            gui.resetInputAreas();
        } catch (NumberFormatException e)//todo gestire
        {
            System.out.println("errore, formato non valido");
            e.printStackTrace();
        }

    }

    public void saveDrinks() {
        try {
            model.drinksMap.put(gui.cfgInputArea1.getText(), Double.parseDouble(gui.cfgInputArea2.getText()));
            gui.resetInputAreas();
        } catch (NumberFormatException e)//todo gestire
        {
            System.out.println("errore, formato non valido");
            e.printStackTrace();
        }
    }

    public void saveFoods() {
        try {
            model.extraFoodsMap.put(gui.cfgInputArea1.getText(), Double.parseDouble(gui.cfgInputArea2.getText()));
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
        System.out.println(model.recipesList);
    }

    public void printDishes() {
        System.out.println(model.dishesList);
    }

    public void saveRecipe() {
        try
        {
            HashMap<String, Double> ingredientQuantityMap = new HashMap<>();
            String text = gui.cfgInputArea3.getText();
            String[] lines = text.split("\n");
            for (String line: lines){
                String[] words = line.split(":");
                ingredientQuantityMap.put(words[0], Double.parseDouble(words[1]));
            }
            model.recipesList.add(new Recipe(gui.cfgInputArea1.getText(), ingredientQuantityMap, Integer.parseInt(gui.cfgInputArea2.getText()), Double.parseDouble(gui.cfgInputArea4.getText())));
            gui.resetInputAreas();
        }
        catch (NumberFormatException e)//todo gestire
        {
            System.out.println("errore, formato non valido");
            e.printStackTrace();
        }
    }

    public void saveDish() {
    }
}
