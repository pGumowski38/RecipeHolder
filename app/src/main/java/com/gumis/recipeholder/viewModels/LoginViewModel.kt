package com.gumis.recipeholder.viewModels

import androidx.lifecycle.ViewModel
import com.gumis.recipeholder.data.RecipesRepository

class LoginViewModel(private val repository: RecipesRepository) : ViewModel() {

    suspend fun login(email: String, password: String): Boolean {
        val user = repository.login(email, password)
        return user != null
    }

}