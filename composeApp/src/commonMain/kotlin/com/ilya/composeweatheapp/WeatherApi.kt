

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import com.ilya.composeweatheapp.DatacllaRespond.Respondata
import io.ktor.client.HttpClient
import io.ktor.client.call.body
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
}


fun handleFetchWeather(cityName: String, onWeatherFetched: (Respondata?) -> Unit) {
    CoroutineScope(Dispatchers.IO).launch {
        val weather = getWeather(cityName)
        onWeatherFetched(weather)
    }
}
// Функция для выполнения GET-запроса

suspend fun getWeather(city: String): Respondata {
    val url = "https://api.openweathermap.org/data/2.5/weather?q=$city&appid=Well Well Well Бесплатный сыр, только в мышеловке "

    // Выполнение GET-запроса
    val response: HttpResponse = client.get(url)

    // Логирование деталей ответа
    println("WeatherData Запрос URL: $url")
    println("WeatherData Статус код: ${response.status.value}")
    println("WeatherData Заголовки: ${response.headers}")
    println("WeatherDataТело ответа: ${response.bodyAsText()}")

    // Преобразование тела ответа в объект WeatherResponse и возврат результата
    return response.body()
}


