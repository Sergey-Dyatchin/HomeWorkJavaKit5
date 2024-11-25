import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Решение позволяет:
 * использовать любое количество филосовов за круглым столом задается в конструкторе стола
 * Задовать в конструкторе произвольное количество необходимых приемов пищи одинаковое для всех
 * Расположение филосовов и вилок за круглым столом строится исходя из следующего:
 * первый филосов слева имеет вилку №1 справа вилку №2
 * последующие филосовы имеет слева вилку равную своему номеру справа (кроме последнего филосова) вилка номер +1
 * Последний филосов справа будет пользоваться вилкой №1
 * номерация филосово и вилок начинается с 1
 */
public class Table extends Thread{
    private int countPhilosophers;
    private List<Fork> forkList;
    private List<Philosophers> philosophersList;
    private CountDownLatch cdl;

    public Table(int countPhilosophers, int countNeedEat) {
        this.countPhilosophers = countPhilosophers;
        forkList = new ArrayList<>();
        philosophersList = new ArrayList<>();
        cdl = new CountDownLatch(countPhilosophers);
        initForks();
        initPhilosophers(countNeedEat);
    }

    private void initForks() {
        for (int i = 0; i < countPhilosophers; i++) {
            forkList.add(new Fork(i + 1));
        }
    }

    private void initPhilosophers(int countNeedEat) {
        for (int i = 0; i < countPhilosophers; i++) {
            philosophersList.add(new Philosophers(this, cdl, countNeedEat, i + 1));
        }
    }
    @Override
    public void run() {
        System.out.println("Начинаем обед");
        for (Philosophers philosopher : philosophersList) {
            philosopher.start();
        }
        try {
            cdl.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Обед закончен");
    }

    public synchronized boolean takeForks(Philosophers philosophers) {
        int number = philosophers.getNumber();
        int rightNumberFork = (number + 1 > countPhilosophers)? 1 : number + 1;
        if (forkList.get(number - 1).isFree() && forkList.get(rightNumberFork - 1).isFree()) {
            forkList.get(number - 1).setFree(false);
            forkList.get(rightNumberFork - 1).setFree(false);
            System.out.println("Филосов №" + number + " взял вилки №" + number + " и " + rightNumberFork);
            return true;
        }
        return false;
    }

    public void putDownForks (Philosophers philosophers) {
        int number = philosophers.getNumber();
        int rightNumberFork = (number + 1 > countPhilosophers)? 1 : number + 1;
        forkList.get(number - 1).setFree(true);
        forkList.get(rightNumberFork - 1).setFree(true);
        System.out.println("Филосов №" + number + " положил вилки №" + number + " и " + rightNumberFork);
    }


}
