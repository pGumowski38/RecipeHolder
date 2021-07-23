package com.gumis.recipeholder.viewModels

import android.util.Patterns

class ValidationCheck {

    companion object {
        // A placeholder email validation check
        fun isEmailValid(username: String): Boolean {
            return if (username.contains("@")) {
                Patterns.EMAIL_ADDRESS.matcher(username).matches()
            } else {
                username.isNotBlank()
            }
        }

        // A placeholder password validation check
        fun isPasswordValid(password: String): Boolean {
            return password.length >= 5
        }

        fun arePasswordsSame(password: String, confirmPassword: String): Boolean {
            return password == confirmPassword
        }
    }
}