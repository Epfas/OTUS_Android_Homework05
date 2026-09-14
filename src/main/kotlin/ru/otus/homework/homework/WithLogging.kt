package ru.otus.homework.homework

/**
 * Интерфейс для классов с логированием
 */
interface WithLogging {
    /**
     * Текущие записи журнала
     */
    fun getLog(): List<String>
}

