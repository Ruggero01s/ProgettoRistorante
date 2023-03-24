public class ModelAttributes
{
    int capacity;
    int workloadPerson;
    DateOur today;
    int maxWorkloadIncrement;

    public ModelAttributes(int capacity, int workloadPerson, DateOur today, int maxWorkloadIncrement) {
        this.capacity = capacity;
        this.workloadPerson = workloadPerson;
        this.today = today;
        this.maxWorkloadIncrement = maxWorkloadIncrement;
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

    public int getMaxWorkloadIncrement() {
        return maxWorkloadIncrement;
    }

    public void setMaxWorkloadIncrement(int maxWorkloadIncrement) {
        this.maxWorkloadIncrement = maxWorkloadIncrement;
    }
}
