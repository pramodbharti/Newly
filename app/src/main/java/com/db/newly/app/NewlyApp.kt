package com.db.newly.app

import android.app.Application
import com.db.newly.dagger.component.AppComponent
import com.db.newly.dagger.component.DaggerAppComponent
import com.db.newly.dagger.module.AppModule

class NewlyApp : Application(){

    companion object{
        lateinit var component:AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        component = DaggerAppComponent
            .builder()
            .appModule(AppModule(this))
            .build()
    }
}