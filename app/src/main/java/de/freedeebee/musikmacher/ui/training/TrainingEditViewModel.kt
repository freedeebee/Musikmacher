package de.freedeebee.musikmacher.ui.training

import androidx.lifecycle.*
import de.freedeebee.musikmacher.data.model.TrainingSessionDao
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class TrainingEditViewModel(sessionId: Long, private val dao: TrainingSessionDao): ViewModel() {

    val session = dao.get(sessionId)

    private val _navigateToList = MutableLiveData<Boolean>()
    val navigateToList: LiveData<Boolean>
        get() = _navigateToList

    fun delete() {
        viewModelScope.launch {
            dao.delete(session.value!!)
            _navigateToList.value = true
        }
    }

    fun onNavigatedToList() {
        _navigateToList.value = false
    }

}

class TrainingEditViewModelFactory(private val sessionId: Long, private val dao: TrainingSessionDao): ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TrainingEditViewModel::class.java)) {
            return TrainingEditViewModel(sessionId, dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}