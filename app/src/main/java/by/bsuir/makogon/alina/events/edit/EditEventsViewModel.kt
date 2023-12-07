package by.bsuir.makogon.alina.events.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.bsuir.makogon.alina.domain.manager.LocalUserManager
import by.bsuir.makogon.alina.domain.model.NasaEvent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
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
        val saved: Boolean = false,
        val showCalendarDialog: Boolean = false
    ) : EditEventState

    object Loading : EditEventState
}

class EditEventsViewModel : ViewModel(), KoinComponent {
    private val repo: LocalUserManager by inject()

    private val saved = MutableStateFlow(false)
    private val loading = MutableStateFlow(false)
    private val eventId: MutableStateFlow<UUID?> = MutableStateFlow(null)
    private val showCalendarDialog = MutableStateFlow(false)

    /*showInputDialog: false,
    showSavingErrorDialog: false,
    showDeleteConfirmationDialog: false,*/
    private val actions = MutableSharedFlow<EditEventScreenEvents>()

    val state = combine(
        eventId.flatMapLatest {
            repo.getNasaEvent(it)
        },
        saved,
        showCalendarDialog,
        loading
    ) { event, saved, showCalendarDialog, loading ->
        if (loading) EditEventState.Loading else EditEventState.EditingEvent(
            event?.name ?: "",
            event?.date ?: "2023-10-21",
            event?.type ?: "",
            event?.description ?: "",
            event?.notes ?: "",
            event?.eventId,
            saved,
            showCalendarDialog
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), EditEventState.Loading)

    init {
        viewModelScope.launch {
            actions.collect { action ->
                when (action) {
                    is EditEventScreenEvents.ChangeName -> {
                        changeName(action.name)
                    }

                    EditEventScreenEvents.ChangeCalendarDialogVisibility -> {
                        //changeCalendarDialogVisibility(action.calendarDialogVisibility)
                        //необходимо написать иначе
                    }

                    EditEventScreenEvents.ChangeInputDialogVisibility -> {
                        TODO()
                    }

                    EditEventScreenEvents.ChangeDeleteConfirmationDialogVisibility -> {
                        TODO()
                    }

                    EditEventScreenEvents.ChangeSavingErrorDialogVisibility -> {
                        TODO()
                    }

                    is EditEventScreenEvents.ChangeDate -> {
                        changeDate(action.date)
                    }

                    is EditEventScreenEvents.ChangeDescription -> {
                        changeDescription(action.description)
                    }

                    is EditEventScreenEvents.ChangeNotes -> {
                        changeNotes(action.notes)
                    }

                    is EditEventScreenEvents.ChangeType -> {
                        changeType(action.type)
                    }

                    EditEventScreenEvents.DeleteEvent -> {
                        TODO()
                    }

                    EditEventScreenEvents.ReturnBack -> {
                        TODO()
                    }

                    EditEventScreenEvents.SaveEvent -> {
                        TODO()
                    }
                }
            }
        }
    }

    fun setEventId(eventId: UUID?) {
        this.eventId.value = eventId
    }

    fun onClickSave(editingEvent: EditEventState.EditingEvent) = viewModelScope.launch {
        loading.update { true }
        repo.upsertNasaEvent(
            NasaEvent(
                editingEvent.eventId ?: UUID.randomUUID(),
                editingEvent.name,
                editingEvent.date,
                editingEvent.type,
                editingEvent.description,
                editingEvent.notes
            )
        )
        loading.update { false }
        saved.update { true }
    }

//    fun onClickDelete() = viewModelScope.launch {
//        loading.update { true }
//        eventId.value?.let { repo.deleteNasaEvent(it) }
//        loading.update { false }
//        saved.update { true }
//    }


    fun onEvent(event: EditEventScreenEvents) {
        viewModelScope.launch {
            when (event) {
                is EditEventScreenEvents.ChangeName -> {
                    changeName(event.name)
                }

                is EditEventScreenEvents.ChangeDate -> {
                    changeName(event.date)
                }

                is EditEventScreenEvents.ChangeType -> {
                    changeName(event.type)
                }

                is EditEventScreenEvents.ChangeDescription -> {
                    changeName(event.description)
                }

                is EditEventScreenEvents.ChangeNotes -> {
                    changeName(event.notes)
                }

                EditEventScreenEvents.ChangeCalendarDialogVisibility -> TODO()
                EditEventScreenEvents.ChangeDeleteConfirmationDialogVisibility -> TODO()
                EditEventScreenEvents.ChangeInputDialogVisibility -> TODO()
                EditEventScreenEvents.ChangeSavingErrorDialogVisibility -> TODO()
                EditEventScreenEvents.SaveEvent -> TODO()
                EditEventScreenEvents.DeleteEvent -> TODO()
                EditEventScreenEvents.ReturnBack -> TODO()
            }
        }
    }
    fun changeCalendarDialogVisibility(calendarDialogVisibility: Boolean) {
        //изменить
    }

    fun changeName(newName: String) {
        val currentEditingEvent = state.value as? EditEventState.EditingEvent
        currentEditingEvent?.let { editingEvent ->
            val updatedEditingEvent = editingEvent.copy(name = newName)
            onClickSave(updatedEditingEvent)
        }
    }

    fun changeDate(newDate: String) {
        val currentEditingEvent = state.value as? EditEventState.EditingEvent
        currentEditingEvent?.let { editingEvent ->
            val updatedEditingEvent = editingEvent.copy(date = newDate)
            onClickSave(updatedEditingEvent)
        }
    }

    fun changeType(newValue: String) {
        val currentEditingEvent = state.value as? EditEventState.EditingEvent
        currentEditingEvent?.let { editingEvent ->
            val updatedEditingEvent = editingEvent.copy(type = newValue)
            onClickSave(updatedEditingEvent)
        }
    }

    fun changeDescription(newValue: String) {
        val currentEditingEvent = state.value as? EditEventState.EditingEvent
        currentEditingEvent?.let { editingEvent ->
            val updatedEditingEvent = editingEvent.copy(description = newValue)
            onClickSave(updatedEditingEvent)
        }
    }

    fun changeNotes(newValue: String) {
        val currentEditingEvent = state.value as? EditEventState.EditingEvent
        currentEditingEvent?.let { editingEvent ->
            val updatedEditingEvent = editingEvent.copy(notes = newValue)
            onClickSave(updatedEditingEvent)
        }
    }
}


//
//            AddEditMovieEvent.DeleteEvent -> {
//                viewModelScope.launch {
//                    watchedRepository.DeleteEvent(movie = state.value.movie.copy(id = movieId))
//                    _actionFlow.emit(AddEditMovieScreenAction.DeleteEvent)
//                }
//            }
//
//            AddEditMovieEvent.SaveEvent -> {
//                viewModelScope.launch {
//                    if (isPossibleToSave()) {
//                        val movie = state.value.movie.copy(id = movieId)
//                        val id = watchedRepository.SaveEvent(movie)
//                        _actionFlow.emit(
//                            AddEditMovieScreenAction.SaveEvent(
//                                isPossibleToSave = true,
//                                id = id
//                            )
//                        )
//                    } else {
//                        _actionFlow.emit(AddEditMovieScreenAction.SaveEvent(isPossibleToSave = false))
//                    }
//                }
//            }
//
//            AddEditMovieEvent.ReturnBack -> {
//                viewModelScope.launch {
//                    _actionFlow.emit(AddEditMovieScreenAction.ReturnBack)
//                }
//            }
//
//            AddEditMovieEvent.ChangeSavingErrorDialogVisibility -> {
//                _state.value =
//                    state.value.copy(showSavingErrorDialog = !state.value.showSavingErrorDialog)
//            }
//
//            AddEditMovieEvent.ChangeDeleteConfirmationDialogVisibility -> {
//                _state.value =
//                    state.value.copy(showDeleteConfirmationDialog = !state.value.showDeleteConfirmationDialog)
//            }
