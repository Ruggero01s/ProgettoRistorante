public class Person
{
    boolean manager, employee, storageWorker;
    String name;

    public Person(String name, boolean manager, boolean employee, boolean storageWorker)
    {
        this.manager = manager;
        this.employee = employee;
        this.storageWorker = storageWorker;
        this.name = name;
    }
    public Person() {
    }
    public boolean isManager() {
        return manager;
    }

    public void setManager(boolean manager) {
        this.manager = manager;
    }

    public boolean isEmployee() {
        return employee;
    }

    public void setEmployee(boolean employee) {
        this.employee = employee;
    }

    public boolean isStorageWorker() {
        return storageWorker;
    }

    public void setStorageWorker(boolean storageWorker) {
        this.storageWorker = storageWorker;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
