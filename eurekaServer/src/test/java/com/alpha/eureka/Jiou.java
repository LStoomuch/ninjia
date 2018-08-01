package com.alpha.eureka;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Jiou {

    public static class DataPrint {

        /**
         * true:奇数，false:偶数
         */
        public boolean flag = true;
        private int num = 1;

        ReentrantLock lock = new ReentrantLock();
        Condition ji = lock.newCondition();
        Condition ou = lock.newCondition();

        public void printJi() {
            try {
                lock.lock();
                if (flag) {
                    System.out.println("奇数是：" + num++);
                    flag = !flag;
                    Thread.sleep(50);
                    ou.signal();
                } else {
                    ji.await();
                }
            } catch (Exception e) {

            } finally {
                lock.unlock();
            }
        }

        public void printOu() {
            try {
                lock.lock();
                if (!flag) {
                    System.out.println("偶数是：" + num++);
                    flag = !flag;
                    Thread.sleep(50);
                    ji.signal();
                } else {
                    ou.await();
                }
            } catch (Exception e) {

            } finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        DataPrint dataPrint = new DataPrint();
        Thread threadJi = new Ji(dataPrint);
        Thread threadOu = new Ou(dataPrint);

        threadJi.start();
        threadOu.start();
    }

    private static class Ji extends Thread {
        DataPrint dataPrint;

        public Ji(DataPrint dataPrint) {
            this.dataPrint = dataPrint;
        }

        @Override
        public void run() {
            while (dataPrint.num <= 100) {
                dataPrint.printJi();
            }
        }
    }

    private static class Ou extends Thread {
        DataPrint dataPrint;

        public Ou(DataPrint dataPrint) {
            this.dataPrint = dataPrint;
        }

        @Override
        public void run() {
            while (dataPrint.num <= 100) {
                dataPrint.printOu();
            }
        }
    }



}
