package ru.otus.java;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleExecutorDemo {
    private static final Logger logger = LoggerFactory.getLogger(SimpleExecutorDemo.class);
    private static final Integer MAX_SIZE = 10;
    private static final Integer MIN_SIZE = 1;
    private Integer count = 0;
    private Boolean up = true;
    private String lastTreadName;
    private String firstTreadName;

    public static void main(String[] args) {
        SimpleExecutorDemo stepByStep = new SimpleExecutorDemo();
        //работает только с двумя потоками
        new Thread(stepByStep::action).start();
        new Thread(stepByStep::action).start();
    }

    private synchronized void action() {
        initVariableOnFirstAction();

        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            try {
                evaluateCountWhenFirstThread();

                while (lastTreadName.equals(Thread.currentThread().getName())) {
                    this.wait();
                }

                lastTreadName = Thread.currentThread().getName();
                sleep();
                notifyAll();
                logger.info("{} step:{}", lastTreadName, String.format("%" + count + "s", count));
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
                throw new NotInterestingException(ex);
            }
        }
    }

    private void initVariableOnFirstAction() {
        if (firstTreadName == null) {
            firstTreadName = Thread.currentThread().getName();
            logger.info("first thread come: {}", firstTreadName);
            lastTreadName = firstTreadName;
        }
    }

    private void evaluateCountWhenFirstThread() {
        if (firstTreadName.equals(Thread.currentThread().getName())) {
            if (count.equals(MAX_SIZE)) {
                up = false;
            } else if (count.equals(MIN_SIZE)) {
                up = true;
            }

            if (up) {
                count++;
            } else {
                count--;
            }
        }
    }

    private static void sleep() {
        try {
            Thread.sleep(1_000);
        } catch (InterruptedException e) {
            logger.error(e.getMessage());
            Thread.currentThread().interrupt();
        }
    }

    private static class NotInterestingException extends RuntimeException {
        NotInterestingException(InterruptedException ex) {
            super(ex);
        }
    }
}
