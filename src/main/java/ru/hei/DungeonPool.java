package ru.hei;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public final class DungeonPool {
    private final @NotNull List<Dungeon> dungeons;
    private final @NotNull Random random;

    public DungeonPool(final @NotNull List<Dungeon> dungeons) {
        this.dungeons = Collections.unmodifiableList(dungeons);
        this.random = new Random();
    }

    public @NotNull List<Dungeon> createXSequence(int length) {
        final List<Dungeon> openDungeons = dungeons.stream()
                .filter(Dungeon::isOpen)
                .collect(Collectors.toList());
        if (openDungeons.isEmpty()) {
            throw new IllegalStateException("No open dungeons available");
        }

        Dungeon currentDungeon = getRandomDungeon(openDungeons);
        final List<Dungeon> sequence = new ArrayList<>();
        while (currentDungeon != Dungeon.DUMMY && sequence.size() < length) {
            sequence.add(currentDungeon);
            currentDungeon = getNextDungeon(openDungeons, currentDungeon);
        }
        if (sequence.size() < length) {
            throw new IllegalStateException("Not enough dungeons available");
        }
        return sequence;
    }

    private @NotNull Dungeon getRandomDungeon(final @NotNull List<Dungeon> openDungeons) {
        return openDungeons.get(random.nextInt(openDungeons.size()));
    }

    private @NotNull Dungeon getNextDungeon(final @NotNull List<Dungeon> openDungeons, final @NotNull Dungeon currentDungeon) {
        final List<Dungeon> comparableDungeons = openDungeons.stream()
                .filter(dungeon -> !dungeon.equals(currentDungeon))
                .filter(currentDungeon::isComparable)
                .collect(Collectors.toList());
        return comparableDungeons.isEmpty()
                ? Dungeon.DUMMY
                : getRandomDungeon(comparableDungeons);
    }
}
