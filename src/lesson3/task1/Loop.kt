@file:Suppress("UNUSED_PARAMETER")

package lesson3.task1

import kotlin.math.sqrt
import kotlin.math.*

/**
 * Пример
 *
 * Вычисление факториала
 */
fun factorial(n: Int): Double {
    var result = 1.0
    for (i in 1..n) {
        result *= i // Please do not fix in master
    }
    return result
}

/**
 * Пример
 *
 * Проверка числа на простоту -- результат true, если число простое
 */
fun isPrime(n: Int): Boolean {
    if (n < 2) return false
    if (n == 2) return true
    if (n % 2 == 0) return false
    for (m in 3..sqrt(n.toDouble()).toInt() step 2) {
        if (n % m == 0) return false
    }
    return true
}

/**
 * Пример
 *
 * Проверка числа на совершенность -- результат true, если число совершенное
 */
fun isPerfect(n: Int): Boolean {
    var sum = 1
    for (m in 2..n / 2) {
        if (n % m > 0) continue
        sum += m
        if (sum > n) break
    }
    return sum == n
}

/**
 * Пример
 *
 * Найти число вхождений цифры m в число n
 */
fun digitCountInNumber(n: Int, m: Int): Int =
        when {
            n == m -> 1
            n < 10 -> 0
            else -> digitCountInNumber(n / 10, m) + digitCountInNumber(n % 10, m)
        }

/**
 * Тривиальная
 *
 * Найти количество цифр в заданном числе n.
 * Например, число 1 содержит 1 цифру, 456 -- 3 цифры, 65536 -- 5 цифр.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun digitNumber(n: Int): Int {
    var kol = 0
    var num = abs(n)
    if (num == 0) return 1
    while (num > 0) {
        num /= 10
        kol++
    }
    return kol
}

/**
 * Простая
 *
 * Найти число Фибоначчи из ряда 1, 1, 2, 3, 5, 8, 13, 21, ... с номером n.
 * Ряд Фибоначчи определён следующим образом: fib(1) = 1, fib(2) = 1, fib(n+2) = fib(n) + fib(n+1)
 */
/*fun fib(n: Int): Int {
    if (n == 1 || n == 2) return 1
    else {
        var num = 2
        var fib = 1
        var second = 1
        var i: Int
        while (true) {
            if (num + 1 == n) return (fib + second)
            else {
                var aux = fib
                fib = second
                second += aux
                num++
            }
        }
    }
}*/

fun fib(n: Int): Int {
    var a = 1
    var b = 1
    for (i in 3..n) {
        var c = b
        b = a
        a += c
    }
    return if (n != 1 || n != 2) a
    else 1
}

/**
 * Простая
 *
 * Для заданных чисел m и n найти наименьшее общее кратное, то есть,
 * минимальное число k, которое делится и на m и на n без остатка
 */

fun maxDelit(m: Int, n: Int): Int {
    var a = m
    var b = n
    while (a != 0 && b != 0) {
        if (a > b) a %= b
        else b %= a
    }
    return a + b
}

fun lcm(m: Int, n: Int): Int = (m * n) / (maxDelit(m, n))

/**
 * Простая
 *
 * Для заданного числа n > 1 найти минимальный делитель, превышающий 1
 */
fun minDivisor(n: Int): Int {
    var m = 2
    while (n > 0) {
        if (n % m == 0) return m
        else m++
    }
    return 0
}

/**
 * Простая
 *
 * Для заданного числа n > 1 найти максимальный делитель, меньший n
 */
fun maxDivisor(n: Int): Int {
    var m = n / 2
    while (n > 0) {
        if (n % m == 0) return m
        else m--
    }
    return 0
}

/**
 * Простая
 *
 * Определить, являются ли два заданных числа m и n взаимно простыми.
 * Взаимно простые числа не имеют общих делителей, кроме 1.
 * Например, 25 и 49 взаимно простые, а 6 и 8 -- нет.
 */
fun isCoPrime(m: Int, n: Int): Boolean = (maxDelit(m, n) == 1)

/**
 * Простая
 *
 * Для заданных чисел m и n, m <= n, определить, имеется ли хотя бы один точный квадрат между m и n,
 * то есть, существует ли такое целое k, что m <= k*k <= n.
 * Например, для интервала 21..28 21 <= 5*5 <= 28, а для интервала 51..61 квадрата не существует.
 */
fun squareBetweenExists(m: Int, n: Int): Boolean {
    for (i in (sqrt(m.toDouble())).toInt() - 1..(sqrt(n.toDouble())).toInt() + 1) {
        if ((i <= sqrt(n.toDouble())) && (i >= sqrt(m.toDouble()))) return true
    }
    return false
}

/**
 * Средняя
 *
 * Гипотеза Коллатца. Рекуррентная последовательность чисел задана следующим образом:
 *
 *   ЕСЛИ (X четное)
 *     Xслед = X /2
 *   ИНАЧЕ
 *     Xслед = 3 * X + 1
 *
 * например
 *   15 46 23 70 35 106 53 160 80 40 20 10 5 16 8 4 2 1 4 2 1 4 2 1 ...
 * Данная последовательность рано или поздно встречает X == 1.
 * Написать функцию, которая находит, сколько шагов требуется для
 * этого для какого-либо начального X > 0.
 */
fun collatzSteps(x: Int): Int {
    var y = x
    var num = 0
    while (y != 1) {
        if (y % 2 == 0) {
            y /= 2
            num++
        } else {
            y = 3 * y + 1
            num++
        }
    }
    return num
}

/**
 * Средняя
 *
 * Для заданного x рассчитать с заданной точностью eps
 * sin(x) = x - x^3 / 3! + x^5 / 5! - x^7 / 7! + ...
 * Нужную точность считать достигнутой, если очередной член ряда меньше eps по модулю
 */
fun sin(x: Double, eps: Double): Double { //не работает!!!
    val xNormal = x % (2 * PI)
    var p = xNormal
    var fac = 1
    var ans = p
    while (abs(p) >= eps) {
        p *= xNormal * xNormal * (-1) / ((fac + 1) * (fac + 2))
        ans += p
        fac += 2
    }
    return ans
}

/**
 * Средняя
 *
 * Для заданного x рассчитать с заданной точностью eps
 * cos(x) = 1 - x^2 / 2! + x^4 / 4! - x^6 / 6! + ...
 * Нужную точность считать достигнутой, если очередной член ряда меньше eps по модулю
 */
fun cos(x: Double, eps: Double): Double = TODO()

/**
 * Средняя
 *
 * Поменять порядок цифр заданного числа n на обратный: 13478 -> 87431.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun revert(n: Int): Int {
    var num = n
    var rev = 0
    var ost: Int
    while (num > 0) {
        ost = num % 10
        rev = rev * 10 + ost
        num /= 10
    }
    return rev
}

/**
 * Средняя
 *
 * Проверить, является ли заданное число n палиндромом:
 * первая цифра равна последней, вторая -- предпоследней и так далее.
 * 15751 -- палиндром, 3653 -- нет.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun isPalindrome(n: Int): Boolean = n == revert(n)


/**
 * Средняя
 *
 * Для заданного числа n определить, содержит ли оно различающиеся цифры.
 * Например, 54 и 323 состоят из разных цифр, а 111 и 0 из одинаковых.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun hasDifferentDigits(n: Int): Boolean {
    var d = n % 10
    var m = n / 10
    while (m > 0) {
        if (m % 10 != d) return true
        m /= 10
    }
    return false
}

/**
 * Сложная
 *
 * Найти n-ю цифру последовательности из квадратов целых чисел:
 * 149162536496481100121144...
 * Например, 2-я цифра равна 4, 7-я 5, 12-я 6.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun numberDigit(t: Int): Int {
    var count = 0
    var copy = t
    while (copy > 0) {
        copy /= 10
        count++
    }
    return count
}

fun control(a: Int, b: Int, c: Int): Int {
    var p = a
    var k = b
    var m = c
    var y = 1
    if (p == m) return k % 10
    else if (p > m) {
        while (true) {
            if ((p - y) == m) return ((k / 10) % 10)
            else {
                k /= 10
                y++
            }
        }
    }
    return -1
}

/*fun squareSequenceDigit(n: Int): Int {
    var m = n
    var i = 1
    var k: Int
    //var p = 0
    var y = 1
    while (i >= 1) {
        k = i * i
        var p = numberDigit(k)
        if (p == m) return k % 10
        else if (p > m) {
            while (true) {
                if ((p - y) == m) return ((k / 10) % 10)
                else {
                    k /= 10
                    y++
                }
            }
        }
        m -= p
        i++
    }
    return 0
}*/
fun squareSequenceDigit(n: Int): Int {
    var num = n
    var i = 1
    var square: Int
    while (i >= 1) {
        square = i * i
        var count = numberDigit(square)
        var check = control(count, square, num)
        if (check != -1) return check
        num -= count
        i++
    }
    return 0
}

/**
 * Сложная
 *
 * Найти n-ю цифру последовательности из чисел Фибоначчи (см. функцию fib выше):
 * 1123581321345589144...
 * Например, 2-я цифра равна 1, 9-я 2, 14-я 5.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
/*fun fibSequenceDigit(n: Int): Int { //не проходит последний тест
    var num = n
    var i = 1
    var check = 1
    while (true) {
        var fib = fib(i)
        var count = numberDigit(fib)
        if (count == num) return fib % 10
        else if (count > num) {
            while (true) {
                if ((count - check) == num) return ((fib / 10) % 10)
                else {
                    fib /= 10
                    check++
                }
            }
        }
        num -= count
        i++
    }
    return 0
} */

fun fibSequenceDigit(n: Int): Int { //не проходит последний тест
    var num = n
    var i = 1
    while (true) {
        var fib = fib(i)
        var count = numberDigit(fib)
        var check = control(count, fib, num)
        if (check != -1) return check
        num -= count
        i++
    }
    return 0
}

fun main(args: Array<String>) {
    val x1x2 = squareSequenceDigit(17)
    println("Root product: $x1x2")
}