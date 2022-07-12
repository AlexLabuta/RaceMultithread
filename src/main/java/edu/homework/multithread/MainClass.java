package edu.homework.multithread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainClass {
    public static final int CARS_COUNT = 4 ;
    private static CountDownLatch cdl = new CountDownLatch(CARS_COUNT);
    public static void main (String[] args) {
        System.out.println( "ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!" );
        Race race = new Race( new Road( 60 ), new Tunnel(), new Road( 40 ));
        Car[] cars = new Car[CARS_COUNT];
        CyclicBarrier cb = new CyclicBarrier(CARS_COUNT);
        for ( int i = 0 ; i < cars.length; i++) {
            cars[i] = new Car(race, 20 + ( int ) (Math.random() * 10 ));
        }

        ExecutorService service = Executors.newFixedThreadPool(CARS_COUNT);
        for( int i = 0; i < cars.length; i++){
            final int w = i;
            service.execute(new Runnable() {
                                @Override
                                public void run() {

                                    System.out.println("Участник №" +  w + " готовиться...");
                                    System.out.println("Участник №" +  w + " готов");
                                    cdl.countDown();
                                }
                            }
            );

        }
        try {
            cdl.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        service.shutdown();
        System.out.println( "ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!" );




//        for ( int i = 0 ; i < cars.length; i++) {
//            new Thread(cars[i]).start();
//        }
//        System.out.println( "ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!" );
//        System.out.println( "ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!" );
    }
}
