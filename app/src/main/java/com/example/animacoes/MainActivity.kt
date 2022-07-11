package com.example.animacoes

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.animacoes.ui.theme.AnimacoesTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel: MainViewModel by viewModels ()

        setContent {
            AnimacoesTheme {
                Surface(
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(enabled = true, state = rememberScrollState()),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        var value by remember { mutableStateOf(0f) }

                        CustomComponent(
                            indicatorValue = value.toInt()
                        )

                        TextField(
                            value = value.toInt().toString(),
                            onValueChange = {
                                value = if(it.isNotEmpty()){
                                    it.toFloat()
                                }else {
                                    0f
                                }
                            },
                            keyboardOptions = KeyboardOptions(
                                keyboardType =  KeyboardType.Number
                            )
                        )

                        Slider(
                            modifier = Modifier.fillMaxSize(fraction = 0.8f),
                            value = value,
                            onValueChange = { value = it},
                            onValueChangeFinished = { println("terminei")},
                            valueRange = 0f..100f
                        )

                        var minutes by remember {
                            mutableStateOf(0L)
                        }
                        var seconds by remember {
                            mutableStateOf(0L)
                        }
                        var timeInMillisUntilFinished  by remember {
                            mutableStateOf(0L)
                        }

                        val TIME_IN_MILLIS = 2 * 60000L


                        LaunchedEffect(key1 = "" ) {
                            val timer = object: CountDownTimer(TIME_IN_MILLIS, 1000) {
                                override fun onTick(millisUntilFinished: Long) {

                                    seconds = (millisUntilFinished / 1000) % 60

                                    minutes =(millisUntilFinished / 1000) / 60
                                    timeInMillisUntilFinished = millisUntilFinished
                                    Log.d("CountDownTimer", "${timeInMillisUntilFinished / 1000 %60}")

                                }

                                override fun onFinish() {

                                }
                            }
                            timer.start()
                        }

                        PercentageCircleAnimation(
                            value = timeInMillisUntilFinished.toInt(),
                            maxValue = TIME_IN_MILLIS.toInt()
                        )
                        Text("$minutes:$seconds")



                        StopwatchAnimation()
                        AnimatedShimmer()
                    }
                }
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AnimacoesTheme {
        Surface(
            color = MaterialTheme.colors.surface,
            elevation = 5.dp
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LoadingAnimation()
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun DefaultDarkPreview() {
    AnimacoesTheme {
        Surface(
            color = MaterialTheme.colors.surface,
            elevation = 5.dp
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LoadingAnimation()
            }
        }
    }
}