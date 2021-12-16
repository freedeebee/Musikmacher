package de.freedeebee.musikmacher.views.training

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TrainingViewModel: ViewModel() {

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
        Log.i("TrainingViewModel", "Training session started.")
        _trainingStarted.value = true
    }

    private fun stopTraining() {
        Log.i("TrainingViewModel", "Training session stopped.")
        _trainingStarted.value = false
    }

}