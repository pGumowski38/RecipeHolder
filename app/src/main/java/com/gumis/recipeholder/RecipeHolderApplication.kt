package com.gumis.recipeholder

import android.app.Application
import com.gumis.recipeholder.data.AppDatabase
import com.gumis.recipeholder.data.RecipesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class RecipeHolderApplication: Application() {
    val applicationScope = CoroutineScope(SupervisorJob())

    val database by lazy { AppDatabase.getInstance(this, applicationScope) }
    val recipesRepository by lazy { RecipesRepository(database.userDao()) }
}