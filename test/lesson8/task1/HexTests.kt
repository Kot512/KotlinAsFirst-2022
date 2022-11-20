package lesson8.task1

import lesson8.task1.Direction.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import java.lang.IllegalArgumentException

class HexTests {

    @Test
    @Tag("3")
    fun hexPointDistance() {
        assertEquals(5, HexPoint(6, 1).distance(HexPoint(1, 4)))
        assertEquals(1, HexPoint(3, 3).distance(HexPoint(2, 3)))
        assertEquals(2, HexPoint(3, 3).distance(HexPoint(4, 4)))
    }

    @Test
    @Tag("3")
    fun hexagonDistance() {
        assertEquals(2, Hexagon(HexPoint(1, 3), 1).distance(Hexagon(HexPoint(6, 2), 2)))
    }

    @Test
    @Tag("1")
    fun hexagonContains() {
        assertTrue(Hexagon(HexPoint(3, 3), 1).contains(HexPoint(2, 3)))
        assertFalse(Hexagon(HexPoint(3, 3), 1).contains(HexPoint(4, 4)))
        assertFalse(Hexagon(HexPoint(4, 3), 2).contains(HexPoint(7, 3)))
    }

    @Test
    @Tag("2")
    fun hexSegmentValid() {
        assertTrue(HexSegment(HexPoint(1, 3), HexPoint(5, 3)).isValid())
        assertTrue(HexSegment(HexPoint(3, 1), HexPoint(3, 6)).isValid())
        assertTrue(HexSegment(HexPoint(1, 5), HexPoint(4, 2)).isValid())
        assertFalse(HexSegment(HexPoint(3, 1), HexPoint(6, 2)).isValid())
    }

    @Test
    @Tag("3")
    fun hexSegmentDirection() {
        assertEquals(RIGHT, HexSegment(HexPoint(1, 3), HexPoint(5, 3)).direction())
        assertEquals(UP_RIGHT, HexSegment(HexPoint(3, 1), HexPoint(3, 6)).direction())
        assertEquals(DOWN_RIGHT, HexSegment(HexPoint(1, 5), HexPoint(4, 2)).direction())
        assertEquals(LEFT, HexSegment(HexPoint(5, 3), HexPoint(1, 3)).direction())
        assertEquals(DOWN_LEFT, HexSegment(HexPoint(3, 6), HexPoint(3, 1)).direction())
        assertEquals(UP_LEFT, HexSegment(HexPoint(4, 2), HexPoint(1, 5)).direction())
        assertEquals(INCORRECT, HexSegment(HexPoint(3, 1), HexPoint(6, 2)).direction())
    }

    @Test
    @Tag("2")
    fun oppositeDirection() {
        assertEquals(LEFT, RIGHT.opposite())
        assertEquals(DOWN_LEFT, UP_RIGHT.opposite())
        assertEquals(UP_LEFT, DOWN_RIGHT.opposite())
        assertEquals(RIGHT, LEFT.opposite())
        assertEquals(DOWN_RIGHT, UP_LEFT.opposite())
        assertEquals(UP_RIGHT, DOWN_LEFT.opposite())
        assertEquals(INCORRECT, INCORRECT.opposite())
    }

    @Test
    @Tag("3")
    fun nextDirection() {
        assertEquals(UP_RIGHT, RIGHT.next())
        assertEquals(UP_LEFT, UP_RIGHT.next())
        assertEquals(RIGHT, DOWN_RIGHT.next())
        assertEquals(DOWN_LEFT, LEFT.next())
        assertEquals(LEFT, UP_LEFT.next())
        assertEquals(DOWN_RIGHT, DOWN_LEFT.next())
        assertThrows(IllegalArgumentException::class.java) {
            INCORRECT.next()
        }
    }

    @Test
    @Tag("2")
    fun isParallelDirection() {
        assertTrue(RIGHT.isParallel(RIGHT))
        assertTrue(RIGHT.isParallel(LEFT))
        assertFalse(RIGHT.isParallel(UP_LEFT))
        assertFalse(RIGHT.isParallel(INCORRECT))
        assertTrue(UP_RIGHT.isParallel(UP_RIGHT))
        assertTrue(UP_RIGHT.isParallel(DOWN_LEFT))
        assertFalse(UP_RIGHT.isParallel(UP_LEFT))
        assertFalse(INCORRECT.isParallel(INCORRECT))
        assertFalse(INCORRECT.isParallel(UP_LEFT))
    }

    @Test
    @Tag("3")
    fun hexPointMove() {
        assertEquals(HexPoint(3, 3), HexPoint(0, 3).move(RIGHT, 3))
        assertEquals(HexPoint(3, 5), HexPoint(5, 3).move(UP_LEFT, 2))
        assertEquals(HexPoint(5, 0), HexPoint(5, 4).move(DOWN_LEFT, 4))
        assertEquals(HexPoint(1, 1), HexPoint(1, 1).move(DOWN_RIGHT, 0))
        assertEquals(HexPoint(4, 2), HexPoint(2, 2).move(LEFT, -2))
        assertEquals(HexPoint(5, 6), HexPoint(5, 3).move(UP_RIGHT, 3))
        assertThrows(IllegalArgumentException::class.java) {
            HexPoint(0, 0).move(INCORRECT, 0)
        }
    }

    @Test
    @Tag("5")
    fun pathBetweenHexes() {
        assertEquals(
            5, pathBetweenHexes(HexPoint(y = 2, x = 2), HexPoint(y = 5, x = 3)).size
        )
    }

    @Test
    @Tag("20")
    fun hexagonByThreePoints() {
        assertEquals(
            Hexagon(HexPoint(4, 2), 2),
            hexagonByThreePoints(HexPoint(3, 1), HexPoint(2, 3), HexPoint(4, 4))
        )
        assertNull(
            hexagonByThreePoints(HexPoint(3, 1), HexPoint(2, 3), HexPoint(5, 4))
        )
        assertEquals(
            3,
            hexagonByThreePoints(HexPoint(2, 3), HexPoint(3, 3), HexPoint(5, 3))?.radius
        )
    }

    @Test
    @Tag("20")
    fun minContainingHexagon() {
        var points = arrayOf(
            HexPoint(3, 1),
            HexPoint(3, 2),
            HexPoint(5, 4),
            HexPoint(8, 1)
        )
        var result = minContainingHexagon(*points)
        assertEquals(3, result.radius)
        assertTrue(points.all { result.contains(it) })

        points = arrayOf(
            HexPoint(2, 5),
            HexPoint(5, 6),
            HexPoint(5, 1),
            HexPoint(3, 0)
        )
        result = minContainingHexagon(*points)
        assertEquals(4, result.radius)
        assertTrue(points.all { result.contains(it) })

        points = arrayOf(
            HexPoint(1, 4),
            HexPoint(1, 3),
            HexPoint(2, 3),
            HexPoint(2, 2),
            HexPoint(6, 4)
        )
        result = minContainingHexagon(*points)
        assertEquals(3, result.radius)
        assertTrue(points.all { result.contains(it) })

        points = arrayOf(
            HexPoint(-558, -1000),
            HexPoint(-558, 329),
            HexPoint(-999, -557)
        )
        result = minContainingHexagon(*points)
        assertEquals(665, result.radius)
        assertTrue(points.all { result.contains(it) })

        points = arrayOf(
            HexPoint(-999, -999),
            HexPoint(644, -1000),
            HexPoint(-557, -39),
            HexPoint(-999, 832),
            HexPoint(-650, 463),
            HexPoint(-723, -1000),
            HexPoint(615, -500),
            HexPoint(-570, -999)
        )
        result = minContainingHexagon(*points)
        assertEquals(1158, result.radius)
        assertTrue(points.all { result.contains(it) })

        points = arrayOf(
            HexPoint(-1000, 318),
            HexPoint(471, -558),
            HexPoint(672, -558),
            HexPoint(-883, 189),
            HexPoint(-557, 458),
            HexPoint(-1000, -1000),
            HexPoint(-557, -976),
        )
        result = minContainingHexagon(*points)
        assertEquals(1057, result.radius)
        assertTrue(points.all { result.contains(it) })

        points = arrayOf(
            HexPoint(1, 4),
            HexPoint(999999, 999999)
        )
        result = minContainingHexagon(*points)
        assertEquals(999997, result.radius)
        assertTrue(points.all { result.contains(it) })
    }

}