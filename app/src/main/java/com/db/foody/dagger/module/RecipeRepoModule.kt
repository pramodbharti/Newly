package com.db.foody.dagger.module

import com.db.foody.data.sync.ApiService
import com.db.foody.data.sync.RecipesRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RecipeRepoModule {
    @Provides
    @Singleton
    internal fun provideRecipesRepository(apiService: ApiService):RecipesRepository{
    return RecipesRepository(apiService)
    }
}