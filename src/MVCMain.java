public class MVCMain {
    public static void main(String[] args) throws IllegalArgumentException, NotPermitted {
        System.out.print("Hello and welcome!\n");
        try {
            String st_d = "02.12.2024";
            Permissions perm_t = new ShortPeriodPermission(st_d, StartTimeSkyPass.AM, DurationSkyPass.FIVE_DAYS, false);
            SkiPass sky_pass = new SkiPass("1", perm_t);
            sky_pass.useSkyPass();
            Permissions perm_c = new CountPermission(false, 0);
            SkiPass sky_pass_2 = new SkiPass("2", perm_c);
            sky_pass_2.useSkyPass();
        }
        catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }

    }
}