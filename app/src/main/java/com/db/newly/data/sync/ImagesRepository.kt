package com.db.newly.data.sync

import android.annotation.SuppressLint
import com.db.newly.data.model.ImgurResponse
import com.db.newly.extensions.maybe
import io.reactivex.Maybe
import io.reactivex.schedulers.Schedulers

class ImagesRepository(private val apiService: ApiService) {
    private var cache = ImageCache()

    fun getImages(page: Int?=null, search: String?=null): Maybe<ImgurResponse> {
        return Maybe
            .concat(
                cache.getPageOfImages(page,search)
                    .subscribeOn(Schedulers.io()),
                apiService.getImages(page,search)
                    .doOnSuccess {
                        cache.update(page,search, it)
                    }.subscribeOn(Schedulers.io())
            )
            .firstElement()
    }


    class ImageCache {
        @SuppressLint("UseSparseArrays")
        private var cachedImages = HashMap<Int, ImgurResponse>()

        var searchQuery = ""

        private fun clear() {
            cachedImages.clear()
        }

        fun getPageOfImages(page: Int?=null, search: String?=null): Maybe<ImgurResponse> {
            if (searchQuery != search)
                clear()
            searchQuery = search!!
            if (cachedImages.keys.contains(page))
                return maybe(cachedImages[page])
            return Maybe.empty()
        }

        fun update(page: Int?=null, search: String?=null, data: ImgurResponse) {
            if (searchQuery == search)
                cachedImages[page!!] = data
        }
    }
}