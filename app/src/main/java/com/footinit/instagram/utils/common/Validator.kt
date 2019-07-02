package com.footinit.instagram.utils.common

import com.footinit.instagram.R

object Validator {
    private const val MINIMUM_PASSWORD_LENGTH = 6

    fun validateLoginFields(email: String?, password: String?): List<Validation> =
        ArrayList<Validation>().apply {
            when {
                email.isNullOrBlank() ->
                    add(
                        Validation(
                            Validation.Field.EMAIL,
                            Resource.error(R.string.all_empty_email)
                        )
                    )
                !email.isValidEmail() ->
                    add(
                        Validation(
                            Validation.Field.EMAIL,
                            Resource.error(R.string.all_invalid_email)
                        )
                    )
                else ->
                    add(
                        Validation(
                            Validation.Field.EMAIL,
                            Resource.success()
                        )
                    )
            }
            when {
                password.isNullOrBlank() -> add(
                    Validation(
                        Validation.Field.PASSWORD,
                        Resource.error(R.string.all_empty_password)
                    )
                )
                password.length < MINIMUM_PASSWORD_LENGTH -> add(
                    Validation(
                        Validation.Field.PASSWORD,
                        Resource.error(R.string.all_invalid_password)
                    )
                )
                else -> add(
                    Validation(
                        Validation.Field.PASSWORD,
                        Resource.success()
                    )
                )
            }
        }

    fun validateSignUpFields(fullName: String?, email: String?, password: String?) =
        ArrayList<Validation>().apply {
            when {
                fullName.isNullOrBlank() ->
                    add(
                        Validation(
                            Validation.Field.FULLNAME,
                            Resource.error(R.string.all_empty_name)
                        )
                    )
                else ->
                    add(
                        Validation(
                            Validation.Field.FULLNAME,
                            Resource.success()
                        )
                    )
            }

            addAll(validateLoginFields(email, password))
        }

    fun validateProfileFields(name: String?, bio: String?) =
        ArrayList<Validation>().apply {
            when {
                name.isNullOrBlank() ->
                    add(
                        Validation(
                            Validation.Field.NAME,
                            Resource.error(R.string.all_empty_name)
                        )
                    )
                else ->
                    add(
                        Validation(
                            Validation.Field.NAME,
                            Resource.success()
                        )
                    )
            }

            add(
                Validation(
                    Validation.Field.BIO,
                    Resource.success()
                )
            )
        }
}

data class Validation(val field: Field, val resource: Resource<Int>) {
    enum class Field {
        EMAIL,
        PASSWORD,
        FULLNAME,
        NAME,
        BIO
    }
}