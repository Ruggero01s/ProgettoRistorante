import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Writer
{
    static ArrayList<Person> listP = new ArrayList<>();

    private static final String SALUTO = "\nOutput generato correttamente, arrivederci";
    private static final String ERRORE = "\nErrore nel writer: ";

    public static void writeOutput(String output, ArrayList list)
    {
        listP.addAll(list);
        XMLOutputFactory xmlof = null;
        XMLStreamWriter xmlw = null;
        try
        {
            xmlof = XMLOutputFactory.newInstance();
            xmlw = xmlof.createXMLStreamWriter(new FileOutputStream("saves/"+output+".xml"), "utf-8");
            xmlw.writeStartDocument("utf-8", "1.0");
            xmlw.writeCharacters("\n");
            xmlw.writeStartElement("people"); // scrittura del tag radice <people>
            for (Person p: listP)
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
}
