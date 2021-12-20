package de.freedeebee.musikmacher.ui.training

import android.util.Log
import androidx.lifecycle.*
import de.freedeebee.musikmacher.data.model.TrainingSession
import de.freedeebee.musikmacher.data.model.TrainingSessionDao
import de.freedeebee.musikmacher.util.convertLongToDateString
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class TrainingViewModel(private val dao: TrainingSessionDao): ViewModel() {

    private var activeSession = MutableLiveData<TrainingSession?>()

    val completedTrainings = dao.getCompletedSessions()

    val stopButtonVisible = Transformations.map(activeSession) {
        null != it
    }

    init {
        initializeActiveSession()
    }

    private fun initializeActiveSession() {
        viewModelScope.launch {
            activeSession.value = getActiveSession()
        }
    }

    private suspend fun getActiveSession(): TrainingSession? {
        val session = dao.getLatestSession()
        if (session?.timeEnded == null) {
            Log.i("TrainingViewModel", "Active session detected: ID ${session?.id}")
            return session
        }
        return null
    }

    fun toggleTraining() {
        if (activeSession.value != null) {
            stopTraining()
        } else {
            startTraining()
        }
    }

    private fun startTraining() {
        viewModelScope.launch {
            val session = TrainingSession(timeStarted = System.currentTimeMillis())
            val id = dao.save(session)
            session.id = id
            Log.i("TrainingViewModel", "Training session ${session.id} started at ${convertLongToDateString(session.timeStarted!!)}.")
            activeSession.value = session
        }
    }

    private fun stopTraining() {
        viewModelScope.launch {
            val session = activeSession.value!!
            session.timeEnded = System.currentTimeMillis()
            dao.update(session)
            Log.i("TrainingViewModel", "Training session ${session.id} stopped at ${convertLongToDateString(session.timeStarted!!)}.")

            activeSession.value = null
        }
    }

}

class TrainingViewModelFactory(private val dao: TrainingSessionDao): ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TrainingViewModel::class.java)) {
            return TrainingViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}