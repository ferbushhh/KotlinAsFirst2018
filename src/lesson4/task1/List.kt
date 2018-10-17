@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson4.task1

import lesson1.task1.discriminant
import kotlin.math.sqrt

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
    val lowerCase = str.toLowerCase().filter { it != ' ' }
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
 * Простая
 *
 * Найти модуль заданного вектора, представленного в виде списка v,
 * по формуле abs = sqrt(a1^2 + a2^2 + ... + aN^2).
 * Модуль пустого вектора считать равным 0.0.
 */
fun abs(v: List<Double>): Double {
    return if (v.isEmpty()) 0.0
    else {
        var sum = 0.0
        for (i in 0 until v.size) {
            sum += v[i] * v[i]
        }
        sqrt(sum)
    }
}

/**
 * Простая
 *
 * Рассчитать среднее арифметическое элементов списка list. Вернуть 0.0, если список пуст
 */
fun mean(list: List<Double>): Double {
    return if (list.isEmpty()) 0.0
    else {
        var averageValue = 0.0
        for (i in 0 until list.size)
            averageValue += list[i]
        averageValue / list.size
    }
}

/**
 * Средняя
 *
 * Центрировать заданный список list, уменьшив каждый элемент на среднее арифметическое всех элементов.
 * Если список пуст, не делать ничего. Вернуть изменённый список.
 *
 * Обратите внимание, что данная функция должна изменять содержание списка list, а не его копии.
 */
fun center(list: MutableList<Double>): MutableList<Double> { //не понимаю смысл :\
    return if (list.isEmpty()) list
    else {
        var average = mean(list)
        for (i in 0 until list.size)
            list[i] = list[i] - average
        list
    }
}

/**
 * Средняя
 *
 * Найти скалярное произведение двух векторов равной размерности,
 * представленные в виде списков a и b. Скалярное произведение считать по формуле:
 * C = a1b1 + a2b2 + ... + aNbN. Произведение пустых векторов считать равным 0.0.
 */
fun times(a: List<Double>, b: List<Double>): Double {
    return if (a.isEmpty() && b.isEmpty()) 0.0
    else {
        var sum = 0.0
        for (i in 0 until a.size) {
            sum += a[i] * b[i]
        }
        sum
    }
}

/**
 * Средняя
 *
 * Рассчитать значение многочлена при заданном x:
 * p(x) = p0 + p1*x + p2*x^2 + p3*x^3 + ... + pN*x^N.
 * Коэффициенты многочлена заданы списком p: (p0, p1, p2, p3, ..., pN).
 * Значение пустого многочлена равно 0.0 при любом x.
 */
fun polynom(p: List<Double>, x: Double): Double {
    return if (p.isEmpty()) 0.0
    else {
        var xDegree = x
        var sum = p[0]
        for (i in 1 until p.size) {
            sum += p[i] * xDegree
            xDegree *= x
        }
        sum
    }
}

/**
 * Средняя
 *
 * В заданном списке list каждый элемент, кроме первого, заменить
 * суммой данного элемента и всех предыдущих.
 * Например: 1, 2, 3, 4 -> 1, 3, 6, 10.
 * Пустой список не следует изменять. Вернуть изменённый список.
 *
 * Обратите внимание, что данная функция должна изменять содержание списка list, а не его копии.
 */
fun accumulate(list: MutableList<Double>): MutableList<Double> {
    return if (list.isEmpty()) list
    else {
        for (i in 1 until list.size)
            list[i] = list[i - 1] + list[i]
        list
    }
}

/**
 * Средняя
 *
 * Разложить заданное натуральное число n > 1 на простые множители.
 * Результат разложения вернуть в виде списка множителей, например 75 -> (3, 5, 5).
 * Множители в списке должны располагаться по возрастанию.
 */
fun factorize(n: Int): List<Int> {
    var i = 2
    var int = n
    var list = mutableListOf<Int>()
    while (int != 1) {
        if (int % i == 0) {
            list.add(i)
            int /= i
        } else i++
    }
    return list
}

/**
 * Сложная
 *
 * Разложить заданное натуральное число n > 1 на простые множители.
 * Результат разложения вернуть в виде строки, например 75 -> 3*5*5
 * Множители в результирующей строке должны располагаться по возрастанию.
 */
fun factorizeToString(n: Int): String = factorize(n).joinToString(separator = "*")

/**
 * Средняя
 *
 * Перевести заданное целое число n >= 0 в систему счисления с основанием base > 1.
 * Результат перевода вернуть в виде списка цифр в base-ичной системе от старшей к младшей,
 * например: n = 100, base = 4 -> (1, 2, 1, 0) или n = 250, base = 14 -> (1, 3, 12)
 */
fun convert(n: Int, base: Int): List<Int> {
    if (n == 0) {
        var listZero = listOf(0)
        return listZero
    }
    var int = n
    var list = mutableListOf<Int>()
    while (int != 0) {
        var mod = int % base
        list.add(mod)
        int /= base
    }
    return list.reversed()
}

/**
 * Сложная
 *
 * Перевести заданное целое число n >= 0 в систему счисления с основанием 1 < base < 37.
 * Результат перевода вернуть в виде строки, цифры более 9 представлять латинскими
 * строчными буквами: 10 -> a, 11 -> b, 12 -> c и так далее.
 * Например: n = 100, base = 4 -> 1210, n = 250, base = 14 -> 13c
 */
fun convertToString(n: Int, base: Int): String {
    if (n == 0) {
        var convertZero: String
        convertZero = "0"
        return convertZero
    }
    var int = n
    var convert = String()
    while (int != 0) {
        var assistive: String
        var mod = int % base
        if (mod >= 10) assistive = convert + (mod + 87).toChar()
        else assistive = convert + (mod + 48).toChar()
        convert = assistive
        int /= base
    }
    return convert.reversed()
}

/**
 * Средняя
 *
 * Перевести число, представленное списком цифр digits от старшей к младшей,
 * из системы счисления с основанием base в десятичную.
 * Например: digits = (1, 3, 12), base = 14 -> 250
 */
fun decimal(digits: List<Int>, base: Int): Int {
    var sum = digits.last()
    var degree = base
    for (i in (digits.size - 2) downTo 0) {
        sum += degree * digits[i]
        degree *= base
    }
    return sum
}

/**
 * Сложная
 *
 * Перевести число, представленное цифровой строкой str,
 * из системы счисления с основанием base в десятичную.
 * Цифры более 9 представляются латинскими строчными буквами:
 * 10 -> a, 11 -> b, 12 -> c и так далее.
 * Например: str = "13c", base = 14 -> 250
 */
fun decimalFromString(str: String, base: Int): Int {
    var last = str.last().toInt()
    var sum: Int
    if (last > 57) sum = last - 87
    else sum = last - 48
    var degree = base
    for (i in (str.length - 2) downTo 0) {
        var assistive: Int
        if (str[i].toInt() > 57) assistive = str[i].toInt() - 87
        else assistive = str[i].toInt() - 48
        sum += degree * assistive
        degree *= base
    }
    return sum
}

/**
 * Сложная
 *
 * Перевести натуральное число n > 0 в римскую систему.
 * Римские цифры: 1 = I, 4 = IV, 5 = V, 9 = IX, 10 = X, 40 = XL, 50 = L,
 * 90 = XC, 100 = C, 400 = CD, 500 = D, 900 = CM, 1000 = M.
 * Например: 23 = XXIII, 44 = XLIV, 100 = C
 */
fun roman(n: Int): String {
    var arab = n
    var thousand = arab / 1000
    var roman = String()
    if (thousand != 0) {
        var assistive = String()
        for (i in 1..thousand) {
            roman = assistive + "M"
            assistive = roman
        }
    }
    arab %= 1000
    if (arab in 500..899) {
        var assistive = roman
        roman = assistive + "D"
        arab -= 500
    } else if (arab >= 900) {
        var assistive = roman
        roman = assistive + "CM"
        arab -= 900
    }
    var hundred = arab / 100
    if (hundred in 1..3) {
        var assistive = roman
        for (i in 1..hundred) {
            roman = assistive + "C"
            assistive = roman
        }
    } else if (hundred == 4) {
        var assistive = roman
        roman = assistive + "CD"
    }
    arab %= 100
    if (arab in 50..89) {
        var assistive = roman
        roman = assistive + "L"
        arab -= 50
    } else if (arab >= 90) {
        var assistive = roman
        roman = assistive + "XC"
        arab -= 90
    }
    var decade = arab / 10
    if (decade in 1..3) {
        var assistive = roman
        for (i in 1..decade) {
            roman = assistive + "X"
            assistive = roman
        }
    } else if (decade == 4) {
        var assistive = roman
        roman = assistive + "XL"
    }
    arab %= 10
    if (arab in 5..8) {
        var assistive = roman
        roman = assistive + "V"
        arab -= 5
    } else if (arab == 9) {
        var assistive = roman
        roman = assistive + "IX"
        arab = 0
    }
    if (arab in 1..3) {
        var assistive = roman
        for (i in 1..arab) {
            roman = assistive + "I"
            assistive = roman
        }
    } else if (arab == 4) {
        var assistive = roman
        roman = assistive + "IV"
    }
    return roman
}

/**
 * Очень сложная
 *
 * Записать заданное натуральное число 1..999999 прописью по-русски.
 * Например, 375 = "триста семьдесят пять",
 * 23964 = "двадцать три тысячи девятьсот шестьдесят четыре"
 */

fun hundred(n: Int): String {
    var hundred = listOf<String>("", "сто", "двести", "триста", "четыреста", "пятьсот",
            "шестьсот", "семьсот", "восемьсот", "девятьсот")
    var decade = listOf<String>("", "", "двадцать", "тридцать", "сорок", "пятьдесят",
            "шестьдесят", "семьдесят", "восемьдесят", "девяносто")
    var unit = listOf<String>("", "один", "два", "три", "четыре", "пять", "шесть",
            "семь", "восемь", "девять")
    var firstDecade = listOf<String>("", "одиннадцать", "двенадцать", "тринадцать", "четырнадцать",
            "пятнадцать", "шестнадцать", "семнадцать", "восемнадцать", "девятнадцать")
    var digital = n
    return if (((digital % 100) / 10 == 1) && (digital % 10 != 0) && (digital / 100 != 0)) //если последние две цифры 11 - 19 И есть сотни, например: 317
        hundred[digital / 100] + " " + firstDecade[digital % 10]
    else if (digital == 10) //если равно 10, например: 10
        "десять"
    else if (((digital % 100) / 10 == 1) && (digital % 10 == 0)) //если на конце 10 И есть сотни, например: 210
        hundred[digital / 100] + " десять"
    else if (digital % 10 == 0 && digital % 100 / 10 == 0) //если есть только сотни (десятки и единицы по нулям), например: 400
        hundred[digital / 100]
    else if (digital / 100 == 0 && digital % 10 == 0) //если есть только десятки (сотни и единицы равны 0), например: 50
        decade[digital / 10]
    else if (digital < 10) //если есть только единицы (сотни и десятки равны 0), например: 7
        unit[digital]
    else if (digital in 11..19)// если 11 - 19, например: 16
        firstDecade[digital % 10]
    else if (digital % 100 / 10 == 0)// если десятки равны 0 (только единицы и сотни), например: 807
        hundred[digital / 100] + " " + unit[digital % 10]
    else if (digital % 10 == 0) // если единицы равны 0 (только сотни и десятки), например: 670
        hundred[digital / 100] + " " + decade[(digital % 100) / 10]
    else hundred[digital / 100] + " " + decade[(digital % 100) / 10] + " " + unit[digital % 10] //все остальное
}

fun thousand(n: Int): String {
    var thousand = listOf<String>(" тысяча", " тысячи", " тысяч") //окончание тысяч
    var thousandNum = n
    if (thousandNum == 1) //если всего одна тысяча, например: 1
        return hundred(thousandNum - 1) + "одна" + thousand[0]
    else if (thousandNum % 10 == 1) //если на конце числа стоит 1, например: 201
        return hundred(thousandNum - 1) + " одна" + thousand[0]
    else if (thousandNum == 2) //если всего две тысячи, например: 2
        return "две" + thousand[1]
    else if (thousandNum % 10 == 2) //если на конце числа стоит 2, например: 202
        return hundred(thousandNum - 2) + " две" + thousand[1]
    else if (thousandNum % 10 == 3 || thousandNum % 10 == 4) //если на конце числа стоит 3, например: 203
        return hundred(thousandNum) + thousand[1]
    else if (thousandNum % 10 in 5..9) //если на конце числа стоит 5 - 9, например: 208
        return hundred(thousandNum) + thousand[2]
    else if (thousandNum % 100 in 10..19) //если на конце числа стоит 10 - 19, например: 217
        return hundred(thousandNum) + thousand[2]
    else if (thousandNum % 10 == 0 && (thousandNum % 100) / 10 in 2..9) //если последние две цифры 20, 30, 40, ... 90, например: 250
        return hundred(thousandNum) + thousand[2]
    else (thousandNum != 0) //все остальные случаи
    return hundred(thousandNum) + thousand[2]
}

fun russian(n: Int): String {
    return if (n > 1000 && n % 1000 == 0) thousand(n / 1000) //число имеет только тысячи (меньшие разряды равны 0)
    else if (n > 1000) thousand(n / 1000) + " " + hundred(n % 1000) //тысячи и более мелкие
    else hundred(n % 1000) //тысяч нет
}


fun main(args: Array<String>) {
    val x1x2 = convertToString(0, 2)
    println("Root product:$x1x2")
}