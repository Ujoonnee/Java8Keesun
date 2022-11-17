package com.example.java8keesun;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

/**
 * <p>Arrays.sort는 기본적으로 single thread를 이용한 Dual-Pivot Quicksort( O(n log(n)) ) 알고리즘을 이용한다.
 * <p>parallelSort는 적당히 크기로 계속 쪼갠 뒤 합치면서 정렬하는 방식으로,
 * <p>그 과정에서 Fork/Join framework을 이용한 여러 thread로 분산시켜 처리해 더 빠르게 처리한다.
 * <p>다만 resource(threads)와 정렬하는 배열의 크기에 따라서 처리 속도에 차이가 생길 수 있다.
 */
public class SortApp {
    public static void main(String[] args) {
        int size = 8193;
        int[] numbers = new int[size];
        Random random = new Random();

        IntStream.range(0, size).forEach(i -> numbers[i] = random.nextInt());
        long start = System.nanoTime();
        Arrays.sort(numbers);
        System.out.println("serial sorting took "+ (System.nanoTime() - start)/100);

        IntStream.range(0, size).forEach(i -> numbers[i] = random.nextInt());
        start = System.nanoTime();
        Arrays.parallelSort(numbers);
        System.out.println("parallel sorting took "+ (System.nanoTime() - start)/100);
    }
}
