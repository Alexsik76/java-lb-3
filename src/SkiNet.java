import java.util.ArrayList;

public class SkiNet {
    ArrayList <SkiPass> tickets;
    SkiNet (ArrayList <SkiPass> tickets){
        this.tickets = tickets;
    }
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (SkiPass ticket : tickets) {
           s.append(ticket);
           s.append("\n");
        }
        return s.toString();
    }


}
