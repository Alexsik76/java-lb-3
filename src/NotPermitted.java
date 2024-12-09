public class NotPermitted extends Exception {
    public NotPermitted(String reason) {
        super("Not permitted: " + reason);
    }
}
