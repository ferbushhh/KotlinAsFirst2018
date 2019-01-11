@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson7.task1

import java.awt.SystemColor.text
import java.io.File

/**
 * Пример
 *
 * Во входном файле с именем inputName содержится некоторый текст.
 * Вывести его в выходной файл с именем outputName, выровняв по левому краю,
 * чтобы длина каждой строки не превосходила lineLength.
 * Слова в слишком длинных строках следует переносить на следующую строку.
 * Слишком короткие строки следует дополнять словами из следующей строки.
 * Пустые строки во входном файле обозначают конец абзаца,
 * их следует сохранить и в выходном файле
 */
fun alignFile(inputName: String, lineLength: Int, outputName: String) {
    val outputStream = File(outputName).bufferedWriter()
    var currentLineLength = 0
    for (line in File(inputName).readLines()) {
        if (line.isEmpty()) {
            outputStream.newLine()
            if (currentLineLength > 0) {
                outputStream.newLine()
                currentLineLength = 0
            }
            continue
        }
        for (word in line.split(" ")) {
            if (currentLineLength > 0) {
                if (word.length + currentLineLength >= lineLength) {
                    outputStream.newLine()
                    currentLineLength = 0
                } else {
                    outputStream.write(" ")
                    currentLineLength++
                }
            }
            outputStream.write(word)
            currentLineLength += word.length
        }
    }
    outputStream.close()
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName содержится некоторый текст.
 * На вход подаётся список строк substrings.
 * Вернуть ассоциативный массив с числом вхождений каждой из строк в текст.
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 *
 */
fun countSubstrings(inputName: String, substrings: List<String>): Map<String, Int> {
    val result = mutableMapOf<String, Int>()
    for (element in substrings)
        result[element] = 0

    val inputName2 = File(inputName).readText()
    val str = inputName2.toUpperCase()

    for (word in substrings) {
        val newWord = word.toUpperCase()
        var answer = Regex(newWord).find(str, 0)
        while (answer != null) {
            result[word] = result[word]!! + 1
            var assistive: Int
            if (word.length == 1 || word.length == 2) {
                assistive = answer.range.start + 1
            } else {
                assistive = answer.range.start + word.length - 1
            }
            answer = Regex(newWord).find(str, assistive)
        }
    }
    return result
}


/**
 * Средняя
 *
 * В русском языке, как правило, после букв Ж, Ч, Ш, Щ пишется И, А, У, а не Ы, Я, Ю.
 * Во входном файле с именем inputName содержится некоторый текст на русском языке.
 * Проверить текст во входном файле на соблюдение данного правила и вывести в выходной
 * файл outputName текст с исправленными ошибками.
 *
 * Регистр заменённых букв следует сохранять.
 *
 * Исключения (жюри, брошюра, парашют) в рамках данного задания обрабатывать не нужно
 *
 */

fun sibilants(inputName: String, outputName: String) {
    val map = mapOf(
            'ы' to "и",
            'я' to "а",
            'ю' to "у",
            'Ы' to "И",
            'Я' to "А",
            'Ю' to "У"
    )
    val letters = listOf('ж', 'ч', 'щ', 'ш')
    val writer = File(outputName).bufferedWriter()
    var sim = ' '
    for (char in File(inputName).readText()) {
        if (map.containsKey(char) && sim in letters) {
            writer.write(map[char])
        } else writer.write(char.toString())
        sim = char.toLowerCase()
    }
    writer.close()
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 * Вывести его в выходной файл с именем outputName, выровняв по центру
 * относительно самой длинной строки.
 *
 * Выравнивание следует производить путём добавления пробелов в начало строки.
 *
 *
 * Следующие правила должны быть выполнены:
 * 1) Пробелы в начале и в конце всех строк не следует сохранять.
 * 2) В случае невозможности выравнивания строго по центру, строка должна быть сдвинута в ЛЕВУЮ сторону
 * 3) Пустые строки не являются особым случаем, их тоже следует выравнивать
 * 4) Число строк в выходном файле должно быть равно числу строк во входном (в т. ч. пустых)
 *
 */
fun centerFile(inputName: String, outputName: String) {
    TODO()
}

/**
 * Сложная
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 * Вывести его в выходной файл с именем outputName, выровняв по левому и правому краю относительно
 * самой длинной строки.
 * Выравнивание производить, вставляя дополнительные пробелы между словами: равномерно по всей строке
 *
 * Слова внутри строки отделяются друг от друга одним или более пробелом.
 *
 * Следующие правила должны быть выполнены:
 * 1) Каждая строка входного и выходного файла не должна начинаться или заканчиваться пробелом.
 * 2) Пустые строки или строки из пробелов трансформируются в пустые строки без пробелов.
 * 3) Строки из одного слова выводятся без пробелов.
 * 4) Число строк в выходном файле должно быть равно числу строк во входном (в т. ч. пустых).
 *
 * Равномерность определяется следующими формальными правилами:
 * 5) Число пробелов между каждыми двумя парами соседних слов не должно отличаться более, чем на 1.
 * 6) Число пробелов между более левой парой соседних слов должно быть больше или равно числу пробелов
 *    между более правой парой соседних слов.
 *
 * Следует учесть, что входной файл может содержать последовательности из нескольких пробелов  между слвоами. Такие
 * последовательности следует учитывать при выравнивании и при необходимости избавляться от лишних пробелов.
 * Из этого следуют следующие правила:
 * 7) В самой длинной строке каждая пара соседних слов должна быть отделена В ТОЧНОСТИ одним пробелом
 * 8) Если входной файл удовлетворяет требованиям 1-7, то он должен быть в точности идентичен выходному файлу
 */
fun alignFileByWidth(inputName: String, outputName: String) {
    TODO()
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 *
 * Вернуть ассоциативный массив, содержащий 20 наиболее часто встречающихся слов с их количеством.
 * Если в тексте менее 20 различных слов, вернуть все слова.
 *
 * Словом считается непрерывная последовательность из букв (кириллических,
 * либо латинских, без знаков препинания и цифр).
 * Цифры, пробелы, знаки препинания считаются разделителями слов:
 * Привет, привет42, привет!!! -привет?!
 * ^ В этой строчке слово привет встречается 4 раза.
 *
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 * Ключи в ассоциативном массиве должны быть в нижнем регистре.
 *
 */
fun top20Words(inputName: String): Map<String, Int> = TODO()

/**
 * Средняя
 *
 * Реализовать транслитерацию текста из входного файла в выходной файл посредством динамически задаваемых правил.

 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 *
 * В ассоциативном массиве dictionary содержится словарь, в котором некоторым символам
 * ставится в соответствие строчка из символов, например
 * mapOf('з' to "zz", 'р' to "r", 'д' to "d", 'й' to "y", 'М' to "m", 'и' to "yy", '!' to "!!!")
 *
 * Необходимо вывести в итоговый файл с именем outputName
 * содержимое текста с заменой всех символов из словаря на соответствующие им строки.
 *
 * При этом регистр символов в словаре должен игнорироваться,
 * но при выводе символ в верхнем регистре отображается в строку, начинающуюся с символа в верхнем регистре.
 *
 * Пример.
 * Входной текст: Здравствуй, мир!
 *
 * заменяется на
 *
 * Выходной текст: Zzdrавствуy, mир!!!
 *
 * Пример 2.
 *
 * Входной текст: Здравствуй, мир!
 * Словарь: mapOf('з' to "zZ", 'р' to "r", 'д' to "d", 'й' to "y", 'М' to "m", 'и' to "YY", '!' to "!!!")
 *
 * заменяется на
 *
 * Выходной текст: Zzdrавствуy, mир!!!
 *
 * Обратите внимание: данная функция не имеет возвращаемого значения
 */
fun transliterate(inputName: String, dictionary: Map<Char, String>, outputName: String) {
    val writer = File(outputName).bufferedWriter()
    val dic = mutableMapOf<Char, String>()
    for ((key, value) in dictionary) {
        dic[key.toLowerCase()] = value.toLowerCase()
    }
    for (ch in File(inputName).readText()) {
        if (ch.toLowerCase() in dic) {
            if (ch.toLowerCase() == ch)
                writer.write(dic[ch])
            else {
                writer.write(dic[ch.toLowerCase()]!!.capitalize())
            }
        } else writer.write(ch.toString())
    }
    writer.close()
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName имеется словарь с одним словом в каждой строчке.
 * Выбрать из данного словаря наиболее длинное слово,
 * в котором все буквы разные, например: Неряшливость, Четырёхдюймовка.
 * Вывести его в выходной файл с именем outputName.
 * Если во входном файле имеется несколько слов с одинаковой длиной, в которых все буквы разные,
 * в выходной файл следует вывести их все через запятую.
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 *
 * Пример входного файла:
 * Карминовый
 * Боязливый
 * Некрасивый
 * Остроумный
 * БелогЛазый
 * ФиолетОвый

 * Соответствующий выходной файл:
 * Карминовый, Некрасивый
 *
 * Обратите внимание: данная функция не имеет возвращаемого значения
 */
fun chooseLongestChaoticWord(inputName: String, outputName: String) {
    TODO()
}

/**
 * Сложная
 *
 * Реализовать транслитерацию текста в заданном формате разметки в формат разметки HTML.
 *
 * Во входном файле с именем inputName содержится текст, содержащий в себе элементы текстовой разметки следующих типов:
 * - *текст в курсивном начертании* -- курсив
 * - **текст в полужирном начертании** -- полужирный
 * - ~~зачёркнутый текст~~ -- зачёркивание
 *
 * Следует вывести в выходной файл этот же текст в формате HTML:
 * - <i>текст в курсивном начертании</i>
 * - <b>текст в полужирном начертании</b>
 * - <s>зачёркнутый текст</s>
 *
 * Кроме того, все абзацы исходного текста, отделённые друг от друга пустыми строками, следует обернуть в теги <p>...</p>,
 * а весь текст целиком в теги <html><body>...</body></html>.
 *
 * Все остальные части исходного текста должны остаться неизменными с точностью до наборов пробелов и переносов строк.
 * Отдельно следует заметить, что открывающая последовательность из трёх звёздочек (***) должна трактоваться как "<b><i>"
 * и никак иначе.
 *
 * Пример входного файла:
Lorem ipsum *dolor sit amet*, consectetur **adipiscing** elit.
Vestibulum lobortis, ~~Est vehicula rutrum *suscipit*~~, ipsum ~~lib~~ero *placerat **tortor***,

Suspendisse ~~et elit in enim tempus iaculis~~.
 *
 * Соответствующий выходной файл:
<html>
<body>
<p>
Lorem ipsum <i>dolor sit amet</i>, consectetur <b>adipiscing</b> elit.
Vestibulum lobortis. <s>Est vehicula rutrum <i>suscipit</i></s>, ipsum <s>lib</s>ero <i>placerat <b>tortor</b></i>.
</p>
<p>
Suspendisse <s>et elit in enim tempus iaculis</s>.
</p>
</body>
</html>
 *
 * (Отступы и переносы строк в примере добавлены для наглядности, при решении задачи их реализовывать не обязательно)
 */
/*fun markdownToHtmlSimple(inputName: String, outputName: String) {
    val outputStream = File(outputName).bufferedWriter()
    outputStream.write("<html><body><p>")
    var flag1 = false
    var flag2 = false
    var flag3 = false
    var pastCh = ' '
    var pastPastCh = ' '
    var fl = 0
    var print = 2
    var howP = 4
    for (line in File(inputName).readLines()) {
        if (!line.isEmpty()) {
            for (ch in line) {
                if (howP == 2) outputStream.write("</p><p>")
                when {
                    pastCh == '~' && pastPastCh == '~' -> {
                        if (!flag3) {
                            flag3 = true
                            outputStream.write("<s>")
                        } else {
                            flag3 = false
                            outputStream.write("</s>")
                        }
                        print++
                    }
                    pastPastCh != '*' && print == 0 -> {
                        outputStream.write(pastPastCh.toString())
                    }
                    print != 0 -> print--
                    pastCh != '*' -> {
                        if (!flag1) {
                            flag1 = true
                            outputStream.write("<i>")
                            fl = 1
                        } else {
                            flag1 = false
                            outputStream.write("</i>")
                        }
                    }
                    ch != '*' -> {
                        if (!flag2) {
                            flag2 = true
                            outputStream.write("<b>")
                            fl = 2
                        } else {
                            flag2 = false
                            outputStream.write("</b>")
                        }
                        print++
                    }
                    else -> {
                        when (fl) {
                            1 -> {
                                outputStream.write("</i>")
                                flag1 = false
                                fl = 0
                            }
                            2 -> {
                                outputStream.write("</b>")
                                flag2 = false
                                fl = 0
                                print++
                            }
                            else -> {
                                outputStream.write("<b>")
                                flag2 = true
                                fl = 2
                                print++
                            }
                        }
                    }

                }
                pastPastCh = pastCh
                pastCh = ch
                howP++
            }
        } else howP = 0
    }
    when {
        pastCh == '~' && pastPastCh == '~' -> {
            if (!flag3) {
                outputStream.write("<s>")
            } else {
                outputStream.write("</s>")
            }
        }
        pastPastCh != '*' -> {
            if (pastCh != '*') {
                if (print == 0)
                    outputStream.write(pastPastCh.toString())
                outputStream.write(pastCh.toString())
            } else {
                if (!flag1) {
                    outputStream.write("<i>")
                } else {
                    outputStream.write("</i>")
                }
            }
        }
        pastCh != '*' -> {
            if (!flag1) {
                outputStream.write("<i>")
            } else {
                outputStream.write("</i>")
            }
        }
        else -> {
            if (!flag2) {
                outputStream.write("<b>")
            } else {
                outputStream.write("</b>")
            }
        }
    }
    outputStream.write("</p></body></html>")
    outputStream.close()
}*/

/*fun markdownToHtmlSimple(inputName: String, outputName: String) {
    val outputStream = File(outputName).bufferedWriter()
    outputStream.write("<html><body><p>")
    var flag1 = false
    var flag2 = false
    var flagS = false
    var depth = 0
    var string1 = StringBuilder()
    var string2 = StringBuilder()
    var string3 = StringBuilder()
    var masFlag = mutableListOf<Char>()
    var pastCh = ' '
    var pastPastCh = ' '
    var fl = 0
    var print = 2
    var howP = 4
    for (line in File(inputName).readLines()) {
        if (!line.isEmpty()) {
            for (ch in line) {
                if (howP == 2) outputStream.write("</p><p>")
                when {
                    pastCh == '~' && pastPastCh == '~' -> {
                        if (!flagS) {
                            flagS = true
                            depth++
                            masFlag[depth] = 's'

                        } else {
                            flagS = false
                            val index = masFlag.indexOf('s')
                            if (index == depth) {
                                when (index) {
                                    1 -> {
                                        outputStream.write("<s>")
                                        outputStream.write(string1.toString())
                                        outputStream.write("</s>")
                                        string1.removeRange(0, string1.length)
                                    }
                                    2 -> {
                                        string1.append("<s>")
                                        string1.append(string2)
                                        string1.append("</s>")
                                        string2.removeRange(0, string2.length)
                                    }
                                    3 -> {
                                        string2.append("<s>")
                                        string2.append(string3)
                                        string2.append("</s>")
                                        string3.removeRange(0, string3.length)
                                    }
                                }
                                depth--
                            } else {
                                if (index == 1) {
                                    if (depth == 2) {
                                        var t = masFlag[2]
                                        string1.append("<")
                                        string1.append(t.toString())
                                        string1.append(">")
                                        string1.append(string2)
                                        string1.append("</")
                                        string1.append(t.toString())
                                        string1.append(">")
                                        string2.removeRange(0, string2.length)
                                        outputStream.write("<s>")
                                        outputStream.write(string1.toString())
                                        outputStream.write("</s>")
                                        string1.removeRange(0, string1.length)
                                        depth--
                                        masFlag[depth] = t
                                    } else {
                                        val t3 = masFlag[3]
                                        val t2 = masFlag[2]
                                    }
                                }
                            }
                        }
                        print++
                    }
                    pastPastCh != '*' && print == 0 -> {
                        if (depth == 0) outputStream.write(pastPastCh.toString())
                        else {
                            if (masFlag[depth] == 's') stringS.append(ch)
                            if (masFlag[depth] == 'b') stringB.append(ch)
                            if (masFlag[depth] == 'i') stringI.append(ch)
                        }
                    }
                    print != 0 -> print--
                    pastCh != '*' -> {
                        if (!flag1) {
                            flag1 = true
                            outputStream.write("<i>")
                            fl = 1
                        } else {
                            flag1 = false
                            outputStream.write("</i>")
                        }
                    }
                    ch != '*' -> {
                        if (!flag2) {
                            flag2 = true
                            outputStream.write("<b>")
                            fl = 2
                        } else {
                            flag2 = false
                            outputStream.write("</b>")
                        }
                        print++
                    }
                    else -> {
                        when (fl) {
                            1 -> {
                                outputStream.write("</i>")
                                flag1 = false
                                fl = 0
                            }
                            2 -> {
                                outputStream.write("</b>")
                                flag2 = false
                                fl = 0
                                print++
                            }
                            else -> {
                                outputStream.write("<b>")
                                flag2 = true
                                fl = 2
                                print++
                            }
                        }
                    }

                }
                pastPastCh = pastCh
                pastCh = ch
                howP++
            }
        } else howP = 0
    }
}

fun main(args: Array<String>) {
    val x1x2 = markdownToHtmlSimple("input/markdown_simple.md", "temp.html")
    println("Root product: $x1x2")
}*/

/**
 * Сложная
 *
 * Реализовать транслитерацию текста в заданном формате разметки в формат разметки HTML.
 *
 * Во входном файле с именем inputName содержится текст, содержащий в себе набор вложенных друг в друга списков.
 * Списки бывают двух типов: нумерованные и ненумерованные.
 *
 * Каждый элемент ненумерованного списка начинается с новой строки и символа '*', каждый элемент нумерованного списка --
 * с новой строки, числа и точки. Каждый элемент вложенного списка начинается с отступа из пробелов, на 4 пробела большего,
 * чем список-родитель. Максимально глубина вложенности списков может достигать 6. "Верхние" списки файла начинются
 * прямо с начала строки.
 *
 * Следует вывести этот же текст в выходной файл в формате HTML:
 * Нумерованный список:
 * <ol>
 *     <li>Раз</li>
 *     <li>Два</li>
 *     <li>Три</li>
 * </ol>
 *
 * Ненумерованный список:
 * <ul>
 *     <li>Раз</li>
 *     <li>Два</li>
 *     <li>Три</li>
 * </ul>
 *
 * Кроме того, весь текст целиком следует обернуть в теги <html><body>...</body></html>
 *
 * Все остальные части исходного текста должны остаться неизменными с точностью до наборов пробелов и переносов строк.
 *
 * Пример входного файла:
///////////////////////////////начало файла/////////////////////////////////////////////////////////////////////////////
 * Утка по-пекински
 * Утка
 * Соус
 * Салат Оливье
1. Мясо
 * Или колбаса
2. Майонез
3. Картофель
4. Что-то там ещё
 * Помидоры
 * Фрукты
1. Бананы
23. Яблоки
1. Красные
2. Зелёные
///////////////////////////////конец файла//////////////////////////////////////////////////////////////////////////////
 *
 *
 * Соответствующий выходной файл:
///////////////////////////////начало файла/////////////////////////////////////////////////////////////////////////////
<html>
<body>
<ul>
<li>
Утка по-пекински
<ul>
<li>Утка</li>
<li>Соус</li>
</ul>
</li>
<li>
Салат Оливье
<ol>
<li>Мясо
<ul>
<li>
Или колбаса
</li>
</ul>
</li>
<li>Майонез</li>
<li>Картофель</li>
<li>Что-то там ещё</li>
</ol>
</li>
<li>Помидоры</li>
<li>
Яблоки
<ol>
<li>Красные</li>
<li>Зелёные</li>
</ol>
</li>
</ul>
</body>
</html>
///////////////////////////////конец файла//////////////////////////////////////////////////////////////////////////////
 * (Отступы и переносы строк в примере добавлены для наглядности, при решении задачи их реализовывать не обязательно)
 */
fun markdownToHtmlLists(inputName: String, outputName: String) {
    TODO()
}

/**
 * Очень сложная
 *
 * Реализовать преобразования из двух предыдущих задач одновременно над одним и тем же файлом.
 * Следует помнить, что:
 * - Списки, отделённые друг от друга пустой строкой, являются разными и должны оказаться в разных параграфах выходного файла.
 *
 */
fun markdownToHtml(inputName: String, outputName: String) {
    TODO()
}

/**
 * Средняя
 *
 * Вывести в выходной файл процесс умножения столбиком числа lhv (> 0) на число rhv (> 0).
 *
 * Пример (для lhv == 19935, rhv == 111):
19935
 *    111
--------
19935
+ 19935
+19935
--------
2212785
 * Используемые пробелы, отступы и дефисы должны в точности соответствовать примеру.
 * Нули в множителе обрабатывать так же, как и остальные цифры:
235
 *  10
-----
0
+235
-----
2350
 *
 */
fun printMultiplicationProcess(lhv: Int, rhv: Int, outputName: String) {
    TODO()
}


/**
 * Сложная
 *
 * Вывести в выходной файл процесс деления столбиком числа lhv (> 0) на число rhv (> 0).
 *
 * Пример (для lhv == 19935, rhv == 22):
19935 | 22
-198     906
----
13
-0
--
135
-132
----
3

 * Используемые пробелы, отступы и дефисы должны в точности соответствовать примеру.
 *
 */
fun printDivisionProcess(lhv: Int, rhv: Int, outputName: String) {
    TODO()
}

