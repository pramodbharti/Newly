package com.db.foody.app

import android.app.Application
import com.db.foody.dagger.component.AppComponent
import com.db.foody.dagger.component.DaggerAppComponent
import com.db.foody.dagger.module.AppModule

class FoodyApplication : Application(){

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