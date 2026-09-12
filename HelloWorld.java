public class HelloWorld {
    private static final int MAX_ARG_LENGTH = 100;

    public static void main(String[] args) {
        if (args.length > 0) {
            String name = args[0];
            
            // Validate input length
            if (name.length() > MAX_ARG_LENGTH) {
                System.err.println("Error: Argument exceeds maximum length of " + MAX_ARG_LENGTH);
                System.exit(1);
            }
            
            System.out.println("Hello, " + name + "!");
        } else {
            System.out.println("Hello, World!");
        }
    }
}
