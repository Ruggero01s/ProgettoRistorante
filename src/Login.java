public interface Login
{

	User login(String name, String password);
	boolean checkPermission(String role, User currentUser);
}
