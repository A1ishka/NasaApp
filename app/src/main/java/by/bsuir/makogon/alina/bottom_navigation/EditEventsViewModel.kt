package by.bsuir.makogon.alina.bottom_navigation

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

sealed interface EditEventState {
    data class Error(val e: Exception) : EditEventState
    data class EditingEvent(
        val name: String,
        val date: String,
        val type: String,
        val description: String,
        val notes: String,
        val eventId: UUID? = null,
        val saved: Boolean = false
    ) : EditEventState

    object Loading : EditEventState
}


class EditEventsViewModel(
    private val eventId: UUID?,
    private val repo: LocalUserManager = LocalUserManagerIml,
) : ViewModel() {

    private val saved = MutableStateFlow(false)
    private val loading = MutableStateFlow(false)

    val state = combine(
        repo.getNasaEvent(eventId),
        saved,
        loading,
    ) { event, saved, loading ->
        if (loading) EditEventState.Loading else EditEventState.EditingEvent(
            event?.name ?: "",
            event?.date ?: "2023-10-21",//ocalDate.parse("2023.10.26"),
            event?.type ?: "",
            event?.description ?: "",
            event?.notes ?: "",
            event?.eventId ?: null,
            saved
        )
    }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), EditEventState.Loading)

    fun onClickSave(
        name: String,
        date: String,
        type: String,
        description: String,
        notes: String
    ) = viewModelScope.launch {
        loading.update { true }
        repo.upsertNasaEvent(
            NasaEvent(
                name,
                date,
                type,
                description,
                notes,
                eventId ?: UUID.randomUUID()
            )
        )
        loading.update { false }
        saved.update { true }
    }

    fun onClickDelete() = viewModelScope.launch {
        loading.update { true }
        eventId?.let { repo.deleteNasaEvent(eventId) }
        loading.update { false }
        saved.update { true }
    }
}
