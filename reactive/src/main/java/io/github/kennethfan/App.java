package io.github.kennethfan;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/**
 * Hello world!
 *
 */
@Slf4j
public class App 
{
    public static void main( String[] args )
    {
//        System.out.println( "Hello World!" );

//        Mono.just("Hello World!").subscribe(System.out::println);

        Mono.fromCallable(() -> "Hello World!").subscribe(System.out::println);
    }
}
