package de.freedeebee.musikmacher.data.model

import androidx.lifecycle.LiveData
import androidx.room.*

@Entity(tableName = "training_session")
data class TrainingSession(

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,

    @ColumnInfo(name = "time_started_millis")
    var timeStarted: Long? = null,

    @ColumnInfo(name = "time_ended_millis")
    var timeEnded: Long? = null
)

@Dao
interface TrainingSessionDao {

    @Insert
    suspend fun save(trainingSession: TrainingSession): Long

    @Update
    suspend fun update(trainingSession: TrainingSession)

    @Query("SELECT * FROM training_session ORDER BY time_started_millis DESC LIMIT 1")
    suspend fun getLatestSession(): TrainingSession?

    @Query("SELECT * FROM training_session WHERE time_ended_millis IS NOT NULL ORDER BY time_started_millis DESC")
    fun getCompletedSessions(): LiveData<List<TrainingSession>>

}