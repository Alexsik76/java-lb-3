//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.\
import java.time.Duration;
import java.time.LocalDateTime;

public class MVCMain {
    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        System.out.print("Hello and welcome!\n");

        for (int i = 1; i <= 5; i++) {
            //TIP Press <shortcut actionId="Debug"/> to start debugging your code. We have set one <icon src="AllIcons.Debugger.Db_set_breakpoint"/> breakpoint
            // for you, but you can always add more by pressing <shortcut actionId="ToggleLineBreakpoint"/>.
            System.out.println("i = " + i);


        }
        LocalDateTime st_t = LocalDateTime.now().minusHours(5);
        Duration dur = Duration.ofHours(4);
        Permissions perm_t =  new TimePermission(false, st_t, dur);
        SkiPass sky_pass = new SkiPass("1", perm_t );
        System.out.println(perm_t);
        System.out.println(sky_pass);
        sky_pass.useSkyPass();
    }
}