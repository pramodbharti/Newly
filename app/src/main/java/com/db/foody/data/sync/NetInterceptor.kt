package com.db.foody.data.sync

import com.db.foody.BuildConfig
import com.db.foody.util.KEY
import okhttp3.Interceptor
import okhttp3.Response

class NetInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val ongoing = chain
            .request()

        val url = ongoing
            .url()
            .newBuilder()
            .addQueryParameter(KEY, BuildConfig.API_KEY)
            .build()

        val requestBuilder = ongoing
            .newBuilder()
            .url(url)
            .addHeader("Content-Type", "application/json")

        print(requestBuilder.toString())

        return chain.proceed(requestBuilder.build())
    }

}