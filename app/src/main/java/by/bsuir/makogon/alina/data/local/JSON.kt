package by.bsuir.makogon.alina.data.local

import com.google.android.datatransport.BuildConfig
import kotlinx.serialization.json.Json

fun main() {
    val json = Json {
        explicitNulls = false
        ignoreUnknownKeys = true
        encodeDefaults = true
        prettyPrint = BuildConfig.DEBUG
        coerceInputValues = true
    }

    // Далее можно использовать экземпляр `json` для сериализации и десериализации объектов
    // ...
}