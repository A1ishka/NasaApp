package by.bsuir.makogon.alina.domain.model

import java.util.UUID

data class NasaEvent(
    val eventId: UUID = UUID.randomUUID(),
    val name: String,
    val date: String,
    val type: String? = null,
    val description: String,
    val notes: String? = null
)