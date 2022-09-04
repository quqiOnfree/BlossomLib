package dev.codedsakura.blossom.lib.text;

import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

import java.util.stream.Collector;

public class TextSuperJoiner {
    private final MutableText prepend;
    private final MutableText append;
    private final MutableText join;

    private MutableText result;

    private TextSuperJoiner(Text prepend, Text append, Text join) {
        this.prepend = prepend.copy();
        this.append = append.copy();
        this.join = join.copy();
    }

    private TextSuperJoiner(Text prepend, Text append, Text join, MutableText result) {
        this.prepend = prepend.copy();
        this.append = append.copy();
        this.join = join.copy();
        this.result = result;
    }

    private void accumulator(Text text) {
        if (result == null) {
            result = prepend.copy()
                    .append(text)
                    .append(append);
        } else {
            result = result
                    .append(join)
                    .append(prepend)
                    .append(text)
                    .append(append);
        }
    }

    private TextSuperJoiner combiner(TextSuperJoiner b) {
        return new TextSuperJoiner(
                prepend,
                append,
                join,
                result.append(join).append(b.result)
        );
    }

    private MutableText finisher() {
        return result;
    }

    public static Collector<Text, ?, MutableText> collector(Text prepend, Text append, Text join) {
        return Collector.of(
                () -> new TextSuperJoiner(prepend, append, join),
                TextSuperJoiner::accumulator,
                TextSuperJoiner::combiner,
                TextSuperJoiner::finisher
        );
    }

    public static Collector<Text, ?, MutableText> joiner(Text join) {
        return Collector.of(
                () -> new TextSuperJoiner(Text.empty(), Text.empty(), join),
                TextSuperJoiner::accumulator,
                TextSuperJoiner::combiner,
                TextSuperJoiner::finisher
        );
    }
}
