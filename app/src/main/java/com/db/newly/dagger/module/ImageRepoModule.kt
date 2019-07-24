package com.db.newly.dagger.module

import com.db.newly.data.sync.ApiService
import com.db.newly.data.sync.ImagesRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ImageRepoModule {
    @Provides
    @Singleton
    internal fun provideImagesRepository(apiService: ApiService):ImagesRepository{
    return ImagesRepository(apiService)
    }
}