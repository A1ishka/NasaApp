package by.bsuir.makogon.alina.events.edit


sealed class EditEventScreenEvents {

    data class ChangeName(val name: String) : EditEventScreenEvents()
    data class ChangeDate(val date: String) : EditEventScreenEvents()
    data class ChangeType(val type: String) : EditEventScreenEvents()
    data class ChangeDescription(val description: String) : EditEventScreenEvents()
    data class ChangeNotes(val notes: String) : EditEventScreenEvents()


    object ChangeCalendarDialogVisibility : EditEventScreenEvents()
    object ChangeInputDialogVisibility : EditEventScreenEvents()
    object ChangeSavingErrorDialogVisibility : EditEventScreenEvents()
    object ChangeDeleteConfirmationDialogVisibility : EditEventScreenEvents()

    object SaveEvent : EditEventScreenEvents()
    object DeleteEvent : EditEventScreenEvents()
    object ReturnBack : EditEventScreenEvents()
}