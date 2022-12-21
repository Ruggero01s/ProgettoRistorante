import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Writer
{
    private static final String ROOT = "saves/";
    private static final String PEOPLE_NAME_FILE= "people.xml";
    private static final String CONFIG_NAME_FILE= "config.xml";
    private static final String DRINKS_NAME_FILE= "drinks.xml";
    private static final String EXTRA_FOODS_NAME_FILE= "extraFoods.xml";


    private static final String SALUTO = "\nOutput generato correttamente, arrivederci";
    private static final String ERRORE = "\nErrore nel writer: ";

    public static void writePeople(ArrayList<Person> people)
    {
        XMLOutputFactory xmlof = null;
        XMLStreamWriter xmlw = null;
        try
        {
            xmlof = XMLOutputFactory.newInstance();
            xmlw = xmlof.createXMLStreamWriter(new FileOutputStream(ROOT+PEOPLE_NAME_FILE), "utf-8");
            xmlw.writeStartDocument("utf-8", "1.0");
            xmlw.writeCharacters("\n");
            xmlw.writeStartElement("people"); // scrittura del tag radice <people>
            for (Person p: people)
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

    public static void writeConfigBase(int capacity, double workPersonLoad, double workResturantLoad)
    {

        XMLOutputFactory xmlof = null;
        XMLStreamWriter xmlw = null;
        try
        {
            xmlof = XMLOutputFactory.newInstance();
            xmlw = xmlof.createXMLStreamWriter(new FileOutputStream(ROOT+CONFIG_NAME_FILE), "utf-8");
            xmlw.writeStartDocument("utf-8", "1.0");
            xmlw.writeCharacters("\n");
            xmlw.writeStartElement("config"); // scrittura del tag radice <config>
            xmlw.writeCharacters("\n\t");
            xmlw.writeStartElement("baseConfig"); // <baseconfig>
            xmlw.writeAttribute("capacity", Integer.toString(capacity));
            xmlw.writeAttribute("workPersonLoad", Double.toString(workPersonLoad));
            xmlw.writeAttribute("workResturantLoad", Double.toString(workResturantLoad));
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

    public static void writeDrinks(HashMap <String,Double> drinks)
    {
        XMLOutputFactory xmlof = null;
        XMLStreamWriter xmlw = null;
        try
        {
            xmlof = XMLOutputFactory.newInstance();
            xmlw = xmlof.createXMLStreamWriter(new FileOutputStream(ROOT+DRINKS_NAME_FILE), "utf-8");
            xmlw.writeStartDocument("utf-8", "1.0");
            xmlw.writeCharacters("\n");
            xmlw.writeStartElement("drinks"); // scrittura del tag radice <drinks>
            for(Map.Entry<String,Double> drink : drinks.entrySet())
            {
                xmlw.writeCharacters("\n\t");
                xmlw.writeStartElement("drink"); // <drink>
                xmlw.writeAttribute("name", drink.getKey());
                xmlw.writeAttribute("liter", Double.toString(drink.getValue()));
                xmlw.writeEndElement(); // </drink>
            };
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

    public static void writeExtraFoods(HashMap <String,Double> foods)
    {
        XMLOutputFactory xmlof = null;
        XMLStreamWriter xmlw = null;
        try
        {
            xmlof = XMLOutputFactory.newInstance();
            xmlw = xmlof.createXMLStreamWriter(new FileOutputStream(ROOT+EXTRA_FOODS_NAME_FILE), "utf-8");
            xmlw.writeStartDocument("utf-8", "1.0");
            xmlw.writeCharacters("\n");
            xmlw.writeStartElement("foods"); // scrittura del tag radice <foods>
            for(Map.Entry<String,Double> food : foods.entrySet())
            {
                xmlw.writeCharacters("\n\t");
                xmlw.writeStartElement("food"); // <food>
                xmlw.writeAttribute("name", food.getKey());
                xmlw.writeAttribute("hg", Double.toString(food.getValue()));
                xmlw.writeEndElement(); // </food>
            };
            xmlw.writeCharacters("\n");
            xmlw.writeEndElement();//</foods>

            xmlw.writeEndDocument(); // scrittura della fine del documento
            xmlw.flush(); // svuota il buffer e procede alla scrittura
            xmlw.close(); // chiusura del documento e delle risorse impiegate
            System.out.println(SALUTO);
        } catch (Exception e) {
            System.out.println(ERRORE);
            System.out.println(e.getMessage());
        }
    }
}
