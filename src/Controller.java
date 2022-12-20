public class Controller
{
    Model model = new Model();
    GUI gui = new GUI(this);

    public void init()
    {
        gui.init();
    }

    public void writeAll()
    {
        Writer.writeDrinks(model.drinks);
        Writer.writeExtraFoods(model.extraFoods);
        Writer.writeConfigBase(model.capacity, model.workPersonLoad, model.workResturantLoad);
    }

    public void saveConfig ()
    {
        model.setCapacity(Integer.parseInt(gui.cfgBaseCapArea.getText()));
        model.setWorkPersonLoad(Integer.parseInt(gui.cfgBaseIndivualWorkloadArea.getText()));
    }

    public void saveDrinks()
    {
       model.drinks.put(gui.cfgDrinksNameArea.getText(), Double.parseDouble(gui.cfgDrinksQuantityArea.getText()));
    }

    public void saveFoods()
    {
        model.extraFoods.put(gui.cfgFoodNameArea.getText(), Double.parseDouble(gui.cfgFoodQuantityArea.getText()));
    }

    public void printDrinks()
    {
        model.getDrinks().forEach((name, quantity) -> {
            //setText
            //TODO
        });
    }

    public void printExtraFoods ()
    {
        model.getExtraFoods().forEach((name, quantity)->
        {
            //setText
            //TODO
        });
    }
}
