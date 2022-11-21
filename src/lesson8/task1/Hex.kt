@file:Suppress("UNUSED_PARAMETER")

package lesson8.task1

import java.lang.IllegalArgumentException
import kotlin.Int.Companion.MAX_VALUE
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min
import kotlin.math.pow


/**
 * Точка (гекс) на шестиугольной сетке.
 * Координаты заданы как в примере (первая цифра - y, вторая цифра - x)
 *         70  71  72  73  74  75  76  77  78  79  (7 10)
 *       60  61  62  63  64  65  66  67  68  69  (6 10)
 *     50  51  52  53  54  55  56  57  58  59  (5 10)
 *   40  41  42  43  44  45  46  47  48  49  (4 10)
 * 30  31  32  33  34  35  36  37  38  39  (3 10)
 *   21  22  23  24  25  26  27  28  29  (2 10)
 *     12  13  14  15  16  17  18  19  (1 10)
 *       03  04  05  06  07  08  09  (0 10)
 *
 * В примерах к задачам используются те же обозначения точек,
 * к примеру, 16 соответствует HexPoint(x = 6, y = 1), а 41 -- HexPoint(x = 1, y = 4).
 *
 * В задачах, работающих с шестиугольниками на сетке, считать, что они имеют
 * _плоскую_ ориентацию:
 *  __
 * /  \
 * \__/
 *
 * со сторонами, параллельными координатным осям сетки.
 *
 * Более подробно про шестиугольные системы координат можно почитать по следующей ссылке:
 *   https://www.redblobgames.com/grids/hexagons/
 */
data class HexPoint(val x: Int, val y: Int) {
    /**
     * Средняя (3 балла)
     *
     * Найти целочисленное расстояние между двумя гексами сетки.
     * Расстояние вычисляется как число единичных отрезков в пути между двумя гексами.
     * Например, путь межу гексами 16 и 41 (см. выше) может проходить через 25, 34, 43 и 42 и имеет длину 5.
     */
    fun distance(other: HexPoint): Int {
        var pathLength = 0
        var difference = Pair(x - other.x, y - other.y)

        while (difference != Pair(0, 0)) {
            when {
                difference.first > 0 -> {
                    if (difference.second < 0) {
                        pathLength += min(abs(difference.first), abs(difference.second))
                        difference =
                            Pair(
                                difference.first - min(abs(difference.first), abs(difference.second)),
                                difference.second + min(abs(difference.first), abs(difference.second))
                            )
                    } else {
                        pathLength += difference.first
                        difference = difference.copy(first = 0)
                    }
                }

                difference.first < 0 -> {
                    if (difference.second > 0) {
                        pathLength += min(abs(difference.first), abs(difference.second))
                        difference =
                            Pair(
                                difference.first + min(abs(difference.first), abs(difference.second)),
                                difference.second - min(abs(difference.first), abs(difference.second))
                            )
                    } else {
                        pathLength += abs(difference.first)
                        difference = difference.copy(first = 0)
                    }
                }

                else -> {
                    pathLength += abs(difference.second)
                    difference = difference.copy(second = 0)
                }
            }
        }
        return pathLength
    }


    override fun toString(): String = "$y.$x"
}

/**
 * Правильный шестиугольник на гексагональной сетке.
 * Как окружность на плоскости, задаётся центральным гексом и радиусом.
 * Например, шестиугольник с центром в 33 и радиусом 1 состоит из гексов 42, 43, 34, 24, 23, 32.
 */
data class Hexagon(val center: HexPoint, val radius: Int) {

    /**
     * Средняя (3 балла)
     *
     * Рассчитать расстояние между двумя шестиугольниками.
     * Оно равно расстоянию между ближайшими точками этих шестиугольников,
     * или 0, если шестиугольники имеют общую точку.
     *
     * Например, расстояние между шестиугольником A с центром в 31 и радиусом 1
     * и другим шестиугольником B с центром в 26 и радиуоом 2 равно 2
     * (расстояние между точками 32 и 24)
     */
    fun distance(other: Hexagon): Int = TODO()

    /**
     * Тривиальная (1 балл)
     *
     * Вернуть true, если заданная точка находится внутри или на границе шестиугольника
     */
    fun contains(point: HexPoint): Boolean =
        point.distance(center) <= radius
}

/**
 * Прямолинейный отрезок между двумя гексами
 */
class HexSegment(val begin: HexPoint, val end: HexPoint) {
    /**
     * Простая (2 балла)
     *
     * Определить "правильность" отрезка.
     * "Правильным" считается только отрезок, проходящий параллельно одной из трёх осей шестиугольника.
     * Такими являются, например, отрезок 30-34 (горизонталь), 13-63 (прямая диагональ) или 51-24 (косая диагональ).
     * А, например, 13-26 не является "правильным" отрезком.
     */
    fun isValid(): Boolean = TODO()

    /**
     * Средняя (3 балла)
     *
     * Вернуть направление отрезка (см. описание класса Direction ниже).
     * Для "правильного" отрезка выбирается одно из первых шести направлений,
     * для "неправильного" -- INCORRECT.
     */
    fun direction(): Direction = TODO()

    override fun equals(other: Any?) =
        other is HexSegment && (begin == other.begin && end == other.end || end == other.begin && begin == other.end)

    override fun hashCode() =
        begin.hashCode() + end.hashCode()
}

/**
 * Направление отрезка на гексагональной сетке.
 * Если отрезок "правильный", то он проходит вдоль одной из трёх осей шестугольника.
 * Если нет, его направление считается INCORRECT
 */
enum class Direction {
    RIGHT,      // слева направо, например 30 -> 34    0
    UP_RIGHT,   // вверх-вправо, например 32 -> 62     1
    UP_LEFT,    // вверх-влево, например 25 -> 61      2
    LEFT,       // справа налево, например 34 -> 30    3
    DOWN_LEFT,  // вниз-влево, например 62 -> 32       4
    DOWN_RIGHT, // вниз-вправо, например 61 -> 25      5
    INCORRECT;  // отрезок имеет изгиб, например 30 -> 55 (изгиб в точке 35)

    /**
     * Простая (2 балла)
     *
     * Вернуть направление, противоположное данному.
     * Для INCORRECT вернуть INCORRECT
     */
    fun opposite(): Direction =
        when (this.ordinal) {
            in 0..2 -> Direction.values()[this.ordinal + 3]
            in 3..5 -> Direction.values()[this.ordinal - 3]
            else -> INCORRECT
        }

    /**
     * Средняя (3 балла)
     *
     * Вернуть направление, повёрнутое относительно
     * заданного на 60 градусов против часовой стрелки.
     *
     * Например, для RIGHT это UP_RIGHT, для UP_LEFT это LEFT, для LEFT это DOWN_LEFT.
     * Для направления INCORRECT бросить исключение IllegalArgumentException.
     * При решении этой задачи попробуйте обойтись без перечисления всех семи вариантов.
     */
    fun next(): Direction =
        when (this.ordinal) {
            in 0..4 -> Direction.values()[this.ordinal + 1]
            5 -> Direction.values()[0]
            else -> throw IllegalArgumentException()
        }

    /**
     * Простая (2 балла)
     *
     * Вернуть true, если данное направление совпадает с other или противоположно ему.
     * INCORRECT не параллельно никакому направлению, в том числе другому INCORRECT.
     */
    fun isParallel(other: Direction): Boolean = TODO()
}

/**
 * Средняя (3 балла)
 *
 * Сдвинуть точку в направлении direction на расстояние distance.
 * Бросить IllegalArgumentException(), если задано направление INCORRECT.
 * Для расстояния 0 и направления не INCORRECT вернуть ту же точку.
 * Для отрицательного расстояния сдвинуть точку в противоположном направлении на -distance.
 *
 * Примеры:
 * 30, direction = RIGHT, distance = 3 --> 33
 * 35, direction = UP_LEFT, distance = 2 --> 53
 * 45, direction = DOWN_LEFT, distance = 4 --> 05
 */
fun HexPoint.move(direction: Direction, distance: Int): HexPoint =
    when (if (distance >= 0) direction else direction.opposite()) {
        Direction.RIGHT -> HexPoint(this.x + abs(distance), this.y)
        Direction.DOWN_RIGHT -> HexPoint(this.x + abs(distance), this.y - abs(distance))
        Direction.LEFT -> HexPoint(this.x - abs(distance), this.y)
        Direction.DOWN_LEFT -> HexPoint(this.x, this.y - abs(distance))
        Direction.UP_LEFT -> HexPoint(this.x - abs(distance), this.y + abs(distance))
        Direction.UP_RIGHT -> HexPoint(this.x, this.y + abs(distance))
        else -> throw IllegalArgumentException()
    }

/**
 * Сложная (5 баллов)
 *
 * Найти кратчайший путь между двумя заданными гексами, представленный в виде списка всех гексов,
 * которые входят в этот путь.
 * Начальный и конечный гекс также входят в данный список.
 * Если кратчайших путей существует несколько, вернуть любой из них.
 *
 * Пример (для координатной сетки из примера в начале файла):
 *   pathBetweenHexes(HexPoint(y = 2, x = 2), HexPoint(y = 5, x = 3)) ->
 *     listOf(
 *       HexPoint(y = 2, x = 2),
 *       HexPoint(y = 2, x = 3),
 *       HexPoint(y = 3, x = 3),
 *       HexPoint(y = 4, x = 3),
 *       HexPoint(y = 5, x = 3)
 *     )
 */
fun pathBetweenHexes(from: HexPoint, to: HexPoint): List<HexPoint> = TODO()

/**
 * Очень сложная (20 баллов)
 *
 * Дано три точки (гекса). Построить правильный шестиугольник, проходящий через них
 * (все три точки должны лежать НА ГРАНИЦЕ, а не ВНУТРИ, шестиугольника).
 * Все стороны шестиугольника должны являться "правильными" отрезками.
 * Вернуть null, если такой шестиугольник построить невозможно.
 * Если шестиугольников существует более одного, выбрать имеющий минимальный радиус.
 *
 * Пример: через точки 13, 32 и 44 проходит правильный шестиугольник с центром в 24 и радиусом 2.
 * Для точек 13, 32 и 45 такого шестиугольника не существует.
 * Для точек 32, 33 и 35 следует вернуть шестиугольник радиусом 3 (с центром в 62 или 05).
 *
 * Если все три точки совпадают, вернуть шестиугольник нулевого радиуса с центром в данной точке.
 */
fun hexagonByThreePoints(a: HexPoint, b: HexPoint, c: HexPoint): Hexagon? = TODO()
/*//    множества всевозможных правильных шестиугольников, где каждая из точек может лежать на границе
    val possHexA = mutableSetOf<Hexagon>()
    val possHexB = mutableSetOf<Hexagon>()
    val possHexC = mutableSetOf<Hexagon>()


}*/

/**
 * Очень сложная (20 баллов)
 *
 * Дано множество точек (гексов). Найти правильный шестиугольник минимального радиуса,
 * содержащий все эти точки (безразлично, внутри или на границе).
 * Если множество пустое, бросить IllegalArgumentException.
 * Если множество содержит один гекс, вернуть шестиугольник нулевого радиуса с центром в данной точке.
 *
 * Пример: 13, 32, 45, 18 -- шестиугольник радиусом 3 (с центром, например, в 15)
 */
fun minContainingHexagon(vararg points: HexPoint): Hexagon {
    require(points.isNotEmpty())

    // определяю примерный центр и погрешность его определения
    var curCenter = HexPoint(
        points.fold(0) { sum, el -> sum + el.x } / points.size,
        points.fold(0) { sum, el -> sum + el.y } / points.size,
    )
    var curRadius = points.maxOfOrNull { it.distance(curCenter) }!! / 2
    var inaccuracy =
        if (curRadius * 4 < 1) 1
        else curRadius * 4


    var optimalHex = Hexagon(curCenter, curRadius)
    var minR = MAX_VALUE
    val hexes = mutableListOf<Hexagon>()

    while (inaccuracy > 0) {

        // для выбранного центра curCenter определяю список центров, сдвинутых на конкретное значение
        // погрешности в каждую сторону

        val centersWithInaccuracies = mutableListOf(curCenter)
        if (inaccuracy < 25) {
            for (direction in Direction.values()) {
                if (direction != Direction.INCORRECT)
                    centersWithInaccuracies += curCenter.move(direction, inaccuracy)
            }
        }
        else hexes.forEach {
            for (direction in Direction.values()) {
                if (direction != Direction.INCORRECT)
                    centersWithInaccuracies += it.center.move(direction, inaccuracy)
            }
        }


        hexes.clear()

        // для центра curCenter и его доп. центров строим шестиугольники с минимально возможным радиусом
        // и добавляем в список в том случае, если радиус меньше или равен минимально допустимому радиусу minR
        for (center in centersWithInaccuracies) {
            var hexagon = Hexagon(center, curRadius)
            var hexRadius = curRadius
            while (points.any { !hexagon.contains(it) }) {
                if (hexRadius > minR) break
                hexRadius += 1
                hexagon = hexagon.copy(radius = hexRadius)
            }
            if (hexRadius <= minR) {
                minR = hexRadius
                hexes += hexagon
            }
        }

        // выбираем новый центр curCenter по шестиугольнику с минимальным радиусом:
        // если выбранный центр совпадает со старым, то понижаем погрешность (пока не станет 0) и снова
        // прогоняем старый центр, если несовпадает - выбираем новый и не меняем погрешность
        optimalHex = hexes.minByOrNull { it.radius }!!
        inaccuracy = when {
            optimalHex.center != curCenter -> inaccuracy
            inaccuracy == 1 -> 0
            else ->
                if ((inaccuracy / 2.0.pow(inaccuracy.toString().length) / 1.5).toInt() < 1) 1
                else (inaccuracy / 2.0.pow(inaccuracy.toString().length) / 1.5).toInt()

        }
        curRadius = optimalHex.radius / 2
        curCenter = optimalHex.center
    }


    return optimalHex
}
