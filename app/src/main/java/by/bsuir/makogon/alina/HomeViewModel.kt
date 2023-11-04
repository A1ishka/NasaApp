package by.bsuir.makogon.alina

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.bsuir.makogon.alina.data.manager.LocalUserManagerIml
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

class HomeViewModel(private val repository: LocalUserManager = LocalUserManagerIml) : ViewModel() {
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




/*fun elementOnClick(route: String, navController: NavHostController) {
    val prePreviousEntry = navController.previousBackStackEntry
    navController.navigate(route = route) {
        prePreviousEntry?.destination?.let {
            popUpTo(it.id) {
                inclusive = true
            }
        }
    }
}*/