package ru.hei;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static ru.hei.Dungeon.Block;
import static ru.hei.Dungeon.Block.AIR;
import static ru.hei.Dungeon.Block.GROUND;

final class DungeonTest {
    private static Stream<TestCase> testCases() {
        return Stream.of(
                new TestCase(
                        new Block[][]{
                                {AIR, AIR, AIR, AIR, AIR, AIR, AIR},
                                {AIR, AIR, AIR, AIR, AIR, AIR, AIR},
                                {AIR, AIR, AIR, AIR, AIR, AIR, AIR},
                                {AIR, AIR, AIR, AIR, AIR, AIR, AIR}
                        },
                        true
                ),
                new TestCase(
                        new Block[][]{
                                {GROUND, GROUND, GROUND, GROUND, GROUND, GROUND, GROUND},
                                {GROUND, GROUND, GROUND, GROUND, GROUND, GROUND, GROUND},
                                {GROUND, GROUND, GROUND, GROUND, GROUND, GROUND, GROUND},
                                {GROUND, GROUND, GROUND, GROUND, GROUND, GROUND, GROUND}
                        },
                        false
                ),
                new TestCase(
                        new Block[][]{
                                {AIR, AIR, AIR, AIR, AIR, AIR, AIR},
                                {GROUND, GROUND, GROUND, GROUND, GROUND, GROUND, GROUND},
                                {GROUND, GROUND, GROUND, GROUND, GROUND, GROUND, GROUND},
                                {AIR, AIR, AIR, AIR, AIR, AIR, AIR}
                        },
                        true
                ),
                new TestCase(
                        new Block[][]{
                                {AIR, AIR, AIR, AIR, AIR, AIR, GROUND},
                                {GROUND, GROUND, GROUND, GROUND, GROUND, GROUND, GROUND},
                                {GROUND, GROUND, GROUND, GROUND, GROUND, GROUND, GROUND},
                                {AIR, AIR, AIR, AIR, AIR, AIR, GROUND}
                        },
                        false
                ),
                new TestCase(
                        new Block[][]{
                                {AIR, GROUND, GROUND, GROUND, GROUND, GROUND, AIR},
                                {AIR, AIR, GROUND, GROUND, GROUND, AIR, AIR},
                                {GROUND, AIR, AIR, AIR, AIR, AIR, GROUND},
                                {GROUND, GROUND, GROUND, GROUND, GROUND, GROUND, GROUND}
                        },
                        true
                ),
                new TestCase(
                        new Block[][]{
                                {AIR, GROUND, GROUND, GROUND, GROUND, GROUND, AIR},
                                {AIR, AIR, GROUND, GROUND, GROUND, AIR, GROUND},
                                {GROUND, AIR, AIR, AIR, AIR, AIR, GROUND},
                                {GROUND, GROUND, GROUND, GROUND, GROUND, GROUND, GROUND}
                        },
                        false
                ),
                new TestCase(
                        new Block[][]{
                                {AIR, AIR, GROUND, GROUND, GROUND, AIR, AIR},
                                {AIR, AIR, GROUND, GROUND, GROUND, AIR, AIR},
                                {AIR, AIR, GROUND, GROUND, GROUND, AIR, AIR},
                                {AIR, AIR, GROUND, GROUND, GROUND, AIR, AIR}
                        },
                        false
                ),
                new TestCase(
                        new Block[][]{
                                {AIR, AIR, GROUND, AIR, GROUND, AIR, AIR},
                                {AIR, AIR, GROUND, AIR, GROUND, AIR, AIR},
                                {AIR, AIR, GROUND, AIR, GROUND, AIR, AIR},
                                {AIR, AIR, AIR, AIR, GROUND, AIR, AIR}
                        },
                        false
                )
        );
    }

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

    @ParameterizedTest
    @MethodSource("testCases")
    void open(final @NotNull TestCase testCase) {
        //given
        final Dungeon dungeon = new Dungeon(testCase.area);
        //when
        //then
        assertEquals(dungeon.isOpen, testCase.isOpen);
    }

    @Test
    void comparable() {
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
    void notComparable() {
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

    private static final class TestCase {
        private final @NotNull Block[][] area;
        private final boolean isOpen;

        public TestCase(final @NotNull Block[][] area, final boolean isOpen) {
            this.area = area;
            this.isOpen = isOpen;
        }

        @Override
        public String toString() {
            return "TestCase: isOpen=" + isOpen;
        }
    }
}