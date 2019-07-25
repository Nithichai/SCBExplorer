package com.nopyjf.projectscbexplorer.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase


@Database(entities = [TokenEntity::class], version = 1, exportSchema = true)
abstract class AppDatabase: RoomDatabase()  {

    abstract fun tokenDao(): TokenDAO

    companion object {

        private val TAG: String by lazy { AppDatabase::class.java.simpleName }

        // For Singleton instantiation, visible to other threads.
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {

            instance?.let {
                return it
            }

            synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "SCBExplorerToken" // #step1
                ).addCallback(object : RoomDatabase.Callback() {
                    // onCreate will be called when the database is created for the first time
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        Log.d(TAG, "onCreate")
                    }
                })
                    .addMigrations()
                    .fallbackToDestructiveMigration()
                    .build().also {
                        instance = it
                        return instance!!
                    }
            }
        }

        fun destroyInstance() {
            instance = null
        }
    }
}