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
/*
fun HttpClient(json: Json) = HttpClient(CIO) {
    // Logging
    // ContentNegotiation
    // Auth
    // HttpRequestRetry
    // HttpTimeout
    // HttpCache
    // DataConversion
    // ContentEncoding
    // defaultRequest()
    // addDefaultResponseValidation()
    // developmentMode = BuildConfig.DEBUG
    // expectSuccess = true
    // followRedirects = true
    // engine {
    //    pipelining = true
    //    endpoint { }
    // }
}*/