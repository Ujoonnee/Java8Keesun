package com.example.java8keesun;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;

public class CallableApp {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        Callable<String> hello = () -> {
            Thread.sleep(2000L);
            System.out.println("hello end");
            return "Hello";
        };
        Callable<String> java = () -> {
            Thread.sleep(3000L);
            System.out.println("java end");
            return "Java";
        };
        Callable<String> spring = () -> {
            Thread.sleep(1000L);
            System.out.println("spring end");
            return "Spring";
        };

        Future<String> helloFuture = executorService.submit(hello);
        System.out.println("helloFuture.isDone = " + helloFuture.isDone());
        System.out.println("started");

        /**
         * cancel(boolean) : true는 즉시, false는 대기 후 종료
         * param과 관계없이 return 값을 받을 수 없다. -> get() 시도 시 CancellationException
         */
        helloFuture.cancel(false);
        //blocking call : thread가 실행되고 callable의 결과를 return 할 때까지 대기
//        helloFuture.get();

        System.out.println("helloFuture.isDone = " + helloFuture.isDone());
        System.out.println("end");

        //invokeAll : 모든 Callable이 종료될 때까지 대기
        List<Future<String>> futures = executorService.invokeAll(Arrays.asList(hello, java, spring));
        for (Future<String> f: futures) {
            System.out.println(f.get());
        }

        /**
         * invokeAny : blockinig call, Future가 아니라 결과를 바로 return
         * executor에서 가장 먼저 종료되는 Callable의 결과를 return
         * thread의 갯수에 따라 결과가 달라질 수 있음
         */
        String s = executorService.invokeAny(Arrays.asList(hello, java, spring));
        System.out.println(s);

        executorService.shutdown();
    }
}
