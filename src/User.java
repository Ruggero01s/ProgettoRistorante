import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

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
    
    /**
     * Hashes and salt the given password using SHA-256.
     *
     */
    public void hashAndSaltPassword()
    {
        // Generate a random salt for the password hash
        byte[] salt = generateSalt();
        // Hash the password using SHA-256 and the salt
        byte[] hashedPassword;
        hashedPassword = hashPassword(this.password.getBytes(), salt);
        // Encode the hashed password and salt as Base64 and return the result
        this.password= Base64.getEncoder().encodeToString(hashedPassword) + ":" + Base64.getEncoder().encodeToString(salt);
    }
    
    /**
     * Generates a random salt for use in password hashing.
     *
     * @return the salt as a byte array
     */
    private byte[] generateSalt()
    {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }
    
    /**
     * Hashes the given data using SHA-256 and the given salt.
     *
     * @param pass the data to hash
     * @param salt the salt to use for hashing
     * @return the hashed data as a byte array
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
     * Checks if the given password matches the hashed and peppered password.
     *
     * @param password       the password to check
     * @return true if the passwords match, false otherwise
     */
    public boolean checkPassword(String password)
    {
        // Split the hashed password and salt
        String[] parts = this.password.split(":");
        /*if (parts.length != 2)
        {
            return false;
        }*/
        byte[] hashedData = Base64.getDecoder().decode(parts[0]);
        byte[] salt = Base64.getDecoder().decode(parts[1]);
        
        //  hash it with the salt
        byte[] hashedPepperedPassword = hashPassword(password.getBytes(StandardCharsets.UTF_8), salt);
        
        // Compare the hashed password to the stored hash
        return MessageDigest.isEqual(hashedData, hashedPepperedPassword);
    }
    
}
