package ru.hei;

import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.IntStream;

import static java.lang.Math.max;
import static java.lang.Math.min;
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

    public final boolean isOpen;

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
        isOpen = !inputs.isEmpty() && isOpen(inputs.get(0), area);
    }

    private boolean validArea(final @NotNull Block[][] area) {
        return area.length == ROW_SIZE
                && Arrays.stream(area).noneMatch(col -> col.length != COL_SIZE);
    }

    private boolean isOpen(final int inputRow, final @NotNull Dungeon.Block[][] area) {
        final Deque<Step> steps = new ArrayDeque<>();
        final Step startStep = new Step(inputRow, 0);
        steps.offer(startStep);

        final boolean[][] visited = new boolean[area.length][area[0].length];
        visited[startStep.row][startStep.col] = true;

        while (!steps.isEmpty()) {
            final Step current = steps.pollLast();

            if (isExit(current)) {
                return true;
            }

            for (int xDirection = -1; xDirection <= 1; xDirection++) {
                for (int yDirection = -1; yDirection <= 1; yDirection++) {
                    if (xDirection == 0 && yDirection == 0) {
                        continue;
                    }

                    final int x = min(max(0, current.row + xDirection), ROW_SIZE - 1);
                    final int y = min(max(0, current.col + yDirection), COL_SIZE - 1);

                    if (visited[x][y] || area[x][y] != AIR) {
                        continue;
                    }

                    visited[x][y] = true;
                    steps.offer(new Step(x, y));
                }
            }
        }
        return false;
    }

    private boolean isExit(final @NotNull Step step) {
        return step.col == COL_SIZE - 1 && previousStepIsFree(step);
    }

    private boolean previousStepIsFree(final @NotNull Step step) {
        return area[step.row][step.col - 1] == AIR;
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

    private static final class Step {
        final int row;
        final int col;

        public Step(final int row, final int col) {
            this.row = row;
            this.col = col;
        }
    }
}

