package by.bsuir.makogon.alina.data

import by.bsuir.makogon.alina.data.local.DbEventMapper
import by.bsuir.makogon.alina.data.local.EventDao
import by.bsuir.makogon.alina.domain.model.NasaEvent
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import java.util.UUID

interface LocalNasaEventDataSource {
    fun getNasaEvents(): Flow<List<NasaEvent>>
    fun getNasaEvent(eventId: UUID): Flow<NasaEvent?>
    suspend fun upsertNasaEvent(event: NasaEvent)
    suspend fun deleteNasaEvent(eventId: UUID)
}

object InMemoryEventDatasource: LocalNasaEventDataSource {

    private var DefaultEvents = setOf(
        NasaEvent(
            name = "New black flow was found!!..",
            date = /*LocalDate.parse("2023-10-21", HomeViewModel.formatter)*/"2023-10-21",
            type = "black flow",
            description = "Acsidently it was found by british scientisifists",
            notes = "I have nothing to say",
        ),
        NasaEvent(
            name = "Who will be extreamly lucky this retrograd mercury",
            date = /*LocalDate.parse("2023-5-11", HomeViewModel.formatter)*/"2023-10-21",
            type = "retrograd mercury",
            description = "Signs that can get it well",
            notes = "I have nothing to say",
        )
    )

    private val events = DefaultEvents.associateBy { it.eventId }.toMutableMap()
    private val _eventsFlow = MutableSharedFlow<Map<UUID, NasaEvent>>(1)

    override fun getNasaEvents(): Flow<List<NasaEvent>> = _eventsFlow
        .asSharedFlow()
        .onStart{
            delay(1000L)
            emit(events)
        }
        .map{it.values.toList()}


    override fun getNasaEvent(eventId: UUID): Flow<NasaEvent?> = _eventsFlow
        .asSharedFlow()
        .onStart{
            delay(1000L)
            emit(events)
        }
        .map{ it[eventId] }

    override suspend fun upsertNasaEvent(event: NasaEvent) {
        delay(1000L)
        events[event.eventId] = event
        _eventsFlow.emit(events)
    }

    override suspend fun deleteNasaEvent(eventId: UUID) {
        delay(1000L)
        events.remove(eventId)
        _eventsFlow.emit(events)
    }
}

internal class RoomEventDataSource(
    private val dao: EventDao,
    private val eventMapper: DbEventMapper)
    : LocalNasaEventDataSource {


    private val _eventsFlow = MutableSharedFlow<Map<UUID, NasaEvent>>()

    init {
        flow {
            delay(1000L)
            val eventEntities = dao.getEventsEntities()
            val events = eventMapper.fromEntityList(eventEntities)//тт
            val eventsMap = events.associateBy { it.eventId }
            emit(eventsMap)
        }.emitAll(_eventsFlow)
    }

    override fun getNasaEvents(): Flow<List<NasaEvent>> = _eventsFlow.map { it.values.toList() }

    override fun getNasaEvent(eventId: UUID): Flow<NasaEvent?> = _eventsFlow.map { eventMap ->
        eventMap[eventId]
    }

    override suspend fun upsertNasaEvent(event: NasaEvent) {
        //первая строка должна быть убрана сразу после починки куска выше
        val eventMap = _eventsFlow.replayCache.firstOrNull() ?: emptyMap()
        val updatedEventMap = eventMap.toMutableMap()
        updatedEventMap[event.eventId] = event
        _eventsFlow.tryEmit(updatedEventMap)
        dao.upsertEventEntity(eventMapper.mapToEntity(event))
    }

    override suspend fun deleteNasaEvent(eventId: UUID) {
        //первая строка должна быть убрана сразу после починки куска выше!!!
        val eventMap = _eventsFlow.replayCache.firstOrNull() ?: emptyMap()
        val updatedEventMap = eventMap.toMutableMap()
        updatedEventMap.remove(eventId)
        _eventsFlow.tryEmit(updatedEventMap)
        dao.deleteEventEntity(eventId)
    }
}