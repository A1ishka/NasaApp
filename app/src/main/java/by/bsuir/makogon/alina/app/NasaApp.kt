package by.bsuir.makogon.alina.app

import android.app.Application
import by.bsuir.makogon.alina.data.LocalNasaEventDataSource
import by.bsuir.makogon.alina.data.RoomEventDataSource
import by.bsuir.makogon.alina.data.local.AppDatabase
import by.bsuir.makogon.alina.data.local.DbEventMapper
import by.bsuir.makogon.alina.data.local.EventEntity
import by.bsuir.makogon.alina.data.manager.LocalUserManagerIml
import by.bsuir.makogon.alina.domain.manager.LocalUserManager
import by.bsuir.makogon.alina.domain.model.NasaEvent
import by.bsuir.makogon.alina.domain.util.EntityMapper
import by.bsuir.makogon.alina.events.edit.editEventsViewModelModule
import by.bsuir.makogon.alina.events.home.homeViewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module


val dataModule = module {
}

val domainModule = module {

}

val appModule = module {
    single { AppDatabase(context = get()).eventDao() }
    single<LocalNasaEventDataSource> { RoomEventDataSource(get(), DbEventMapper()) }
    single<LocalUserManager> { LocalUserManagerIml(get()) }
    includes(dataModule, domainModule, homeViewModelModule, editEventsViewModelModule)
}

class NasaApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(applicationContext)
            modules(appModule)
        }
    }
}
