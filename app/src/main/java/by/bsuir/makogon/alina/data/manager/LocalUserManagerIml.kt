package by.bsuir.makogon.alina.data.manager

import by.bsuir.makogon.alina.data.InMemoryEventDatasource
import by.bsuir.makogon.alina.data.LocalNasaEventDataSource
import by.bsuir.makogon.alina.domain.manager.LocalUserManager
import by.bsuir.makogon.alina.domain.model.NasaEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.util.UUID

object LocalUserManagerIml:LocalUserManager {

    private val dataSource: LocalNasaEventDataSource = InMemoryEventDatasource

    override fun getNasaEvents(): Flow<List<NasaEvent>> = dataSource.getNasaEvents()
    override fun getNasaEvent(eventId: UUID?): Flow<NasaEvent?> = eventId?.let{dataSource.getNasaEvent(eventId)}?:flowOf(null)

    override suspend fun upsertNasaEvent(event: NasaEvent) = dataSource.upsertNasaEvent(event)
    override suspend fun deleteNasaEvent(eventId: UUID) = dataSource.deleteNasaEvent(eventId)

}