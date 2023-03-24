import java.text.*;
import java.util.Calendar;

public class DateOur
{
	private Calendar date = Calendar.getInstance();
	
	public Calendar getDate()
	{
		return date;
	}
	
	public DateOur(String day, String month, String year)
	{
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		java.util.Date dateFormat;
		try
		{
			dateFormat = format.parse(day.concat("/".concat(month).concat("/").concat(year)));
		}
		catch (ParseException e)
		{
			throw new RuntimeException(e);
		}
		this.date.setTime(dateFormat);
	}
	
	/**
	 * Controllo se una data si trova tra altre due in caso di piatti stagionali,
	 * ovvero controllo solo mese e giorno ignorando l'anno
	 * @param start Data d'inizio periodo
	 * @param end Data di fine periodo
	 * @return true se la data si trova tra start ed end, false altrimenti
	 */
	public boolean bet(DateOur start, DateOur end)
	{
		if(start.getDate().after(end.getDate())) //se le date sono al contrario torno false
			return false;
		
		boolean result = false;
		boolean result2;
		if (start.getDate().get(Calendar.YEAR) < end.getDate().get(Calendar.YEAR)) //la data d'inizio è l'anno prima di quella di fine
		{
			Calendar temp = Calendar.getInstance();
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			java.util.Date dateFormat;
			try
			{
				dateFormat = format.parse("31/12/".concat(Integer.toString(start.getDate().get(Calendar.YEAR)))); //creo il giorno 31/12/anno di start
			}
			catch (ParseException ex)
			{
				throw new RuntimeException(ex);
			}
			temp.setTime(dateFormat);
			int t = this.date.get(Calendar.YEAR);
			this.date.set(Calendar.YEAR, start.getDate().get(Calendar.YEAR));
			result = between(start.getDate(), temp); //controllo se la data di oggi è tra start ed il 31/12 dell'anno corrente
			this.date.set(Calendar.YEAR, end.getDate().get(Calendar.YEAR));
			result2 = between(temp, end.getDate()); //controllo se la data di oggi è tra il 31/12 e la data end
			this.date.set(Calendar.YEAR, t);
			if(this.date.get(Calendar.DAY_OF_MONTH)==31 && this.date.get(Calendar.MONTH)== Calendar.DECEMBER)
				return true;
			return result || result2;
		}
		else if (start.getDate().get(Calendar.YEAR) == end.getDate().get(Calendar.YEAR)) //inizio e fine nello stesso anno
		{
			int temp = this.date.get(Calendar.YEAR);
			this.date.set(Calendar.YEAR, start.getDate().get(Calendar.YEAR)); //cambio il vecchio anno con l'anno corrente
			result = between(start, end);  //confronto le date
			this.date.set(Calendar.YEAR, temp);
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
	
	/**
	 * override dell'equals
	 * @param data in ingresso
	 * @return true se le due date convertite in stringhe sono uguali, false altrimenti
	 */
	@Override
	public boolean equals(Object data)
	{
		if (data == this)
			return true;
		if (!(data instanceof DateOur))
			return false;
		DateOur date = (DateOur) data;
		String me = this.getStringDate();
		return (me.equals((date.getStringDate())));
	}
	@Override
	public int hashCode()
	{
		int result = 17;
		result = 31 * result + getStringDate().hashCode();
		return result;
	}
	
	/**
	 * Controllo se una data è tra due estremi
	 * @param start inizio periodo di validità
	 * @param end fine periodo di validità
	 * @return true se la data è tra gli estremi, false altrimenti
	 */
	public boolean between(DateOur start, DateOur end)
	{
		return (this.date.before(end.getDate()) && this.date.after(start.getDate())) || this.equals(start) || this.equals(end);
	}
	
	/**
	 * Metodo per il calcolo dei periodi di validità dei piatti stagionali
	 * @param start inizio validità
	 * @param end inizio validità
	 * @return true se la data è valida, false altrimenti
	 */
	private boolean between(Calendar start, Calendar end)
	{
		//if (start.get(Calendar.DAY_OF_YEAR) == end.get(Calendar.DAY_OF_YEAR)) return true; //se anno d'inizio e fine sono uguali allora true
		return this.date.before(end) && this.date.after(start);
	}
	
}
