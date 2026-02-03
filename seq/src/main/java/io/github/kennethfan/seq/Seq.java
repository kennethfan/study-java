package io.github.kennethfan.seq;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Created by kenneth on 2023/5/30.
 */
public interface Seq<T> extends Seq0<Consumer<T>> {

    static <T> Seq<T> unit(T t) {
//        return new Seq<T>() {
//            @Override
//            public void consume(Consumer<T> consumer) {
//                consumer.accept(t);
//            }
//        };

        return (Consumer<T> consumer) -> consumer.accept(t);
    }

    static <T> Seq<T> of(T... elements) {
//        return new Seq<T>() {
//            @Override
//            public void consume(Consumer<T> consumer) {
//                for (T element : elements) {
//                    consumer.accept(element);
//                }
//            }
//        };

        return consumer -> {
            for (T element : elements) {
                consumer.accept(element);
            }
        };
    }

    static <T> T stop() {
        throw StopException.INSTANCE;
    }

    default <E> Seq<E> map(Function<? super T, E> mapper) {
//        return new Seq<E>() {
//            @Override
//            public void consume(Consumer<E> consumer) {
//                Seq.this.consume(new Consumer<T>() {
//                    @Override
//                    public void accept(T t) {
//                        consumer.accept(mapper.apply(t));
//                    }
//                });
//            }
//        };

        return (Consumer<E> consumer) -> Seq.this.consume((T t) -> consumer.accept(mapper.apply(t)));
    }

    default <E> Seq<E> flatMap(Function<? super T, Seq<E>> mapper) {
//        return new Seq<E>() {
//            @Override
//            public void consume(Consumer<E> consumer) {
//                Seq.this.consume(new Consumer<T>() {
//                    @Override
//                    public void accept(T t) {
//                        mapper.apply(t).consume(consumer);
//                    }
//                });
//            }
//        };

        return consumer -> Seq.this.consume(t -> mapper.apply(t).consume(consumer));
    }

    default Seq<T> filter(Predicate<T> predicate) {
//        return new Seq<T>() {
//            @Override
//            public void consume(Consumer<T> consumer) {
//                Seq.this.consume(new Consumer<T>() {
//                    @Override
//                    public void accept(T t) {
//                        if (predicate.test(t)) {
//                            consumer.accept(t);
//                        }
//                    }
//                });
//            }
//        };

        return consumer -> Seq.this.consume(t -> {
            if (predicate.test(t)) {
                consumer.accept(t);
            }
        });
    }

    default Seq<T> take(int n) {
//        return new Seq<T>() {
//            @Override
//            public void consume(Consumer<T> consumer) {
//                final int[] counter = {0};
//                Seq.this.consumeTillStop(new Consumer<T>() {
//                    @Override
//                    public void accept(T t) {
//                        if (counter[0]++ < n) {
//                            consumer.accept(t);
//                        } else {
//                            stop();
//                        }
//                    }
//                });
//            }
//        };

        return consumer -> {
            final int[] counter = {0};
            Seq.this.consumeTillStop(t -> {
                if (counter[0]++ < n) {
                    consumer.accept(t);
                } else {
                    stop();
                }
            });
        };
    }

    default Seq<T> drop(int n) {
//        return new Seq<T>() {
//            @Override
//            public void consume(Consumer<T> consumer) {
//                final int[] counter = {0};
//                Seq.this.consume(new Consumer<T>() {
//                    @Override
//                    public void accept(T t) {
//                        if (counter[0]++ < n) {
//                            return;
//                        }
//                        consumer.accept(t);
//                    }
//                });
//            }
//        };

        return consumer -> {
            final int[] counter = {0};
            Seq.this.consume(t -> {
                if (counter[0]++ < n) {
                    return;
                }
                consumer.accept(t);
            });
        };
    }
}
