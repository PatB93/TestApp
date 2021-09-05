package com.example.testapplication.modules

import com.example.data.modules.Environment
import com.example.testapplication.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun provideEnvironment(): Environment {
        return Environment(BuildConfig.FLICKR_KEY, BuildConfig.BASE_FLICKR_URL, BuildConfig.FLICKR_SHORT_URL)
    }
}