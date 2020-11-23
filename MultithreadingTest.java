package com.JohnnyGomezzz;

import java.util.Arrays;

public class MultithreadingTest{

    static final int SIZE = 10000000;
    static final int HALF = SIZE / 2;

    float[] arr = new float[SIZE];



    public void firstMethod()
    {
        long a = System.currentTimeMillis();
        
        Arrays.fill(arr, 1);

        for (int i = 0; i < SIZE; i++) {
            arr[i] = (float)(arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }

        System.out.println("Один поток отработал за " + (System.currentTimeMillis() - a) + " мс");
        
    }

    private void secondMethod() throws InterruptedException
    {
        long a = System.currentTimeMillis();

        Arrays.fill(arr, 1);

        float[] a1 = new float[HALF];
        float[] a2 = new float[HALF];

        System.arraycopy(arr, 0, a1, 0, HALF);
        System.arraycopy(arr, HALF, a2, 0, HALF);

        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < HALF; i++) {
                a1[i] = (float)(a1[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < HALF; i++) {
                a2[i] = (float)(a2[i] * Math.sin(0.2f + (i + HALF) / 5) * Math.cos(0.2f + (i +
                        HALF) / 5) * Math.cos(0.4f + (i + HALF) / 2));
            }
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        System.arraycopy(a1, 0, arr, 0, HALF);
        System.arraycopy(a2, 0, arr, HALF, HALF);

        System.out.println("Два потока отработали за " + (System.currentTimeMillis() - a) + " мс");


    }

    public static void main(String[] args) throws InterruptedException {

        MultithreadingTest test = new MultithreadingTest();
        
        test.firstMethod();
        test.secondMethod();

    }

}
