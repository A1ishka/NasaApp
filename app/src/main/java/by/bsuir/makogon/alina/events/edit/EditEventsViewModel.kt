package by.bsuir.makogon.alina.events.edit

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
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.UUID

sealed interface EditEventState {
    data class Error(val e: Exception) : EditEventState
    data class EditingEvent(
        val name: String? = null,
        val date: String? = null,
        val type: String? = null,
        val description: String? = null,
        val notes: String? = null,
        val eventId: UUID? = UUID.randomUUID(),
        val saved: Boolean = false
    ) : EditEventState

    object Loading : EditEventState
}

//private val editEventsViewModel: EditEventsViewModel by viewModels()
////private val localUserManager: LocalUserManager by inject()

class EditEventsViewModel(
    private val eventId: UUID?,
) : ViewModel(), KoinComponent {
    private val repo: LocalUserManager by inject()

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
            event?.eventId ?: UUID.randomUUID(),
            saved
        )
    }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), EditEventState.Loading)

    fun onClickSave(
        name: String,
        date: String,
        type: String,
        description: String,
        notes: String,
        //eventId: UUID
    ) = viewModelScope.launch {
        loading.update { true }
        repo.upsertNasaEvent(
            NasaEvent(
                eventId ?: UUID.randomUUID(),
                name,
                date,
                type,
                description,
                notes
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
