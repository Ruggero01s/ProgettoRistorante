import java.util.HashMap;
import java.util.Map;

public class Controller {
    Model model = new Model();
    GUI gui = new GUI(this);

    int idDish = 0;
    int idRecipe = 0;

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
            String[] inputs = getUserInputs();
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
        System.out.println(model.recipesMap);
    }

    public void printDishes() {
        System.out.println(model.dishesMap);
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
            model.recipesMap.put(new Recipe(gui.cfgInputArea1.getText(), ingredientQuantityMap, Integer.parseInt(gui.cfgInputArea2.getText()), Double.parseDouble(gui.cfgInputArea4.getText())), idRecipe++);
            gui.resetInputAreas();
        }
        catch (NumberFormatException e)//todo gestire
        {
            System.out.println("errore, formato non valido");
            e.printStackTrace();
        }
    }

    public void saveDish() {
        try{
            if(gui.checkboxpermanente) {
                for (Map.Entry<Recipe, Integer> entry: model.recipesMap.entrySet()) {
                    if (entry.getKey().id.equals(gui.cfgInputArea2))
                    model.dishesMap.put(new Dish(gui.cfgInputArea1.getText(), entry.getKey(), "1/1", "31/12"), idDish++);
                }
            }
        }catch (Exception e){

        }
    }
}
