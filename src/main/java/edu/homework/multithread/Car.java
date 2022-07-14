package edu.homework.multithread;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicBoolean;

public class Car implements Runnable {
    private static int CARS_COUNT;
    private static final AtomicBoolean abForWinner;

    static {
        CARS_COUNT = 0;
        abForWinner = new AtomicBoolean(false);
    }

    private final CyclicBarrier cb;
    private final Race race;
    private final int speed;
    private final String name;

    public Car(Race race, int speed, CyclicBarrier cb) {
        this.race = race;
        this.speed = speed;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
        this.cb = cb;
    }

    public String getName() {
        return name;
    }

    public int getSpeed() {
        return speed;
    }

    @Override
    public void run() {
        try {
            System.out.println(this.name + " готовится");
            Thread.sleep(500 + (int) (Math.random() * 800));
            System.out.println(this.name + " готов");
            cb.await();
            cb.await();
            for (int i = 0; i < race.getStages().size(); i++) {
                race.getStages().get(i).go(this);
            }
            if (abForWinner.compareAndSet(false, true)) {
                System.out.println(name + " WIN!!!");
            }
            cb.await();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
