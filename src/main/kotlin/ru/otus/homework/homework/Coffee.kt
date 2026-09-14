package ru.otus.homework.homework

/**
 * Базовый интерфейс кофейного напитка
 */
interface Coffee {
    /**
     * Цена кофейного напитка в копейках
     */
    fun cost(): Int

    /**
     * Описание кофейного напитка
     */
    fun description(): String
}

class SimpleCoffee : Coffee {
    override fun cost() = 200
    override fun description() = "Простой кофе"
}

class MilkDecorator(private val coffee: Coffee) : Coffee {
    override fun cost(): Int {
        return coffee.cost() + 50
    }

    override fun description(): String {
        return coffee.description() + ", молоко"
    }
}

class SugarDecorator(private val coffee: Coffee) : Coffee {
    override fun cost(): Int {
        return coffee.cost() + 20
    }

    override fun description(): String {
        return coffee.description() + ", сахар"
    }
}

class VanillaDecorator(private val coffee: Coffee) : Coffee {
    override fun cost(): Int {
        return coffee.cost() + 70
    }

    override fun description(): String {
        return coffee.description() + ", ваниль"
    }
}


/*
    Бизнес логика централизована в общем декораторе CoffeeDecorator2
    А конкретные реализации базового декоратора задают его значения
*/

abstract class CoffeeDecorator2( private val coffee: Coffee ) : Coffee
{
    protected abstract val extraCost: Int
    protected abstract val extraDescription: String

    override fun cost(): Int
    {
        return coffee.cost() + extraCost
    }

    override fun description(): String
    {
        return coffee.description() + ", " + extraDescription
    }
}

class MilkDecorator2(coffee: Coffee) : CoffeeDecorator2(coffee)
{
    override val extraCost = 50
    override val extraDescription = "молоко"
}

class SugarDecorator2(coffee: Coffee) : CoffeeDecorator2(coffee)
{
    override val extraCost = 20
    override val extraDescription = "сахар"
}

class VanillaDecorator2(coffee: Coffee) : CoffeeDecorator2(coffee)
{
    override val extraCost = 70
    override val extraDescription = "ваниль"
}