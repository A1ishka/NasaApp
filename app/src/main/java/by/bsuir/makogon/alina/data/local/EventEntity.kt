package by.bsuir.makogon.alina.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity
data class EventEntity(
    @PrimaryKey val eventId: UUID,
    val name: String,
    val date: String,
    val type: String? = null,
    val description: String,
    val notes: String? = null
){
    internal companion object {
        const val TableName = "nasa_event" // <- удобно потом ссылаться будет
    }
}