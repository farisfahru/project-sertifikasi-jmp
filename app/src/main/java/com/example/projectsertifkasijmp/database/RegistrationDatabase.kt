package com.example.projectsertifkasijmp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Student::class], version = 1)
abstract class RegistrationDatabase : RoomDatabase() {
    abstract fun registrationDao(): RegistrationDao

    companion object {
        @Volatile
        private var INSTANCE: RegistrationDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): RegistrationDatabase {
            if (INSTANCE == null) {
                synchronized(RegistrationDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        RegistrationDatabase::class.java, "registraion_database")
                        .build()
                }
            }
            return INSTANCE as RegistrationDatabase
        }
    }
}