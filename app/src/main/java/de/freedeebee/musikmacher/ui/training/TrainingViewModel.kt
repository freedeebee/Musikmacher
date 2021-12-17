package de.freedeebee.musikmacher.ui.training

import android.util.Log
import androidx.lifecycle.*
import de.freedeebee.musikmacher.data.model.TrainingSession
import de.freedeebee.musikmacher.data.model.TrainingSessionDao
import de.freedeebee.musikmacher.util.convertLongToDateString
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class TrainingViewModel(private val dao: TrainingSessionDao): ViewModel() {

    private var _trainingStarted = MutableLiveData<Boolean>()
    val trainingStarted: LiveData<Boolean>
        get() = _trainingStarted

    fun toggleTraining() {
        when (_trainingStarted.value){
            false -> startTraining()
            true -> stopTraining()
            else -> startTraining()
        }
    }

    private fun startTraining() {
        viewModelScope.launch {
            val session = TrainingSession(timeStarted = System.currentTimeMillis())
            dao.save(session)
            Log.i("TrainingViewModel", "Training session started at ${convertLongToDateString(session.timeStarted!!)}.")
            _trainingStarted.value = true
        }
    }

    private fun stopTraining() {
        Log.i("TrainingViewModel", "Training session stopped.")
        _trainingStarted.value = false
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