package io.github.kennethfan.seq;

import java.util.List;

public class SeqTest {

    public static void main(String[] args) {

        System.out.println("test unit");
        Seq.unit("hello seq").consume(System.out::println);

        List<Integer> list = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
//        Seq<Integer> seq = new Seq<Integer>() {
//            @Override
//            public void consume(Consumer<Integer> consumer) {
//                list.forEach(consumer);
//            }
//        };
        Seq<Integer> seq = list::forEach;

        System.out.println("test simple");
        seq.consume(System.out::println);

        System.out.println("test filter");
        seq.filter(i -> i % 2 == 0).consume(System.out::println);

        System.out.println("test map");
        seq.map(i -> i * 2).consume(System.out::println);

        System.out.println("test flatMap");
        seq.flatMap(i -> Seq.of(i, (int) Math.pow(i, 2))).consume(System.out::println);

        System.out.println("test tak");
        seq.take(2).consume(System.out::println);

        System.out.println("test drop");
        seq.drop(3).consume(System.out::println);
    }
}
