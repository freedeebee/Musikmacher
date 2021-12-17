package de.freedeebee.musikmacher.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import de.freedeebee.musikmacher.data.model.TrainingSession
import de.freedeebee.musikmacher.data.model.TrainingSessionDao

@Database(entities = [TrainingSession::class], version = 1, exportSchema = false)
abstract class MusikmacherDatabase: RoomDatabase() {

    abstract val trainingSessionDao: TrainingSessionDao

    companion object {
        @Volatile
        private var INSTANCE: MusikmacherDatabase? = null

        fun getInstance(context: Context): MusikmacherDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context,
                        MusikmacherDatabase::class.java,
                        "musikmacher_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }

}