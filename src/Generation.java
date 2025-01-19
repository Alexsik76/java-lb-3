import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;


public class Generation {
    ArrayList<SkiPass> tickets = new ArrayList<>();
    boolean verbose;
    Generation(int number, boolean verbose) {
        this.verbose = verbose;
        if (number < 1) {
            throw new IllegalArgumentException("Число квитків має бути більше 0");
        }
        if (number > 50) {
            System.out.println(Colors.RED + "Буде створено забагато квитків" + Colors.RESET);
        }
        System.out.println(Colors.GREEN + "Буде створено " + number + " квитків" + Colors.RESET);
        int j = 0;
        while (j <= number) {
            for (int i = 0; i < Math.random() * 10; i++) {
                if (j > number) {
                    break;
                }
                try {
                    Permissions perm = getRandomShortPeriodPermission();
                    SkiPass skiPass = new SkiPass(j, perm);
                    tickets.add(skiPass);
                    System.out.println("Створено квиток:" + skiPass.getInfo(verbose));
                    j++;
                } catch (IllegalArgumentException e) {
                    System.err.println(e.getMessage());
                }
            }
            for (int i = 0; i < Math.random() * 10; i++) {
                if (j > number) {
                    break;
                }
                Permissions perm = getRandomCountPermission();
                SkiPass skiPass = new SkiPass(j, perm);
                tickets.add(skiPass);
                System.out.println("Створено квиток:" + skiPass.getInfo(verbose));
                j++;
            }
            for (int i = 0; i < Math.random() * 10; i++) {
                if (j > number) {
                    break;
                }
                Permissions perm = getRandomSeasonsPermission();
                SkiPass skiPass = new SkiPass(j, perm);
                tickets.add(skiPass);
                System.out.println("Створено квиток:" + skiPass.getInfo(verbose));
                j++;

            }
        }
    }

    private Enums.StartTimes getRandomStartTimes() {
        Enums.StartTimes[] allItems = Enums.StartTimes.values();
        Collections.shuffle(Arrays.asList(allItems));
        return allItems[0];
    }

    private Enums.ShortPeriod getRandomShortPeriod() {
        Enums.ShortPeriod[] allItems = Enums.ShortPeriod.values();
        Collections.shuffle(Arrays.asList(allItems));
        return allItems[0];
    }

    private Permissions getRandomShortPeriodPermission() {
        return new ShortPeriodPermission(LocalDate.now(), getRandomStartTimes(), getRandomShortPeriod(), true);
    }

    private Permissions getRandomCountPermission() {
        return new CountPermission(true, (int) (Math.random() * 10));
    }

    private Permissions getRandomSeasonsPermission() {
        return new SeasonPermission(LocalDate.now(), getRandomStartTimes());
    }

    public ArrayList<SkiPass> getTickets() {
        return tickets;
    }
}
