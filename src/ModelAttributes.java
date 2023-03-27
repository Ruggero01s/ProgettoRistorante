/**
 * Classe di passaggio che il reader può usare per inizializzare il model
 */
public class ModelAttributes
{
    int capacity;
    int workloadPerson;
    DateOur today;
    int increment;

    public ModelAttributes(int capacity, int workloadPerson, DateOur today, int increment) {
        this.capacity = capacity;
        this.workloadPerson = workloadPerson;
        this.today = today;
        this.increment = increment;
    }

    public ModelAttributes() {}

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getWorkloadPerson() {
        return workloadPerson;
    }

    public void setWorkloadPerson(int workloadPerson) {
        this.workloadPerson = workloadPerson;
    }

    public DateOur getToday() {
        return today;
    }

    public void setToday(DateOur today) {
        this.today = today;
    }

    public int getIncrement() {
        return increment;
    }

    public void setIncrement(int increment) {
        this.increment = increment;
    }
}
