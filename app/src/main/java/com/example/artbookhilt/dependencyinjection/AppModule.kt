package com.example.artbookhilt.dependencyinjection

import android.content.Context
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.artbookhilt.R
import com.example.artbookhilt.api.RetrofitAPI
import com.example.artbookhilt.repo.ArtRepository
import com.example.artbookhilt.repo.ArtRepositoryInterface
import com.example.artbookhilt.roomdb.ArtDAO
import com.example.artbookhilt.roomdb.ArtDatabase
import com.example.artbookhilt.util.Util.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun injectRoomDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, ArtDatabase::class.java, "ArtBookDB").build()

    @Singleton
    @Provides
    fun injectDao(database: ArtDatabase) = database.artDAO()

    @Singleton
    @Provides
    fun injectRetrofitAPI(): RetrofitAPI {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(RetrofitAPI::class.java)
    }

    @Singleton
    @Provides
    fun injectNormalRepo(dao: ArtDAO, api: RetrofitAPI) =
        ArtRepository(dao, api) as ArtRepositoryInterface

    @Singleton
    @Provides
    fun injectGlide(@ApplicationContext context: Context) = Glide.with(context)
        .setDefaultRequestOptions(
            RequestOptions().placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)
        )
}