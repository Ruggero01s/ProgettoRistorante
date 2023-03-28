import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class User
{
    private final boolean manager;
    private final boolean employee;
    private final boolean storageWorker;
    private final String name;
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
    
    public boolean isManager() {
        return manager;
    }
    
    public boolean isEmployee() {
        return employee;
    }
    
    public boolean isStorageWorker() {
        return storageWorker;
    }
    
    public String getName() {
        return name;
    }
    
    /**
     * applico hash e sale alla password
     */
    public void hashAndSaltPassword()
    {
        byte[] salt = generateSalt(); //genero il sale
        byte[] hashedPassword;
        hashedPassword = hashPassword(this.password.getBytes(), salt);
        this.password= Base64.getEncoder().encodeToString(hashedPassword) + ":" + Base64.getEncoder().encodeToString(salt);
    }
    
    /**
     * Genera un sale casuale per le password
     * @return il sale come array di bite
     */
    private byte[] generateSalt()
    {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }
    
    /**
     * Hasho la password utilizzando SHA-256 e il sale che viene passato
     *
     * @param pass password da hashare
     * @param salt il sale
     * @return password hashata
     */
    private byte[] hashPassword(byte[] pass, byte[] salt)
    {
        MessageDigest md;
        try
        {
            md = MessageDigest.getInstance("SHA-256");
        }
        catch (NoSuchAlgorithmException e)
        {
            throw new RuntimeException(e);
        }
        md.update(salt);
        return md.digest(pass);
    }
    
    /**
     * La strina in ingresso viene hashata e paragonata alla password salvata
     *
     * @param password       stringa da checkare
     * @return true se coincidono, false altrimenti
     */
    public boolean checkPassword(String password)
    {
        String[] parts = this.password.split(":"); //separo password e sale
        byte[] hashedData = Base64.getDecoder().decode(parts[0]);
        byte[] salt = Base64.getDecoder().decode(parts[1]);
        
        byte[] hashedPepperedPassword = hashPassword(password.getBytes(StandardCharsets.UTF_8), salt); //hasho la stringa in ingresso
       
        return MessageDigest.isEqual(hashedData, hashedPepperedPassword); //paragono password e stringa
    }


    /**
     * override dell'equals
     * @param obj oggetto da controllare
     * @return true se i nomi sono uguali e sono entrambi della stessa classe, false altrimenti
     */
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (!(obj instanceof User))
            return false;

        User user = (User) obj;

        return this.name.equals(user.getName());
    }

    @Override
    public int hashCode() {
        int result = 37;
        result = 41 * result + name.hashCode();
        return result;
    }
    
}
