package com.florianschoeberl.hiringfs.di.app

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import at.allaboutapps.retrofit.converter.unwrap.UnwrapConverterFactory
import com.florianschoeberl.hiringfs.BuildConfig
import com.florianschoeberl.hiringfs.di.viewmodel.ViewModelModule
import com.florianschoeberl.hiringfs.model.ClubDB
import com.florianschoeberl.hiringfs.model.ClubRepo
import com.florianschoeberl.hiringfs.networking.UserAgentInterceptor
import com.florianschoeberl.hiringfs.networking.services.ApiService
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.Reusable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.*
import javax.inject.Singleton

@Module(
        includes = [
            ViewModelModule::class,
            AppModule.Bindings::class
        ]
)
class AppModule {

    @Module
    interface Bindings {
        @Binds
        fun context(app: Application): Context
    }

    @Singleton
    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
                .add(Date::class.java, Rfc3339DateJsonAdapter())
                .build()
    }

    @Singleton
    @Provides
    fun provideOkHttp(agentInterceptor: UserAgentInterceptor): OkHttpClient {
        val builder = OkHttpClient.Builder()
                .addInterceptor(agentInterceptor)

        if (BuildConfig.DEBUG) {
            builder.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        }

        return builder.build()
    }

    @Singleton
    @Provides
    fun provideApiService(
            okHttp: OkHttpClient,
            moshi: Moshi
    ): ApiService {
        return Retrofit.Builder()
                .baseUrl(BuildConfig.SERVER_API_URL)
                .client(okHttp)
                .addConverterFactory(UnwrapConverterFactory())
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
                .build()
                .create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideClubRepo(app: Application, apiService: ApiService): ClubRepo {
        val clubsDao = ClubDB.getDatabase(app).clubDao()
        return ClubRepo(clubsDao, apiService)
    }

    @Reusable
    @Provides
    fun preferences(app: Application): SharedPreferences = PreferenceManager.getDefaultSharedPreferences(app)
}