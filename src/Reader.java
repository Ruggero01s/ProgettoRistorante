import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class Reader
{
    private static final String ERRORE = "\nErrore in Input: ";
    private static final String ROOT = "saves/";
    private static final String PEOPLE_NAME_FILE= "people.xml";
    private static final String CONFIG_NAME_FILE= "config.xml";
    private static final String DRINKS_NAME_FILE= "drinks.xml";
    private static final String EXTRA_FOODS_NAME_FILE= "extraFoods.xml";

    public static ArrayList<Person> readPeople()
    {
        XMLInputFactory xmlif = null;
        XMLStreamReader xmlr = null;

        ArrayList<Person> listP = new ArrayList<>();

        try
        {
            String tagName = "";

            xmlif = XMLInputFactory.newInstance();
            xmlr = xmlif.createXMLStreamReader(new FileInputStream(ROOT + PEOPLE_NAME_FILE));
            while (xmlr.hasNext())
            { // continua a leggere finche ha eventi a disposizione
                boolean manager = false, employee = false, storageWorker = false;
                String name = "";
               if(xmlr.getEventType() == XMLStreamConstants.START_ELEMENT) // inizio di un elemento
               {
                   tagName = xmlr.getLocalName();
                   if (tagName.equals("person")) {
                       name = xmlr.getAttributeValue(0);
                       String mana = xmlr.getAttributeValue(1);
                       String empl = xmlr.getAttributeValue(2);
                       String stor = xmlr.getAttributeValue(3);
                       if (mana.equals("true")) manager = true;
                       else manager = false;
                       if (empl.equals("true")) employee = true;
                       else employee = false;
                       if (stor.equals("true")) storageWorker = true;
                       else storageWorker = false;
                       listP.add(new Person(name, manager, employee, storageWorker));
                   }
               }
                xmlr.next();
            }
        }
        catch (Exception e)
        {
            System.out.println(ERRORE + PEOPLE_NAME_FILE);
            System.out.println(e.getMessage());
        }
        return listP;
    }

    public static void readConfig(Model model)
    {
        XMLInputFactory xmlif = null;
        XMLStreamReader xmlr = null;

        try
        {
            String tagName = "";

            xmlif = XMLInputFactory.newInstance();
            xmlr = xmlif.createXMLStreamReader(new FileInputStream(ROOT + CONFIG_NAME_FILE));
            while (xmlr.hasNext())
            { // continua a leggere finche ha eventi a disposizione
                if(xmlr.getEventType() == XMLStreamConstants.START_ELEMENT) // inizio di un elemento
                {
                    tagName = xmlr.getLocalName();
                    if (tagName.equals("baseConfig"))
                    {
                        model.setCapacity(Integer.parseInt(xmlr.getAttributeValue(0)));
                        model.setWorkPersonLoad(Integer.parseInt(xmlr.getAttributeValue(1)));
                    }
                }
                xmlr.next();
            }
        }
        catch (Exception e)
        {
            System.out.println(ERRORE + CONFIG_NAME_FILE);
            System.out.println(e.getMessage());
        }
    }

    public static HashMap<String,Double> readDrinks()
    {
        XMLInputFactory xmlif = null;
        XMLStreamReader xmlr = null;

        HashMap<String,Double> drinks = new HashMap<>();

        try
        {
            String tagName = "";

            xmlif = XMLInputFactory.newInstance();
            xmlr = xmlif.createXMLStreamReader(new FileInputStream(ROOT + DRINKS_NAME_FILE));
            while (xmlr.hasNext())
            { // continua a leggere finche ha eventi a disposizione
                if(xmlr.getEventType() == XMLStreamConstants.START_ELEMENT) // inizio di un elemento
                {
                    tagName = xmlr.getLocalName();
                    if (tagName.equals("drink"))
                        drinks.put(xmlr.getAttributeValue(0),Double.parseDouble(xmlr.getAttributeValue(1)));

                }
                xmlr.next();
            }
        }
        catch (Exception e)
        {
            System.out.println(ERRORE + DRINKS_NAME_FILE);
            System.out.println(e.getMessage());
        }
        return drinks;
    }

    public static HashMap<String,Double> readExtraFoods()
    {
        XMLInputFactory xmlif = null;
        XMLStreamReader xmlr = null;

        HashMap<String,Double> foods = new HashMap<>();

        try
        {
            String tagName = "";

            xmlif = XMLInputFactory.newInstance();
            xmlr = xmlif.createXMLStreamReader(new FileInputStream(ROOT + EXTRA_FOODS_NAME_FILE));
            while (xmlr.hasNext())
            { // continua a leggere finche ha eventi a disposizione
                if(xmlr.getEventType() == XMLStreamConstants.START_ELEMENT) // inizio di un elemento
                {
                    tagName = xmlr.getLocalName();
                    if (tagName.equals("food"))
                        foods.put(xmlr.getAttributeValue(0),Double.parseDouble(xmlr.getAttributeValue(1)));

                }
                xmlr.next();
            }
        }
        catch (Exception e)
        {
            System.out.println(ERRORE + EXTRA_FOODS_NAME_FILE);
            System.out.println(e.getMessage());
        }
        return foods;
    }
}
