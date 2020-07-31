package ru.otus.java;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

public class CompletableFutureDemo {
    private static final Logger logger = LoggerFactory.getLogger(CompletableFutureDemo.class);
    private static final int MAX_SIZE = 10;
    private static final int MIN_SIZE = 1;
    private static AtomicInteger count = new AtomicInteger(1);
    private static Boolean up = true;

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        Function<String, String> compose = (v) -> String.format("%s: %s", v, count.get());
        Runnable newsSupplier = CountWriter::changeCount;

        for (int i = 0; i < Integer.MAX_VALUE; i++) {

            CompletableFuture<String> futureT1 = CompletableFuture.supplyAsync(() -> "поток 2")
                    .thenApply(compose);

            CompletableFuture.supplyAsync(() -> "поток 1")
                    .thenApply(compose)
                    .thenCombine(futureT1, (a, b) -> String.format("%s, %s", a, b))
                    .thenAccept(logger::info).thenRun(newsSupplier)
                    .get();
        }
    }

    public static class CountWriter {
        public static void changeCount() {
            try {
                Thread.sleep(1000);
                if (count.get() == MAX_SIZE) {
                    up = false;
                } else if (count.get() == MIN_SIZE) {
                    up = true;
                }

                if (up) {
                    count.incrementAndGet();
                } else {
                    count.decrementAndGet();
                }
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
        }
    }
}
