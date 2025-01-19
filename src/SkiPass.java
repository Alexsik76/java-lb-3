public class SkiPass {
    int id;
    boolean blocked = false;
    Permissions permission;

    SkiPass(int id, Permissions permission) {
        this.id = id;
        this.permission = permission;
    }

    public void checkSkyPass(boolean verbose) throws NotPermitted {
        if (blocked) {
            throw new NotPermitted(getInfo(verbose), "SkyPass blocked");
        }
        if (!permission.checkPermission()) {
            throw new NotPermitted(getInfo(verbose), "");
        }
    }

    public String getInfo(boolean verbose) {
        String spInfo = String.valueOf(this.id);
        if (verbose) {
            spInfo = this.toString();
        }
        return spInfo;
    }

    public void useSkyPass(boolean verbose) {
        try {
            checkSkyPass(verbose);
            permission.usePermission();
            System.out.println("SkiPass " + getInfo(verbose) + "\n" +
                    Colors.GREEN + "successfully used" + Colors.RESET);
        } catch (NotPermitted e) {
            System.err.println(e.getMessage());
        }
    }

    public void blockSkyPass() {
        this.blocked = true;
        System.out.println("SkiPass " + getInfo(true) + "\n" +
                Colors.RED + "successfully blocked" + Colors.RESET);
    }

    public void unBlockSkyPass() {

        this.blocked = false;
        System.out.println("SkiPass " + getInfo(true) + "\n" +
                Colors.GREEN + "successfully unblocked" + Colors.RESET);
    }

    @Override
    public String toString() {

        return "ID: " + this.id + "\n" +
                "Permission class: " + this.permission.getClass().getSimpleName() + "\n" +
                "Blocked: " + this.blocked + "\n" +
                this.permission.get_additional_info() + "\n";
    }
}

