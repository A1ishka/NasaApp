package by.bsuir.makogon.alina.domain.util

interface EntityMapper<Entity, DomainModel> {
    fun mapFromEntity(entity: Entity):DomainModel
    fun mapToEntity(domainModel: DomainModel):Entity
}