package ru.hei;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static ru.hei.Dungeon.Block;
import static ru.hei.Dungeon.Block.AIR;
import static ru.hei.Dungeon.Block.GROUND;

final class DungeonTest {
    @Test
    void invalidAreaByRowsFails() {
        //given
        final Block[][] area = {
                {AIR, AIR, AIR, AIR, AIR, AIR, AIR},
                {GROUND, GROUND, GROUND, GROUND, GROUND, GROUND, GROUND}
        };
        //when
        //then
        assertThrows(IllegalStateException.class, () -> new Dungeon(area));
    }

    @Test
    void invalidAreaByColumnsFails() {
        //given
        final Block[][] area = {
                {AIR, AIR, AIR, AIR, AIR, AIR, AIR},
                {AIR, AIR, AIR, AIR, AIR, AIR, AIR},
                {AIR, AIR, AIR, AIR, AIR, AIR, AIR},
                {GROUND, GROUND, GROUND, GROUND, GROUND, GROUND}
        };
        //when
        //then
        assertThrows(IllegalStateException.class, () -> new Dungeon(area));
    }

    @Test
    public void open() {
        //given
        final Block[][] area = {
                {AIR, AIR, AIR, AIR, AIR, AIR, AIR},
                {GROUND, GROUND, GROUND, GROUND, GROUND, GROUND, GROUND},
                {GROUND, GROUND, GROUND, GROUND, GROUND, GROUND, GROUND},
                {AIR, AIR, AIR, AIR, AIR, AIR, AIR}
        };
        final Dungeon dungeon = new Dungeon(area);
        //when
        //then
        assertTrue(dungeon.isOpen());
    }

    @Test
    public void notOpen() {
        //given
        final Block[][] area = {
                {AIR, AIR, AIR, AIR, AIR, AIR, GROUND},
                {GROUND, GROUND, GROUND, GROUND, GROUND, GROUND, GROUND},
                {GROUND, GROUND, GROUND, GROUND, GROUND, GROUND, GROUND},
                {AIR, AIR, AIR, AIR, AIR, AIR, GROUND}
        };
        final Dungeon dungeon = new Dungeon(area);
        //when
        //then
        assertFalse(dungeon.isOpen());
    }

    @Test
    public void comparable() {
        //given
        final Block[][] area1 = {
                {AIR, AIR, AIR, AIR, AIR, AIR, AIR},
                {GROUND, GROUND, GROUND, GROUND, GROUND, GROUND, GROUND},
                {GROUND, GROUND, GROUND, GROUND, GROUND, GROUND, GROUND},
                {AIR, AIR, AIR, AIR, AIR, AIR, AIR}
        };
        final Dungeon dungeon1 = new Dungeon(area1);
        final Block[][] area2 = {
                {AIR, AIR, AIR, AIR, AIR, AIR, AIR},
                {GROUND, GROUND, GROUND, GROUND, GROUND, GROUND, GROUND},
                {GROUND, GROUND, GROUND, GROUND, GROUND, GROUND, GROUND},
                {GROUND, GROUND, GROUND, GROUND, GROUND, GROUND, GROUND}
        };
        final Dungeon dungeon2 = new Dungeon(area2);
        //when
        //then
        assertTrue(dungeon1.isComparable(dungeon2));
    }

    @Test
    public void notComparable() {
        //given
        final Block[][] area1 = {
                {AIR, AIR, AIR, AIR, AIR, AIR, AIR},
                {GROUND, GROUND, GROUND, GROUND, GROUND, GROUND, GROUND},
                {GROUND, GROUND, GROUND, GROUND, GROUND, GROUND, GROUND},
                {AIR, AIR, AIR, AIR, AIR, AIR, AIR}
        };
        final Dungeon dungeon1 = new Dungeon(area1);
        final Block[][] area2 = {
                {GROUND, GROUND, GROUND, GROUND, GROUND, GROUND, GROUND},
                {AIR, AIR, AIR, AIR, AIR, AIR, AIR},
                {GROUND, GROUND, GROUND, GROUND, GROUND, GROUND, GROUND},
                {GROUND, AIR, AIR, AIR, AIR, AIR, AIR}
        };
        final Dungeon dungeon2 = new Dungeon(area2);
        //when
        //then
        assertFalse(dungeon1.isComparable(dungeon2));
    }
}