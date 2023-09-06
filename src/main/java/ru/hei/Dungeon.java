package ru.hei;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static ru.hei.Dungeon.Block.AIR;
import static ru.hei.Dungeon.Block.GROUND;

public final class Dungeon {
    public static final @NotNull Dungeon DUMMY = new Dungeon(new Block[][]{
            {GROUND, GROUND, GROUND, GROUND, GROUND, GROUND, GROUND},
            {GROUND, GROUND, GROUND, GROUND, GROUND, GROUND, GROUND},
            {GROUND, GROUND, GROUND, GROUND, GROUND, GROUND, GROUND},
            {GROUND, GROUND, GROUND, GROUND, GROUND, GROUND, GROUND}
    });
    private static final int ROW_SIZE = 4;
    private static final int COL_SIZE = 7;
    private final @NotNull Block[][] area;
    private final @NotNull List<Integer> inputs;
    private final @NotNull List<Integer> outputs;

    public Dungeon(final @NotNull Block[][] area) {
        if (!validArea(area)) {
            throw new IllegalStateException("Not valid area");
        }
        this.area = area;
        this.inputs = new ArrayList<>();
        this.outputs = new ArrayList<>();
        IntStream.range(0, area.length)
                .forEach(row -> {
                    if (area[row][0] == AIR) {
                        inputs.add(row);
                    }
                    if (area[row][COL_SIZE - 1] == AIR) {
                        outputs.add(row);
                    }
                });
    }

    private boolean validArea(final @NotNull Block[][] area) {
        return area.length == ROW_SIZE
                && Arrays.stream(area).noneMatch(col -> col.length != COL_SIZE);
    }

    public boolean isOpen() {
        return !inputs.isEmpty() && !outputs.isEmpty();
    }

    public boolean isComparable(final @NotNull Dungeon other) {
        return outputs.stream().anyMatch(other.inputs::contains);
    }

    @Override
    public boolean equals(final @NotNull Object obj) {
        if (this == obj) return true;
        if (getClass() != obj.getClass()) return false;
        Dungeon dungeon = (Dungeon) obj;
        return Arrays.deepEquals(area, dungeon.area);
    }

    public enum Block {
        GROUND,
        AIR
    }
}

