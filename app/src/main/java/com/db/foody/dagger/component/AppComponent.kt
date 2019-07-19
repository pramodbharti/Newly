package com.db.foody.dagger.component

import com.db.foody.dagger.module.ApiModule
import com.db.foody.dagger.module.AppModule
import com.db.foody.dagger.module.RecipeRepoModule
import com.db.foody.viewmodel.MainActivityViewModel
import dagger.Component

@Component(modules = [ApiModule::class,AppModule::class,RecipeRepoModule::class])
interface AppComponent {
    fun inject(mainActivityViewModel:MainActivityViewModel)
}