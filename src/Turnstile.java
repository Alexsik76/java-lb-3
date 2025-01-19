import java.util.ArrayList;
import java.util.Scanner;

public class Turnstile {
    SkiNet system;
    public int currentId =0;
    Turnstile (SkiNet system) {
       this.system = system;
    }
    public ArrayList<Integer> getIds(){
        ArrayList<Integer> ids = new ArrayList<>();
        for (SkiPass ticket: system.tickets){
            ids.add(ticket.id);
        }
        return ids;
    }
    public SkiPass getTicket(int id){
        return system.tickets.get(id);
    }

    public void blockTicket(int id){
        SkiPass ticket = getTicket(id);
        ticket.blockSkyPass();
    }
    public void unBlockTicket(int id){
        SkiPass ticket = getTicket(id);
        ticket.unBlockSkyPass();
    }

    public void useTicket(int id){
        SkiPass ticket = getTicket(id);
        ticket.useSkyPass(true);
    }

    public void showTicketInfo(int id){
        SkiPass ticket = getTicket(id);
        System.out.println(ticket.toString());
    }
    public void useTurnstile() {
        Scanner userInput = new Scanner(System.in);
        String greeting = "Привіт\n";
        System.out.println(greeting);
        boolean exit = false;
        while (!exit) {
            System.out.println("""
                    Введіть:
                    'c' - для вибору квитка;
                    'p' - для використання квитка;
                    або 'q' для виходу""");
            String value = userInput.nextLine();
            switch (value) {
                case "c":
                    choiceTicketId();
                    break;
                case "p":
                    processTicket();
                    break;
                case "q":
                    exit = true;
                    break;
                default:
                    System.out.println("Ввід не розпізнано");
            }
        }
    }
    private void choiceTicketId(){
        boolean exit = false;
        Scanner userInput = new Scanner(System.in);

        while (!exit){
            ArrayList<Integer> ids = getIds();
            System.out.println(ids.toString());
            System.out.println("Введіть номер квитка, або 'q' для виходу");
            String value = userInput.nextLine();
            if (value.equals("q")){
                exit = true;
            } else {
                try {
                    int id = Integer.parseInt(value);
                    if (id < 0 || id >= system.tickets.size()){
                        throw new IllegalArgumentException("Такого квитка не існує");
                    }
                    currentId = id;
                    System.out.printf("обрано квиток %d\n", currentId);
                    showTicketInfo(currentId);
                    exit = true;
                } catch (IllegalArgumentException e) {
                    System.err.println(e.getMessage());
                }
            }
        }
    }

    private void processTicket() {
        boolean exit = false;
        Scanner userInput = new Scanner(System.in);
        showTicketInfo(currentId);
        while (!exit){
            System.out.println("""
                    Введіть:
                    'i' - для отримання інформації про квиток;
                    'u' - для використання квитка;
                    'b' - для блокування квитка;
                    'n' - для розблокування квитка,
                    або 'q' для виходу""");
            String value = userInput.nextLine();
            switch (value) {
                case "i":
                    showTicketInfo(currentId);
                    break;
                case "u":
                    useTicket(currentId);
                    break;
                case "b":
                    blockTicket(currentId);
                    break;
                case "n":
                    unBlockTicket(currentId);
                    break;
                case "q":
                    exit = true;
                    break;
                default:
                    System.out.println("Ввід не розпізнано");
            }
        }
    }
}
