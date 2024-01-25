package ru.aapodomatko.interestingplaces.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.aapodomatko.interestingplaces.data.api.KudaGoService
import ru.aapodomatko.interestingplaces.data.database.PlacesDao
import ru.aapodomatko.interestingplaces.data.database.PlacesDataBase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    @Provides
    @Singleton
    fun provideRetrofit(): KudaGoService {

        val loginInterceptor = HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(loginInterceptor)
            .build()

        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://kudago.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(KudaGoService::class.java)

    }

    @Provides
    fun providePlacesDao(appDatabase: PlacesDataBase): PlacesDao {
        return appDatabase.getPlacesDao()
    }

    @Provides
    @Singleton
    fun providePlacesDatabase(@ApplicationContext context: Context): PlacesDataBase {
        return Room.databaseBuilder(
            context,
            PlacesDataBase::class.java,
            "places_database"
        ).build()
    }

}