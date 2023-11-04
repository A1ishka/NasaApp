package by.bsuir.makogon.alina.domain.model

import java.util.UUID

data class NasaEvent(
    val eventId: UUID = UUID.randomUUID(),
    val name: String? = null,
    val date: String? = null,
    val type: String? = null,
    val description: String? = null,
    val notes: String? = null
)