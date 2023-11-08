package by.bsuir.makogon.alina.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [EventEntity::class], version = 1, exportSchema = false)
@TypeConverters(UUIDConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun eventDao(): EventEntityDao
}

internal fun AppDatabase(context: Context) = Room.databaseBuilder( // билдер-функция
    context,
    AppDatabase::class.java,
    "event_database" // ваше имя, расширение файла не нужно
)
    .fallbackToDestructiveMigration()
    .build()