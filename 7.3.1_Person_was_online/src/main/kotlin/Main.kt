package ru.netology

/**
 * Задача №1. Когда собеседник был онлайн
 * Используя when, напишите функцию agoToText, которая учитывает, сколько времени прошло с последнего
 * визита пользователя, и выдаёт в консоль результат проверки в следующем виде:
 * «был(а) только что», «был(а) 3 часа назад» и т. п.
 */

fun main() {
    val persons = listOf<Person>(
        Person("Alex", 15),
        Person("Vasya", 600),
        Person("Eugen", 1_500),
        Person("Oleg", 10_000),
        Person("Arkadiy", 150_000),
        Person("CrazyFrog", 370_000),
    )

    // Обрабатываем список и выводим статусы построчно
    persons.forEach { person -> println(agoToText(person)) }

}

fun agoToText(person: Person): String {
    val result = when {
        person.seconds < 61 -> "${person.name} был(а) только что"
        person.seconds < 60 * 60 + 1 -> "${person.name} был(а) ${correctWordFormatMinutes(person.seconds/60)}"
        person.seconds < 3600 * 24 + 1 -> "${person.name} был(а) ${correctWordFormatHours(person.seconds/3600)}"
        person.seconds < 3600 * 24 * 2 + 1 -> "${person.name} был(а) вчера"
        person.seconds < 3600 * 24 * 3 + 1 -> "${person.name} был(а) позавчера"
        else -> "${person.name} был(а) давно"
    }
    return result
}
// ========================================================================
// auxiliary function
// ========================================================================

// Выбираем правильную форму слов «минута»
//1 / 21 / 31 / 41 / 51 минуту назад
//2 / 22 / 32 / 42 / 52 минуты назад (то же самое для 3 и 4)
//5 / 25 / 35 / 45 / 55 минут назад (то же самое для 6-9, но помните, что с 60 минут результат выводится уже в часах)
//11 / 12 / 13 / 14 минут назад.
// Пример. Условие minute % 10 == 1 отбирает числа, оканчивающиеся на 1. Аналогично для 2, 3, 4
// Пример. Дополнительные условия minute % 100 != 11...14, исключает числа, оканчивающиеся с 11...14
fun correctWordFormatMinutes (minute: Int): String {
    val word = when {
        minute % 10 == 1 && minute % 100 != 11 -> "$minute минуту назад"
        minute % 10 in 2..4 && minute % 100 !in 12..14 -> "$minute минуты назад"
        else -> "$minute минут назад"
    }
    return word
}

// Выбираем правильную форму слов «часы»
// 1, 21 час назад
// 2, 3, 4, 22, 23, 24 часа назад
// 5-20 часов назад.
// Пример. Условие hour % 10 == 1 отбирает числа, оканчивающиеся на 1. Аналогично для 2, 3, 4
// Пример. Дополнительное условие hour % 100 != 11...14 исключает часы, от 11 до 4, так как для них должно быть "часов"
fun correctWordFormatHours (hour: Int): String {
    val word = when {
        hour % 10 == 1 && hour % 100 != 11 -> "${hour} час назад"
        hour % 10 in 2..4 && hour % 100 !in 12..14 -> "${hour} часа назад"
        else -> "${hour} часов назад"
    }
    return word
}

data class Person(
    val name: String,
    val seconds: Int,
)