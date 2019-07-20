package com.db.foody.dagger.component

import com.db.foody.dagger.module.ApiModule
import com.db.foody.dagger.module.AppModule
import com.db.foody.dagger.module.RecipeRepoModule
import com.db.foody.viewmodel.MainActivityViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class,ApiModule::class,RecipeRepoModule::class])
interface AppComponent {
    fun inject(mainActivityViewModel:MainActivityViewModel)
}