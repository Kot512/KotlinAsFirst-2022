@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson4.task1

import lesson1.task1.discriminant
import kotlin.math.*


// Урок 4: списки
// Максимальное количество баллов = 12
// Рекомендуемое количество баллов = 8
// Вместе с предыдущими уроками = 24/33

/**
 * Пример
 *
 * Найти все корни уравнения x^2 = y
 */
fun sqRoots(y: Double) =
    when {
        y < 0 -> listOf()
        y == 0.0 -> listOf(0.0)
        else -> {
            val root = sqrt(y)
            // Результат!
            listOf(-root, root)
        }
    }

/**
 * Пример
 *
 * Найти все корни биквадратного уравнения ax^4 + bx^2 + c = 0.
 * Вернуть список корней (пустой, если корней нет)
 */
fun biRoots(a: Double, b: Double, c: Double): List<Double> {
    if (a == 0.0) {
        return if (b == 0.0) listOf()
        else sqRoots(-c / b)
    }
    val d = discriminant(a, b, c)
    if (d < 0.0) return listOf()
    if (d == 0.0) return sqRoots(-b / (2 * a))
    val y1 = (-b + sqrt(d)) / (2 * a)
    val y2 = (-b - sqrt(d)) / (2 * a)
    return sqRoots(y1) + sqRoots(y2)
}

/**
 * Пример
 *
 * Выделить в список отрицательные элементы из заданного списка
 */
fun negativeList(list: List<Int>): List<Int> {
    val result = mutableListOf<Int>()
    for (element in list) {
        if (element < 0) {
            result.add(element)
        }
    }
    return result
}

/**
 * Пример
 *
 * Изменить знак для всех положительных элементов списка
 */
fun invertPositives(list: MutableList<Int>) {
    for (i in 0 until list.size) {
        val element = list[i]
        if (element > 0) {
            list[i] = -element
        }
    }
}

/**
 * Пример
 *
 * Из имеющегося списка целых чисел, сформировать список их квадратов
 */
fun squares(list: List<Int>) = list.map { it * it }

/**
 * Пример
 *
 * Из имеющихся целых чисел, заданного через vararg-параметр, сформировать массив их квадратов
 */
fun squares(vararg array: Int) = squares(array.toList()).toTypedArray()

/**
 * Пример
 *
 * По заданной строке str определить, является ли она палиндромом.
 * В палиндроме первый символ должен быть равен последнему, второй предпоследнему и т.д.
 * Одни и те же буквы в разном регистре следует считать равными с точки зрения данной задачи.
 * Пробелы не следует принимать во внимание при сравнении символов, например, строка
 * "А роза упала на лапу Азора" является палиндромом.
 */
fun isPalindrome(str: String): Boolean {
    val lowerCase = str.lowercase().filter { it != ' ' }
    for (i in 0..lowerCase.length / 2) {
        if (lowerCase[i] != lowerCase[lowerCase.length - i - 1]) return false
    }
    return true
}

/**
 * Пример
 *
 * По имеющемуся списку целых чисел, например [3, 6, 5, 4, 9], построить строку с примером их суммирования:
 * 3 + 6 + 5 + 4 + 9 = 27 в данном случае.
 */
fun buildSumExample(list: List<Int>) = list.joinToString(separator = " + ", postfix = " = ${list.sum()}")

/**
 * Простая (2 балла)
 *
 * Найти модуль заданного вектора, представленного в виде списка v,
 * по формуле abs = sqrt(a1^2 + a2^2 + ... + aN^2).
 * Модуль пустого вектора считать равным 0.0.
 */
fun abs(v: List<Double>): Double =
    sqrt(v.map { it * it }.sum())

/**
 * Простая (2 балла)
 *
 * Рассчитать среднее арифметическое элементов списка list. Вернуть 0.0, если список пуст
 */
fun mean(list: List<Double>): Double =
    if (list.isNotEmpty()) list.sum() / list.size
    else 0.0

/**
 * Средняя (3 балла)
 *
 * Центрировать заданный список list, уменьшив каждый элемент на среднее арифметическое всех элементов.
 * Если список пуст, не делать ничего. Вернуть изменённый список.
 *
 * Обратите внимание, что данная функция должна изменять содержание списка list, а не его копии.
 */
fun center(list: MutableList<Double>): MutableList<Double> {
    val av = mean(list)
    return if (list.isEmpty()) return list
    else {
        for (i in list.indices) list[i] -= av
        list
    }
}

/**
 * Средняя (3 балла)
 *
 * Найти скалярное произведение двух векторов равной размерности,
 * представленные в виде списков a и b. Скалярное произведение считать по формуле:
 * C = a1b1 + a2b2 + ... + aNbN. Произведение пустых векторов считать равным 0.
 */
fun times(a: List<Int>, b: List<Int>): Int {
    var sum = 0
    if (a.isNotEmpty())
        for (i in a.indices) sum += a[i] * b[i]
    return sum
}

/**
 * Средняя (3 балла)
 *
 * Рассчитать значение многочлена при заданном x:
 * p(x) = p0 + p1*x + p2*x^2 + p3*x^3 + ... + pN*x^N.
 * Коэффициенты многочлена заданы списком p: (p0, p1, p2, p3, ..., pN).
 * Значение пустого многочлена равно 0 при любом x.
 */
fun polynom(p: List<Int>, x: Int): Int {
    var sum = 0
    if (p.isNotEmpty())
        for (i in p.indices)
            sum += p[i] * x.toDouble().pow(i).toInt()
    return sum
}


/**
 * Средняя (3 балла)
 *
 * В заданном списке list каждый элемент, кроме первого, заменить
 * суммой данного элемента и всех предыдущих.
 * Например: 1, 2, 3, 4 -> 1, 3, 6, 10.
 * Пустой список не следует изменять. Вернуть изменённый список.
 *
 * Обратите внимание, что данная функция должна изменять содержание списка list, а не его копии.
 */
fun accumulate(list: MutableList<Int>): MutableList<Int> {
    return if (list.size < 2) list
    else {
        for (i in 1 until list.size)
            list[i] += list[i - 1]
        list
    }
}

/**
 * Средняя (3 балла)
 *
 * Разложить заданное натуральное число n > 1 на простые множители.
 * Результат разложения вернуть в виде списка множителей, например 75 -> (3, 5, 5).
 * Множители в списке должны располагаться по возрастанию.
 */
fun isPrime(n: Int) = n > 1 && (2..n / 2).all { n % it != 0}
fun factorize(n: Int): List<Int> {
    var usableN = n
    val result = mutableListOf<Int>()
    if (n in listOf(2, 3, 5, 7)) result += n
    for (i in 2..n / 2) {
        if (usableN % i == 0 && isPrime(i)) {
            while (usableN % i == 0 && usableN > 1) {
                result += i
                usableN /= i
            }
            if (usableN == 1) break
        }
    }
    return if (result.size != 0)
        result.sorted()
    else listOf(n)
}


/**
 * Сложная (4 балла)
 *
 * Разложить заданное натуральное число n > 1 на простые множители.
 * Результат разложения вернуть в виде строки, например 75 -> 3*5*5
 * Множители в результирующей строке должны располагаться по возрастанию.
 */
fun factorizeToString(n: Int): String {
    var usableN = n
    val result = mutableListOf<Int>()
    if (n in listOf(2, 3, 5, 7)) result += n
    for (i in 2..n / 2) {
        if (usableN % i == 0 && isPrime(i)) {
            while (usableN % i == 0 && usableN > 1) {
                result += i
                usableN /= i
            }
            if (usableN == 1) break
        }
    }
    return if (result.size != 0)
        result.sorted().joinToString(separator = "*")
    else "$n"
}

/**
 * Средняя (3 балла)
 *
 * Перевести заданное целое число n >= 0 в систему счисления с основанием base > 1.
 * Результат перевода вернуть в виде списка цифр в base-ичной системе от старшей к младшей,
 * например: n = 100, base = 4 -> (1, 2, 1, 0) или n = 250, base = 14 -> (1, 3, 12)
 */
fun convert(n: Int, base: Int): List<Int> = TODO()

/**
 * Сложная (4 балла)
 *
 * Перевести заданное целое число n >= 0 в систему счисления с основанием 1 < base < 37.
 * Результат перевода вернуть в виде строки, цифры более 9 представлять латинскими
 * строчными буквами: 10 -> a, 11 -> b, 12 -> c и так далее.
 * Например: n = 100, base = 4 -> 1210, n = 250, base = 14 -> 13c
 *
 * Использовать функции стандартной библиотеки, напрямую и полностью решающие данную задачу
 * (например, n.toString(base) и подобные), запрещается.
 */
fun convertToString(n: Int, base: Int): String = TODO()

/**
 * Средняя (3 балла)
 *
 * Перевести число, представленное списком цифр digits от старшей к младшей,
 * из системы счисления с основанием base в десятичную.
 * Например: digits = (1, 3, 12), base = 14 -> 250
 */
fun decimal(digits: List<Int>, base: Int): Int = TODO()

/**
 * Сложная (4 балла)
 *
 * Перевести число, представленное цифровой строкой str,
 * из системы счисления с основанием base в десятичную.
 * Цифры более 9 представляются латинскими строчными буквами:
 * 10 -> a, 11 -> b, 12 -> c и так далее.
 * Например: str = "13c", base = 14 -> 250
 *
 * Использовать функции стандартной библиотеки, напрямую и полностью решающие данную задачу
 * (например, str.toInt(base)), запрещается.
 */
fun decimalFromString(str: String, base: Int): Int = TODO()

/**
 * Сложная (5 баллов)
 *
 * Перевести натуральное число n > 0 в римскую систему.
 * Римские цифры: 1 = I, 4 = IV, 5 = V, 9 = IX, 10 = X, 40 = XL, 50 = L,
 * 90 = XC, 100 = C, 400 = CD, 500 = D, 900 = CM, 1000 = M.
 * Например: 23 = XXIII, 44 = XLIV, 100 = C
 */

fun romanTransformer(n: Int): String {
    val romans= mapOf(1 to "I", 2 to "II", 3 to "III", 4 to "IV",
        5 to "V", 6 to "VI", 7 to "VII", 8 to "VIII", 9 to "IX",
        10 to "X", 20 to "XX", 30 to "XX", 40 to "XL", 50 to "L",
        60 to "LX", 70 to "LXX", 80 to "LXXX", 90 to "XC", 100 to "C",
        200 to "CC", 300 to "CCC", 400 to "CD", 500 to "D", 600 to "DC",
        700 to "DC", 800 to "DCC", 900 to "CM", 1000 to "M", 2000 to "MM",
        3000 to "MMM")
    return romans[n] ?: ""
}

fun roman(n: Int): String {
    val ranksOfN = n.toString().map { it.digitToInt() }.toMutableList()
    for (i in 0 until ranksOfN.size)
        ranksOfN[i] *= 10.0.pow(ranksOfN.size - i - 1).toInt()
    return ranksOfN.fold("") { s, el -> s + romanTransformer(el) }
}


/**
 * Очень сложная (7 баллов)
 *
 * Записать заданное натуральное число 1..999999 прописью по-русски.
 * Например, 375 = "триста семьдесят пять",
 * 23964 = "двадцать три тысячи девятьсот шестьдесят четыре"
 */

fun body(l:List<Char>, k: Int): String {
    var num = ""
    val rus = mapOf(
        1 to "один ", 2 to "два ", 3 to "три ", 4 to "четыре ",
        5 to "пять ", 6 to "шесть ", 7 to "семь ", 8 to "восемь ", 9 to "девять ",
        10 to "десять ", 20 to "двадцать ", 30 to "тридцать ", 40 to "сорок ",
        50 to "пятьдесят ", 60 to "шестьдесят ", 70 to "семьдесят ", 80 to "восемьдесят ",
        90 to "девяносто ", 100 to "сто ", 200 to "двести ", 300 to "триста ",
        400 to "четыреста ", 500 to "пятьсот ", 600 to "шестьсот ", 700 to "семьсот ",
        800 to "восемьсот ", 900 to "девятьсот "
    )

    for (i in l.indices) {
        val uN =
            l[i].digitToInt() * 10.0.pow(l.size - 1 - i).toInt()
        num += rus[uN] ?: ""
    }

    val replaceble = mapOf(
        "десять один" to "одиннадцать", "десять два" to "двенадцать",
        "десять три" to "тринадцать", "десять четыре" to "четырнадцать",
        "десять пять" to "пятнадцать", "десять шесть" to "шестнадцать",
        "десять семь" to "семнадцать", "десять восемь" to "восемнадцать",
        "десять девять" to "девятнадцать", "null" to ""
    )
    for (i in replaceble.keys) num = num.replace(i, replaceble[i] ?: "")
    if (k == 1) num = num.replace("один ", "одна ")
    if (k == 1) num = num.replace("два ", "две ")
    return num
}

fun tale(body: String, n:List<Char>): String {
    val uN = n.fold("") { s, el -> s + el} + "000"
    val zeroAmount = uN.count {it == '0'}
    if (body == "") return ""
    return when (zeroAmount) {
        in 0..2 -> ""
        in 3..5 -> when (body.substring(body.length - 3, body.length)) {
            "на " -> "тысяча "
            in listOf("ве ", "ри ", "ре ") -> "тысячи "
            else -> "тысяч "
        }

        else -> ""
    }
}

fun russian(n: Int): String {
    val strN = n.toString().toMutableList()
    val hundreds =
        if (strN.size >= 3) strN.subList(strN.size - 3, strN.size)
        else strN
    val thousands =
        if (strN.size > 3) strN.subList(0, strN.size - 3)
        else mutableListOf<Char>()
    val res =
        body(thousands, 1) + tale(body(thousands, 1), thousands) + body(hundreds, 0)
    return res.trim()
}