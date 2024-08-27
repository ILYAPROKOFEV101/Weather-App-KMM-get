
package com.ilya.composeweatheapp



import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ilya.composeweatheapp.DatacllaRespond.Respondata
import getWeather
import handleFetchWeather
import io.ktor.http.HttpHeaders.Date
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import weather.composeapp.generated.resources.Res
import weather.composeapp.generated.resources.baseline_search_24
import weather.composeapp.generated.resources.compose_multiplatform
import weather.composeapp.generated.resources.feelslike
import weather.composeapp.generated.resources.humidity
import weather.composeapp.generated.resources.temp
import weather.composeapp.generated.resources.weather
import weather.composeapp.generated.resources.pressure
import weather.composeapp.generated.resources.sunrise
import weather.composeapp.generated.resources.sunset
import weather.composeapp.generated.resources.visibility
import weather.composeapp.generated.resources.weather_description
import weather.composeapp.generated.resources.windspeed

@Composable
@Preview
fun App() {
    var cityName by remember { mutableStateOf("") }
    var weatherInfo by remember { mutableStateOf<Respondata?>(null) }

    MaterialTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                getCityName { newCityName ->
                    cityName = newCityName
                    handleFetchWeather(cityName) { weather ->
                        weatherInfo = weather
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                WeatherInfoDisplay(weatherInfo)
            }
        }
    }
}



@Composable
fun WeatherInfoDisplay(weatherInfo: Respondata?) {
    weatherInfo?.let {
        // парс дданных
        var temp = it.main.temp - 273.15

        val pressure = it.main.pressure * 0.75006375541921f
        val pressureStatus = when {
            pressure.toInt() < 1000 -> "Низкое"
            pressure.toInt() in 1000..1020 -> "Нормальное"
            else -> "Высокое"
        }

    Spacer(modifier = Modifier.height(20.dp))
    Card(modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight()
        .clip(RoundedCornerShape(30.dp))
        .padding(start = 5.dp , end = 5.dp)
        .background(Color(0xFF87CEEB))
    ) {
        Column(modifier = Modifier
            .fillMaxSize()
            )
        {
            Spacer(modifier = Modifier.height(20.dp))
            Box(modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
                .clip(RoundedCornerShape(30.dp))
                .background(Color(0xFF87CEEB))
                .height(70.dp)
            ) {
                Text(text = "Weather in ${weatherInfo?.name}", modifier = Modifier.align(Alignment.Center), fontSize = 24.sp, color = Color.White)
            }
            Spacer(modifier = Modifier.height(20.dp))

            Row(modifier = Modifier.fillMaxWidth().height(50.dp)){
                Text(text = "${stringResource(Res.string.temp)}: ${temp.toInt()}°C", fontSize = 24.sp)
                Spacer(modifier = Modifier.width(20.dp))
                Text(text = "${stringResource(Res.string.weather)}: ${weatherInfo?.weather?.get(0)?.description}", fontSize = 24.sp)
            }
            Spacer(modifier = Modifier.height(20.dp))

            Box(modifier = Modifier.fillMaxWidth().height(40.dp)) {
                Text(
                    text = "${stringResource(Res.string.pressure)} $pressure гПа ($pressureStatus)",
                    textAlign = TextAlign.Start,
                    fontSize = 24.sp
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            Box(modifier = Modifier.fillMaxWidth().wrapContentHeight()) {
                WeatherDisplay(weatherInfo)
            }



            }
        }

    }
}

// Функция для форматирования времени
fun formatTime(unixTime: Long): String {
    val dateTime = Instant.fromEpochSeconds(unixTime).toLocalDateTime(TimeZone.currentSystemDefault())
    val hour = dateTime.hour.toString().padStart(2, '0')
    val minute = dateTime.minute.toString().padStart(2, '0')
    return "$hour:$minute"
}


@Composable
fun WeatherDisplay(weather: Respondata) {


    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "${stringResource(Res.string.temp)} ${weather.main.temp}°C",
            fontSize = 24.sp
        )
        Text(
            text = "${stringResource(Res.string.feelslike)} ${weather.main.feelsLike}°C",
            fontSize = 20.sp
        )
        Text(
            text = "${stringResource(Res.string.weather_description)} ${weather.weather[0].description}",
            fontSize = 20.sp
        )
        Text(
            text = "${stringResource(Res.string.humidity)} ${weather.main.humidity}%",
            fontSize = 20.sp
        )
        Text(
            text = "${stringResource(Res.string.windspeed)} ${weather.wind.speed} m/s",
            fontSize = 20.sp
        )
        Text(
            text = "${stringResource(Res.string.visibility)} ${weather.visibility / 1000} km",
            fontSize = 20.sp
        )
        // Пример использования в вашем коде
        Text(
            text = "${stringResource(Res.string.sunrise)} ${formatTime(weather.sys.sunrise.toLong())}",
            fontSize = 20.sp
        )
        Text(
            text = "${stringResource(Res.string.sunset)} ${formatTime(weather.sys.sunset.toLong())}",
            fontSize = 20.sp
        )
    }
}




@Composable
fun getCityName(onCityNameEntered: (String) -> Unit) {
    var cityname by remember { mutableStateOf("") }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(30.dp))
            .height(80.dp),
        verticalAlignment = Alignment.CenterVertically // Центровка по вертикали
    ) {
        Card(
            modifier = Modifier
                .weight(0.8f)
                .height(80.dp),
            shape = RoundedCornerShape(30.dp)
        ) {
            TextField(
                modifier = Modifier.fillMaxSize(),
                value = cityname,
                onValueChange = {
                    cityname = it
                },
                textStyle = TextStyle(fontSize = 24.sp),
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.White,
                    unfocusedIndicatorColor = Color.White,
                    disabledIndicatorColor = Color.White,
                    backgroundColor = Color.White,
                ),
                label = {
                    Text(
                        text = "Название вашего города?",
                        fontSize = 18.sp, // Уменьшил размер текста для лучшего отображения
                        color = Color.Gray, // Изменил цвет текста, чтобы отличать от введенного текста
                        textAlign = TextAlign.Start,
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardOptions.Default.keyboardType
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        onCityNameEntered(cityname)
                    }
                ),
            )
        }

        IconButton(
            modifier = Modifier
                .weight(0.2f) // Ограничиваем ширину кнопки
                .height(80.dp), // Высота кнопки
            onClick = {
                onCityNameEntered(cityname) }
        ) {
            Image(
                painter = painterResource(Res.drawable.baseline_search_24),
                contentDescription = "Search Icon",
                modifier = Modifier.size(36.dp), // Увеличил размер иконки для лучшей видимости
                colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(Color.Red)
            )
        }
    }
}

