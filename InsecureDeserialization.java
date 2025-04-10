import java.io.*;

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
public class InsecureDeserialization {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java InsecureDeserialization <serialized-file>");
            return;
        }
        String filename = args[0];
        System.out.println("Attempting to deserialize: " + filename);
        try (FileInputStream fis = new FileInputStream(filename);
                ObjectInputStream ois = new ObjectInputStream(fis)) {
            Object obj = ois.readObject();
            System.out.println("Successfully deserialized object: " + obj.toString());
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error during deserialization: " + e.getMessage());
            e.printStackTrace();
        }
    }
}