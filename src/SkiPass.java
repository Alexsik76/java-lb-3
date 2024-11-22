public class SkiPass {
    String id;
    boolean blocked = false;
    Permissions permission;

    SkiPass(String id, Permissions permission) {
        this.id = id;
        this.permission = permission;
    }

    public boolean checkSkyPass() throws NotPermitted {
        if (blocked) {
            throw new NotPermitted("sky pass blocked");
        }
        if (!permission.checkPermission()) {
            throw new NotPermitted("");
        }
        return true;
    }

    public void useSkyPass() throws NotPermitted {
        if (checkSkyPass()) {
            permission.usePermission();
            System.out.println("SkiPass " + id + " successfully used");
        } else {
            System.out.println("SkyPass " + id + " not permitted");
        }
    }
}

