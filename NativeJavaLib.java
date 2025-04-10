public class NativeJavaLib {

    public native void processString(String input);

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
        NativeJavaLib nativeLib = new NativeJavaLib();
        String inputData;

        if (args.length > 0) {
            inputData = args[0];
            System.out.println("Java Code: Sending input to native code: \"" + inputData + "\"");
            try {
                nativeLib.processString(inputData);
                System.out.println("Java Code: Native method executed successfully.");
            } catch (UnsatisfiedLinkError e) {
                System.err.println("Error linking native method. Check class/method names and signatures.");
                System.err.println("Error: " + e.getMessage());
            } catch (Exception e) {
                System.err.println("An error occurred during native call: " + e.getMessage());
            }
        } else {
            System.out.println("Usage: java NativeJavaLib <input_string>");
            System.out.println("Try with short input: java NativeJavaLib Hello");
            System.out.println("Try with long input: java NativeJavaLib AAAAAAAAAAAAAAAAAAAA");
        }
    }
}
