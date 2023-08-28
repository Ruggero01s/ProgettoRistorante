import javax.management.InstanceNotFoundException;
public class Loginner implements Login{

    RestaurantRepository repo;
    ErrorSetter erSet;
    public Loginner(RestaurantRepository repo, ErrorSetter erSet){
        this.repo = repo;
        this.erSet = erSet;
    }

    /**
     * Metodo che gestisce il login di un utente tramite utente e password
     *
     * @param name     nome utente
     * @param password password inserita
     * @return true se il login è andato a buon fine, false altrimenti
     */
    public User login(String name, String password) {
        if (!name.trim().isEmpty() && !password.trim().isEmpty()) //controllo che abbiano un valore
        {
            try {
                User user = repo.findUser(name);
                if (user.checkPassword(password)) //se la password coincide
                {
                    return user;
                } else erSet.errorSetter(Controller.INVALID_PASSWORD);
            } catch (InstanceNotFoundException e) {
                erSet.errorSetter(Controller.INVALID_USERNAME);
            }
        } else erSet.errorSetter(Controller.INVALID_FORMAT);
        return null;
    }

    /**
     * Controlla se l'utente ha i permessi per accedere ad un certo ruolo
     *
     * @param role ruolo che devo controllare
     * @return true se l'utente ha i permessi, false altrimenti
     */
    public boolean checkPermission(String role, User currentUser) {
        boolean out;
        switch (role) {
            case "manager":
                out = currentUser.isManager();
                break;
            case "employee":
                out = currentUser.isEmployee();
                break;
            case "warehouse worker":
                out = currentUser.isStorageWorker();
                break;
            default:
                out = false;
        };
        if (!out)
            erSet.errorSetter(Controller.NO_PERMISSION); //in caso non abbia i permessi giusto avviso l'utente
        return out;
    }
}
