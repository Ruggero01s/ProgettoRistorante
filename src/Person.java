public class Person
{
    boolean manager, employee, warehouseman;
    String name;

    public Person(String name, boolean manager, boolean employee, boolean warehouseman)
    {
        this.manager = manager;
        this.employee = employee;
        this.warehouseman = warehouseman;
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

    public boolean isWarehouseman() {
        return warehouseman;
    }

    public void setWarehouseman(boolean warehouseman) {
        this.warehouseman = warehouseman;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
