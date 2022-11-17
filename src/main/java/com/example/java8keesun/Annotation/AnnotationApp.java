package com.example.java8keesun.Annotation;

import java.util.Arrays;

@Chicken("후라이드")
@Chicken("간장")
public class AnnotationApp {
    public static void main(String[] args) throws RuntimeException {

        //getAnnotationByType : 원하는 타입의 annotation을 가지고 올 때
        Chicken[] chickens = AnnotationApp.class.getAnnotationsByType(Chicken.class);
        Arrays.stream(chickens)
                .map(Chicken::value)
                .forEach(System.out::println);

        //getAnnotation : Container의 타입으로 가지고 올 때
        ChickenContainer chickenContainer = AnnotationApp.class.getAnnotation(ChickenContainer.class);
        Arrays.stream(chickenContainer.value())
                .map(Chicken::value)
                .forEach(System.out::println);


    }

    /**
     * Annotation의 Target에 따른 annotation 위치
     */
    //  static class ChickenDinner<@Chicken T> {
    //     public static <@Chicken C> void print(@Chicken C c) {
    //         List<@Chicken String> names = Arrays.asList("Kyochon");
    //     }
    // }

}
