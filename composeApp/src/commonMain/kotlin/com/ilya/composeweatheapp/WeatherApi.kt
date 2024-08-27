

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import com.ilya.composeweatheapp.DatacllaRespond.Respondata
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

// Создание клиента Ktor
private val client = HttpClient {
    install(ContentNegotiation) {
        json(Json { ignoreUnknownKeys = true })
    }
    install(HttpTimeout) {
        requestTimeoutMillis = 30000 // Увеличение времени ожидания запроса до 30 секунд
        connectTimeoutMillis = 30000 // Увеличение времени ожидания подключения до 30 секунд
        socketTimeoutMillis = 30000 // Увеличение времени ожидания сокета до 30 секунд
    }
}



fun handleFetchWeather(cityName: String, onWeatherFetched: (Respondata?) -> Unit) {
    CoroutineScope(Dispatchers.IO).launch {
        try {
            val weather = getWeather(cityName)
            onWeatherFetched(weather)
        } catch (e: Exception) {
            println("Ошибка при выполнении запроса: ${e.message}")
            onWeatherFetched(null)
        }
    }
}

suspend fun getWeather(city: String): Respondata {
    val url = "https://api.openweathermap.org/data/2.5/weather?q=$city&appid=b60a2f76c2ada4f0ea798662ba686e3d"

    // Выполнение GET-запроса
    val response: HttpResponse = client.get(url)

    // Логирование деталей ответа
    println("WeatherData Запрос URL: $url")
    println("WeatherData Статус код: ${response.status.value}")
    println("WeatherData Заголовки: ${response.headers}")
    println("WeatherData Тело ответа: ${response.bodyAsText()}")

    // Преобразование тела ответа в объект Respondata и возврат результата
    return response.body()
}

