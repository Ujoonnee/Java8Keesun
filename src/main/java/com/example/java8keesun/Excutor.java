package com.example.java8keesun;

import java.time.temporal.ChronoUnit;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Excutor {
    public static void main(String[] args) {
        /**
         * ExecutorService의 thread pool에 설정한 개수의 thread가 존재
         * thread 개수 이상의 작업을 ES가 접수할 경우, Blocking Queue에 작업을 쌓아두고 대기시킴
         * 이후 작업이 종료된 thread가 Queue의 작업을 수행
         * thread 생성 비용을 줄이는 효과가 있음
         */
//        ExecutorService executorService = Executors.newSingleThreadExecutor();
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.execute(getRunnable("Hello "));
        executorService.execute(getRunnable("World "));
        executorService.execute(getRunnable("Java8 "));

        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.schedule(getRunnable("Hello scheduled "), 3, TimeUnit.SECONDS);
        //주기를 두고 반복해서 실행, 단 shutdown이 interrupt 하면 종료됨
        scheduledExecutorService.scheduleAtFixedRate(getRunnable("Hello scheduled "), 1, 2, TimeUnit.SECONDS);

        /**
         * 주의
         * ExecutorService는 작업 실행 후 다음 작업이 들어올 때까지 대기 -> 프로세스가 종료되지 않음
         * 직접 종료시켜야 함
         */
        // graceful shutdown : 현재 진행중인 작업을 끝내고 종료
        executorService.shutdown();
        scheduledExecutorService.shutdown();

        // 모르겠고 걍 종료
//        executorService.shutdownNow();
    }

    private static Runnable getRunnable(String message) {
        return () -> System.out.println(message + Thread.currentThread().getName());
    }


}
