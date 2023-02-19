import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;
import java.io.FileOutputStream;
import java.util.*;

public class Writer
{
	public static final String ROOT = "saves/";
	public static final String PEOPLE_NAME_FILE = "people.xml";
	public static final String CONFIG_NAME_FILE = "config.xml";
	public static final String DRINKS_NAME_FILE = "drinks.xml";
	public static final String EXTRA_FOODS_NAME_FILE = "extraFoods.xml";
	public static final String RECIPES_NAME_FILE = "recipes.xml";
	public static final String DISHES_NAME_FILE = "dishes.xml";
    public static final String MENUS_NAME_FILE = "thematicMenus.xml";
	public static final String BOOKINGS_NAME_FILE = "bookings.xml";
	public static final String REGISTER_NAME_FILE = "register.xml";
	
	
	private static final String SALUTO = "\nOutput generato correttamente, arrivederci";
	private static final String ERRORE = "\nErrore nel writer: ";
	
	public static void writePeople(ArrayList<Person> people)
	{
		XMLOutputFactory xmlof = null;
		XMLStreamWriter xmlw = null;
		try
		{
			xmlof = XMLOutputFactory.newInstance();
			xmlw = xmlof.createXMLStreamWriter(new FileOutputStream(ROOT + PEOPLE_NAME_FILE), "utf-8");
			xmlw.writeStartDocument("utf-8", "1.0");
			xmlw.writeCharacters("\n");
			xmlw.writeStartElement("people"); // scrittura del tag radice <people>
			for (Person p : people)
			{
				xmlw.writeCharacters("\n\t");
				xmlw.writeStartElement("person");
				xmlw.writeAttribute("name", p.getName());
				xmlw.writeAttribute("manager", String.valueOf(p.isManager()));
				xmlw.writeAttribute("employee", String.valueOf(p.isEmployee()));
				xmlw.writeAttribute("storageWorker", String.valueOf(p.isStorageWorker()));
				xmlw.writeEndElement(); // </person>
			}
			xmlw.writeCharacters("\n");
			xmlw.writeEndElement();//</people>
			xmlw.writeEndDocument(); // scrittura della fine del documento
			xmlw.flush(); // svuota il buffer e procede alla scrittura
			xmlw.close(); // chiusura del documento e delle risorse impiegate
			System.out.println(SALUTO);
		}
		catch (Exception e)
		{
			System.out.println(ERRORE);
			System.out.println(e.getMessage());
		}
	}
	
	public static void writeConfigBase(int capacity, int workPersonLoad, DateOur today, int increment)
	{
		
		XMLOutputFactory xmlof = null;
		XMLStreamWriter xmlw = null;
		try
		{
			xmlof = XMLOutputFactory.newInstance();
			xmlw = xmlof.createXMLStreamWriter(new FileOutputStream(ROOT + CONFIG_NAME_FILE), "utf-8");
			xmlw.writeStartDocument("utf-8", "1.0");
			xmlw.writeCharacters("\n");
			xmlw.writeStartElement("config"); // scrittura del tag radice <config>
			xmlw.writeCharacters("\n\t");
			xmlw.writeStartElement("baseConfig"); // <baseconfig>
			xmlw.writeAttribute("capacity", Integer.toString(capacity));
			xmlw.writeAttribute("workPersonLoad", Integer.toString(workPersonLoad));
			xmlw.writeAttribute("today", today.getStringDate());
			xmlw.writeAttribute("increment",Integer.toString(increment));
			xmlw.writeEndElement(); // </baseconfig>
			xmlw.writeCharacters("\n");
			xmlw.writeEndElement();//</config>
			xmlw.writeEndDocument(); // scrittura della fine del documento
			xmlw.flush(); // svuota il buffer e procede alla scrittura
			xmlw.close(); // chiusura del documento e delle risorse impiegate
			System.out.println(SALUTO);
		}
		catch (Exception e)
		{
			System.out.println(ERRORE);
			System.out.println(e.getMessage());
		}
	}
	
	public static void writeDrinks(HashMap<String, Double> drinks)
	{
		XMLOutputFactory xmlof = null;
		XMLStreamWriter xmlw = null;
		try
		{
			xmlof = XMLOutputFactory.newInstance();
			xmlw = xmlof.createXMLStreamWriter(new FileOutputStream(ROOT + DRINKS_NAME_FILE), "utf-8");
			xmlw.writeStartDocument("utf-8", "1.0");
			xmlw.writeCharacters("\n");
			xmlw.writeStartElement("drinks"); // scrittura del tag radice <drinks>
			for (Map.Entry<String, Double> drink : drinks.entrySet())
			{
				xmlw.writeCharacters("\n\t");
				xmlw.writeStartElement("drink"); // <drink>
				xmlw.writeAttribute("name", drink.getKey());
				xmlw.writeAttribute("liter", Double.toString(drink.getValue()));
				xmlw.writeEndElement(); // </drink>
			}
			xmlw.writeCharacters("\n");
			xmlw.writeEndElement();//</drinks>
			
			xmlw.writeEndDocument(); // scrittura della fine del documento
			xmlw.flush(); // svuota il buffer e procede alla scrittura
			xmlw.close(); // chiusura del documento e delle risorse impiegate
			System.out.println(SALUTO);
		}
		catch (Exception e)
		{
			System.out.println(ERRORE);
			System.out.println(e.getMessage());
		}
	}
	
	public static void writeExtraFoods(HashMap<String, Double> foods)
	{
		XMLOutputFactory xmlof = null;
		XMLStreamWriter xmlw = null;
		try
		{
			xmlof = XMLOutputFactory.newInstance();
			xmlw = xmlof.createXMLStreamWriter(new FileOutputStream(ROOT + EXTRA_FOODS_NAME_FILE), "utf-8");
			xmlw.writeStartDocument("utf-8", "1.0");
			xmlw.writeCharacters("\n");
			xmlw.writeStartElement("foods"); // scrittura del tag radice <foods>
			for (Map.Entry<String, Double> food : foods.entrySet())
			{
				xmlw.writeCharacters("\n\t");
				xmlw.writeStartElement("food"); // <food>
				xmlw.writeAttribute("name", food.getKey());
				xmlw.writeAttribute("hg", Double.toString(food.getValue()));
				xmlw.writeEndElement(); // </food>
			}
			xmlw.writeCharacters("\n");
			xmlw.writeEndElement();//</foods>
			
			xmlw.writeEndDocument(); // scrittura della fine del documento
			xmlw.flush(); // svuota il buffer e procede alla scrittura
			xmlw.close(); // chiusura del documento e delle risorse impiegate
			System.out.println(SALUTO);
		}
		catch (Exception e)
		{
			System.out.println(ERRORE);
			System.out.println(e.getMessage());
		}
	}

	public static void writeRecipes(Set <Recipe> recipes)
	{
		XMLOutputFactory xmlof = null;
		XMLStreamWriter xmlw = null;
		try
		{
			xmlof = XMLOutputFactory.newInstance();
			xmlw = xmlof.createXMLStreamWriter(new FileOutputStream(ROOT + RECIPES_NAME_FILE), "utf-8");
			xmlw.writeStartDocument("utf-8", "1.0");
			xmlw.writeCharacters("\n");
			xmlw.writeStartElement("recipes"); // scrittura del tag radice <recipes>
			for (Recipe recipe : recipes)
			{
				xmlw.writeCharacters("\n\t");
				xmlw.writeStartElement("recipe"); // <recipe>
				xmlw.writeAttribute("id", recipe.getId());
				xmlw.writeAttribute("portions", Integer.toString(recipe.getPortions()));
				xmlw.writeAttribute("workLoadPortion", Double.toString(recipe.getWorkLoadPortion()));
				for (Map.Entry<String, Double> ingredient : recipe.getIngredients().entrySet())
				{
					xmlw.writeCharacters("\n\t\t");
					xmlw.writeStartElement("ingredient"); // <ingredient>
					xmlw.writeAttribute("name", ingredient.getKey());
					xmlw.writeAttribute("quantity", Double.toString(ingredient.getValue()));
					xmlw.writeEndElement(); // </ingredient>
				}
				xmlw.writeEndElement(); // </recipe>
			}
			xmlw.writeCharacters("\n");
			xmlw.writeEndElement();//</recipes>
			xmlw.writeEndDocument(); // scrittura della fine del documento
			xmlw.flush(); // svuota il buffer e procede alla scrittura
			xmlw.close(); // chiusura del documento e delle risorse impiegate
			System.out.println(SALUTO);
		}
		catch (Exception e)
		{
			System.out.println(ERRORE);
			System.out.println(e.getMessage());
		}
	}

	public static void writeDishes(Set<Dish> dishes)
	{
		XMLOutputFactory xmlof = null;
		XMLStreamWriter xmlw = null;
		try
		{
			xmlof = XMLOutputFactory.newInstance();
			xmlw = xmlof.createXMLStreamWriter(new FileOutputStream(ROOT + DISHES_NAME_FILE), "utf-8");
			xmlw.writeStartDocument("utf-8", "1.0");
			xmlw.writeCharacters("\n");
			xmlw.writeStartElement("dishes"); // scrittura del tag radice <dishes>
			for (Dish dish : dishes)
			{
				xmlw.writeCharacters("\n\t");
				xmlw.writeStartElement("dish"); // <dish>
				xmlw.writeAttribute("name", dish.getName());
				xmlw.writeAttribute("startDate", dish.getStartPeriod().getStringDate());
				xmlw.writeAttribute("endDate", dish.getEndPeriod().getStringDate());
				xmlw.writeAttribute("seasonal", Boolean.toString(dish.isSeasonal()));
				xmlw.writeAttribute("permanent", Boolean.toString(dish.isPermanent()));
				xmlw.writeCharacters("\n\t\t");
				xmlw.writeStartElement("recipe"); // <recipe>
				xmlw.writeAttribute("id", dish.getRecipe().getId());
				xmlw.writeEndElement(); // </recipe>
				xmlw.writeEndElement(); // </dish>
			}
			xmlw.writeCharacters("\n");
			xmlw.writeEndElement();//</dishes>
			
			xmlw.writeEndDocument(); // scrittura della fine del documento
			xmlw.flush(); // svuota il buffer e procede alla scrittura
			xmlw.close(); // chiusura del documento e delle risorse impiegate
			System.out.println(SALUTO);
		}
		catch (Exception e)
		{
			System.out.println(ERRORE);
			System.out.println(e.getMessage());
		}
	}
	
	public static void writeThematicMenu(Set<ThematicMenu> menus)
	{
		XMLOutputFactory xmlof = null;
		XMLStreamWriter xmlw = null;
		try
		{
			xmlof = XMLOutputFactory.newInstance();
			xmlw = xmlof.createXMLStreamWriter(new FileOutputStream(ROOT + MENUS_NAME_FILE), "utf-8");
			xmlw.writeStartDocument("utf-8", "1.0");
			xmlw.writeCharacters("\n");
			xmlw.writeStartElement("menus"); // scrittura del tag radice <menus>
			for (ThematicMenu menu : menus)
			{
				xmlw.writeCharacters("\n\t");
				xmlw.writeStartElement("menu"); // <menu>
				xmlw.writeAttribute("name", menu.getName());
				xmlw.writeAttribute("startDate", menu.getStartPeriod().getStringDate());
				xmlw.writeAttribute("endDate", menu.getEndPeriod().getStringDate());
				xmlw.writeAttribute("seasonal", Boolean.toString(menu.isSeasonal()));
				xmlw.writeAttribute("permanent", Boolean.toString(menu.isPermanent()));
				for (Dish dish: menu.getDishes())
				{
					xmlw.writeCharacters("\n\t\t");
					xmlw.writeStartElement("dish"); // <dish>
					xmlw.writeAttribute("name", dish.getName());
					xmlw.writeEndElement(); // </dish>
				}
				xmlw.writeEndElement(); // </menu>
			}
			xmlw.writeCharacters("\n");
			xmlw.writeEndElement();//</menus>
			
			xmlw.writeEndDocument(); // scrittura della fine del documento
			xmlw.flush(); // svuota il buffer e procede alla scrittura
			xmlw.close(); // chiusura del documento e delle risorse impiegate
			System.out.println(SALUTO);
		}
		catch (Exception e)
		{
			System.out.println(ERRORE);
			System.out.println(e.getMessage());
		}
	}

	public static void writeBookings(HashMap <DateOur,ArrayList<Booking>> bookings)
	{
		XMLOutputFactory xmlof = null;
		XMLStreamWriter xmlw = null;
		try
		{
			xmlof = XMLOutputFactory.newInstance();
			xmlw = xmlof.createXMLStreamWriter(new FileOutputStream(ROOT + BOOKINGS_NAME_FILE), "utf-8");
			xmlw.writeStartDocument("utf-8", "1.0");
			xmlw.writeCharacters("\n");
			xmlw.writeStartElement("bookings"); // scrittura del tag radice <bookings>
			for (Map.Entry<DateOur,ArrayList<Booking>> booking : bookings.entrySet())
			{
				xmlw.writeCharacters("\n\t");
				xmlw.writeStartElement("booking"); // <booking>
				xmlw.writeAttribute("date", booking.getKey().getStringDate());
				for (Booking b: booking.getValue())
				{
					xmlw.writeCharacters("\n\t\t");
					xmlw.writeStartElement("book"); // <book>
					xmlw.writeAttribute("name", b.getName());
					xmlw.writeAttribute("number", Integer.toString(b.getNumber()));
					xmlw.writeAttribute("workload",Integer.toString(b.getWorkload()));
					for (Map.Entry<Dish, Integer> dish : b.getOrder().entrySet())
					{
						xmlw.writeCharacters("\n\t\t\t");
						xmlw.writeStartElement("order"); // <order>
						xmlw.writeAttribute("dish", dish.getKey().getName());
						xmlw.writeAttribute("quantity", Integer.toString(dish.getValue()));
						xmlw.writeEndElement(); // </order>
					}
					xmlw.writeEndElement(); // </book>
				}
				xmlw.writeEndElement(); // </booking>
			}
			xmlw.writeCharacters("\n");
			xmlw.writeEndElement();//</bookings>

			xmlw.writeEndDocument(); // scrittura della fine del documento
			xmlw.flush(); // svuota il buffer e procede alla scrittura
			xmlw.close(); // chiusura del documento e delle risorse impiegate
			System.out.println(SALUTO);
		}
		catch (Exception e)
		{
			System.out.println(ERRORE);
			System.out.println(e.getMessage());
		}
	}
	
	public static void writeRegister(HashMap <String,Double> register)
	{
		XMLOutputFactory xmlof = null;
		XMLStreamWriter xmlw = null;
		try
		{
			xmlof = XMLOutputFactory.newInstance();
			xmlw = xmlof.createXMLStreamWriter(new FileOutputStream(ROOT + REGISTER_NAME_FILE), "utf-8");
			xmlw.writeStartDocument("utf-8", "1.0");
			xmlw.writeCharacters("\n");
			xmlw.writeStartElement("register"); // scrittura del tag radice <register>
			for (Map.Entry<String,Double> reg : register.entrySet())
			{
				xmlw.writeCharacters("\n\t");
				xmlw.writeStartElement("ingredient"); // <ingredient>
				xmlw.writeAttribute("name", reg.getKey());
				xmlw.writeAttribute("quantity", reg.getValue().toString());
				xmlw.writeEndElement(); // </ingredient>
			}
			xmlw.writeCharacters("\n");
			xmlw.writeEndElement();//</register>
			xmlw.writeEndDocument(); // scrittura della fine del documento
			xmlw.flush(); // svuota il buffer e procede alla scrittura
			xmlw.close(); // chiusura del documento e delle risorse impiegate
			System.out.println(SALUTO);
		}
		catch (Exception e)
		{
			System.out.println(ERRORE);
			System.out.println(e.getMessage());
		}
	}
}
