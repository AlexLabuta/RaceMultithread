package edu.homework.multithread;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class MainClass {
    public static final int CARS_COUNT = 4;

    public static void main(String[] args) {
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");
        Race race = new Race(new Road(60), new Tunnel(), new Road(40));
        Car[] cars = new Car[CARS_COUNT];
        CyclicBarrier cb = new CyclicBarrier(CARS_COUNT + 1);
        for (int i = 0; i < cars.length; i++) {
            cars[i] = new Car(race, 20 + (int) (Math.random() * 10), cb);
            new Thread(cars[i]).start();
        }
        try {
            cb.await();
            System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");
            cb.await();
            cb.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");
    }
}



