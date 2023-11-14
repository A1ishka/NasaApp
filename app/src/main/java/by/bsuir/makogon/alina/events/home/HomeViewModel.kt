package by.bsuir.makogon.alina.events.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.bsuir.makogon.alina.domain.manager.LocalUserManager
import by.bsuir.makogon.alina.domain.model.NasaEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID


sealed interface HomeState {
    object Loading : HomeState
    data class DisplayingEvents(val events: List<NasaEvent>) : HomeState
    data class Error(val e: Exception) : HomeState
}

////val repository: LocalUserManager = get() //юзаем, когда нужно жестко инджектить
//val homeViewModel: HomeViewModel by getViewModel { parametersOf(repository) }

class HomeViewModel(
    private val repository: LocalUserManager
) : ViewModel() {

    private val loading = MutableStateFlow(false)

    val state = combine(
        repository.getNasaEvents(),
        loading,
    ) { events, loading ->
        if (loading) HomeState.Loading else HomeState.DisplayingEvents(events)
    }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), HomeState.Loading)

    fun onClickRemoveEvent(eventId: UUID) = viewModelScope.launch {
        loading.update { true }
        repository.deleteNasaEvent(eventId)
        loading.update { false }
    }

}