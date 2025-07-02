package com.test.gymapptest.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.test.gymapptest.data.room.Daos.EjercicioDao
import com.test.gymapptest.data.room.Daos.RutinaDao
import com.test.gymapptest.data.room.Daos.RutinaEjercicioDao
import com.test.gymapptest.data.room.Daos.TipoDao
import com.test.gymapptest.data.room.Entitys.EjercicioEntity
import com.test.gymapptest.data.room.Entitys.RutinaEjercicioEntity
import com.test.gymapptest.data.room.Entitys.RutinaEntity
import com.test.gymapptest.data.room.Entitys.TipoEntity

@Database(entities = [RutinaEntity::class, EjercicioEntity::class, TipoEntity::class, RutinaEjercicioEntity::class], version = 1)
abstract class GymAppDatabase: RoomDatabase() {
    //Dao
    abstract fun rutinaDao(): RutinaDao
    abstract fun ejercicioDao(): EjercicioDao
    abstract fun tipoDao(): TipoDao
    abstract fun rutinaEjercicioDao(): RutinaEjercicioDao

    companion object{
        const val DATABASE_NAME = "gym_app_database"
        @Volatile
        private var INSTANCE: GymAppDatabase? = null

        fun getDataBase(context: Context): GymAppDatabase{
            return INSTANCE ?: synchronized(this)
            {
                val instance = Room.databaseBuilder(
                                            context.applicationContext,
                                            GymAppDatabase::class.java,
                                            DATABASE_NAME
                                        ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }

    }

}