package com.example.animacoes

import android.util.Log
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.animacoes.ui.theme.AnimacoesTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

@ExperimentalAnimationApi
@Composable
fun StopwatchAnimation(mainViewModel: MainViewModel = viewModel()) {
    val seconds by mainViewModel.seconds.collectAsState(initial = "00")

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AnimatedContent(
            targetState = seconds,
            transitionSpec = {
                addAnimation().using(
                    SizeTransform(clip = true)
                )
            }
        ) { targetCount ->
            Text(
                text = "$targetCount",
                style = TextStyle(fontSize = MaterialTheme.typography.h1.fontSize),
                textAlign = TextAlign.Center
            )
        }
    }
}

@ExperimentalAnimationApi
fun addAnimation(duration: Int = 800): ContentTransform {
    return slideInVertically(
        animationSpec = tween(durationMillis = duration)
    ) { height -> height } + fadeIn(animationSpec = tween(durationMillis = duration)
    ) with slideOutVertically(
        animationSpec = tween(durationMillis = duration)
    ) { height -> -height } + fadeOut(animationSpec = tween(durationMillis = duration))
}

@OptIn(ExperimentalAnimationApi::class)
@Preview(showBackground = true)
@Composable
fun PreviewStopwatchAnimation() {
    AnimacoesTheme {
        StopwatchAnimation()
    }
}

class MainViewModel : ViewModel() {
    val seconds = (10 downTo 0)
        .asSequence()
        .asFlow()
        .map {
            if (it in 0..9) "0$it" else it
        }
        .onEach {
            delay(1000)
        }

}