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
		boolean result = false,result2;
		if(s.getDate().get(Calendar.YEAR)<e.getDate().get(Calendar.YEAR))
		{
			Calendar temp = Calendar.getInstance();
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			java.util.Date dateFormat;
			try {
				dateFormat = format.parse("31/12/".concat(Integer.toString(s.getDate().get(Calendar.YEAR))));
			} catch (ParseException ex) {
				throw new RuntimeException(ex);
			}
			temp.setTime(dateFormat);
			int t = this.date.get(Calendar.YEAR);
			this.date.set(Calendar.YEAR,s.getDate().get(Calendar.YEAR));
			result = between(s.getDate(),temp);
			this.date.set(Calendar.YEAR,e.getDate().get(Calendar.YEAR));
			result2= between(temp,e.getDate());
			this.date.set(Calendar.YEAR,t);
			return result || result2;
		}
		else if (s.getDate().get(Calendar.YEAR)==e.getDate().get(Calendar.YEAR))
		{
			int t = this.date.get(Calendar.YEAR);
			this.date.set(Calendar.YEAR,s.getDate().get(Calendar.YEAR));
			result = between(s,e);
			this.date.set(Calendar.YEAR,t);
		}
		return result;
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
		return (this.date.before(e.getDate()) && this.date.after(s.getDate())) || this.equals(s) || this.equals(e);
	}
	private boolean between (Calendar s, Calendar e)
	{
		if (s.get(Calendar.DAY_OF_YEAR)==e.get(Calendar.DAY_OF_YEAR)) return true;
		return this.date.before(e) && this.date.after(s);
	}
	
	
}
