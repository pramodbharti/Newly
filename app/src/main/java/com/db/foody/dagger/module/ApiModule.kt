package com.db.foody.dagger.module

import com.db.foody.BuildConfig
import com.db.foody.data.sync.NetInterceptor
import com.db.foody.data.sync.ApiService
import com.db.foody.util.BASE_URL
import com.db.foody.util.OKHTTP_TIMEOUT
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

class ApiModule {

    @Singleton
    @Provides
    internal fun provideInterceptor(): NetInterceptor {
        return NetInterceptor()
    }

    @Singleton
    @Provides
    internal fun provideHttpClient(interceptor: NetInterceptor):OkHttpClient{
        val okHttpClient = OkHttpClient.Builder()
            .callTimeout(OKHTTP_TIMEOUT,TimeUnit.SECONDS)
            .writeTimeout(OKHTTP_TIMEOUT,TimeUnit.SECONDS)
            .readTimeout(OKHTTP_TIMEOUT,TimeUnit.SECONDS)
            .addInterceptor(interceptor)

        try {
            if(BuildConfig.DEBUG){
                val loggingInterceptor = okhttp3.logging.HttpLoggingInterceptor()
                loggingInterceptor.level = okhttp3.logging.HttpLoggingInterceptor.Level.BODY
                okHttpClient.addInterceptor(loggingInterceptor)
            }
        }catch (e: Exception){
            e.printStackTrace()
        }

        return okHttpClient.build()
    }

    @Provides
    internal fun provideApiService(okHttpClient: OkHttpClient,interceptor: NetInterceptor): ApiService {

        val client = okHttpClient.newBuilder()
            .addInterceptor(interceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client)
            .build()
        return retrofit.create(ApiService::class.java)
    }
}