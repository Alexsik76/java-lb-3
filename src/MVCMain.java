//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.\


public class MVCMain {
    public static void main(String[] args) throws IllegalArgumentException, NotPermitted {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        System.out.print("Hello and welcome!\n");

        for (int i = 1; i <= 5; i++) {
            //TIP Press <shortcut actionId="Debug"/> to start debugging your code. We have set one <icon src="AllIcons.Debugger.Db_set_breakpoint"/> breakpoint
            // for you, but you can always add more by pressing <shortcut actionId="ToggleLineBreakpoint"/>.
            System.out.println("i = " + i);
        }
        String st_t = "09:00 22.11.2024";
        try {
            Permissions perm_t = new ShortPeriodPermission(st_t, Period.HALF_DAY, false);
            SkiPass sky_pass = new SkiPass("1", perm_t);
            sky_pass.useSkyPass();
        } catch (IllegalArgumentException | NotPermitted e) {
            System.err.println(e.getMessage());
        }
        Permissions perm_c = new CountPermission(false, 5);
        SkiPass sky_pass_2 = new SkiPass("2", perm_c);
        sky_pass_2.useSkyPass();
    }
}