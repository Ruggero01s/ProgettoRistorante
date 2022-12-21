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
        try
        {
            model.setCapacity(Integer.parseInt(gui.cfgBaseCapArea.getText()));
            model.setWorkPersonLoad(Integer.parseInt(gui.cfgBaseIndivualWorkloadArea.getText()));
            gui.cfgBaseCapArea.setText("");
            gui.cfgBaseIndivualWorkloadArea.setText("");
        }
        catch (NumberFormatException e)
        {
            System.out.println("errore, formato non valido");
            e.printStackTrace();
        }

    }

    public void saveDrinks()
    {
      try
      {
          model.drinks.put(gui.cfgDrinksNameArea.getText(), Double.parseDouble(gui.cfgDrinksQuantityArea.getText()));
          gui.cfgDrinksNameArea.setText("");
          gui.cfgDrinksQuantityArea.setText("");
      }
      catch (NumberFormatException e)
      {
          System.out.println("errore, formato non valido");
          e.printStackTrace();
      }
    }

    public void saveFoods()
    {
        try
        {
            model.extraFoods.put(gui.cfgFoodNameArea.getText(), Double.parseDouble(gui.cfgFoodQuantityArea.getText()));
            gui.cfgFoodNameArea.setText("");
            gui.cfgFoodQuantityArea.setText("");
        }
        catch (NumberFormatException e)
        {
            System.out.println("errore, formato non valido");
            e.printStackTrace();
        }
    }

    public void printDrinks()
    {
        System.out.println(model.drinks);
    }

    public void printExtraFoods ()
    {
        System.out.println(model.extraFoods);
    }
}
