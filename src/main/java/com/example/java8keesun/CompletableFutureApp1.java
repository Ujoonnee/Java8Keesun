package com.example.java8keesun;

import sun.java2d.loops.TransformHelper;

import java.util.concurrent.*;

public class CompletableFutureApp1 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        CompletableFuture<String> future = new CompletableFuture<>();
        future.complete("1. 직접 값을 넘기며 작업 종료");

        CompletableFuture<String> future1 = CompletableFuture.completedFuture("2. static factory method 사용");

        CompletableFuture<Void> future2 = CompletableFuture.runAsync(() -> {
            System.out.println("3. return이 없는 경우 runAsync" + Thread.currentThread().getName());
        });

        CompletableFuture<String> future3 = CompletableFuture.supplyAsync(() -> {
            System.out.println("4. callback이  Function일 경우 thenApply" + Thread.currentThread().getName());
            return "Hello";
        }).thenApply((s -> {
            System.out.println("5. Thread = " + Thread.currentThread().getName());
            return s.toUpperCase();
        }));

        CompletableFuture<Void> future4 = CompletableFuture.supplyAsync(() -> {
            System.out.println("6. suuplyAsync는 특정 threadpool 사용 가능 = " + Thread.currentThread().getName());
            return "Hello";
        }, executorService).thenAccept((s -> {
            System.out.println("7. callback이 Consumer일 경우 thenAccept = " + Thread.currentThread().getName());
            System.out.println("8. "+ s.toLowerCase());
        }));

        CompletableFuture<Void> future5 = CompletableFuture.supplyAsync(() -> "return")
                        .thenRun(() -> {
                            System.out.println("9. return을 받을 필요도 없고 뭔가 하기만 하겠다");
                            System.out.println("Thread = "+ Thread.currentThread().getName());
                        }).thenRunAsync(() -> {
                            System.out.println("10. thenRunAync도 특정 threadpool 사용 가능 = "+ Thread.currentThread().getName());
                        }, executorService);

        System.out.println("11. "+ future.get());
        System.out.println("12. "+ future1.get());
        System.out.println("13. "+ future2.get());
        System.out.println("14. "+ future3.get());
        System.out.println("15. "+ future4.get());
        System.out.println("16. "+ future5.get());

        executorService.shutdown();
    }
}
