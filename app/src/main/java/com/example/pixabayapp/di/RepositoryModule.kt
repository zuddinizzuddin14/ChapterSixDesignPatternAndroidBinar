package com.example.pixabayapp.di

import com.example.pixabayapp.data.repository.MainRepository
import com.example.pixabayapp.data.service.ApiHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @ViewModelScoped
    @Provides
    fun provideRepository(apiHelper: ApiHelper) = MainRepository(apiHelper)

}
