public class SkiPass {
    String id;
    boolean blocked = false;
    Permissions permission;

    SkiPass(String id, Permissions permission) {
        this.id = id;
        this.permission = permission;
    }

    public void useSkyPass() throws NotPermitted {
        try {
            if (blocked) {
                throw new NotPermitted("sky pass blocked");
            } else {
                permission.usePermission();
                System.out.println("SkiPass " + id + " successfully used");
            }
        }
        catch (NotPermitted e) {
            System.err.println(e.getMessage());
        }

        }
}