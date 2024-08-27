
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import weather.composeapp.generated.resources.Res
import weather.composeapp.generated.resources.baseline_search_24
import weather.composeapp.generated.resources.compose_multiplatform

@Composable
@Preview
fun App() {
    var cityName by remember { mutableStateOf("") }
    var weatherInfo by remember { mutableStateOf<Respondata?>(null) }

    MaterialTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
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
        var temp = it.main.temp - 273.15

    Spacer(modifier = Modifier.height(20.dp))
    Card(modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight()
        .clip(RoundedCornerShape(30.dp))
        .padding(start = 10.dp , end = 10.dp)
        .background(Color.White)
    ) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(5.dp)
            ) {
            Box(modifier = Modifier.fillMaxWidth().background(Color.Blue)){
                Text(text = "Weather in ${weatherInfo?.name}", modifier = Modifier.align(Alignment.Center))
            }
            Spacer(modifier = Modifier.height(20.dp))
            Row(modifier = Modifier.fillMaxWidth()){
                Text(text = "Temperature: ${temp}°C")
                Text(text = "Humidity: ${it.weather}%")
            }



            }
        }

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

