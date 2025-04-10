import java.io.*;
import org.apache.commons.io.serialization.ValidatingObjectInputStream;
class UserData implements Serializable {
    private static final long serialVersionUID = 1L;
    public String username;
    public String role;
    public UserData(String username, String role) {
        this.username = username;
        this.role = role;
    }
    @Override
    public String toString() {
        return "UserData [username=" + username + ", role=" + role + "]";
    }
}
class MaliciousCommand implements Serializable {
        private static final long serialVersionUID = 1L;
        private String command;
}
public class SecureDeserialization {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java SecureDeserialization <serialized-file>");
            return;
        }
        String filename = args[0];
        System.out.println("Attempting to securely deserialize: " + filename);
        try (FileInputStream fis = new FileInputStream(filename)) {
            ValidatingObjectInputStream vois = new ValidatingObjectInputStream(fis);
            System.out.println("Whitelisting allowed classes: UserData, java.lang.String");
            vois.accept(UserData.class); 
            vois.accept(String.class);   
            Object obj = vois.readObject(); 
            System.out.println("Successfully deserialized object: " + obj.toString());
            vois.close(); 
        } catch (InvalidClassException e) {
            System.err.println("Blocked potentially malicious deserialization attempt!");
            System.err.println("Error: " + e.getMessage());
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error during deserialization: " + e.getMessage());
            e.printStackTrace();
        }
    }
}