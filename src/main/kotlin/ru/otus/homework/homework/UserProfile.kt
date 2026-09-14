@file:Suppress("RemoveRedundantQualifierName")

package ru.otus.homework.homework

import kotlin.properties.Delegates.vetoable

/**
 * Профиль пользователя
 */
interface UserProfile {
    /**
     * Полное имя
     * Не должно принимать пустые строки
     */
    var fullName: String

    /**
     * Email.
     * Не должен принимать пустые и некорректные строки
     */
    var email: String

    /**
     * Профиль с логированием
     */
    interface Logging : UserProfile, WithLogging

    companion object {
        /**
         * Создает профиль пользователя
         */
        fun create(fullName: String, email: String): UserProfile {
            require(fullName.isNotBlank()) { "Full name should not be empty" }
            require(email.isNotBlank() && emailRegex.matches(email)) { "Invalid email" }
            return ProfileImplementation(fullName, email)
        }

        /**
         * Creates user profile with logging
         */
        fun createWithLogging(fullName: String, email: String): UserProfile.Logging {
            return LoggingUserProfile(create(fullName, email))
        }
    }
}

/**
 * Проверка емейла на корректность
 */
private val emailRegex = Regex("^[A-Za-z](.*)([@])(.+)(\\.)(.+)")

/**
 * Реализация простого [UserProfile].
 */
private class ProfileImplementation(fullName: String, email: String): UserProfile
{
    override var fullName: String by NonEmptyStringDelegate()
    override var email: String by vetoable(email) { _, _, newValue -> newValue.isNotBlank() && emailRegex.matches(newValue) }

    init {
        this.fullName = fullName
        this.email = email
    }
}

private class LoggingUserProfile(private val profile: UserProfile) : UserProfile.Logging, UserProfile by profile
{
    private val logs = mutableListOf<String>()

    override fun getLog(): List<String> = logs

    override var fullName: String
        get() = profile.fullName
        set(value)
        {
            val oldValue = profile.fullName
            profile.fullName = value

            if (profile.fullName != oldValue) {
                logs.add("Changing `fullName` from '$oldValue' to '$value'")
            }
        }

    override var email: String
        get() = profile.email
        set(value)
        {
            val oldValue = profile.email
            profile.email = value

            if (profile.email != oldValue) {
                logs.add("Changing `email` from '$oldValue' to '$value'")
            }
        }
}
