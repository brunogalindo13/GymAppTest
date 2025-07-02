package com.test.gymapptest.data.di

import android.content.Context
import androidx.room.Room
import com.test.gymapptest.data.room.GymAppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DBModule {

    @Provides
    fun provideRutinaDao(appDatabase: GymAppDatabase) = appDatabase.rutinaDao()
    @Provides
    fun provideEjercicioDao(appDatabase: GymAppDatabase) = appDatabase.ejercicioDao()
    @Provides
    fun provideTipoDao(appDatabase: GymAppDatabase) = appDatabase.tipoDao()
    @Provides
    fun provideRutinaEjercicioDao(appDatabase: GymAppDatabase) = appDatabase.rutinaEjercicioDao()


    @Provides
    @Singleton
    fun provideGymAppDB(@ApplicationContext appContext: Context): GymAppDatabase {
        return Room.databaseBuilder(appContext, GymAppDatabase::class.java, "GymDB").build()
    }
}