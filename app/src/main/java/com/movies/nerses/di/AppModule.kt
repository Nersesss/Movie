package com.movies.nerses.di

import com.movies.nerses.BuildConfig
import com.movies.nerses.api.ApiHelper
import com.movies.nerses.api.ApiHelperImpl
import com.movies.nerses.api.ApiService
import com.movies.nerses.repository.MoviesRepository
import com.movies.nerses.viewmodel.MoviesViewModel
import okhttp3.OkHttpClient
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    single { provideRetrofit() }
    single { provideApiService(get()) }
}


val viewModelModule = module {
    viewModel { MoviesViewModel(get()) }
}

val repoModule = module {
    single {
        MoviesRepository(get())
    }

    single<ApiHelper> {
        return@single ApiHelperImpl(get())
    }
}


private fun provideOkHttpClient() = if (BuildConfig.DEBUG) {
    OkHttpClient.Builder()
        .build()
} else OkHttpClient
    .Builder()
    .build()

private fun provideRetrofit(
): Retrofit =
    Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BuildConfig.BASE_URL)
        .client(provideOkHttpClient())
        .build()

private fun provideApiService(retrofit: Retrofit): ApiService =
    retrofit.create(ApiService::class.java)

