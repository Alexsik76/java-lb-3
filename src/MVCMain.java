import java.util.ArrayList;

public class MVCMain {
    public static boolean verbose = false;

    public static void main(String[] args) throws IllegalArgumentException {
        System.out.print("Hello and welcome!\n");
        processArgs(args);
        Generation gen = new Generation(51, verbose);
        ArrayList<SkiPass> tickets = gen.getTickets();
        SkiNet sky_net = new SkiNet(tickets);
        Turnstile turnstile = new Turnstile(sky_net);
        turnstile.useTurnstile();
    }

    private static void processArgs(String[] args) {
        if (args.length > 0 && (args[0].equals("-V") || args[0].equals("--verbose"))) {
            System.out.println("Увімкнено розширене інформування. \n" +
                    "Щоб вимкнути - приберіть '-V' або '--verbose' з параметрів виклику програми");
            verbose = true;
        } else {
            System.out.println("Щоб увімкнути розширене інформування - додайте '-V' або '--verbose' \n" +
                    "до параметрів виклику програми");
        }
        System.out.println("-".repeat(20));
    }
}