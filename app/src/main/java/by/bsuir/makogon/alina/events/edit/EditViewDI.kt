package by.bsuir.makogon.alina.events.edit

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import java.util.UUID


val editEventsViewModelModule = module {
    viewModel { (eventId: UUID?) -> EditEventsViewModel(eventId) }
}