package by.bsuir.makogon.alina

import android.app.Application
import by.bsuir.makogon.alina.data.local.AppDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module

val appModule = module {
    single { AppDatabase(context = get()).eventDao() }
    includes(dataModule, domainModule)
}

val dataModule = module {
    //single { AppDatabase(context = get()) }
    //single { get<AppDatabase>().eventDao() }
}

val domainModule = module {

}
//val myApp = listOf(myModule, anotherModule)

class NasaApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(applicationContext)
            modules(appModule)
        }
    }
}