package by.bsuir.makogon.alina.data.local

import by.bsuir.makogon.alina.domain.model.NasaEvent
import by.bsuir.makogon.alina.domain.util.EntityMapper

class DbEventMapper : EntityMapper<EventEntity, NasaEvent> {
    override fun mapFromEntity(entity: EventEntity): NasaEvent {
        return NasaEvent(
            name = entity.name,
            date = entity.date,
            type = entity.type,
            description = entity.description,
            notes = entity.notes,
            eventId = entity.eventId
        )
    }

    override fun mapToEntity(domainModel: NasaEvent): EventEntity {
        return EventEntity(
            eventId = domainModel.eventId,
            name = domainModel.name,
            date = domainModel.date,
            type = domainModel.type,
            description = domainModel.description,
            notes = domainModel.notes
        )
    }


    fun fromEntityList(initial: List<EventEntity>): List<NasaEvent>{
        return initial.map{mapFromEntity(it)}
    }
    fun toEntityList(initial: List<NasaEvent>): List<EventEntity>{
        return initial.map{mapToEntity(it)}
    }
}