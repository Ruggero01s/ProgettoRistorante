import java.text.*;
import java.util.Calendar;

public class Date
{
	public Date()
	{
	}
	
	DateFormat Date = DateFormat.getDateInstance(); //inizializzazione
	Calendar calendar = Calendar.getInstance(); //inizializzazione calendar
	//System.out.println("Data lunga: "+calendar.getTime());
	String currentDate = Date.format(calendar.getTime()); //format la rende una stringa
	//System.out.println("Data corta: "+currentDate);
}
