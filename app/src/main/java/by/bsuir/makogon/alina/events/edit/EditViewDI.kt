package by.bsuir.makogon.alina.events.edit

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val editEventsViewModelModule = module {
    viewModel { EditEventsViewModel() }
}
