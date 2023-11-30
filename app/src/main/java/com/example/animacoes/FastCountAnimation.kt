package com.example.animacoes

import android.content.res.Configuration
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Ease
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.animacoes.ui.theme.AnimacoesTheme
import kotlinx.coroutines.launch

@Composable
fun FastCountAnimation(
    targetCount: Float,
    format: String = "%.1f",
    textStyle: TextStyle = TextStyle(
        fontSize = 76.sp,
    ),
    onCount: (Float) -> Unit = {}
) {

    val coroutineScope = rememberCoroutineScope()

    val animatableX = remember { Animatable(0f) }

    LaunchedEffect(null) {
        animatableX.animateTo(
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = keyframes {
                    durationMillis = 300
                    0f at 0 with LinearEasing
                    1f at 150 with LinearEasing
                },
            ),
        )
    }

    val animatedCount by animateFloatAsState(
        targetValue = targetCount,
        label = "",
        animationSpec = tween(
            4000,
            easing = Ease
        ),
        finishedListener = {
            coroutineScope.launch {
                animatableX.stop()
            }
        }
    )

    LaunchedEffect(
        key1 = animatedCount,
        block = {
            onCount(animatedCount)
        }
    )

    Text(
        modifier = Modifier
            .offset(x =( animatableX.value * 3).dp),
        text = String.format(
            format,
            animatedCount
        ),
        style = textStyle,
        maxLines = 1,
        softWrap = false
    )
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun FastCountAnimationPreview() {
    AnimacoesTheme {
        Surface {
            var count by remember {
                mutableFloatStateOf(100f)
            }

            LaunchedEffect(
                key1 = count,
                block = {
                    while (count > 0) count--
                }
            )

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                FastCountAnimation(
                    targetCount = count
                )
            }
        }
    }
}