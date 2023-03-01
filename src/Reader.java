import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Reader
{
	private static final String ERRORE = "\nErrore in Input: ";
	
	Controller ctrl;
	
	public Reader(Controller ctrl)
	{
		this.ctrl = ctrl;
	}
	
	public ArrayList<Person> readPeople()
	{
		XMLInputFactory xmlif;
		XMLStreamReader xmlr;
		
		ArrayList<Person> listP = new ArrayList<>();
		
		try
		{
			xmlif = XMLInputFactory.newInstance();
			xmlr = xmlif.createXMLStreamReader(new FileInputStream(Writer.ROOT + Writer.PEOPLE_NAME_FILE));
			while (xmlr.hasNext())
			{ // continua a leggere finche ha eventi a disposizione
				boolean manager, employee, storageWorker;
				String name;
				if (xmlr.getEventType() == XMLStreamConstants.START_ELEMENT) // inizio di un elemento
				{
					if (xmlr.getLocalName().equals("person"))
					{
						name = xmlr.getAttributeValue(0);
						String mana = xmlr.getAttributeValue(1);
						String empl = xmlr.getAttributeValue(2);
						String stor = xmlr.getAttributeValue(3);
						manager = mana.equals("true");
						employee = empl.equals("true");
						storageWorker = stor.equals("true");
						listP.add(new Person(name, manager, employee, storageWorker));
					}
				}
				xmlr.next();
			}
		}
		catch (Exception e)
		{
			System.out.println(ERRORE + Writer.PEOPLE_NAME_FILE);
			System.out.println(e.getMessage());
		}
		return listP;
	}
	
	public void readConfig(Model model)
	{
		XMLInputFactory xmlif;
		XMLStreamReader xmlr;
		
		try
		{
			xmlif = XMLInputFactory.newInstance();
			xmlr = xmlif.createXMLStreamReader(new FileInputStream(Writer.ROOT + Writer.CONFIG_NAME_FILE));
			String[] today;
			while (xmlr.hasNext())
			{ // continua a leggere finche ha eventi a disposizione
				if (xmlr.getEventType() == XMLStreamConstants.START_ELEMENT) // inizio di un elemento
				{
					if (xmlr.getLocalName().equals("baseConfig"))
					{
						model.setCapacity(Integer.parseInt(xmlr.getAttributeValue(0)));
						model.setWorkPersonLoad((int) Double.parseDouble(xmlr.getAttributeValue(1)));
						today = xmlr.getAttributeValue(2).split("/");
						model.setIncrement(Integer.parseInt(xmlr.getAttributeValue(3)));
						model.setToday(new DateOur(today[0], today[1], today[2]));
					}
				}
				xmlr.next();
			}
		}
		catch (Exception e)
		{
			System.out.println(ERRORE + Writer.CONFIG_NAME_FILE);
			System.out.println(e.getMessage());
		}
	}
	
	public HashMap<String, Double> readDrinks()
	{
		XMLInputFactory xmlif;
		XMLStreamReader xmlr;
		
		HashMap<String, Double> drinks = new HashMap<>();
		
		try
		{
			xmlif = XMLInputFactory.newInstance();
			xmlr = xmlif.createXMLStreamReader(new FileInputStream(Writer.ROOT + Writer.DRINKS_NAME_FILE));
			while (xmlr.hasNext())
			{ // continua a leggere finche ha eventi a disposizione
				if (xmlr.getEventType() == XMLStreamConstants.START_ELEMENT) // inizio di un elemento
					if (xmlr.getLocalName().equals("drink"))
						drinks.put(xmlr.getAttributeValue(0), Double.parseDouble(xmlr.getAttributeValue(1)));
				xmlr.next();
			}
		}
		catch (Exception e)
		{
			System.out.println(ERRORE + Writer.DRINKS_NAME_FILE);
			System.out.println(e.getMessage());
		}
		return drinks;
	}
	
	public HashMap<String, Double> readExtraFoods()
	{
		XMLInputFactory xmlif;
		XMLStreamReader xmlr;
		
		HashMap<String, Double> foods = new HashMap<>();
		
		try
		{
			xmlif = XMLInputFactory.newInstance();
			xmlr = xmlif.createXMLStreamReader(new FileInputStream(Writer.ROOT + Writer.EXTRA_FOODS_NAME_FILE));
			while (xmlr.hasNext())
			{ // continua a leggere finche ha eventi a disposizione
				if (xmlr.getEventType() == XMLStreamConstants.START_ELEMENT) // inizio di un elemento
					if (xmlr.getLocalName().equals("food"))
						foods.put(xmlr.getAttributeValue(0), Double.parseDouble(xmlr.getAttributeValue(1)));
				xmlr.next();
			}
		}
		catch (Exception e)
		{
			System.out.println(ERRORE + Writer.EXTRA_FOODS_NAME_FILE);
			System.out.println(e.getMessage());
		}
		return foods;
	}
	
	public HashSet<Recipe> readRecipes()
	{
		XMLInputFactory xmlif;
		XMLStreamReader xmlr;
		
		HashSet<Recipe> recipes = new HashSet<>();
		
		try
		{
			xmlif = XMLInputFactory.newInstance();
			xmlr = xmlif.createXMLStreamReader(new FileInputStream(Writer.ROOT + Writer.RECIPES_NAME_FILE));
			String id = "";
			int portions = 0;
			double workLoadPortion = 0;
			Set<Ingredient> ingredients = new HashSet<>();
			while (xmlr.hasNext())
			{
				// continua a leggere finche ha eventi a disposizione
				
				if (xmlr.getEventType() == XMLStreamConstants.START_ELEMENT) // inizio di un elemento
				{
					switch (xmlr.getLocalName())
					{
						case "recipe" ->
						{
							id = xmlr.getAttributeValue(0);
							portions = Integer.parseInt(xmlr.getAttributeValue(1));
							workLoadPortion = Double.parseDouble(xmlr.getAttributeValue(2));
						}
						case "ingredient" ->
								ingredients.add(new Ingredient(xmlr.getAttributeValue(0), xmlr.getAttributeValue(2), Double.parseDouble(xmlr.getAttributeValue(1))));
					}
				}
				if (xmlr.getEventType() == XMLStreamConstants.END_ELEMENT)
					if (xmlr.getLocalName().equals("recipe"))
					{
						recipes.add(new Recipe(id, (new HashSet<>(ingredients)), portions, workLoadPortion));
						ingredients.clear();
					}
				xmlr.next();
			}
		}
		catch (Exception e)
		{
			System.out.println(ERRORE + Writer.RECIPES_NAME_FILE);
			System.out.println(e.getMessage());
		}
		return recipes;
	}
	
	public HashSet<Dish> readDishes()
	{
		XMLInputFactory xmlif;
		XMLStreamReader xmlr;
		
		HashSet<Dish> dishes = new HashSet<>();
		
		try
		{
			xmlif = XMLInputFactory.newInstance();
			xmlr = xmlif.createXMLStreamReader(new FileInputStream(Writer.ROOT + Writer.DISHES_NAME_FILE));
			String name = "", id = "", startPeriod = "", endPeriod = "";
			boolean seasonal = false, permanent = false;
			while (xmlr.hasNext())
			{
				// continua a leggere finche ha eventi a disposizione
				
				
				if (xmlr.getEventType() == XMLStreamConstants.START_ELEMENT) // inizio di un elemento
				{
					switch (xmlr.getLocalName())
					{
						case "dish" ->
						{
							name = xmlr.getAttributeValue(0);
							startPeriod = xmlr.getAttributeValue(1);
							endPeriod = xmlr.getAttributeValue(2);
							seasonal = Boolean.parseBoolean(xmlr.getAttributeValue(3));
							permanent = Boolean.parseBoolean(xmlr.getAttributeValue(4));
						}
						case "recipe" -> id = xmlr.getAttributeValue(0);
					}
				}
				if (xmlr.getEventType() == XMLStreamConstants.END_ELEMENT)
					if (xmlr.getLocalName().equals("dish"))
					{
						dishes.add(new Dish(name, ctrl.stringToRecipe(id), startPeriod, endPeriod, seasonal, permanent));
					}
				
				xmlr.next();
			}
		}
		catch (Exception e)
		{
			System.out.println(ERRORE + Writer.DISHES_NAME_FILE);
			System.out.println(e.getMessage());
		}
		return dishes;
	}
	
	public HashSet<ThematicMenu> readThematicMenu()
	{
		XMLInputFactory xmlif;
		XMLStreamReader xmlr;
		
		HashSet<ThematicMenu> menu = new HashSet<>();
		try
		{
			xmlif = XMLInputFactory.newInstance();
			xmlr = xmlif.createXMLStreamReader(new FileInputStream(Writer.ROOT + Writer.MENUS_NAME_FILE));
			String name = "", startPeriod = "", endPeriod = "";
			ArrayList<String> dishes = new ArrayList<>();
			boolean seasonal = false, permanent = false;
			while (xmlr.hasNext())
			{
				// continua a leggere finche ha eventi a disposizione
				if (xmlr.getEventType() == XMLStreamConstants.START_ELEMENT) // inizio di un elemento
				{
					switch (xmlr.getLocalName())
					{
						case "menu" ->
						{
							name = xmlr.getAttributeValue(0);
							startPeriod = xmlr.getAttributeValue(1);
							endPeriod = xmlr.getAttributeValue(2);
							seasonal = Boolean.parseBoolean(xmlr.getAttributeValue(3));
							permanent = Boolean.parseBoolean(xmlr.getAttributeValue(4));
						}
						case "dish" -> dishes.add(xmlr.getAttributeValue(0));
					}
				}
				if (xmlr.getEventType() == XMLStreamConstants.END_ELEMENT)
					if (xmlr.getLocalName().equals("menu"))
					{
						menu.add(new ThematicMenu(name, startPeriod, endPeriod, ctrl.stringListToDishList(dishes), seasonal, permanent));
						dishes.clear();
					}
				xmlr.next();
			}
		}
		catch (Exception e)
		{
			System.out.println(ERRORE + Writer.MENUS_NAME_FILE);
			System.out.println(e.getMessage());
		}
		return menu;
	}
	
	public HashMap<DateOur, ArrayList<Booking>> readBooking()
	{
		XMLInputFactory xmlif;
		XMLStreamReader xmlr;
		
		HashMap<DateOur, ArrayList<Booking>> bookings = new HashMap<>();
		try
		{
			xmlif = XMLInputFactory.newInstance();
			xmlr = xmlif.createXMLStreamReader(new FileInputStream(Writer.ROOT + Writer.BOOKINGS_NAME_FILE));
			String name = "";
			String[] dateString = {};
			int number = 0, workload = 0;
			ArrayList<Booking> book = new ArrayList<>();
			HashMap<String, Integer> order = new HashMap<>();
			while (xmlr.hasNext())
			{
				// continua a leggere finche ha eventi a disposizione
				if (xmlr.getEventType() == XMLStreamConstants.START_ELEMENT) // inizio di un elemento
				{
					switch (xmlr.getLocalName())
					{
						case "booking" -> dateString = xmlr.getAttributeValue(0).split("/");
						case "book" ->
						{
							name = xmlr.getAttributeValue(0);
							number = Integer.parseInt(xmlr.getAttributeValue(1));
							workload = Integer.parseInt(xmlr.getAttributeValue(2));
						}
						case "order" ->
								order.put(xmlr.getAttributeValue(0), Integer.parseInt(xmlr.getAttributeValue(1)));
					}
				}
				if (xmlr.getEventType() == XMLStreamConstants.END_ELEMENT)
					switch (xmlr.getLocalName())
					{
						case "booking" ->
						{
							if (dateString.length == 0) //per evitare rotture in lettura
								return new HashMap<>();
							ArrayList<Booking> out = new ArrayList<>(book);
							bookings.put(new DateOur(dateString[0], dateString[1], dateString[2]), out);
							book.clear();
						}
						case "book" ->
						{
							book.add(new Booking(name, number, workload, ctrl.dishToMap(order)));
							order.clear();
						}
					}
				xmlr.next();
			}
		}
		catch (Exception e)
		{
			System.out.println(ERRORE + Writer.BOOKINGS_NAME_FILE);
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return bookings;
	}
	
	public Set<Ingredient> readRegister()
	{
		XMLInputFactory xmlif;
		XMLStreamReader xmlr;
		
		Set<Ingredient> reg = new HashSet<>();
		try
		{
			xmlif = XMLInputFactory.newInstance();
			xmlr = xmlif.createXMLStreamReader(new FileInputStream(Writer.ROOT + Writer.REGISTER_NAME_FILE));
			while (xmlr.hasNext())
			{
				// continua a leggere finche ha eventi a disposizione
				if (xmlr.getEventType() == XMLStreamConstants.START_ELEMENT) // inizio di un elemento
				{
                    if(xmlr.getLocalName().equals("ingredient"))
                        reg.add(new Ingredient(xmlr.getAttributeValue(0),xmlr.getAttributeValue(1),Double.parseDouble(xmlr.getAttributeValue(2))));
				}
				xmlr.next();
			}
		}
		catch (Exception e)
		{
			System.out.println(ERRORE + Writer.REGISTER_NAME_FILE);
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return reg;
	}
}