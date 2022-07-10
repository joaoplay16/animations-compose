package com.example.animacoes

import android.content.res.Configuration.UI_MODE_NIGHT_MASK
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
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
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Slider
import androidx.compose.material.Surface
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.animacoes.ui.theme.AnimacoesTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext

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

                        val oneMinute = 60

                        var second by remember {
                            mutableStateOf(0)
                        }
                        LaunchedEffect(key1 = null,  ){
                          (oneMinute downTo 0)
                                .asSequence()
                                .asFlow()

                                .onEach {
                                }.collect{
                                    delay(1000)
                                Log.d("FLOW", "$it")
                                second = it as Int

                            }
                        }
                        PercentageCircleAnimation(
                            value = second,
                            maxValue = oneMinute
                        )

                        Slider(
                            modifier = Modifier.fillMaxSize(fraction = 0.8f),
                            value = value,
                            onValueChange = { value = it},
                            onValueChangeFinished = { println("terminei")},
                            valueRange = 0f..100f
                        )

                        AnimatedShimmer()
                        StopwatchAnimation()
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