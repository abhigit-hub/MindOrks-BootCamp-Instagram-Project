package com.footinit.instagram.utils

import com.footinit.instagram.commons.Resource
import com.footinit.instagram.commons.Status
import com.footinit.instagram.extension.isValidEmail
import com.footinit.instagram.extension.isValidName

object Validator {
    private const val MINIMUM_PASSWORD_LENGTH = 6

    fun validateLoginFields(email: String, password: String): List<Validation> =
        listOf(
            email.isValidEmail().let {
                return@let if (it) Validation(Validation.Field.EMAIL, Resource(Status.SUCCESS))
                else Validation(Validation.Field.EMAIL, Resource(Status.ERROR))
            },
            (!password.isBlank() && password.length >= MINIMUM_PASSWORD_LENGTH).let {
                return@let if (it) Validation(Validation.Field.PASSWORD, Resource(Status.SUCCESS))
                else Validation(Validation.Field.PASSWORD, Resource(Status.ERROR))
            }
        )

    fun validateSignUpFields(fullName: String, email: String, password: String): List<Validation> {
        val list: MutableList<Validation> = mutableListOf()
        list.addAll(validateLoginFields(email, password))
        list.add(fullName.isValidName().let {
            return@let if (it) Validation(Validation.Field.FULLNAME, Resource(Status.SUCCESS))
            else Validation(Validation.Field.FULLNAME, Resource(Status.ERROR))
        })
        return list
    }
}

data class Validation(val field: Field, val resource: Resource<Any>) {
    enum class Field {
        EMAIL,
        PASSWORD,
        FULLNAME
    }
}