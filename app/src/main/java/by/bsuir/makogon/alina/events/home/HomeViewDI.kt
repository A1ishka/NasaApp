package by.bsuir.makogon.alina.events.home

import by.bsuir.makogon.alina.events.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val homeViewModelModule = module {
    viewModel { HomeViewModel(repository = get()) }
}