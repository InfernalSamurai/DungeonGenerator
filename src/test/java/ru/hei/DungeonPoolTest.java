package ru.hei;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static ru.hei.Dungeon.Block;
import static ru.hei.Dungeon.Block.AIR;
import static ru.hei.Dungeon.Block.GROUND;

final class DungeonPoolTest {
    private @NotNull Dungeon dungeon1;
    private @NotNull Dungeon dungeon2;
    private @NotNull Dungeon dungeon3;
    private @NotNull Dungeon dungeon4;
    private @NotNull Dungeon freeDungeon;
    private @NotNull Dungeon closeDungeon;

    @BeforeEach
    public void setUp() {
        final Block[][] area1 = {
                {AIR, AIR, AIR, AIR, AIR, AIR, AIR},
                {GROUND, GROUND, GROUND, GROUND, GROUND, GROUND, AIR},
                {GROUND, GROUND, GROUND, GROUND, GROUND, GROUND, GROUND},
                {GROUND, GROUND, GROUND, GROUND, GROUND, GROUND, GROUND}
        };
        dungeon1 = new Dungeon(area1);
        final Block[][] area2 = {
                {GROUND, GROUND, GROUND, GROUND, GROUND, GROUND, GROUND},
                {AIR, AIR, AIR, AIR, AIR, AIR, AIR},
                {GROUND, GROUND, GROUND, GROUND, GROUND, GROUND, AIR},
                {GROUND, GROUND, GROUND, GROUND, GROUND, GROUND, GROUND}
        };
        dungeon2 = new Dungeon(area2);
        final Block[][] area3 = {
                {GROUND, GROUND, GROUND, GROUND, GROUND, GROUND, GROUND},
                {GROUND, GROUND, GROUND, GROUND, GROUND, GROUND, GROUND},
                {AIR, AIR, AIR, AIR, AIR, AIR, AIR},
                {GROUND, GROUND, GROUND, GROUND, GROUND, GROUND, AIR}
        };
        dungeon3 = new Dungeon(area3);
        final Block[][] area4 = {
                {GROUND, GROUND, GROUND, GROUND, GROUND, GROUND, AIR},
                {GROUND, GROUND, GROUND, GROUND, GROUND, GROUND, GROUND},
                {GROUND, GROUND, GROUND, GROUND, GROUND, GROUND, GROUND},
                {AIR, AIR, AIR, AIR, AIR, AIR, AIR}
        };
        dungeon4 = new Dungeon(area4);
        final Block[][] freeArea = {
                {AIR, AIR, AIR, AIR, AIR, AIR, AIR},
                {AIR, AIR, AIR, AIR, AIR, AIR, AIR},
                {AIR, AIR, AIR, AIR, AIR, AIR, AIR},
                {AIR, AIR, AIR, AIR, AIR, AIR, AIR}
        };
        freeDungeon = new Dungeon(freeArea);
        final Block[][] closeArea = {
                {AIR, AIR, AIR, AIR, AIR, AIR, GROUND},
                {AIR, AIR, AIR, AIR, AIR, AIR, GROUND},
                {AIR, AIR, AIR, AIR, AIR, AIR, GROUND},
                {AIR, AIR, AIR, AIR, AIR, AIR, GROUND}
        };
        closeDungeon = new Dungeon(closeArea);
    }

    @Test
    public void sequenceHasCorrectSize() {
        //given
        int expectedSize = 3;
        final List<Dungeon> dungeons = new ArrayList<>();
        dungeons.add(dungeon1);
        dungeons.add(dungeon2);
        dungeons.add(dungeon3);
        dungeons.add(dungeon4);
        final DungeonPool dungeonPool = new DungeonPool(dungeons);
        //when
        final List<Dungeon> sequence = dungeonPool.createXSequence(expectedSize);
        //then
        assertEquals(expectedSize, sequence.size());
    }

    @Test
    void noSameDungeons() {
        //given
        int expectedSize = 50;
        final List<Dungeon> dungeons = new ArrayList<>();
        dungeons.add(dungeon1);
        dungeons.add(dungeon2);
        dungeons.add(dungeon3);
        dungeons.add(dungeon4);
        dungeons.add(freeDungeon);
        final DungeonPool dungeonPool = new DungeonPool(dungeons);
        //when
        final List<Dungeon> sequence = dungeonPool.createXSequence(expectedSize);
        //then
        assertEquals(expectedSize, sequence.size());
        final Dungeon[] array = sequence.toArray(new Dungeon[0]);
        for (int i = 1; i < sequence.size(); i++) {
            assertNotEquals(array[i - 1], array[i]);
        }
    }

    @Test
    void dungeonsIsComparable() {
        //given
        final List<Dungeon> dungeons = new ArrayList<>();
        dungeons.add(dungeon1);
        dungeons.add(dungeon2);
        dungeons.add(dungeon3);
        dungeons.add(dungeon4);
        final DungeonPool dungeonPool = new DungeonPool(dungeons);
        //when
        final List<Dungeon> sequence = dungeonPool.createXSequence(3);
        final Dungeon[] array = sequence.toArray(new Dungeon[0]);
        //then
        for (int i = 1; i < sequence.size(); i++) {
            assertTrue(array[i - 1].isComparable(array[i]));
        }
    }

    @Test
    public void notEnoughDungeonsInSequence() {
        //given
        final List<Dungeon> dungeons = new ArrayList<>();
        dungeons.add(dungeon1);
        dungeons.add(dungeon2);
        final DungeonPool dungeonPool = new DungeonPool(dungeons);
        //when-then
        assertThrows(IllegalStateException.class, () -> dungeonPool.createXSequence(4));
    }

    @Test
    void notEnoughOpenDungeons() {
        //given
        final List<Dungeon> dungeons = new ArrayList<>();
        dungeons.add(closeDungeon);
        dungeons.add(closeDungeon);
        final DungeonPool dungeonPool = new DungeonPool(dungeons);
        //when-then
        assertThrows(IllegalStateException.class, () -> dungeonPool.createXSequence(1));
    }

    @Test
    void noClosedInSequence() {
        //given
        final List<Dungeon> dungeons = new ArrayList<>();
        dungeons.add(dungeon1);
        dungeons.add(freeDungeon);
        dungeons.add(closeDungeon);
        final DungeonPool dungeonPool = new DungeonPool(dungeons);
        //when
        final List<Dungeon> sequence = dungeonPool.createXSequence(3);
        //then
        assertEquals(3, sequence.size());
        assertFalse(sequence.contains(closeDungeon));
    }
}