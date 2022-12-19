import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;
import java.io.FileInputStream;
import java.util.ArrayList;

public class Reader {
    private static final String ERRORE = "\nErrore in Input: ";
    private static final String FILE = "saves/";
    static ArrayList<Person> listP = new ArrayList<>();

    public static ArrayList<Person> readPeople(String filename) {
        XMLInputFactory xmlif = null;
        XMLStreamReader xmlr = null;

        try {

            String tagName = "";

            xmlif = XMLInputFactory.newInstance();
            xmlr = xmlif.createXMLStreamReader(new FileInputStream(FILE + filename));
            while (xmlr.hasNext()) { // continua a leggere finche ha eventi a disposizione
                boolean manager = false, employee = false, warehouseman = false;
                String name = "";
                // switch sul tipo di evento
                // if (xmlr.getEventType()==XMLStreamConstants.START_ELEMENT) // inizio di un elemento
               if(xmlr.getEventType() == XMLStreamConstants.START_ELEMENT) {
                   tagName = xmlr.getLocalName();
                   if (tagName.equals("person")) {
                       name = xmlr.getAttributeValue(0);
                       String mana = xmlr.getAttributeValue(1);
                       String empl = xmlr.getAttributeValue(2);
                       String ware = xmlr.getAttributeValue(3);
                       if (mana.equals("true")) manager = true;
                       else manager = false;
                       if (empl.equals("true")) employee = true;
                       else employee = false;
                       if (ware.equals("true")) warehouseman = true;
                       else warehouseman = false;
                       listP.add(new Person(name, manager, employee, warehouseman));
                   }
               }

                xmlr.next();
            }
        } catch (Exception e) {
            System.out.println(ERRORE + filename);
            System.out.println(e.getMessage());
        }
        return listP;
    }


}
