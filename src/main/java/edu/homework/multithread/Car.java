package edu.homework.multithread;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;

public class Car implements Runnable{
    private static int CARS_COUNT;
    private static final AtomicInteger aiForWinner;
    private CyclicBarrier cb;
    private Race race;
    private int speed;
    private String name;

    static {
        CARS_COUNT = 0;
        aiForWinner = new AtomicInteger(0);
    }

    public String getName () {
        return name;
    }
    public int getSpeed () {
        return speed;
    }
    public Car (Race race, int speed, CyclicBarrier cb) {
        this .race = race;
        this .speed = speed;
        CARS_COUNT++;
        this .name = "Участник #" + CARS_COUNT;
        this.cb = cb;
    }
    @Override
    public void run () {
        try {
            System.out.println( this .name + " готовится" );
            Thread.sleep( 500 + ( int )(Math.random() * 800 ));
            System.out.println( this .name + " готов" );
            cb.await();
            cb.await();
            for ( int i = 0 ; i < race.getStages().size(); i++) {
                race.getStages().get(i).go( this );
            }
            if(aiForWinner.incrementAndGet() == 1){
                System.out.println(name + " WIN!!!");
            }
            cb.await();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
