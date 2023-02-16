import java.text.*;
import java.util.Calendar;

public class DateOur
{
	private Calendar date = Calendar.getInstance();


	public Calendar getDate() {
		return date;
	}

	public DateOur(String day, String month, String year) throws ParseException
	{
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		java.util.Date dateFormat = format.parse(day.concat("/".concat(month).concat("/").concat(year)));
		this.date.setTime(dateFormat);
	}

	public boolean bet(DateOur s, DateOur e)
	{
		try
		{
			Calendar start = Calendar.getInstance(), end = Calendar.getInstance(), today = Calendar.getInstance();
			SimpleDateFormat format = new SimpleDateFormat("dd/MM");
			java.util.Date dateFormat = format.parse(this.getStringDate());
			today.setTime(dateFormat);
			dateFormat = format.parse(s.getStringDate());
			start.setTime(dateFormat);
			dateFormat = format.parse(e.getStringDate());
			end.setTime(dateFormat);
			return today.before(end) && today.after(start);
		}
		catch (Exception err )
		{
			err.printStackTrace();
		}
		return false;
	}

	public DateOur(String day, String month) throws ParseException
	{
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		java.util.Date dateFormat = format.parse(day.concat("/".concat(month).concat("/").concat("1444")));
		this.date.setTime(dateFormat);
	}

	/**
	 * Trasformo una data in una stringa
	 * @return stringa della data
	 */
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
		if (d == this)
			return true;
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

	/**
	 * controllo se una data è tra due estremi
	 * @param s inizio periodo di validità
	 * @param e fine periodo di validità
	 * @return true se la data è tra gli estremi, false altrimenti
	 */
	public boolean between (DateOur s, DateOur e)
	{
		return this.date.before(e.getDate()) && this.date.after(s.getDate());
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
