package com.example.animacoes

import android.content.res.Configuration.UI_MODE_NIGHT_MASK
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.KeyboardOptions
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

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AnimacoesTheme {
                Surface(
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
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
                       AnimatedShimmer()
                        Slider(
                            modifier = Modifier.fillMaxSize(fraction = 0.8f),
                            value = value,
                            onValueChange = { value = it},
                            onValueChangeFinished = { println("terminei")},
                            valueRange = 0f..100f
                        )
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