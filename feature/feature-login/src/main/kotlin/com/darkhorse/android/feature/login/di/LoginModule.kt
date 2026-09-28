package com.darkhorse.android.feature.login.di

import com.darkhorse.android.core.network.ApiFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LoginModule {

    @Provides
    @Singleton
    fun provideLoginApi(apiFactory: ApiFactory): LoginApi {
        return apiFactory.create(LoginApi::class.java)
    }
}
