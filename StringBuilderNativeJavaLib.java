public class StringBuilderNativeJavaLib {
    private static final int NATIVE_BUFFER_SIZE = 10;
    public static void main(String[] args) {
        NativeJavaLib nativeLib = new NativeJavaLib();
        StringBuilder inputDataBuilder;
        if (args.length > 0) {
            inputDataBuilder = new StringBuilder(args[0]);
            if (inputDataBuilder.length() >= NATIVE_BUFFER_SIZE) {
                System.err.println("Java Code (StringBuilder): Input length ("+ inputDataBuilder.length() +") is too long for the native buffer (max "+ (NATIVE_BUFFER_SIZE - 1) +" data chars). Aborting.");
            } else {
                String inputData = inputDataBuilder.toString();
                System.out.println("Java Code (StringBuilder): Input is safe. Sending to native code via NativeJavaLib instance: \"" + inputData + "\"");
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
            System.out.println("Usage: java StringBuilderNativeJavaLib <input_string>");
            System.out.println("Try with short input: java StringBuilderNativeJavaLib Hello");
            System.out.println("Try with long input: java StringBuilderNativeJavaLib AAAAAAAAAAAAAAAAAAAA");
        }
    }
}
