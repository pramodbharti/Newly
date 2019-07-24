package com.db.newly.data.sync

import com.db.newly.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class NetInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()

        builder.header("Authorization", BuildConfig.API_KEY)
        builder.addHeader("Content-Type", "application/json")

        return chain.proceed(builder.build())
    }

}