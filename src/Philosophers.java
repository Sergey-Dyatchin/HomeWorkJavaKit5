import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * В классе намернно отказался от поля имени в пользу номера за столом,
 * для наглядности при увелечении количества филосово за столом.
 */
public class Philosophers extends Thread {
    private int countNeedEat;
    private Table table;
    private int number;
    private CountDownLatch cdl;

    public Philosophers(Table table,CountDownLatch cdl, int countNeedEat, int number) {
        this.table = table;
        this.cdl = cdl;
        this.countNeedEat = countNeedEat;
        this.number = number;
    }

    @Override
    public void run() {
        while (countNeedEat > 0) {
            thinking();
            eating();

        }
        System.out.println("Филосов №" + number + " наелся");
        cdl.countDown();
    }

    private boolean takeForks() {
        return table.takeForks(this);
    }

    private void putDownForks() {
        table.putDownForks(this);
    }

    private void eating() {
        if (takeForks()) {
            System.out.println("Филосов №"+ number + " ест");
            try {
                sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Филосов №"+ number + " поел");
            countNeedEat--;
            putDownForks();
        }
    }

    private void thinking() {
        System.out.println("Филосов №"+ number + " думает");
        try {
            sleep(new Random().nextInt(2000, 5000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Филосов №"+ number + " закончил думать");
    }

    public int getNumber() {
        return number;
    }
}
