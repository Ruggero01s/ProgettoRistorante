public class User
{
    private boolean manager, employee, storageWorker;
    private String name;
    private String password;
    
    public User(String name, String password, boolean manager, boolean employee, boolean storageWorker)
    {
        this.manager = manager;
        this.employee = employee;
        this.storageWorker = storageWorker;
        this.name = name;
        this.password = password;
    }
    
    public String getPassword()
    {
        return password;
    }
    
    public void setPassword(String password)
    {
        this.password = password;
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
