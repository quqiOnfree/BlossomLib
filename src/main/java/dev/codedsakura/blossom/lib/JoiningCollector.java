package dev.codedsakura.blossom.lib;

import java.util.function.BinaryOperator;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class JoiningCollector<T> {
    private final BinaryOperator<T> add;
    private final Supplier<T> joiner;

    private JoiningCollector(BinaryOperator<T> add, Supplier<T> joiner) {
        this.add = add;
        this.joiner = joiner;
    }

    private T previous;
    private T result;

    public void accumulator(T t) {
        if (previous != null) {
            result = add.apply(result, joiner.get());
            result = add.apply(result, t);
        } else {
            result = t;
        }
        previous = t;
    }

    public JoiningCollector<T> combiner(JoiningCollector<T> _b) {
        throw new UnsupportedOperationException("Parallel Stream not supported");
    }

    public T finisher() {
        return result;
    }

    public static <T> Collector<T, ?, T> collector(BinaryOperator<T> add, T joiner) {
        return Collector.of(
                () -> new JoiningCollector<>(add, () -> joiner),
                JoiningCollector::accumulator,
                JoiningCollector::combiner,
                JoiningCollector::finisher
        );
    }

    public static <T> Collector<T, ?, T> collector(BinaryOperator<T> add, Supplier<T> joiner) {
        return Collector.of(
                () -> new JoiningCollector<>(add, joiner),
                JoiningCollector::accumulator,
                JoiningCollector::combiner,
                JoiningCollector::finisher
        );
    }
}
