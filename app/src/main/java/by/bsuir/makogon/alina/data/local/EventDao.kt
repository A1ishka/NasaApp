package by.bsuir.makogon.alina.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import java.util.UUID

/*@Dao
interface EventDao {
    @Query("SELECT * FROM ${EventEntity.TableName}")
    fun getNasaEvents(): Flow<List<EventEntity>>

    @Upsert // INSERT OR REPLACE в БД
    suspend fun saveEvents(e: EventEntity)
}*/
@Dao
interface EventDao {
    @Query("SELECT * FROM ${EventEntity.TableName}")
    fun getEventsEntities(): Flow<List<EventEntity>>

    @Query("SELECT * FROM ${EventEntity.TableName} WHERE eventId = :eventId")
    fun getEventEntity(eventId: UUID): Flow<EventEntity?>

    @Upsert
    suspend fun upsertEventEntity(event: EventEntity)

    @Query("DELETE FROM ${EventEntity.TableName} WHERE eventId = :eventId")
    suspend fun deleteEventEntity(eventId: UUID)
}