public class NotPermitted extends Exception {
    public NotPermitted(String spInfo, String reason) {
        super("SkyPass" + " " + spInfo + " " + "not permitted" + reason);
    }
}
