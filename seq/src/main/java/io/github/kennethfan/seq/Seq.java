package io.github.kennethfan.seq;

import java.util.function.Consumer;

/**
 * Created by kenneth on 2023/5/30.
 */
public interface Seq<T> extends Seq0<Consumer<T>> {

    static <T> Seq<T> empty() {
        return (Seq<T>) Seq.Empty.emptySeq;
    }

    static <T> Consumer<T> nothing() {
        return (Consumer<T>) Empty.nothing;
    }

    class Empty {
        static final Seq<?> emptySeq = consumer -> {
        };

        static final Consumer<?> nothing = t -> {
        };
    }
}
