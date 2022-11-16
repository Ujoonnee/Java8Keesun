package com.example.java8keesun;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class CompletableFutureApp2 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(() -> {
            System.out.println("Hello " + Thread.currentThread().getName());
            return "Hello";
        });
        CompletableFuture<String> java = CompletableFuture.supplyAsync(() -> {
            System.out.println("Java " + Thread.currentThread().getName());
            return "Java";
        });

        /*
         * thenCompose : 한 비동기작업 후에 다른 비동기 작업을 수행할 때
         * ex) hello의 결과를 받아서 world를 수행
         */
        CompletableFuture<String> future = hello.thenCompose(h -> getWorld(h));
//        CompletableFuture<String> future = hello.thenCompose(CompletableFutureApp2::getWorld);
        System.out.println(future.get());

        /*
         * thenCombine : 서로 관련이 없는 비동기 작업을 수행할 때
         */
        CompletableFuture<String> future1 = hello.thenCombine(java, (h, j) -> h + " " + j);
        System.out.println(future1.get());

        /*
         * allOf : 모든 subtask를 한 번에 실행
         * cf) 특정한 결과를 가져올 수 없다.
         * ∵ 각 task의 결과가 동일한 타입이 아닐 수 있음
         * ∵ 모든 task가 오류 없이 실행 완료된다는 보장이 없음
         * ∴ 각 Future의 결과를 담는 task를 만들어 해당 task를 get 하는 방식으로 해결
         */
        CompletableFuture<String>[] futureArray = new CompletableFuture[]{hello, java};
        CompletableFuture<List<String>> futures = CompletableFuture.allOf(futureArray)
                .thenApply(v -> {
                    /*
                     * thenApply가 호출된 시점에서는 futureArray의 모든 future가 작업이 끝난 상태이므로,
                     * get 또는 join을 이용하여 결과를 가져올 수 있다.
                     * cf) get은 checked Exception, join은 unchecked Exception 발생
                     */
                    return Arrays.stream(futureArray)
                            .map(f -> f.join())
//                            .map(CompletableFuture::join)
                            .collect(Collectors.toList());
                });

        futures.get().forEach(result -> {
            System.out.println("result = " + result);
        });

    }

    private static CompletableFuture<String> getWorld(String message) {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println("World " + Thread.currentThread().getName());
            return message +" World";
        });
    }
}
