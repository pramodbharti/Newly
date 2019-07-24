package com.db.newly.data.sync

import com.db.newly.data.model.ImgurResponse
import io.reactivex.Maybe
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("gallery/search/time/{page}")
    fun getImages(
        @Path("page") page: Int?,
        @Query("q") query: String?
    ): Maybe<ImgurResponse>
}