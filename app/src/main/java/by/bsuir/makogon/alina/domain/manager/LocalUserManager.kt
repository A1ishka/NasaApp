package by.bsuir.makogon.alina.domain.manager

import by.bsuir.makogon.alina.domain.model.NasaEvent
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface LocalUserManager {
    fun getNasaEvents():Flow<List<NasaEvent>>
    fun getNasaEvent(eventId: UUID?):Flow<NasaEvent?>
    suspend fun upsertNasaEvent(event: NasaEvent)
    suspend fun deleteNasaEvent(eventId: UUID)
}