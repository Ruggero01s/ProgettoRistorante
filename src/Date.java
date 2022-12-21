import java.text.*;
import java.util.Calendar;

public class Date
{
	Calendar date = Calendar.getInstance();

	public Date (String day,String month, String year) throws ParseException
	{
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		java.util.Date dateFormat = format.parse(day.concat("-".concat(month).concat("-").concat(year)));
		this.date.setTime(dateFormat);
	}

	public String getStringDate ()
	{

		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		java.util.Date dateFormat = new java.util.Date();
		dateFormat.setTime(this.date.getTimeInMillis());
		return format.format(dateFormat);
	}


	public void test ()
	{
		DateFormat Date = DateFormat.getDateInstance(); //inizializzazione
		Calendar calendar = Calendar.getInstance(); //inizializzazione calendar
		System.out.println("Data lunga: "+calendar.getTime());
		String currentDate = Date.format(calendar.getTime()); //format la rende una stringa
		System.out.println("Data corta: "+currentDate);
	}

}
