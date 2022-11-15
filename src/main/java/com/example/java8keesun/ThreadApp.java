package com.example.java8keesun;

public class ThreadApp {
    /**
     * thread는 순서를 보장하지 않는다.
     * 방법1: Thread를 extend 하고 run()을 override
     * 방법2: 생성자에 new Runnable, 람다로 교체 가능
     *
     * sleep : 재움. 다른 thread에 자원이 우선적으로 할당되며, 다른 thread에 의해 interrupted 될 수 있음
     * interrupt : sleep 상태의 thread를 깨움. 이 때 해당 thread에 interrupted 시 실행할 코드를 직접 작성해야함
     * join : 해당 thread가 종료될 때까지 대기. sleep과 마찬가지로 다른 thread에 의해 interrupted 될 수 있음
     *
     * 현실적으로 프로그래머가 모든 thread를 관리하기는 불가능
     * -> executor(Future)를 사용
     */
    public static void main(String[] args) {
        // 방법 1
        MyThread myThread = new MyThread();
        myThread.start();

        // 방법 2
        Thread thread = new Thread(() -> {
            while(true) {
                System.out.println("Runnable: " + Thread.currentThread().getName());
                try {
                    // 다른 thread에 resource 우선 할당
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    // 자는 중에 깨우면 실행
                    System.out.println("exit");
                    return;
                }
            }
        });
        thread.start();

        System.out.println("Hello: "+ Thread.currentThread().getName());
        try {
            Thread.sleep(3000L);
            thread.interrupt();
            thread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(thread +" is finished");
    }

    static class MyThread extends Thread {
        @Override
        public void run() {
            System.out.println("Thread : "+ this.getName());
        }
    }

}
