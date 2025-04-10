import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class ByteBufferNativeJavaLib {
    public native void processBuffer(ByteBuffer buffer);
    static {
        try {
             System.loadLibrary("native");
         } catch (UnsatisfiedLinkError e) {
              System.err.println("FATAL: Native library 'native' could not be loaded.");
              System.err.println(e.getMessage());
              System.exit(1);
         }
    }
    public static void main(String[] args) {
        ByteBufferNativeJavaLib nativeLib = new ByteBufferNativeJavaLib();
        String inputData;
        if (args.length > 0) {
            inputData = args[0];
            byte[] inputBytes = inputData.getBytes(StandardCharsets.UTF_8);
            ByteBuffer bufferToSend = ByteBuffer.allocate(inputBytes.length);
            bufferToSend.put(inputBytes);
            bufferToSend.flip();
            System.out.println("Java Code (ByteBuffer): Sending ByteBuffer to native code.");
            System.out.println("Java Code (ByteBuffer): Capacity = " + bufferToSend.capacity());
            System.out.println("Java Code (ByteBuffer): Limit = " + bufferToSend.limit());
            System.out.println("Java Code (ByteBuffer): Position = " + bufferToSend.position());
            try {
                nativeLib.processBuffer(bufferToSend);
                System.out.println("Java Code: Native method executed successfully.");
            } catch (UnsatisfiedLinkError e) {
                System.err.println("Error linking native method. Check class/method names and signatures in C code and recompile.");
                System.err.println("Error: " + e.getMessage());
            } catch (Exception e) {
                System.err.println("An error occurred during native call: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("Usage: java ByteBufferNativeJavaLib <input_string>");
        }
    }
}
