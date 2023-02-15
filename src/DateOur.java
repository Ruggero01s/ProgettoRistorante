import java.text.*;
import java.util.Calendar;

public class DateOur
{
	private Calendar date = Calendar.getInstance();
	
	public DateOur(String day, String month) throws ParseException
	{
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		java.util.Date dateFormat = format.parse(day.concat("/".concat(month).concat("/").concat("1444")));
		this.date.setTime(dateFormat);
	}
	
	public String getStringDate()
	{
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		java.util.Date dateFormat = new java.util.Date();
		dateFormat.setTime(this.date.getTimeInMillis());
		return format.format(dateFormat);
	}

	@Override
	public boolean equals(Object d)
	{
		if (d == this) return true;
		if (!(d instanceof DateOur))
			return false;

		DateOur date = (DateOur) d;
		String me = this.getStringDate();
		return (me.equals((date.getStringDate())));
	}


	@Override
	public int hashCode() {
		int result = 17;
		result = 31 * result + getStringDate().hashCode();
		return result;
	}

	/*
	public void test()
	{
		DateFormat Date = DateFormat.getDateInstance(); //inizializzazione
		Calendar calendar = Calendar.getInstance(); //inizializzazione calendar
		System.out.println("Data lunga: " + calendar.getTime());
		String currentDate = Date.format(calendar.getTime()); //format la rende una stringa
		System.out.println("Data corta: " + currentDate);
	}
	*/
}
