package de.freedeebee.musikmacher.data.model

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
    suspend fun save(trainingSession: TrainingSession)

}