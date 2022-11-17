package com.example.java8keesun;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamApp {
    public static void main(String[] args) {

        // Stream Api
        List<OnlineClass> springClasses = new ArrayList<>();
        springClasses.add(new OnlineClass(1, "spring boot", true));
        springClasses.add(new OnlineClass(2, "spring security", true));
        springClasses.add(new OnlineClass(3, "spring data JPA", false));
        springClasses.add(new OnlineClass(4, "spring mvc", false));
        springClasses.add(new OnlineClass(5, "rest api development", false));

        System.out.println("spring으로 시작하는 수업");
        springClasses.stream()
                .filter(oc -> oc.getTitle().startsWith("spring"))
                .forEach(oc -> System.out.println(oc.getId()));

        System.out.println("close 되지 않은 수업");
        Predicate<OnlineClass> ocp = OnlineClass::isClosed;
        springClasses.stream()
                .filter((ocp.negate())) // java 11~  : Predicate.not(predicate)
                .forEach(oc -> System.out.println(oc.getId()));

        // 수업 이름만 모아서 스트림 만들기
        springClasses.stream()
                .map(OnlineClass::getTitle)
                .forEach(System.out::println);


        ArrayList<OnlineClass> javaClasses = new ArrayList<>();
        javaClasses.add(new OnlineClass(6, "the Java", true));
        javaClasses.add(new OnlineClass(6, "Clean Code", true));
        javaClasses.add(new OnlineClass(6, "jUnit Test", false));

        List<List<OnlineClass>> listOfList = new ArrayList<>();
        listOfList.add(springClasses);
        listOfList.add(javaClasses);

        System.out.println("두 수업 목록에 들어있는 모든 수업 아이디 출력");
        listOfList.stream()
                .flatMap(Collection::stream)
                .forEach(oc -> System.out.println(oc.getTitle()));

        System.out.println("10부터 1씩 증가하는 무제한 스트림 중에서 앞에 10개 빼고 최대 10개 까지");
        Stream.iterate(10, i -> i+1)
                .skip(10)
                .limit(10)
                .forEach(System.out::println);

        System.out.println("자바 수업 중에 Test가 들어있는 수업이 있는지 확인");
        boolean anyMatch = javaClasses.stream()
                                        .anyMatch(oc -> oc.getTitle().contains(("Test")));
        System.out.println("anyMatch = " + anyMatch);



        System.out.println("스프링 수업 중에 제목에 spring이 들어간 것만 모아서 List로 만들기");
        List<String> spring = springClasses.stream()
                                        .map(OnlineClass::getTitle)
                                        .filter(t -> t.contains("spring"))
                                        .collect(Collectors.toList());
        spring.forEach(System.out::println);



        // Optional
        Optional<OnlineClass> optional = springClasses.stream()
                .filter(oc -> oc.getTitle().startsWith("spring"))
                .findFirst();

//        OnlineClass onlineClass = optional.orElse(createNewClass());  // optional 안에 값이 있어도 무조건 실행된다.

//        OnlineClass onlineClass = optional.orElseGet(Foo::createNewClass);  // 값이 없으면 대안을 실행하고, 있다면 실행하지 않는다. lazy

//        OnlineClass onlineClass = optional.orElseThrow(IllegalArgumentException::new);    // 대안이 없을 경우 에러를 던짐

//        Optional<OnlineClass> onlineClass = optional.filter(OnlineClass::isClosed); // filter의 return type은 Optional

//        Optional<Integer> integer = optional.map(OnlineClass::getId);   // optional 내부값에 접근해 가져온 결과를 감싸는 optional 반환
//        System.out.println(integer.isPresent());

        Optional<Optional<Progress>> progress = optional.map(OnlineClass::getProgress); // optional을 반환하는 메소드를 map 하면 Optional<Optional<T>>
        Optional<Progress> progress1 = progress.orElse(Optional.empty());  // 이런 경우에 flatMap을 사용해 내부의 값을 바로 꺼내올 수 있다.
        Optional<Progress> progress2 = optional.flatMap(OnlineClass::getProgress);


    }

    private static OnlineClass createNewClass() {
        System.out.println("Creating new online class");
        return new OnlineClass(10, "New Class", false);
    }
}
