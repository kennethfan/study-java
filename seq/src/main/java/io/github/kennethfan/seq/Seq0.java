package io.github.kennethfan.seq;

/**
 * Created by kenneth on 2023/5/30.
 */
public interface Seq0<C> {
    /**
     * 消费
     *
     * @param consumer
     */
    void consume(C consumer);

    /**
     * 可打断的消费
     *
     * @param consumer
     */
    default void consumeTillStop(C consumer) {
        try {
            this.consume(consumer);
        } catch (StopException e) {

        }
    }
}
