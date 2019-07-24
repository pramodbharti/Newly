package com.db.newly.dagger.component

import com.db.newly.dagger.module.ApiModule
import com.db.newly.dagger.module.AppModule
import com.db.newly.dagger.module.ImageRepoModule
import com.db.newly.viewmodel.MainActivityViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class,ApiModule::class,ImageRepoModule::class])
interface AppComponent {
    fun inject(mainActivityViewModel:MainActivityViewModel)
}