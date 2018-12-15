@file:Suppress("UNUSED_PARAMETER")

package lesson8.task2

import java.awt.Point
import java.lang.Math.abs

/**
 * Клетка шахматной доски. Шахматная доска квадратная и имеет 8 х 8 клеток.
 * Поэтому, обе координаты клетки (горизонталь row, вертикаль column) могут находиться в пределах от 1 до 8.
 * Горизонтали нумеруются снизу вверх, вертикали слева направо.
 */
data class Square(val column: Int, val row: Int) {
    /**
     * Пример
     *
     * Возвращает true, если клетка находится в пределах доски
     */
    fun inside(): Boolean = column in 1..8 && row in 1..8

    /**
     * Простая
     *
     * Возвращает строковую нотацию для клетки.
     * В нотации, колонки обозначаются латинскими буквами от a до h, а ряды -- цифрами от 1 до 8.
     * Для клетки не в пределах доски вернуть пустую строку
     */
    fun notation(): String = if (!inside()) "" else 'a' + column - 1 + "$row"
}

/**
 * Простая
 *
 * Создаёт клетку по строковой нотации.
 * В нотации, колонки обозначаются латинскими буквами от a до h, а ряды -- цифрами от 1 до 8.
 * Если нотация некорректна, бросить IllegalArgumentException
 */
fun square(notation: String): Square {
    try {
        if (!Regex("[a-h][1-9]").matches(notation)) throw IllegalArgumentException("error")
        return Square(notation[0] - 'a' + 1, notation[1] - '0')
    } catch (e: IllegalArgumentException) {
        throw IllegalArgumentException("error")
    }
}

/**
 * Простая
 *
 * Определить число ходов, за которое шахматная ладья пройдёт из клетки start в клетку end.
 * Шахматная ладья может за один ход переместиться на любую другую клетку
 * по вертикали или горизонтали.
 * Ниже точками выделены возможные ходы ладьи, а крестиками -- невозможные:
 *
 * xx.xxххх
 * xх.хxххх
 * ..Л.....
 * xх.хxххх
 * xx.xxххх
 * xx.xxххх
 * xx.xxххх
 * xx.xxххх
 *
 * Если клетки start и end совпадают, вернуть 0.
 * Если любая из клеток некорректна, бросить IllegalArgumentException().
 *
 * Пример: rookMoveNumber(Square(3, 1), Square(6, 3)) = 2
 * Ладья может пройти через клетку (3, 3) или через клетку (6, 1) к клетке (6, 3).
 */
fun rookMoveNumber(start: Square, end: Square): Int {
    try {
        if (start.notation() == "" || end.notation() == "") throw IllegalArgumentException("error")
        return when {
            start == end -> 0
            start.column == end.column || start.row == end.row -> 1
            else -> 2
        }
    } catch (e: IllegalArgumentException) {
        throw IllegalArgumentException("error")
    }

}

/**
 * Средняя
 *
 * Вернуть список из клеток, по которым шахматная ладья может быстрее всего попасть из клетки start в клетку end.
 * Описание ходов ладьи см. предыдущую задачу.
 * Список всегда включает в себя клетку start. Клетка end включается, если она не совпадает со start.
 * Между ними должны находиться промежуточные клетки, по порядку от start до end.
 * Примеры: rookTrajectory(Square(3, 3), Square(3, 3)) = listOf(Square(3, 3))
 *          (здесь возможен ещё один вариант)
 *          rookTrajectory(Square(3, 1), Square(6, 3)) = listOf(Square(3, 1), Square(3, 3), Square(6, 3))
 *          (здесь возможен единственный вариант)
 *          rookTrajectory(Square(3, 5), Square(8, 5)) = listOf(Square(3, 5), Square(8, 5))
 * Если возможно несколько вариантов самой быстрой траектории, вернуть любой из них.
 */
fun rookTrajectory(start: Square, end: Square): List<Square> {
    val result = rookMoveNumber(start, end)
    return when (result) {
        0 -> listOf(start)
        1 -> listOf(start, end)
        else -> {
            val middle = Square(start.column, end.row)
            listOf(start, middle, end)
        }
    }
}

/**
 * Простая
 *
 * Определить число ходов, за которое шахматный слон пройдёт из клетки start в клетку end.
 * Шахматный слон может за один ход переместиться на любую другую клетку по диагонали.
 * Ниже точками выделены возможные ходы слона, а крестиками -- невозможные:
 *
 * .xxx.ххх
 * x.x.xххх
 * xxСxxxxx
 * x.x.xххх
 * .xxx.ххх
 * xxxxx.хх
 * xxxxxх.х
 * xxxxxхх.
 *
 * Если клетки start и end совпадают, вернуть 0.
 * Если клетка end недостижима для слона, вернуть -1.
 * Если любая из клеток некорректна, бросить IllegalArgumentException().
 *
 * Примеры: bishopMoveNumber(Square(3, 1), Square(6, 3)) = -1; bishopMoveNumber(Square(3, 1), Square(3, 7)) = 2.
 * Слон может пройти через клетку (6, 4) к клетке (3, 7).
 */
fun bishopMoveNumber(start: Square, end: Square): Int {
    try {
        if (start.notation() == "" || end.notation() == "") throw IllegalArgumentException("error")
        val parityFirst = (start.column + start.row) % 2
        val paritySecond = (end.column + end.row) % 2
        return when {
            start == end -> 0
            abs(start.column - end.column) == abs(start.row - end.row) -> 1
            parityFirst == paritySecond -> 2
            else -> -1
        }

    } catch (e: IllegalArgumentException) {
        throw IllegalArgumentException("error")
    }

}

/**
 * Сложная
 *
 * Вернуть список из клеток, по которым шахматный слон может быстрее всего попасть из клетки start в клетку end.
 * Описание ходов слона см. предыдущую задачу.
 *
 * Если клетка end недостижима для слона, вернуть пустой список.
 *
 * Если клетка достижима:
 * - список всегда включает в себя клетку start
 * - клетка end включается, если она не совпадает со start.
 * - между ними должны находиться промежуточные клетки, по порядку от start до end.
 *
 * Примеры: bishopTrajectory(Square(3, 3), Square(3, 3)) = listOf(Square(3, 3))
 *          bishopTrajectory(Square(3, 1), Square(3, 7)) = listOf(Square(3, 1), Square(6, 4), Square(3, 7))
 *          bishopTrajectory(Square(1, 3), Square(6, 8)) = listOf(Square(1, 3), Square(6, 8))
 * Если возможно несколько вариантов самой быстрой траектории, вернуть любой из них.
 */
fun diagonals(point: Square): Set<Square> {
    val diagonals = mutableSetOf<Square>()
    val column = point.column
    val row = point.row
    for (i in 1..8) {
        if (column + i <= 8 && row + i <= 8) diagonals.add(Square(column + i, row + i))
        if (column + i <= 8 && row - i >= 1) diagonals.add(Square(column + i, row - i))
        if (column - i >= 1 && row - i >= 1) diagonals.add(Square(column - i, row - i))
        if (column - i >= 1 && row + i <= 8) diagonals.add(Square(column - i, row + i))
    }
    return diagonals
}

fun bishopTrajectory(start: Square, end: Square): List<Square> {
    val numberOfMoves = bishopMoveNumber(start, end)
    return when (numberOfMoves) {
        0 -> listOf(start)
        -1 -> listOf()
        1 -> listOf(start, end)
        else -> {
            val result = mutableListOf(start)
            val setOfSquareStart = diagonals(start)
            val setOfSquareEnd = diagonals(end)
            val t = setOfSquareEnd.intersect(setOfSquareStart)
            for (element in t) {
                result.add(element)
                result.add(end)
                break
            }
            result
        }
    }
    /*return if (numberOfMoves == 0) listOf(start)
    else if (numberOfMoves == -1) listOf()
    else if (numberOfMoves == 1) listOf(start, end)
    else {
        val result = mutableListOf(start)
        val setOfSquareStart = diagonals(start)
        val setOfSquareEnd = diagonals(end)
        val t = setOfSquareEnd.intersect(setOfSquareStart)
        for (element in t) {
            result.add(element)
            result.add(end)
            break
        }
        result
    } */
}

fun main(args: Array<String>) {
    val x1x2 = bishopTrajectory(square("f1"), square("f7"))
    println("$x1x2")
}

/**
 * Средняя
 *
 * Определить число ходов, за которое шахматный король пройдёт из клетки start в клетку end.
 * Шахматный король одним ходом может переместиться из клетки, в которой стоит,
 * на любую соседнюю по вертикали, горизонтали или диагонали.
 * Ниже точками выделены возможные ходы короля, а крестиками -- невозможные:
 *
 * xxxxx
 * x...x
 * x.K.x
 * x...x
 * xxxxx
 *
 * Если клетки start и end совпадают, вернуть 0.
 * Если любая из клеток некорректна, бросить IllegalArgumentException().
 *
 * Пример: kingMoveNumber(Square(3, 1), Square(6, 3)) = 3.
 * Король может последовательно пройти через клетки (4, 2) и (5, 2) к клетке (6, 3).
 */
fun kingMoveNumber(start: Square, end: Square): Int {
    try {
        if (start.notation() == "" || end.notation() == "") throw IllegalArgumentException("error")
        else if (start == end) return 0
        else {
            when {
                bishopMoveNumber(start, end) == 1 -> return abs(start.row - end.row)
                rookMoveNumber(start, end) == 1 -> return if (start.row == end.row) abs(start.column - end.column)
                else abs(start.row - end.row)
                else -> {
                    var stepColumn = -1
                    var stepRow = -1
                    if (start.column < end.column) stepColumn = 1
                    if (start.row < end.row) stepRow = 1
                    var point = Square(start.column, start.row)
                    var i = 0
                    while (rookMoveNumber(point, end) == 2) {
                        var y = point.row
                        var x = point.column
                        x += stepColumn
                        y += stepRow
                        point = Square(x, y)
                        i++
                    }
                    return (i + abs(point.column - end.column) + abs(point.row - end.row))
                }
            }
        }
    } catch (e: IllegalArgumentException) {
        throw IllegalArgumentException("error")
    }
}


/**
 * Сложная
 *
 * Вернуть список из клеток, по которым шахматный король может быстрее всего попасть из клетки start в клетку end.
 * Описание ходов короля см. предыдущую задачу.
 * Список всегда включает в себя клетку start. Клетка end включается, если она не совпадает со start.
 * Между ними должны находиться промежуточные клетки, по порядку от start до end.
 * Примеры: kingTrajectory(Square(3, 3), Square(3, 3)) = listOf(Square(3, 3))
 *          (здесь возможны другие варианты)
 *          kingTrajectory(Square(3, 1), Square(6, 3)) = listOf(Square(3, 1), Square(4, 2), Square(5, 2), Square(6, 3))
 *          (здесь возможен единственный вариант)
 *          kingTrajectory(Square(3, 5), Square(6, 2)) = listOf(Square(3, 5), Square(4, 4), Square(5, 3), Square(6, 2))
 * Если возможно несколько вариантов самой быстрой траектории, вернуть любой из них.
 */
fun kingTrajectory(start: Square, end: Square): List<Square> {
    if (start == end) return listOf(start)
    else {
        if (bishopMoveNumber(start, end) == 1) {
            var stepColumn = -1
            var stepRow = -1
            if (start.column < end.column) stepColumn = 1
            if (start.row < end.row) stepRow = 1
            val result = mutableListOf<Square>()
            var square = start
            while (square != end) {
                result.add(square)
                val column = square.column
                val row = square.row
                square = Square(column + stepColumn, row + stepRow)
            }
            result.add(end)
            return result
        } else if (rookMoveNumber(start, end) == 1) {
            var stepColumn = 0
            var stepRow = 0
            if (start.row - end.row == 0) {
                if (start.column > end.column) stepColumn = -1
                else stepColumn = 1
            } else {
                if (start.row > end.row) stepRow = -1
                else stepRow = 1
            }
            val result = mutableListOf<Square>()
            var square = start
            while (square != end) {
                result.add(square)
                val column = square.column
                val row = square.row
                square = Square(column + stepColumn, row + stepRow)
            }
            result.add(end)
            return result
        } else {
            var stepColumn = 1
            var stepRow = 1
            if (start.column > end.column) stepColumn = -1
            if (start.row > end.row) stepRow = -1
            var square = start
            val result = mutableListOf<Square>()
            while (rookMoveNumber(square, end) == 2) {
                result.add(square)
                val column = square.column
                val row = square.row
                square = Square(column + stepColumn, row + stepRow)
            }
            stepColumn = 0
            stepRow = 0
            if (square.row - end.row == 0) {
                if (square.column > end.column) stepColumn = -1
                else stepColumn = 1
            } else {
                if (square.row > end.row) stepRow = -1
                else stepRow = 1
            }
            while (square != end) {
                result.add(square)
                val column = square.column
                val row = square.row
                square = Square(column + stepColumn, row + stepRow)
            }
            result.add(end)
            return result
        }
    }
    return listOf()
}

/**
 * Сложная
 *
 * Определить число ходов, за которое шахматный конь пройдёт из клетки start в клетку end.
 * Шахматный конь одним ходом вначале передвигается ровно на 2 клетки по горизонтали или вертикали,
 * а затем ещё на 1 клетку под прямым углом, образуя букву "Г".
 * Ниже точками выделены возможные ходы коня, а крестиками -- невозможные:
 *
 * .xxx.xxx
 * xxKxxxxx
 * .xxx.xxx
 * x.x.xxxx
 * xxxxxxxx
 * xxxxxxxx
 * xxxxxxxx
 * xxxxxxxx
 *
 * Если клетки start и end совпадают, вернуть 0.
 * Если любая из клеток некорректна, бросить IllegalArgumentException().
 *
 * Пример: knightMoveNumber(Square(3, 1), Square(6, 3)) = 3.
 * Конь может последовательно пройти через клетки (5, 2) и (4, 4) к клетке (6, 3).
 */
fun knightMoveNumber(start: Square, end: Square): Int = TODO()

/**
 * Очень сложная
 *
 * Вернуть список из клеток, по которым шахматный конь может быстрее всего попасть из клетки start в клетку end.
 * Описание ходов коня см. предыдущую задачу.
 * Список всегда включает в себя клетку start. Клетка end включается, если она не совпадает со start.
 * Между ними должны находиться промежуточные клетки, по порядку от start до end.
 * Примеры:
 *
 * knightTrajectory(Square(3, 3), Square(3, 3)) = listOf(Square(3, 3))
 * здесь возможны другие варианты)
 * knightTrajectory(Square(3, 1), Square(6, 3)) = listOf(Square(3, 1), Square(5, 2), Square(4, 4), Square(6, 3))
 * (здесь возможен единственный вариант)
 * knightTrajectory(Square(3, 5), Square(5, 6)) = listOf(Square(3, 5), Square(5, 6))
 * (здесь опять возможны другие варианты)
 * knightTrajectory(Square(7, 7), Square(8, 8)) =
 *     listOf(Square(7, 7), Square(5, 8), Square(4, 6), Square(6, 7), Square(8, 8))
 *
 * Если возможно несколько вариантов самой быстрой траектории, вернуть любой из них.
 */
fun knightTrajectory(start: Square, end: Square): List<Square> = TODO()
