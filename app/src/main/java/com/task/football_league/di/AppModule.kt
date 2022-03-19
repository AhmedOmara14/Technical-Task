package com.task.football_league.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.task.football_league.common.Constants
import com.task.football_league.data.remote.ApiService
import com.task.football_league.data.repository.MyRepositoryImpl
import com.task.football_league.common.NetworkHelper
import com.task.football_league.data.datasource.RemoteDataSource
import com.task.football_league.data.datasource.RemoteDataSourceImpl
import com.task.football_league.data.datasource.db.LeagueDatabase
import com.task.football_league.domain.repository.MyRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(
        ApiService::class.java)

    @Singleton
    @Provides
    fun provideRemoteDataSource(apiService: ApiService): RemoteDataSource = RemoteDataSourceImpl(apiService)

    @Singleton
    @Provides
    fun provideNetworkHelper(@ApplicationContext context: Context) = NetworkHelper(context)


    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application): LeagueDatabase {
        return Room.databaseBuilder(
            app,
            LeagueDatabase::class.java,
            LeagueDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    fun provideRepository(remoteDataSource: RemoteDataSource, preferenceModule: PreferenceModule,db: LeagueDatabase): MyRepository {
        return MyRepositoryImpl(remoteDataSource, preferenceModule,db)
    }



}