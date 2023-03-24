public interface Login
{
	boolean saveUser(String name, String password, String confPassword, boolean manager, boolean employee, boolean storageWorker);
	boolean login(String name, String password);
	boolean checkPermission(String role);
}
