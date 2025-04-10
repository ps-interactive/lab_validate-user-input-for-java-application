import java.util.ArrayList;
import java.nio.charset.StandardCharsets;
public class ArrayListNativeJavaLib {
    private static final int NATIVE_BUFFER_MAX_DATA_SIZE = 9;
    public static void main(String[] args) {
        NativeJavaLib nativeLib = new NativeJavaLib();
        String inputData;
        if (args.length > 0) {
            inputData = args[0];
            byte[] inputBytes = inputData.getBytes(StandardCharsets.UTF_8);
            ArrayList<Byte> byteList = new ArrayList<>();
            boolean inputTooLong = false;
            for (byte b : inputBytes) {
                if (byteList.size() >= NATIVE_BUFFER_MAX_DATA_SIZE) {
                    inputTooLong = true;
                    break;
                }
                byteList.add(b);
            }
            if (inputBytes.length > NATIVE_BUFFER_MAX_DATA_SIZE) {
                 inputTooLong = true;
            }
            if (inputTooLong) {
                System.err.println("Java Code (ArrayList Check): Input string as bytes (" + inputBytes.length + ") exceeds native buffer data capacity (" + NATIVE_BUFFER_MAX_DATA_SIZE + "). Aborting.");
            } else {
                System.out.println("Java Code (ArrayList Check): Input size (" + byteList.size() + " bytes) is safe. Sending to native code via NativeJavaLib instance: \"" + inputData + "\"");
                try {
                    nativeLib.processString(inputData);
                    System.out.println("Java Code: Native method executed successfully.");
                } catch (UnsatisfiedLinkError e) {
                    System.err.println("Error linking native method. Check NativeJavaLib setup.");
                    System.err.println("Error: " + e.getMessage());
                } catch (Exception e) {
                    System.err.println("An error occurred during native call: " + e.getMessage());
                }
            }
        } else {
            System.out.println("Usage: java ArrayListNativeJavaLib <input_string>");
            System.out.println("Try with short input: java ArrayListNativeJavaLib Hello");
            System.out.println("Try with long input: java ArrayListNativeJavaLib AAAAAAAAAAAAAAAAAAAA");
        }
    }
}

