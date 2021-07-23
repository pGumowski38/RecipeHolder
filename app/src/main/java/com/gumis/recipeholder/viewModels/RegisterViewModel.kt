package com.gumis.recipeholder.viewModels

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gumis.recipeholder.data.RecipesRepository
import com.gumis.recipeholder.data.User
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class RegisterViewModel(private val repository: RecipesRepository) : ViewModel() {

    suspend fun register(newUser: User, cont: Context): Boolean {
        var success = false
        val job: Job = viewModelScope.launch {
            success = repository.register(newUser)
            onResult(success, cont)
        }
        job.join()
        return success
    }

    private fun onResult(regSuccess: Boolean, cont: Context) {
        if (regSuccess) {
            Toast.makeText(cont, "Registered successfully", Toast.LENGTH_LONG).show()
        }
        else {
            Toast.makeText(cont, "This email is already registered", Toast.LENGTH_LONG).show()
        }
    }

}